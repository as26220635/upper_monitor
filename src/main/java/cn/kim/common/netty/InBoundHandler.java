package cn.kim.common.netty;

import cn.kim.entity.CardProtocol;
import cn.kim.entity.CardRequestProtocol;
import cn.kim.entity.CardStatusProtocol;
import cn.kim.service.EntranceGuardCardService;
import cn.kim.service.LogService;
import cn.kim.util.*;
import com.google.common.collect.Maps;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;

/**
 * Created by 余庚鑫 on 2018/7/29
 * TCP 连接
 */
@Component
public class InBoundHandler extends SimpleChannelInboundHandler<CardProtocol> {

    private static Logger logger = LogManager.getLogger(InBoundHandler.class.getName());

    @Autowired
    private EntranceGuardCardService entranceGuardCardService;
    private static InBoundHandler InBoundHandler;

    public void setEntranceGuardCardService(EntranceGuardCardService entranceGuardCardService) {
        this.entranceGuardCardService = entranceGuardCardService;
    }

    @PostConstruct
    public void init() {
        InBoundHandler = this;
        InBoundHandler.entranceGuardCardService = this.entranceGuardCardService;
    }

    /**
     * 接入连接
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);

        logger.info(DateUtil.getDate() + "client:(" + getRemoteAddress(ctx) + ")接入连接");
        //往channel map中添加channel信息
        TCPServerNetty.getClientMap().put(getIPString(ctx), ctx.channel());

    }

    /**
     * 断开连接
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        //删除Channel Map中的失效Client
        logger.info(DateUtil.getDate() + ",client:(" + getRemoteAddress(ctx) + ")断开连接");
        TCPServerNetty.getClientMap().remove(getIPString(ctx));
        ctx.close();
    }


    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt)
            throws Exception {
        String socketString = ctx.channel().remoteAddress().toString();

        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {
                logger.info("Client: " + socketString + " READER_IDLE 读超时");
                ctx.disconnect();
            } else if (event.state() == IdleState.WRITER_IDLE) {
                logger.info("Client: " + socketString + " WRITER_IDLE 写超时");
                ctx.disconnect();
            } else if (event.state() == IdleState.ALL_IDLE) {
                logger.info("Client: " + socketString + " ALL_IDLE 总超时");
                ctx.disconnect();
            }
        }
    }

    /**
     * 处理上位机包
     *
     * @param ctx
     * @param card
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CardProtocol card) throws Exception {
        //判断指令

        switch (card.getCommand()) {
            //心跳
            case TCPServerNetty.HEART_BEAT: {
                logger.info(DateUtil.getDate() + ",client:(" + getRemoteAddress(ctx) + ")心跳数据");
                //是否存在
                Map<String, Channel> clientMap = TCPServerNetty.getClientMap();
                String ip = getIPString(ctx);
                //不存在就放入map
                if (clientMap.containsKey(ip)) {
                    clientMap.put(ip, ctx.channel());
                }
                //解析心跳
                CardStatusProtocol status = parseStatusData(card);

                float t1 = status.getT1();
                float t2 = status.getT2();
                float h1 = status.getH1();
                float h2 = status.getH2();

                if (!ValidateUtil.isEmpty(t1) && !Float.isNaN(t1)) {
                    t1 = t1 / 10;
                }
                if (!ValidateUtil.isEmpty(t2) && !Float.isNaN(t2)) {
                    t2 = t2 / 10;
                }
                if (!ValidateUtil.isEmpty(h1) && !Float.isNaN(h1)) {
                    h1 = h1 / 10;
                }
                if (!ValidateUtil.isEmpty(h2) && !Float.isNaN(h2)) {
                    h2 = h2 / 10;
                }
                Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(16);
                paramMap.put("BEGC_SERIAL", status.getSerial());
                paramMap.put("BEGC_ID", status.getId());
                paramMap.put("BEGC_STATUS", status.getDoorStatus());
                paramMap.put("BEGC_NOW", status.getDate());
                paramMap.put("BEGC_T1", t1);
                paramMap.put("BEGC_H1", h1);
                paramMap.put("BEGC_T2", t2);
                paramMap.put("BEGC_H2", h2);
                paramMap.put("BEGC_VER", status.getVer());
                paramMap.put("BEGC_NEXT_NUM", status.getNextNum());
                //获取设备所在的公网IP
                paramMap.put("BEGC_IP", getIPString(ctx));
                //更新门禁卡信息和插入心跳日志
                InBoundHandler.entranceGuardCardService.updateEntranceGuardCard(paramMap);

                //返回数据
                ctx.writeAndFlush(status);

            }
            break;
            //刷卡
            case TCPServerNetty.REQUEST_CARD: {
                CardRequestProtocol status = parseRequestData(card);

                //返回数据
                ctx.writeAndFlush(status);
            }
            break;
            default:
                break;
        }

        ReferenceCountUtil.release(card);
    }


    public static String getIPString(ChannelHandlerContext ctx) {
        String ipString = "";
        String socketString = ctx.channel().remoteAddress().toString();
        int colonAt = socketString.indexOf(":");
        ipString = socketString.substring(1, colonAt);
        return ipString;
    }


    public static String getRemoteAddress(ChannelHandlerContext ctx) {
        String socketString = "";
        socketString = ctx.channel().remoteAddress().toString();
        return socketString;
    }

    /**
     * 16进制转换
     *
     * @param s
     * @return
     */
    public int hexConvert16(byte s) {
        return (s & 0xfc) | ((~s) & 0x03);
    }

    /**
     * 解析心跳数据
     *
     * @param card
     * @return
     */
    public CardStatusProtocol parseStatusData(CardProtocol card) {
        CardStatusProtocol statusProtocol = new CardStatusProtocol();
        statusProtocol.setStx(card.getStx());
        statusProtocol.setRand(card.getRand());
        statusProtocol.setCommand(card.getCommand());
        statusProtocol.setAddress(card.getAddress());
        statusProtocol.setDoor(card.getDoor());
        statusProtocol.setLengthL(card.getLengthL());
        statusProtocol.setLengthH(card.getLengthH());
        //起始位置
        int startIndex = 0;
        //解析包数据
        byte[] data = card.getData();
        //—	1	备用
        statusProtocol.setN1(data[startIndex++]);
        //时间	6	控制器的时钟，从低到高：年月日时分秒，年需要加2000
        byte[] time = new byte[6];
        System.arraycopy(data, 1, time, 0, 6);
        statusProtocol.setTime(time);
        //格式化时间
        int second = time[5];
        int minute = time[4];
        int hour = time[3];
        int day = time[2];
        int month = time[1];
        int year = time[0] + 2000;
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day, hour, minute, second);
        statusProtocol.setDate(DateUtil.getDate(DateUtil.FORMAT, calendar.getTime()));
        startIndex += 6;
        //门状态	1	Bit5 出是否被锁；Bit4 进是否被锁；
        //Bit1 出的状态(门磁和锁输出都为关则为关)；
        //Bit0 进的状态(门磁和锁输出都为关则为关)；
        byte s = data[startIndex++];
        statusProtocol.setDoorStatus(TextUtil.toInt16(s));
        //—	1	备用
        statusProtocol.setN2(TextUtil.toInt16(data[startIndex++]));
        //方向控制	1	Bit1 出被锁定
        //Bit0 进被锁定
        statusProtocol.setDirPass(TextUtil.toInt16(data[startIndex++]));
        //—	1	备用
        statusProtocol.setN3(TextUtil.toInt16(data[startIndex++]));
        //型号	1	1 单门
        statusProtocol.setControlType(TextUtil.toInt16(data[startIndex++]));
        //继电器状态	1	Bit2 报警继电器状态
        //Bit1 出继电器状态
        //Bit0 进继电器状态
        statusProtocol.setRelayOut(TextUtil.toInt16(data[startIndex++]));
        //输出	2	全部输出端口的状态
        byte[] outPut = new byte[2];
        System.arraycopy(data, startIndex, outPut, 0, 2);
        statusProtocol.setOutput(outPut);
        startIndex += 2;
        //备用	3
        byte[] spare = new byte[3];
        System.arraycopy(data, startIndex, spare, 0, 3);
        statusProtocol.setSpare(spare);
        startIndex += 3;
        //版本	1	控制器固件的版本
        statusProtocol.setVer(TextUtil.toInt16(data[startIndex++]));
        //OEM代码	2	整数值（可选功能）
        byte[] oem = new byte[2];
        System.arraycopy(data, startIndex, oem, 0, 2);
        statusProtocol.setOemCode(oem[0] + (oem[1] << 8));
        startIndex += 2;
        //序列号	6	控制器的序列号
        byte[] serial = new byte[6];
        System.arraycopy(data, startIndex, serial, 0, 6);
        statusProtocol.setSerial(TextUtil.parsePackParam(serial));
        startIndex += 6;
        //输入端口	2	Bit11 复位跳线
        //Bit10 防拆
        //Bit6-9 扩展输入1-4
        //Bit5  火警输入
        //Bit4  报警输入
        //Bit3  门磁(出)
        //Bit2  门磁(进)
        //Bit1  出按钮
        //Bit0  进按钮
        byte[] input = new byte[2];
        System.arraycopy(data, startIndex, input, 0, 2);
        statusProtocol.setInput(input);
        startIndex += 2;
        //标识	10	在控制器的web界面可以修改，默认为序列号
        byte[] id = new byte[10];
        System.arraycopy(data, startIndex, id, 0, 10);
        statusProtocol.setId(TextUtil.parsePackParam(id));
        startIndex += 10;
        //温度	2	2组温度，浮点值，带一位小数
        statusProtocol.setT1(TextUtil.toInt16(data[startIndex++]));
        statusProtocol.setT2(TextUtil.toInt16(data[startIndex++]));
        //湿度	2	2组湿度，浮点值，带一位小数
        statusProtocol.setH1(TextUtil.toInt16(data[startIndex++]));
        statusProtocol.setH2(TextUtil.toInt16(data[startIndex++]));
        //湿度	2	2组湿度，浮点值，带一位小数
        byte[] version = new byte[4];
        System.arraycopy(data, startIndex, version, 0, 4);
        statusProtocol.setVersion(TextUtil.parsePackParam(version));
        startIndex += 4;
        //剩余人数	1	备用功能，剩余通过人数
        statusProtocol.setNextNum(TextUtil.toInt16(data[startIndex++]));

        return statusProtocol;
    }

    /**
     * 刷卡请求
     *
     * @param card
     * @return
     */
    public CardRequestProtocol parseRequestData(CardProtocol card) {
        CardRequestProtocol request = new CardRequestProtocol();
        request.setStx(card.getStx());
        request.setRand(card.getRand());
        request.setCommand(card.getCommand());
        request.setAddress(card.getAddress());
        request.setDoor(card.getDoor());
        request.setLengthL(00);
        request.setLengthH(00);
        //起始位置
        int startIndex = 0;
        //解析包数据
        byte[] data = card.getData();
        //序列号	6	控制器的序列号
        byte[] serial = new byte[6];
        System.arraycopy(data, startIndex, serial, 0, 6);
        request.setSerial(TextUtil.parsePackParam(serial));
        startIndex += 6;
        //标识	10	Web界面上修改的标识，默认为控制器序列号
        byte[] id = new byte[10];
        System.arraycopy(data, startIndex, id, 0, 10);
        request.setId(TextUtil.parsePackParam(id));
        startIndex += 10;
        //读头	1	数据读头来源，0-3,0=232或者Read A，1=232出或者Reader B，2=485A，3=485B
        request.setReadHead(TextUtil.toInt16(data[startIndex++]));
        //—	5	备用
        byte[] remark = new byte[5];
        System.arraycopy(data, startIndex, remark, 0, 5);
        request.setN1(remark);
        startIndex += 5;
        //数据类型	1	0=普通卡  1=串口232接口输入字符串,如二维码等
        //2=密码    3=按钮
        //6=二代证数据 数据结构见后面身份证数据结构。
        //9=Base64数据，来自串口二维码编码后
        //10=指纹数据   11=指静脉数据
        //12=RFID       13=人脸
        request.setType(TextUtil.toInt16(data[startIndex++]));
        //系统时间	16	前7个有效，年月日时分秒周
        byte[] time = new byte[16];
        System.arraycopy(data, startIndex, time, 0, 16);
        request.setTime(time);
        startIndex += 16;
        //卡号	4	4字节卡号
        byte[] cardNumber = new byte[4];
        System.arraycopy(data, startIndex, cardNumber, 0, 4);
        request.setCard(TextUtil.parsePackParam(cardNumber));
        startIndex += 4;
        //数据	X	传输的数据，卡号、二维码、身份证、静脉、指纹数据等，变化值，不超过2048
        byte[] cardData = new byte[data.length - startIndex];
        System.arraycopy(data, startIndex, cardData, 0, data.length - startIndex);
        request.setData(TextUtil.parsePackParam(cardData));

        return request;
    }

}
package cn.kim.common.netty;

import cn.kim.entity.CardProtocol;
import cn.kim.entity.CardStatusProtocol;
import cn.kim.util.DateUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.ReferenceCountUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.UnsupportedEncodingException;

/**
 * Created by 余庚鑫 on 2018/7/29
 * TCP 连接
 */
public class InBoundHandler extends SimpleChannelInboundHandler<byte[]> {

    private static Logger logger = LogManager.getLogger(InBoundHandler.class.getName());

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
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        CardProtocol card = (CardProtocol) msg;
        //判断指令
        if (card.getCommand() == 0X56) {
            //心跳
            logger.info(DateUtil.getDate() + ",client:(" + getRemoteAddress(ctx) + ")心跳数据");
            //解析心跳
            CardStatusProtocol status = parseStatusData(card);

        } else if (card.getCommand() == 0X53) {
            //刷卡
        }
        ReferenceCountUtil.release(msg);
    }

    /**
     * 处理上位机包
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, byte[] msg) throws Exception {
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
        startIndex += 6;
        //门状态	1	Bit5 出是否被锁；Bit4 进是否被锁；
        //Bit1 出的状态(门磁和锁输出都为关则为关)；
        //Bit0 进的状态(门磁和锁输出都为关则为关)；
        statusProtocol.setDoorStatus(hexConvert16(data[startIndex++]));
        //—	1	备用
        statusProtocol.setN2(data[startIndex++]);
        //方向控制	1	Bit1 出被锁定
        //Bit0 进被锁定
        statusProtocol.setDirPass(hexConvert16(data[startIndex++]));

        int second = hexConvert16(time[5]);
        int minute = hexConvert16(time[4]);
        int hour = hexConvert16(time[3]);
        int day = hexConvert16(time[2]);
        int month = hexConvert16(time[1]);
        int year = hexConvert16(time[0]) + 2000;

        return statusProtocol;
    }

}
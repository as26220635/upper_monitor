package cn.kim.common.netty;

import cn.kim.util.DateUtil;
import cn.kim.util.TextUtil;
import com.sun.xml.internal.fastinfoset.stax.events.Util;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
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
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);

        logger.info(DateUtil.getDate()+"client:("+getRemoteAddress(ctx)+")接入连接");
        //往channel map中添加channel信息
        TCPServerNetty.getClientMap().put(getIPString(ctx), ctx.channel());

    }

    /**
     * 断开连接
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        //删除Channel Map中的失效Client
        logger.info(DateUtil.getDate()+",client:("+getRemoteAddress(ctx)+")断开连接");
        TCPServerNetty.getClientMap().remove(getIPString(ctx));
        ctx.close();
    }

    /**
     * 处理上位机包
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, byte[] msg) throws Exception {
        String recieved = TextUtil.toString(msg);
        logger.info("来自设备的信息："+ TCPServerNetty.bytesToHexString(msg));
        System.out.println(recieved);
        ctx.writeAndFlush(TextUtil.toByte("test"));
    }

    /**
     * 从ByteBuf中获取信息 使用UTF-8编码返回
     * @param buf
     * @return
     */
    private String getMessage(ByteBuf buf) {


        byte[] con = new byte[buf.readableBytes()];
        buf.readBytes(con);
        try {
            return new String(con, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 发送消息转为ByteBuf
     * @param message
     * @return
     * @throws UnsupportedEncodingException
     */
    private ByteBuf getSendByteBuf(String message)
            throws UnsupportedEncodingException {


        byte[] req = message.getBytes("UTF-8");
        ByteBuf pingMessage = Unpooled.buffer();
        pingMessage.writeBytes(req);


        return pingMessage;
    }


    public static String getIPString(ChannelHandlerContext ctx){
        String ipString = "";
        String socketString = ctx.channel().remoteAddress().toString();
        int colonAt = socketString.indexOf(":");
        ipString = socketString.substring(1, colonAt);
        return ipString;
    }


    public static String getRemoteAddress(ChannelHandlerContext ctx){
        String socketString = "";
        socketString = ctx.channel().remoteAddress().toString();
        return socketString;
    }


    private String getKeyFromArray(byte[] addressDomain) {
        StringBuffer sBuffer = new StringBuffer();
        for(int i=0;i<5;i++){
            sBuffer.append(addressDomain[i]);
        }
        return sBuffer.toString();
    }

    protected String to8BitString(String binaryString) {
        int len = binaryString.length();
        for (int i = 0; i < 8-len; i++) {
            binaryString = "0"+binaryString;
        }
        return binaryString;
    }

    protected static byte[] combine2Byte(byte[] bt1, byte[] bt2){
        byte[] byteResult = new byte[bt1.length+bt2.length];
        System.arraycopy(bt1, 0, byteResult, 0, bt1.length);
        System.arraycopy(bt2, 0, byteResult, bt1.length, bt2.length);
        return byteResult;
    }

}
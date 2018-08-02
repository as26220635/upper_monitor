package cn.kim.common.netty;

import cn.kim.entity.CardRequestProtocol;
import cn.kim.entity.CardStatusProtocol;
import cn.kim.entity.TCPSendMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by 余庚鑫 on 2018/7/30
 */
public class OutBoundHandler extends ChannelOutboundHandlerAdapter {

    private static Logger logger = LogManager.getLogger(OutBoundHandler.class.getName());

    @Override
    public void write(ChannelHandlerContext ctx, Object msg,
                      ChannelPromise promise) throws Exception {

        if (msg instanceof CardStatusProtocol) {
            //心跳返回
            CardStatusProtocol status = (CardStatusProtocol) msg;
            byte[] resultBytes = new byte[2];
            //客户代码高位
            resultBytes[0] = (byte) (status.getOemCode() >> 8);
            //客户代码低位
            resultBytes[1] = (byte) (status.getOemCode() & 0xFF);
            ctx.writeAndFlush(getSendByteBuf(TCPServerNetty.stickyPack(status.getCommand(), status.getAddress(), status.getDoor(), resultBytes)));
        } else if (msg instanceof CardRequestProtocol) {
            //刷卡返回
            CardRequestProtocol request = (CardRequestProtocol) msg;
            byte[] resultBytes = new byte[133];
            //是否开门
            resultBytes[0] = 1;
            //继电器	1	输出的继电器编号，从0开始。0进门、1出门、2报警。
            resultBytes[1] = 0;
            //开门时间	2	输出继电器动作的时间，秒为单位。
            resultBytes[2] = 5;
            resultBytes[3] = 0;
            resultBytes[4] = (byte) request.getReadHead();
            resultBytes[5] = 5;
            for (int i = 5; i < resultBytes.length; i++) {
                resultBytes[i] = 00;
            }
            ctx.writeAndFlush(getSendByteBuf(TCPServerNetty.stickyPack(request.getCommand(), request.getAddress(), request.getDoor(), resultBytes)));
        } else if (msg instanceof TCPSendMessage) {
            TCPSendMessage message = (TCPSendMessage) msg;
            ctx.writeAndFlush(getSendByteBuf(message.getData())).addListener((ChannelFutureListener) future -> {
                //是否成功
                message.setSuccess(future.isSuccess());
                //释放同步锁
                message.getCountDownLatch().countDown();
            });
        }
    }

    private ByteBuf getSendByteBuf(byte[] req) {
        ByteBuf pingMessage = Unpooled.buffer();
        pingMessage.writeBytes(req);

        System.out.println(bytesToHexString(req));
        return pingMessage;
    }

    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
            stringBuilder.append(' ');
        }
        return stringBuilder.toString();
    }
}

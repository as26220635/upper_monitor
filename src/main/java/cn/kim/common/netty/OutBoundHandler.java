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
            CardStatusProtocol status = (CardStatusProtocol) msg;
            //心跳放回数据
            byte[] resultBytes = new byte[2];
            //客户代码高位
            resultBytes[0] = (byte) status.getLengthH();
            //客户代码低位
            resultBytes[1] = (byte) status.getLengthL();
            ctx.writeAndFlush(getSendByteBuf(TCPServerNetty.stickyPack(status.getCommand(), status.getAddress(), status.getDoor(), resultBytes)));
        } else if (msg instanceof CardRequestProtocol) {
            CardRequestProtocol request = (CardRequestProtocol) msg;
            //心跳放回数据
            byte[] resultBytes = new byte[133];
            //是否开门
            resultBytes[0] = 1;
            //继电器	1	输出的继电器编号，从0开始。0进门、1出门、2报警。
            resultBytes[1] = 0;
            //开门时间	2	输出继电器动作的时间，秒为单位。
            resultBytes[2] = 05;
            resultBytes[3] = 00;
            resultBytes[4] = (byte) request.getReadHead();
            resultBytes[5] = 05;
            for (int i = 5; i < resultBytes.length; i++) {
                resultBytes[i] = 00;
            }
            ctx.writeAndFlush(getSendByteBuf(TCPServerNetty.stickyPack(request.getCommand(), request.getAddress(), request.getDoor(), resultBytes)));
        } else if (msg instanceof TCPSendMessage) {
            TCPSendMessage message = (TCPSendMessage) msg;
            ctx.writeAndFlush(getSendByteBuf(message.getData())).addListener((ChannelFutureListener) future -> {
                message.setSuccess(true);
            });
        }
    }

    private ByteBuf getSendByteBuf(byte[] req) {
        ByteBuf pingMessage = Unpooled.buffer();
        pingMessage.writeBytes(req);
        return pingMessage;
    }
}

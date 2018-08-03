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
            int byteIndex = 0;
            byte[] resultBytes = new byte[133];
            //是否开门	1	是否通过验证，通过则开锁。1开，0不开
            resultBytes[byteIndex++] = (byte) request.getIsOpen();
            //继电器	1	输出的继电器编号，从0开始。0进门、1出门、2报警。
            resultBytes[byteIndex++] = (byte) request.getDoor();
            //开门时间	2	输出继电器动作的时间，秒为单位。
            resultBytes[byteIndex++] = (byte) request.getOpenTime();
            resultBytes[byteIndex++] = (byte) (request.getOpenTime() >> 8);
            //读头	1	读头，0进门 1出门。
            resultBytes[byteIndex++] = (byte) request.getReader();
            //保持时间	1	显示保持多少秒后显示默认首页。为0则不切换。
            resultBytes[byteIndex++] = (byte) request.getDelay();
            //卡号值类型	1	1表示下面的卡号为4字节整数，0则为字符串。建议为0
            resultBytes[byteIndex++] = (byte) request.getCardIsDWord();
            //卡号	18	用于显示卡号字符串，为0表示无。
            System.arraycopy(request.getReturnCard(), 0, resultBytes, byteIndex, 18);
            byteIndex += 18;
            //声音	40	用于语音播报的声音字符串，为0表示无。
            System.arraycopy(request.getVoice(), 0, resultBytes, byteIndex, 40);
            byteIndex += 40;
            //姓名	16	用于显示的姓名字符串，为0表示无。
            System.arraycopy(request.getName(), 0, resultBytes, byteIndex, 16);
            byteIndex += 16;
            //事件	32	用于显示的事件字符串，为0表示无。
            System.arraycopy(request.getNote(), 0, resultBytes, byteIndex, 32);
            byteIndex += 32;
            // 时间	20	用于显示的时间字符串，为0表示无。
            System.arraycopy(request.getReturnTime(), 0, resultBytes, byteIndex, 20);

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

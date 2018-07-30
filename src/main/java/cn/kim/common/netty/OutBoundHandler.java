package cn.kim.common.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by 余庚鑫 on 2018/7/30
 */
public class OutBoundHandler extends ChannelOutboundHandlerAdapter {

    private static Logger logger = LogManager.getLogger(OutBoundHandler.class.getName());

    @Override
    public void write(ChannelHandlerContext ctx, Object msg,
                      ChannelPromise promise) throws Exception {

        if (msg instanceof byte[]) {
            byte[] bytesWrite = (byte[])msg;
            ByteBuf buf = ctx.alloc().buffer(bytesWrite.length);
            logger.info("向设备下发的信息为："+TCPServerNetty.bytesToHexString(bytesWrite));

            buf.writeBytes(bytesWrite);
            ctx.writeAndFlush(buf).addListener((ChannelFutureListener) future -> logger.info("下发成功！"));
        }
    }
}

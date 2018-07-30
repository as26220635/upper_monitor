package cn.kim.common.netty;

import cn.kim.entity.CardProtocol;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created by 余庚鑫 on 2018/7/30
 */
public class SmartCarEncoder extends MessageToByteEncoder<CardProtocol> {

    @Override
    protected void encode(ChannelHandlerContext tcx, CardProtocol msg,
                          ByteBuf out) throws Exception {
        // 写入消息SmartCar的具体内容
        // 1.写入消息的开头的信息标志(int类型)
//        out.writeInt(msg.getHead_data());
//        // 2.写入消息的长度(int 类型)
//        out.writeInt(msg.getContentLength());
//        // 3.写入消息的内容(byte[]类型)
//        out.writeBytes(msg.getContent());
//        // 写入尾消息
//        out.writeInt(msg.getEnd_data());
    }
}

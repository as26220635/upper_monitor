package cn.kim.common.netty;

import cn.kim.entity.CardProtocol;
import cn.kim.util.TextUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Created by 余庚鑫 on 2018/7/30
 */
public class SmartCarDecoder extends ByteToMessageDecoder {

    /**
     * <pre>
     * 协议开始的标准head_data，int类型，占据1个字节.
     * 包头7位 包尾2位
     * </pre>
     */
    public final int START_LENGTH = 7;
    public final int END_LENGTH = 2;
    public final int BASE_LENGTH = START_LENGTH + END_LENGTH;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buffer,
                          List<Object> out) throws Exception {
        // 可读长度必须大于基本长度
        if (buffer.readableBytes() >= BASE_LENGTH) {

            int packgeLength = buffer.readableBytes();

            boolean isStart = false;
            boolean isEnd = false;

            byte[] req = new byte[buffer.readableBytes()];
            buffer.readBytes(req);
            for (byte b : req) {
                if (b == 0X02) {
                    isStart = true;
                    continue;
                }
                if (b == 0X03) {
                    isEnd = true;
                    break;
                }
            }
            //没有找到开头和结尾
            if (!isStart || !isEnd) {
                return;
            }

            byte[] data = new byte[packgeLength - BASE_LENGTH];

            System.arraycopy(req, START_LENGTH, data, 0, packgeLength - BASE_LENGTH);
            CardProtocol cardProtocol = new CardProtocol(packgeLength - BASE_LENGTH, data);
            cardProtocol.setStx(TextUtil.toInt16(req[0]));
            cardProtocol.setRand(TextUtil.toInt16(req[1]));
            cardProtocol.setCommand(TextUtil.toInt16(req[2]));
            cardProtocol.setAddress(TextUtil.toInt16(req[3]));
            cardProtocol.setDoor(TextUtil.toInt16(req[4]));
            cardProtocol.setLengthL(TextUtil.toInt16(req[5]));
            cardProtocol.setLengthH(TextUtil.toInt16(req[6]));
            cardProtocol.setCs(TextUtil.toInt(req[cardProtocol.getLengthH() + BASE_LENGTH - END_LENGTH]));
            cardProtocol.setEtx(TextUtil.toInt(req[cardProtocol.getLengthH() + BASE_LENGTH - END_LENGTH + 1]));

            //异或校验
            byte checkByte = req[0];
            for (int i = 1; i < req.length - END_LENGTH; i++) {
                checkByte ^= req[i];
            }
            //校验不通过
            if (checkByte != cardProtocol.getCs()) {
                return;
            }
            out.add(cardProtocol);
        }
    }
}

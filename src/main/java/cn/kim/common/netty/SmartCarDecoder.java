package cn.kim.common.netty;

import cn.kim.common.attr.Constants;
import cn.kim.util.TextUtil;
import com.sun.deploy.uitoolkit.impl.fx.Utils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.aspectj.apache.bcel.classfile.ConstantValue;
import org.bouncycastle.pqc.math.linearalgebra.ByteUtils;

import javax.smartcardio.Card;
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


            byte[] req = new byte[buffer.readableBytes()];
            buffer.readBytes(req);

            byte[] data = new byte[packgeLength - BASE_LENGTH];

            System.arraycopy(req, START_LENGTH, data, 0, packgeLength - BASE_LENGTH);
            CardProtocol cardProtocol = new CardProtocol(packgeLength - BASE_LENGTH, data);
            cardProtocol.setStx(req[0]);
            cardProtocol.setRand(req[1]);
            cardProtocol.setCommand(req[2]);
            cardProtocol.setAddress(req[3]);
            cardProtocol.setDoor(req[4]);
            cardProtocol.setLengthL(req[5]);
            cardProtocol.setLengthH(req[6]);
            System.out.println(req[packgeLength - END_LENGTH] == 0xA3);
            cardProtocol.setCs(req[packgeLength - END_LENGTH]);
            cardProtocol.setEtx(req[packgeLength - END_LENGTH + 1]);
            out.add(cardProtocol);
        }
    }

    /**
     * 16进制转换
     *
     * @return
     */
    public String hexConvert16(int s) {
        return TextUtil.toString((s & 0xfc) | ((~s) & 0x03));
    }
}

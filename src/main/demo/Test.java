import cn.kim.common.attr.ConfigProperties;
import cn.kim.util.*;
import com.google.common.collect.Maps;
import org.apache.commons.codec.DecoderException;
import org.springframework.util.Base64Utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Security;
import java.util.Map;

/**
 * Created by 余庚鑫 on 2018/7/26
 */
public class Test {
    public static void main(String[] args) throws Exception {
//        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
//        System.out.println(AESUtil.decrypt("InXDNDnoG2qNwYIMqE4Bb5TKU7fdErfVYWVu444acfQ5bKQL+lBslDwp3Jp5XCnPOxI+ePpJpSgA3FV53GNqYYNzeOohQuZiEOilmnhbZU4G1cqF0RqD6+kGKT2AlnIJPLxgKXSe1ps9jadGEMxgYKnbZau9j/N8G0Oc9h83PAjA0ZdcXMeugN2bgHLIFoKmk6qGT+4Mc+QlshFaczyIMw==", "abcdefgh20161234", false));
//        int a = Integer.parseInt("02",16);
//        System.out.println((a & 0xfc) | ((~a) & 0x03));
//        System.out.println(TextUtil.base64Decrypt("ODIwNDM0MjE3NDQwMDIwNA"));
        System.out.println("00 09 08 18 0C 07 0A 00 00 00 00 00 00 00 00 00 00 00 50 00 00".replaceAll(" ",""));
    }

    public int Byte2Int(Byte[]bytes) {
        return (bytes[0]&0xff)<<24
                | (bytes[1]&0xff)<<16
                | (bytes[2]&0xff)<<8
                | (bytes[3]&0xff);
    }

    public static String asciiToString(String value) {
        StringBuffer sbu = new StringBuffer();
        String[] chars = value.split(",");
        for (int i = 0; i < chars.length; i++) {
            sbu.append((char) Integer.parseInt(chars[i]));
        }
        return sbu.toString();
    }
}

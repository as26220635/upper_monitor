import cn.kim.common.attr.ConfigProperties;
import cn.kim.util.AESUtil;
import cn.kim.util.HttpClient;
import cn.kim.util.HttpUtil;
import com.google.common.collect.Maps;
import org.apache.commons.codec.DecoderException;
import org.springframework.util.Base64Utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.util.Map;

/**
 * Created by 余庚鑫 on 2018/7/26
 */
public class Test {
    public static void main(String[] args) throws Exception {

        System.out.println(AESUtil.decrypt("E53506D31C0C10BFDEC82B4B0D2E5230F45BBAC7BC4898B68A731379BCD8BF959E61CCE521D0E37C11D0554F12419603964DA26A2E553631447A1F3239C7A576271267249505262B4C191A5009E906AB","abcdefgh20161234"));
    }

    public static String asciiToString(String value)
    {
        StringBuffer sbu = new StringBuffer();
        String[] chars = value.split(",");
        for (int i = 0; i < chars.length; i++) {
            sbu.append((char) Integer.parseInt(chars[i]));
        }
        return sbu.toString();
    }
}

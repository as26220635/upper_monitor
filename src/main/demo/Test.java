import cn.kim.common.attr.ConfigProperties;
import cn.kim.util.AESUtil;
import cn.kim.util.DateUtil;
import cn.kim.util.HttpClient;
import cn.kim.util.HttpUtil;
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
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        System.out.println(AESUtil.decrypt("2B6D5851B9D6B67ACE56FB3319BA6EE7B7D865049851587D4F56CB62D9BF605D8CD429C9874CF86119066087FC76E1D14AC37098352183F12B7E12261BCABD69F16B4B4EC679A1AEECFA7433E031B6889BBC36C45BBC12F824311AAC354E0A34B2C976834AB4377D09481461D93D06F17429FF1068C19A0898A65732CD9F60A808BAA1455FBAFF275A4413BC55AC4D49E6DE47441C8C3C633B2ECBC1EDB29979ADC1075CE3FBD3A7906D27DF8C0FF412C3F4D8A45DFC8C51BE569F4918DD55F749431AC6D4185D3999D7292260921C41B11960DF546F59C363443B3E570567F3F83302B80B8BA91A3B2675F899C5A3E9","abcdefgh20161234"));
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

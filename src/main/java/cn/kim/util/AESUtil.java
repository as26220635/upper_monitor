package cn.kim.util;

import cn.kim.common.attr.ConfigProperties;
import cn.kim.common.attr.ConfigProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.util.HtmlUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Created by 余庚鑫 on 2017/11/1.
 */
@Component
public class AESUtil {

    /**
     * 将二进制转换成16进制
     *
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }


    /**
     * 将16进制转换为二进制
     *
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1) {
            return null;
        }
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    // 加密
    public static String encrypt(String sSrc, String sKey) throws Exception {
        if (sKey == null) {
            System.out.print("Key为空null");
            return null;
        }
        // 判断Key是否为16位
        if (sKey.length() != 16) {
            System.out.print("Key长度不是16位");
            return null;
        }
        byte[] raw = sKey.getBytes("utf-8");
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        //"算法/模式/补码方式"
        Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));

        ////此处使用BASE64做转码功能，同时能起到2次加密的作用。
        return parseByte2HexStr(encrypted);
    }

    // 解密
    public static String decrypt(String sSrc, String sKey) throws Exception {
        try {
            // 判断Key是否正确
            if (sKey == null) {
                System.out.print("Key为空null");
                return null;
            }
            // 判断Key是否为16位
            if (sKey.length() != 16) {
                System.out.print("Key长度不是16位");
                return null;
            }
            byte[] raw = sKey.getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);


            try {
                byte[] original = cipher.doFinal(parseHexStr2Byte(sSrc));
                String originalString = new String(original, "utf-8");
                return originalString;
            } catch (Exception e) {
                System.out.println(e.toString());
                return null;
            }
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * 加密
     * 1.构造密钥生成器
     * 2.根据ecnodeRules规则初始化密钥生成器
     * 3.产生密钥
     * 4.创建和初始化密码器
     * 5.内容加密
     * 6.返回字符串
     *
     * @param content
     * @return
     * @throws InvalidKeyException
     */
    public static String encode(Object content) throws InvalidKeyException {
        //生成一个128位的随机源,根据传入的字节数组
        String aesKey = ConfigProperties.JWT_SECRET;
        //根据sessionID作为key
        if (!ValidateUtil.isEmpty(SessionUtil.getSession())) {
            aesKey = TextUtil.toString(SessionUtil.getSession().getId());
        }
        return encode(content, aesKey);
    }

    public static String encode(Object content, String aesKey) throws InvalidKeyException {
        try {
            //1.构造密钥生成器，指定为AES算法,不区分大小写
            KeyGenerator keygen = KeyGenerator.getInstance("AES");
            //2.根据ecnodeRules规则初始化密钥生成器
            keygen.init(128, new SecureRandom(aesKey.getBytes()));
            //3.产生原始对称密钥
            SecretKey originalKey = keygen.generateKey();
            //4.获得原始对称密钥的字节数组
            byte[] raw = originalKey.getEncoded();
            //5.根据字节数组生成AES密钥
            SecretKey key = new SecretKeySpec(raw, "AES");
            //6.根据指定算法AES自成密码器
            Cipher cipher = Cipher.getInstance("AES");
            //7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密解密(Decrypt_mode)操作，第二个参数为使用的KEY
            cipher.init(Cipher.ENCRYPT_MODE, key);
            //8.获取加密内容的字节数组(这里要设置为utf-8)不然内容中如果有中文和英文混合中文就会解密为乱码
            byte[] byteEncode = content.toString().getBytes("utf-8");
            //9.根据密码器的初始化方式--加密：将数据加密
            byte[] byteAES = cipher.doFinal(byteEncode);
            //10.将加密后的数据转换为字符串
            //这里用Base64Encoder中会找不到包
            //解决办法：
            //在项目的Build path中先移除JRE System Library，再添加库JRE System Library，重新编译后就一切正常了。
            String encode = new String(new BASE64Encoder().encode(byteAES));
            //11.将字符串返回
            return filter(encode);
        } catch (Exception e) {
            throw new InvalidKeyException("无效的KEY");
        }
    }

    /**
     * 解密
     * 解密过程：
     * 1.同加密1-4步
     * 2.将加密后的字符串反纺成byte[]数组
     * 3.将加密内容解密
     *
     * @param content
     * @return
     * @throws InvalidKeyException
     */
    public static String dncode(String content) throws InvalidKeyException {
        String aesKey = ConfigProperties.JWT_SECRET;
        //根据sessionID作为key
        if (!ValidateUtil.isEmpty(SessionUtil.getSession())) {
            aesKey = TextUtil.toString(SessionUtil.getSession().getId());
        }
        return dncode(content, aesKey);
    }

    public static String dncode(String content, String aesKey) throws InvalidKeyException {
        try {
            //1.构造密钥生成器，指定为AES算法,不区分大小写
            KeyGenerator keygen = KeyGenerator.getInstance("AES");
            //2.根据ecnodeRules规则初始化密钥生成器
            //生成一个128位的随机源,根据传入的字节数组
            keygen.init(128, new SecureRandom(aesKey.getBytes()));
            //3.产生原始对称密钥
            SecretKey originalKey = keygen.generateKey();
            //4.获得原始对称密钥的字节数组
            byte[] raw = originalKey.getEncoded();
            //5.根据字节数组生成AES密钥
            SecretKey key = new SecretKeySpec(raw, "AES");
            //6.根据指定算法AES自成密码器
            Cipher cipher = Cipher.getInstance("AES");
            //7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密(Decrypt_mode)操作，第二个参数为使用的KEY
            cipher.init(Cipher.DECRYPT_MODE, key);
            //8.将加密并编码后的内容解码成字节数组
            byte[] byteContent = new BASE64Decoder().decodeBuffer(reduction(content));
            /*
             * 解密
             */
            byte[] byteDecode = cipher.doFinal(byteContent);
            String decode = new String(byteDecode, "utf-8");
            return decode;
        } catch (Exception e) {
            throw new InvalidKeyException("无效的KEY");
        }
    }

    /**
     * 过滤特殊字符
     *
     * @param str
     * @return
     */
    private static String filter(String str) {
        String s = "SLASH";
        String s1 = "EQUAL";
        String s2 = "PLUS";
        return HtmlUtils.htmlEscape(str.replaceAll("/", s).replaceAll("=", s1).replaceAll("\\+", s2));
    }

    private static String reduction(String str) {
        String s = "SLASH";
        String s1 = "EQUAL";
        String s2 = "PLUS";
        return HtmlUtils.htmlUnescape(str.replaceAll(s, "/").replaceAll(s1, "=").replaceAll(s2, "\\+"));
    }

}

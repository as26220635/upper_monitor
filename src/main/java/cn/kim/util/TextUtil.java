package cn.kim.util;

import cn.kim.common.attr.Attribute;
import cn.kim.common.attr.MagicValue;
import com.alibaba.fastjson.JSON;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 余庚鑫 on 2017/5/28.
 */
public class TextUtil {
    public static final Pattern BLANK_PATTERN = Pattern.compile("\\s*|\t|\r|\n|\\u0000");


    // 将汉字转换为全拼
    public static String getPingYin(String src) {
        src = src.replaceAll("（", "(").replaceAll("）", ")");
        char[] t1 = null;
        t1 = src.toCharArray();
        String[] t2 = new String[t1.length];
        HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();
        t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        t3.setVCharType(HanyuPinyinVCharType.WITH_V);
        String t4 = "";
        int t0 = t1.length;
        try {
            for (int i = 0; i < t0; i++) {
                // 判断是否为汉字字符
                if (Character.toString(t1[i]).matches(
                        "[\\u4E00-\\u9FA5]+")) {
                    t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i], t3);
                    t4 += t2[0];
                } else {
                    t4 += Character.toString(t1[i]);
                }
            }
            // System.out.println(t4);
            return t4;
        } catch (BadHanyuPinyinOutputFormatCombination e1) {
            e1.printStackTrace();
        }
        return t4;
    }

    // 返回中文的首字母
    public static String getPinYinHeadChar(String str) {

        String convert = "";
        for (int j = 0; j < str.length(); j++) {
            char word = str.charAt(j);
            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
            if (pinyinArray != null) {
                convert += pinyinArray[0].charAt(0);
            } else {
                convert += word;
            }
        }
        return convert;
    }

    // 将字符串转移为ASCII码
    public static String getCnASCII(String cnStr) {
        StringBuffer strBuf = new StringBuffer();
        byte[] bGBK = cnStr.getBytes();
        for (int i = 0; i < bGBK.length; i++) {
            // System.out.println(Integer.toHexString(bGBK[i]&0xff));
            strBuf.append(Integer.toHexString(bGBK[i] & 0xff));
        }
        return strBuf.toString();
    }

    /**
     * 根据split从后面截取
     *
     * @param str
     * @param split
     * @return
     */
    public static String cutFinal(String str, String split) {
        if (ValidateUtil.isEmpty(split)) {
            return str;
        }
        return str.substring(0, str.length() - split.length());
    }

    public static String toString(Object str) {
        try {
            return ValidateUtil.isEmpty(str) ? "" : new String(str.toString());
        } catch (Exception e) {
            return "";
        }
    }

    public static String toString(byte[] con) {
        try {
            return new String(con, "UTF-8");
        } catch (Exception e) {
            return "";
        }
    }

    public static String toString(String[] strs) {
        String str = "";
        for (String s : strs) {
            str += "'" + s + "',";
        }

        return "[" + clearFirstAndLastComma(str) + "]";
    }

    public static String toString(List<String> list) {
        StringBuilder stringBuilder = new StringBuilder();
        list.forEach(role -> {
            stringBuilder.append(role + Attribute.SERVICE_SPLIT);
        });
        return clearFirstAndLastComma(stringBuilder.toString());
    }

    /**
     * @param strs
     * @param isColon 是否需要冒号
     * @return
     */
    public static String toString(String[] strs, boolean isColon) {
        if (isColon) {
            return toString(strs);
        }
        //不需要冒号
        String str = "";
        for (String s : strs) {
            str += s;
        }
        return "[" + clearFirstAndLastComma(str) + "]";
    }

    public static String toString(String str) {
        try {
            return ValidateUtil.isEmpty(str) ? "" : new String(str);
        } catch (Exception e) {
            return "";
        }
    }

    public static byte[] toByte(String str) {
        if (str == null) {
            return null;
        }
        byte[] byteArray = str.getBytes();
        return byteArray;
    }

    public static int toInt16(byte b) {
        return b & 0XFF;
    }

    public static String toJSONString(Map<?, ?> map) {
        return JSON.toJSONString(map);
    }

    public static Integer toInt(Object integer) {
        try {
            return ValidateUtil.isEmpty(integer) ? null : Integer.parseInt(integer.toString());
        } catch (Exception e) {
            return null;
        }
    }

    public static Integer toInt(int integer) {
        try {
            return ValidateUtil.isEmpty(integer) ? null : integer;
        } catch (Exception e) {
            return null;
        }
    }

    public static Long toLong(Object l) {
        try {
            return ValidateUtil.isEmpty(l) ? null : Long.parseLong(l.toString());
        } catch (Exception e) {
            return null;
        }
    }

    public static double toDouble(String obj) {
        try {
            return ValidateUtil.isEmpty(obj) ? 0 : Double.parseDouble(obj);
        } catch (Exception e) {
            return 0;
        }
    }


    public static boolean toBoolean(Object obj) {
        try {
            return ValidateUtil.isEmpty(obj) ? false : (Boolean) obj;
        } catch (Exception e) {
            return false;
        }
    }

    public static Map<?, ?> toMap(Object map) {
        try {
            return ValidateUtil.isEmpty(map) ? null : (Map<?, ?>) map;
        } catch (Exception e) {
            return null;
        }
    }

    public static <T> List<T> toList(Object list) {
        try {
            return ValidateUtil.isEmpty(list) ? null : (List<T>) list;
        } catch (Exception e) {
            return null;
        }
    }

    public static String toSqlIn(Object[] objects) {
        String params = "";
        for (int i = 0; i < objects.length; i++) {
            Object obj = objects[i];
            String keyValue = TextUtil.toString(obj);
            if (ValidateUtil.isEmpty(keyValue)) {
                continue;
            }
            params += obj instanceof String ? "'" + keyValue + "'" : keyValue;
            if (i + 1 != objects.length) {
                params += ",";
            }
        }

        return params;
    }

    /**
     * 判断文本里面有几个sub
     *
     * @param text
     * @param sub
     * @return
     */
    public static int subCount(String text, String sub) {
        int count = 0;
        int start = 0;
        while ((start = text.indexOf(sub, start)) >= 0) {
            start += sub.length();
            count++;
        }
        return count;
    }

    /**
     * 去掉字符串前后的逗号
     *
     * @param str
     * @return
     */
    public static String clearFirstAndLastComma(String str) {
        return interceptSymbol(str, ",");
    }

    /**
     * 去掉文本前后的符号
     *
     * @param str
     * @param symbol 符号
     * @return
     */
    public static String interceptSymbol(String str, String symbol) {
        if (ValidateUtil.isEmpty(str)) {
            return str;
        }

        int countSize = subCount(str, symbol);
        for (int i = 0; i < countSize; i++) {
            if (str.startsWith(symbol)) {
                str = str.substring(symbol.length(), str.length());
            }
        }
        countSize = subCount(str, symbol);

        for (int i = 0; i < countSize; i++) {
            if (str.endsWith(symbol)) {
                str = str.substring(0, str.length() - symbol.length());
            }
        }
        return str;
    }

    /**
     * 去掉字符串最后一个传来的split
     *
     * @param content
     * @param split
     * @return
     */
    public static String interceptLastSymbol(String content, String split) {
        if (!ValidateUtil.isEmpty(content) && content.endsWith(split)) {
            return content.substring(0, content.length() - split.length());
        }
        return content;
    }

    /**
     * 加在文本后面
     *
     * @param text
     * @param symbol
     * @param size
     * @return
     */
    public static String joinLastTextSymbol(Object text, String symbol, int size) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(toString(text));
        for (int i = 0; i < size; i++) {
            stringBuilder.append(symbol);
        }
        return stringBuilder.toString();
    }

    /**
     * 加在文本前面
     *
     * @param text
     * @param symbol
     * @param size
     * @return
     */
    public static String joinFirstTextSymbol(Object text, String symbol, int size) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < size; i++) {
            stringBuilder.append(symbol);
        }
        stringBuilder.append(toString(text));
        return stringBuilder.toString();
    }

    /**
     * 在右边加上大于符号
     *
     * @param text 文本
     * @param size 数量
     * @return
     */
    public static String greaterThanHtml(Object text, int size) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(toString(text));
        for (int i = 0; i < size; i++) {
            stringBuilder.append(MagicValue.GREATER_THAN);
        }
        return stringBuilder.toString();
    }

    /**
     * 返回UUID
     *
     * @return
     */
    public static String toUUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * sql过滤
     *
     * @param val
     * @return
     */
    public static Object sqlValidate(Object val) {
        if (val instanceof String) {
            return sqlValidate(toString(val));
        } else if (val instanceof ArrayList) {
            for (Object object : (List<Object>) val) {
                if (object instanceof String) {
                    object = sqlValidate(toString(object));
                }
            }
        }

        return val;
    }

    /**
     * sql过滤
     *
     * @param str
     * @return
     */
    public static String sqlValidate(String str) {
        //统一转为小写
        String str2 = str.toLowerCase();
        //词语
        String[] sqlStr1 = {"and", "exec", "execute", "insert", "select", "delete", "update", "count", "drop", "chr", "mid", "master", "truncate", "char", "declare", "sitename", "net user", "xp_cmdshell", "like", "and", "exec", "execute", "insert", "create", "drop", "table", "from", "grant", "use", "group_concat", "column_name", "information_schema.columns", "table_schema", "union", "where", "select", "delete", "update", "order", "by", "count", "chr", "mid", "master", "truncate", "char", "declare", "or"};
        //特殊字符
        String[] sqlStr2 = {"\\*", "'", ";", "or", "-", "--", "\\+", "//", "/", "%", "#"};

        for (int i = 0; i < sqlStr1.length; i++) {
            if (str2.indexOf(sqlStr1[i]) >= 0) {
                //正则替换词语，无视大小写
                str = str.replaceAll("(?i)" + sqlStr1[i], "");
            }
        }
        for (int i = 0; i < sqlStr2.length; i++) {
            if (str2.indexOf(sqlStr2[i]) >= 0) {
                str = str.replaceAll(sqlStr2[i], "");
            }
        }

        System.out.println(str);
        return str;
    }

    /**
     * 连接参数
     *
     * @param list
     * @param key
     * @param symbol
     * @return
     */
    public static String joinValue(List<Map<String, Object>> list, String key, String symbol) {
        StringBuilder result = new StringBuilder();

        if (ValidateUtil.isEmpty(list)) {
            return result.toString();
        }
        list.forEach(map -> {
            Object val = map.get(key);
            if (!ValidateUtil.isEmpty(val)) {
                result.append(toString(map.get(key)) + symbol);
            }
        });

        return interceptSymbol(result.toString(), symbol);
    }

    /**
     * BASE64加密
     *
     * @param src
     * @return
     */
    public static String base64Encrypt(String src) {
        try {
            return (new BASE64Encoder()).encodeBuffer(src.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    /**
     * Base64解密
     *
     * @param src
     * @return
     */
    public static String base64Decrypt(String src) {
        try {
            return new String((new BASE64Decoder()).decodeBuffer(src), "UTF-8");
        } catch (IOException e) {
            return "";
        }
    }

    public static String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Matcher m = BLANK_PATTERN.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

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

    public static String parseByte2HexStr(byte buf) {
        String hex = Integer.toHexString(buf & 0xFF);
        if (hex.length() == 1) {
            hex = '0' + hex;
        }
        return hex;
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

    /**
     * byte[]转为 指令包 2位一个空格
     */
    private static final char[] hexCode = "0123456789ABCDEF".toCharArray();

    public static String printHexBinary(byte[] data) {
        StringBuilder r = new StringBuilder(data.length * 2);
        for (byte b : data) {
            r.append(hexCode[(b >> 4) & 0xF]);
            r.append(hexCode[(b & 0xF)] + " ");
        }
        return r.toString();
    }

    /**
     * 16进制转换成为string类型字符串
     *
     * @param s
     * @return
     */
    public static String hexStringToString(String s) {
        if (s == null || s.equals("")) {
            return null;
        }
        s = s.replace(" ", "");
        byte[] baKeyword = new byte[s.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            s = new String(baKeyword, "UTF-8");
            new String();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return s;
    }

    /**
     * 字符串转化成为16进制字符串
     *
     * @param s
     * @return
     */
    public static String strTo16(String s) {
        String str = "";
        for (int i = 0; i < s.length(); i++) {
            int ch = (int) s.charAt(i);
            String s4 = Integer.toHexString(ch);
            str = str + s4;
        }
        return str;
    }

    /**
     * 解析包参数
     *
     * @param bytes
     * @return
     */
    public static String parsePackParam(byte[] bytes) {
        return replaceBlank(hexStringToString(parseByte2HexStr(bytes))).trim();
    }

    /**
     * 打包参数
     *
     * @param s
     * @return
     */
    public static byte[] packParam(String s) {
        return parseHexStr2Byte(strTo16(s));
    }

}

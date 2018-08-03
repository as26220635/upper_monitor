package cn.kim.util;

/**
 * Created by 余庚鑫 on 2018/8/2
 */
public class TCPUtil {
    /**
     * 字符串转为byte[]
     *
     * @param str
     * @param maxLength 一共多少位
     * @return
     */
    public static byte[] strToByte(String str, int maxLength) {
        byte[] resultBytes = new byte[maxLength];

        byte[] strBytes = str.getBytes();
        int strLength = strBytes.length;
        //是否大过最大位数
        if (maxLength < strLength) {
            maxLength = strLength;
        }
        //复制
        System.arraycopy(strBytes, 0, resultBytes, 0, strLength);
        //位数不足补0
        if (maxLength > strLength) {
            for (int i = strLength; i < maxLength; i++) {
                resultBytes[i] = 0;
            }
        }

        return resultBytes;
    }

    /**
     * byte 转为int
     *
     * @param bytes
     * @return
     */
    public static int toInt(byte[] bytes) {
        return bytes[3] & 0xFF |
                (bytes[2] & 0xFF) << 8 |
                (bytes[1] & 0xFF) << 16 |
                (bytes[0] & 0xFF) << 24;
    }

    /***
     * int转为4位byte
     * @param num
     * @return
     */
    public static byte[] toByte(int num) {
        byte[] bytes = new byte[4];
        bytes[0] = (byte) ((num >> 24) & 0xff);
        bytes[1] = (byte) ((num >> 16) & 0xff);
        bytes[2] = (byte) ((num >> 8) & 0xff);
        bytes[3] = (byte) (num & 0xff);

        return bytes;
    }

    public static byte[] toByte(int[] nums) {
        byte[] bytes = new byte[nums.length * 4];

        for (int i = 0; i < nums.length; i++) {
            //循环复制
            System.arraycopy(toByte(nums[i]), 0, bytes, i * 4, 4);
        }

        return bytes;
    }

    /***
     *  int转为
     * @param nums
     * @param maxLength 数组最大长度
     * @return
     */
    public static byte[] toByte(int[] nums, int maxLength) {
        //数组总大小
        int byteLength = maxLength * 4;
        byte[] bytes = new byte[byteLength];

        int copyLength = nums.length * 4;
        if (copyLength > byteLength) {
            copyLength = byteLength;
        }

        if (copyLength != 0) {
            byte[] numBytes = toByte(nums);
            System.arraycopy(numBytes, 0, bytes, 0, copyLength);
        }

        if (copyLength < byteLength) {
            for (int i = copyLength; i < byteLength; i++) {
                bytes[i] = 0;
            }
        }


        return bytes;
    }
}

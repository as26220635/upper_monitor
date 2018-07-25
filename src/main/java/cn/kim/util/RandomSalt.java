package cn.kim.util;

import java.util.Random;

/**
 * Created by 余庚鑫 on 2017/3/4.
 */
public class RandomSalt {

    /**
     * 拿到6位随机生成的盐
     *
     * @return
     */
    public static String salt() {
        String result = "";

        String a = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ;,-+.'";
        char[] rands = new char[6];
        for (int i = 0; i < rands.length; i++) {
            int rand = (int) (Math.random() * a.length());
            rands[i] = a.charAt(rand);
        }
        for (int i = 0; i < rands.length; i++) {
            result += rands[i];
        }
        return result;
    }
}

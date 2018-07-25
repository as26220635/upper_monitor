package cn.kim.util;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;

/**
 * Created by 余庚鑫 on 2017/3/4.
 */
public class PasswordMd5 {

    /**
     * 获取加密后的md5
     * @param password 密码
     * @param salt 盐
     * @return
     */
    public static String password(String password,String salt){
        //散列次数
        int hashIterations = 1;

        //构造方法中：
        //第一个参数：明文，原始密码
        //第二个参数：盐，通过使用随机数
        //第三个参数：散列的次数，比如散列两次，相当 于md5(md5(''))
        Md5Hash md5Hash = new Md5Hash(password, salt, hashIterations);

        String passwordMd5 = md5Hash.toString();
        return passwordMd5;
    }
}

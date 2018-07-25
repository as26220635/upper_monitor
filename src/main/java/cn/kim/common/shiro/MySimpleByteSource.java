package cn.kim.common.shiro;

import org.apache.shiro.util.SimpleByteSource;

import java.io.Serializable;

/**
 * Created by 余庚鑫 on 2017/6/11.
 * 重写继承序列化 防止shiro加盐报错
 */
public class MySimpleByteSource extends SimpleByteSource implements Serializable {

    public MySimpleByteSource(byte[] bytes) {
        super(bytes);
    }

    public MySimpleByteSource(String string) {
        super(string);
    }
}
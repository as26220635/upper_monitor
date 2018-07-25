package cn.kim.common;

/**
 * Created by 余庚鑫 on 2018/3/22
 * 获取服务器根路径
 */
public class ConfigLoad {
    public ConfigLoad() {
        if (ROOT_PATH == null) {
            ROOT_PATH = this.getClass().getResource("//").getPath().replaceAll("%20", " ");
        }
    }

    // 根路径地址
    public static String ROOT_PATH = null;
}

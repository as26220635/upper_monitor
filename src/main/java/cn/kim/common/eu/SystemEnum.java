package cn.kim.common.eu;

/**
 * Created by 余庚鑫 on 2018/3/21.
 * 系统变量
 */
public enum SystemEnum {
    //系统
    SYSTEM("0"),
    //管理员
    MANAGER("1"),
    //用户
    MEMBER("2");

    private String type;

    private SystemEnum(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return this.type.toString();
    }
}

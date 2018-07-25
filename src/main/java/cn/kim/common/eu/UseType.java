package cn.kim.common.eu;

/**
 * Created by 余庚鑫 on 2018/6/6
 * 日志类型
 */
public enum UseType {
    //不可见
    SEE(-1),
    //系统
    SYSTEM(1),
    //使用
    USE(2),
    //用户
    PERSONAL(9);

    private int type;

    private UseType(int type) {
        this.type = type;
    }

    public int getType() {
        return this.type;
    }

}

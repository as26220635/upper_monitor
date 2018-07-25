package cn.kim.common.eu;

import cn.kim.util.TextUtil;

/**
 * Created by 余庚鑫 on 2018/7/4
 * 按钮类型
 */
public enum ButtonType {
    //无
    NONE(-1),
    //顶部按钮
    TOP(0),
    //列表按钮
    LIST(1);

    private int type;

    private ButtonType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return TextUtil.toString(this.type);
    }

    public int getType() {
        return this.type;
    }

}

package cn.kim.common.eu;

import cn.kim.util.TextUtil;

/**
 * Created by 余庚鑫 on 2018/7/4
 * 页面显示流程状态
 */
public enum ProcessShowStatus {
    //全部
    ALL(0),
    //待审
    STAY(1),
    //已审
    ALREADY(2);

    private int type;

    private ProcessShowStatus(int type) {
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

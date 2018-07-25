package cn.kim.common.eu;

import cn.kim.util.TextUtil;

/**
 * Created by 余庚鑫 on 2018/6/6
 * 流程类型
 */
public enum ProcessType {
    //无
    NONE(-1),
    //提交
    SUBMIT(0),
    //退回
    BACK(1),
    //撤回
    WITHDRAW(2);

    private int type;

    private ProcessType(int type) {
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

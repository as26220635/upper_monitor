package cn.kim.common.eu;

import cn.kim.util.TextUtil;

/**
 * Created by 余庚鑫 on 2018/7/11
 * 参数记录日志类型
 */
public enum ValueRecordType {
    //添加
    INSERT(1),
    //更新
    UPDATE(2),
    //删除
    DELETE(3);

    private int type;

    private ValueRecordType(int type) {
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

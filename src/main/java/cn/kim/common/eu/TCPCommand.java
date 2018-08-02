package cn.kim.common.eu;

import cn.kim.util.TextUtil;

/**
 * Created by 余庚鑫 on 2018/8/2
 * TCP指令
 */
public enum TCPCommand {
    //心跳
    HEART_BEAT(0x56),
    //刷卡
    REQUEST_CARD(0x53),
    //开门
    OPEN_DOOR(0x2C),
    //门常开
    OPEN_DOORS(0x2D),
    //关门
    CLOSE_DOOR(0x2E),
    //锁门
    LOCK_DOOR(0x2F),
    //禁止读卡
    PROHIBITION_CARD(0x5A),
    //时间同步
    TIME_ASYNC(0x07),
    //操作报警
    MAST_ALARM(0x18),
    //操作火警
    FIRE_ALARM(0x19),
    //设置参数
    SET_PARAM(0x63),
    //控制器复位
    CONTROLLER_RESET(0x04),
    //控制输出
    OUT_PUT(0x73),
    ;
    private int type;

    private TCPCommand(int type) {
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

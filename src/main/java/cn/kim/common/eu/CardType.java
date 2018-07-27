package cn.kim.common.eu;

import cn.kim.util.TextUtil;

/**
 * Created by 余庚鑫 on 2018/7/27
 * 按钮类型
 */
public enum CardType {
    //卡
    CARD(0),
    // 串口232接口输入字符串,如二维码等；
    ORCODE(1),
    // 密码
    PIN(2),
    // 按钮请求；
    BUTTON(3),
    PC(4),
    ALARM(5),
    // 二代证数据；
    CHINA(6),
    // Base64编码数据，用于串口输入；
    BASE64(9),
    // 指纹数据；
    FINGER(10),
    // 指静脉数据；
    VIEN_FINGER(11),
    // RFID卡；
    RFID(12),
    //人脸；
    FACE(13),
    ;
    private int type;

    private CardType(int type) {
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

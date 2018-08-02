package cn.kim.common.attr;

/**
 * Created by 余庚鑫 on 2018/3/21.
 */
public class Constants {
    public static final String SESSION_USERNAME = "activeUser";

    public static final String SESSION_SQL_EXCEPTION = "sqlException";

    //最后一次访问URL地址
    public static final String SESSION_SERVLET_PATH = "SESSION_SERVLET_PATH";
    /**
     * 偏移值
     */
    public static final Long OFFSET_VALUE = 10L;

    public static final String JWT_ERRCODE_EXPIRE = "success";

    public static final String JWT_ERRCODE_FAIL = "error";
    /**
     * 包开始
     */
    public static final int TCP_HEAD_DATA = 0x02;
    /**
     * 包结束
     */
    public static final int TCP_END_DATA = 0x03;
    /**
     * 门报警
     */
    public static final int DOOR_MAST = 0x01;
    /**
     * 开门时间太长报警
     */
    public static final int DOOR_LOOG_MAST = 0x02;
}

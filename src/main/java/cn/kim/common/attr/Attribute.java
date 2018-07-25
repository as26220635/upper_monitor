package cn.kim.common.attr;

import cn.kim.common.eu.SystemEnum;

/**
 * Created by 余庚鑫 on 2018/3/21
 * 参数类
 */
public class Attribute {
    /**
     * 成功标志
     */
    public static final int STATUS_SUCCESS = 1;
    /**
     * 失败标志
     */
    public static final int STATUS_ERROR = 0;
    /**
     * 加密失败参数
     */
    public static final String INVALID_KEY_ERROR = "@InvalidKey@";
    /**
     * 分隔符
     */
    public static final String SERVICE_SPLIT = ",";
    public static final String COMPLEX_SPLIT = "!@!@!@!@!@!@!";
    /**
     * 提交表单token参数名
     */
    public static final String SUBMIT_TOKEN_NAME = "token";

    /*******************   审核标记 ******************/
    public static final int BACK_CODE = -1;
    public static final int COMPLETE_CODE = 999;

    /*******************    前台使用 ******************/
    public static final String LOADING_IMAGE = "resources/reception/assets/img/loading.gif";
    /**
     * 错误图片
     */
    public static final String IMAGE_ERROR = "reception/assets/img/error.jpg";
    /**
     * 权限错误
     */
    public static final String MANAGER_REFUSE = "error/refuse";
    public static final String RECEPTION_REFUSE = "error/reception_refuse";
    /**
     * 登录地址
     */
    public static final String LOGIN_URL = "/login";
    /*******************    网页链接地址  ******************/
    /***
     * 404
     */
    public static final String MANAGER_404 = "error/404";
    public static final String RECEPTION_404 = "error/reception_404";
    public static final String RECEPTION_404_COMPLETE = "/WEB-INF/jsp/error/reception_404.jsp";
    /**
     * 错误
     */
    public static final String MANAGER_ERROR = "error/error";
    public static final String RECEPTION_ERROR = "error/reception_error";
}

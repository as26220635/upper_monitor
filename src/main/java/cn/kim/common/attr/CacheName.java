package cn.kim.common.attr;

/**
 * Created by 余庚鑫 on 2017/5/12.
 */
public class CacheName {
    /**
     * shiro缓存
     */
    public static final String SHIRO_AUTHORIZATIONCACHE = "shiro-authorizationCache";
    public static final String SHIRO_ACTIVESESSIONCACHE = "shiro-activeSessionCache";
    /**
     * 登录密码错误验证
     */
    public static final String PASSWORD_RETRY_CACHE = "password-retry-cache";

    /**
     * 记录上一次保存的日志信息,重复的日志2分钟记录一次
     */
    public static final String LOG_RECORD_CACHE = "log-record-cache";
    /**
     * 各种值的集合
     */
    public static final String VALUE_COLLECTION = "value-collection";
}

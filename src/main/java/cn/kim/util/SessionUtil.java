package cn.kim.util;

import cn.kim.common.attr.Constants;
import cn.kim.common.attr.Constants;
import org.apache.shiro.session.Session;

/**
 * Created by 余庚鑫 on 2017/3/31.
 */
public class SessionUtil {

    private static final Integer MAX_SQL_EXCEPTION = 5;

    public static Session getSession() {
        return AuthcUtil.getCurrent().getSession();
    }

    /**
     * 获取全局Session
     *
     * @return
     */
    public static Object get(String key) {
        return getSession().getAttribute(key);
    }

    /**
     * 设置值
     *
     * @param key
     * @param value
     */
    public static void set(String key, Object value) {
        getSession().setAttribute(key, value);
    }

    /**
     * 移除
     *
     * @param key
     */
    public static void remove(Object key) {
        getSession().removeAttribute(key);
    }

    /**
     * 拿到sql查询错误次数是否超过限制次数
     *
     * @return
     */
    public static boolean sqlException() {
        Integer value = (Integer) get(Constants.SESSION_SQL_EXCEPTION);
        Integer count = ValidateUtil.isEmpty(value) ? 0 : value + 1;
        set(Constants.SESSION_SQL_EXCEPTION, count);

        return count >= MAX_SQL_EXCEPTION ? true : false;
    }

}

package cn.kim.util;

import cn.kim.common.attr.Constants;
import cn.kim.entity.ActiveUser;
import cn.kim.common.attr.Constants;
import cn.kim.entity.ActiveUser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Component;

/**
 * Created by 余庚鑫 on 2017/3/8.
 */
@Component
public class AuthcUtil {

    private static Logger logger = LogManager.getLogger(AuthcUtil.class.getName());

    /**
     * 当前登录角色
     *
     * @return
     */
    public static ActiveUser getCurrentUser() {
        Object obj = SessionUtil.get(Constants.SESSION_USERNAME);
        return ValidateUtil.isEmpty(obj) ? null : (ActiveUser) obj;
    }

    public static void setCurrentUser(ActiveUser user) {
        SessionUtil.set(Constants.SESSION_USERNAME, user);
    }

    /**
     * 当前登录
     *
     * @return
     */
    public static Subject getCurrent() {
        return SecurityUtils.getSubject();
    }

    /**
     * 判断权限
     *
     * @param str
     * @return
     */
    public static boolean isPermitted(String str) {
        return getCurrent().isPermitted(str);
    }

    /**
     * 退出登录
     */
    public static void logout() {
        if (getCurrent() != null) {
            getCurrent().logout();
        }
    }

}

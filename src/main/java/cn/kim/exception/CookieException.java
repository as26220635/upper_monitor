package cn.kim.exception;

import org.apache.shiro.authc.AuthenticationException;

/**
 * Created by 余庚鑫 on 2017/2/26.
 */
public class CookieException extends AuthenticationException {

    public CookieException() {
        super();
    }

    public CookieException(String message, Throwable cause) {
        super(message, cause);
    }

    public CookieException(String message) {
        super(message);
    }

    public CookieException(Throwable cause) {
        super(cause);
    }
}
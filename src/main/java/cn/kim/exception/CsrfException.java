package cn.kim.exception;

import org.apache.shiro.authc.AuthenticationException;

/**
 * Created by 余庚鑫 on 2017/2/26.
 */
public class CsrfException extends AuthenticationException {

    public CsrfException() {
        super();
    }

    public CsrfException(String message, Throwable cause) {
        super(message, cause);
    }

    public CsrfException(String message) {
        super(message);
    }

    public CsrfException(Throwable cause) {
        super(cause);
    }
}
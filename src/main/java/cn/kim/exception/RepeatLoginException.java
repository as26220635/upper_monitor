package cn.kim.exception;

import org.apache.shiro.authc.AuthenticationException;

/**
 * Created by 余庚鑫 on 2017/2/26.
 */
public class RepeatLoginException extends AuthenticationException {

    public RepeatLoginException() {
        super();
    }

    public RepeatLoginException(String message, Throwable cause) {
        super(message, cause);
    }

    public RepeatLoginException(String message) {
        super(message);
    }

    public RepeatLoginException(Throwable cause) {
        super(cause);
    }
}
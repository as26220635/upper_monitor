package cn.kim.exception;

import org.apache.shiro.authc.AuthenticationException;

/**
 * Created by 余庚鑫 on 2017/2/26.
 */
public class UnknownTypeException extends AuthenticationException {

    public UnknownTypeException() {
        super();
    }

    public UnknownTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnknownTypeException(String message) {
        super(message);
    }

    public UnknownTypeException(Throwable cause) {
        super(cause);
    }
}
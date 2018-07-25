package cn.kim.exception;

import org.apache.shiro.authc.AuthenticationException;

/**
 * Created by 余庚鑫 on 2018/6/8.
 */
public class NotFoundException extends AuthenticationException {

    public NotFoundException() {
        super();
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(Throwable cause) {
        super(cause);
    }
}
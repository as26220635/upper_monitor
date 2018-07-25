package cn.kim.exception;

import org.apache.shiro.authc.AuthenticationException;

/**
 * Created by 余庚鑫 on 2017/2/26.
 */
public class FrozenException extends AuthenticationException {

    public FrozenException() {
        super();
    }

    public FrozenException(String message, Throwable cause) {
        super(message, cause);
    }

    public FrozenException(String message) {
        super(message);
    }

    public FrozenException(Throwable cause) {
        super(cause);
    }
}
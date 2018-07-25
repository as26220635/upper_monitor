package cn.kim.exception;

import org.apache.shiro.authc.AuthenticationException;

/**
 * Created by 余庚鑫 on 2017/2/26.
 */
public class RoleFrozenException extends AuthenticationException {

    public RoleFrozenException() {
        super();
    }

    public RoleFrozenException(String message, Throwable cause) {
        super(message, cause);
    }

    public RoleFrozenException(String message) {
        super(message);
    }

    public RoleFrozenException(Throwable cause) {
        super(cause);
    }
}
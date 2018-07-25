package cn.kim.exception;

import org.apache.shiro.authc.AuthenticationException;

/**
 * Created by 余庚鑫 on 2017/2/26.
 */
public class NullRoleFrozenException extends AuthenticationException {

    public NullRoleFrozenException() {
        super();
    }

    public NullRoleFrozenException(String message, Throwable cause) {
        super(message, cause);
    }

    public NullRoleFrozenException(String message) {
        super(message);
    }

    public NullRoleFrozenException(Throwable cause) {
        super(cause);
    }
}
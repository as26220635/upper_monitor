package cn.kim.common.annotation;

import java.lang.annotation.*;

/**
 * Created by 余庚鑫 on 2017/11/6.
 * 自定义注解 拦截Controller  返回的数据是否加密
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DecryptParams {

    boolean AES() default true;
}

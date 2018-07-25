package cn.kim.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by 余庚鑫 on 2018/3/1
 * 唯一UUID防止重复提交表单
 * <input type="hidden" name="${SUBMIT_TOKEN_NAME}" value="${token}" />
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Token {
    /**
     * 进入需要防止重复提交的页面的时候使用
     *
     * @return
     */
    boolean save() default false;

    /**
     * 提交表单的接受方法使用
     *
     * @return
     */
    boolean remove() default false;
}

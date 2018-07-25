package cn.kim.common.annotation;

import cn.kim.common.attr.Attribute;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * Created by 余庚鑫 on 2018/4/1.
 * 自定义注解 拦截参数验证
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Validate {
    /**
     * 验证的table表名
     *
     * @return String[] SV_TABLE
     */
    @AliasFor("SV_TABLE")
    String[] value() default "";

    /**
     * 验证组ID 默认全部验证 当有特殊需求的时候可以使用多组验证或其他验证
     * 当没有找到这个验证组的时候会默认验证全部字段
     *
     * @return String[] SVG_GROUP
     */
    @AliasFor("SVG_GROUP")
    String[] group() default "";

    /**
     * 是否必填
     * 为true的情况下,会拿到表和组的必填字段进行验证 当提交的表单字段没有出现的时候会提示
     * 为false的情况下，不会对没有的记录进行必填验证
     *
     * @return boolean
     */
    boolean required() default false;
}

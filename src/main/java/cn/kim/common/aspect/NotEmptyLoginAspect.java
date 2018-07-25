package cn.kim.common.aspect;

import cn.kim.common.BaseData;
import cn.kim.common.annotation.Validate;
import cn.kim.common.attr.Attribute;
import cn.kim.entity.ActiveUser;
import cn.kim.entity.AnnotationParam;
import cn.kim.exception.CustomException;
import cn.kim.service.ValidateService;
import cn.kim.util.AnnotationUtil;
import cn.kim.util.AuthcUtil;
import cn.kim.util.ValidateUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by 余庚鑫 on 2018/6/11.
 * aop验证是否登录
 */
@Aspect
@Component
public class NotEmptyLoginAspect extends BaseData {
    private static Logger logger = LogManager.getLogger(NotEmptyLoginAspect.class.getName());

    @Autowired
    private ValidateService validateService;

    //Controller层切点
    @Pointcut("@annotation(cn.kim.common.annotation.NotEmptyLogin)")
    public void notEmptyLoginAspect() {
    }

    @Around("notEmptyLoginAspect()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        if (isEmpty(AuthcUtil.getCurrentUser())) {
            throw new CustomException("没有登录!");
        }
        return pjp.proceed();
    }


}

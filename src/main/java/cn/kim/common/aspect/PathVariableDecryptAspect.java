package cn.kim.common.aspect;

import cn.kim.common.BaseData;
import cn.kim.entity.AnnotationParam;
import cn.kim.util.AnnotationUtil;
import cn.kim.common.BaseData;
import cn.kim.entity.AnnotationParam;
import cn.kim.util.AnnotationUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by 余庚鑫 on 2018/4/1.
 * 获取PathVariable 如果字段名称是id的话就要解密
 */
@Aspect
@Component
public class PathVariableDecryptAspect extends BaseData {

    //Controller层切点
    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void requestMapping() {
    }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.GetMapping)")
    public void getMapping() {
    }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping)")
    public void postMapping() {
    }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.PutMapping)")
    public void putMapping() {
    }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.DeleteMapping)")
    public void deleteMapping() {
    }

    @Around("requestMapping()")
    public Object doAroundRequest(ProceedingJoinPoint pjp) throws Throwable {
        return idDecrypt(pjp);
    }

    @Around("getMapping()")
    public Object doAroundGet(ProceedingJoinPoint pjp) throws Throwable {
        return idDecrypt(pjp);
    }

    @Around("postMapping()")
    public Object doAroundPost(ProceedingJoinPoint pjp) throws Throwable {
        return idDecrypt(pjp);
    }

    @Around("putMapping()")
    public Object doAroundPut(ProceedingJoinPoint pjp) throws Throwable {
        return idDecrypt(pjp);
    }

    @Around("deleteMapping()")
    public Object doAroundDelete(ProceedingJoinPoint pjp) throws Throwable {
        return idDecrypt(pjp);
    }

    /**
     * ID解密
     *
     * @param pjp
     * @return
     * @throws Throwable
     */
    public Object idDecrypt(ProceedingJoinPoint pjp) throws Throwable {

        Object[] objs = pjp.getArgs();
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();

        //检测
        Annotation[][] annos = method.getParameterAnnotations();
        boolean flag = AnnotationUtil.validateParameterAnnotation(PathVariable.class, annos);
        //虽然方法加了注解,但是参数么有注解,直接执行方法
        if (!flag) {
            return pjp.proceed();
        }
        //得到标注@PathVariable注解的参数
        List<AnnotationParam> params = AnnotationUtil.getAnnotationParams(PathVariable.class, method, objs);
        for (AnnotationParam param : params) {
            //ID解密
            if (isEncrypt(param.getName(), param.getType())) {
                objs[param.getIndex()] = idDecrypt(param.getValue());
            }
        }
        return pjp.proceed(objs);
    }

}

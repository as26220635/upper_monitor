package cn.kim.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

import cn.kim.common.annotation.SystemControllerLog;
import cn.kim.entity.AnnotationParam;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * Created by 余庚鑫 on 2018/4/1.
 */
public class AnnotationUtil {
    /**
     * 获取MethodSignature
     *
     * @param point
     * @return
     */
    public static Signature getMethod(ProceedingJoinPoint point) {
        MethodSignature sign = (MethodSignature) point.getSignature();
        return sign;
    }

    /**
     * 获取参数列表
     *
     * @param point
     * @return
     */
    public static Object[] getArgs(ProceedingJoinPoint point) {
        return point.getArgs();
    }

    /**
     * 验证是否有某个注解
     *
     * @param annos
     * @param tClass
     * @return
     */
    public static boolean validateParameterAnnotation(Class<?> tClass, Annotation[][] annos) {
        boolean flag = false;
        for (Annotation[] at : annos) {
            for (Annotation a : at) {
                if (a.annotationType() == tClass) {
                    flag = true;
                }
            }
        }
        return flag;
    }

    /**
     * 获取参数的描述
     *
     * @param tClass 指定的类型
     * @param method 方法
     * @param objs   参数
     * @return
     */
    public static List<AnnotationParam> getAnnotationParams(Class<?> tClass, Method method, Object[] objs) {
        Annotation[][] annos = method.getParameterAnnotations();
        Class<?>[] paramTypes = method.getParameterTypes();
        List<AnnotationParam> params = new ArrayList<AnnotationParam>();
        for (int i = 0; i < annos.length; i++) {
            for (int j = 0; j < annos[i].length; j++) {
                //如果出现指定的注解类型
                if (annos[i][j].annotationType() == tClass) {
                    AnnotationParam param = new AnnotationParam(
                            //简单名称
                            paramTypes[i].getSimpleName(),
                            //名称
                            tClass == PathVariable.class ? ((PathVariable) annos[i][j]).value() : paramTypes[i].getName(),
                            //参数类型
                            paramTypes[i],
                            //参数值
                            objs[i],
                            //筛选出的注解
                            annos[i][j],
                            //index
                            i);
                    params.add(param);
                }
            }
        }
        return params;
    }

    /**
     * 查询pjp 返回注解
     *
     * @param joinPoint
     * @param annotationClass
     * @param <T>
     * @return
     * @throws ClassNotFoundException
     */
    public static <T extends Annotation> T getAnnotation(JoinPoint joinPoint, Class<T> annotationClass) throws ClassNotFoundException {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        String event = "";
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length) {
                    return method.getAnnotation(annotationClass);
                }
            }
        }
        return null;
    }
}

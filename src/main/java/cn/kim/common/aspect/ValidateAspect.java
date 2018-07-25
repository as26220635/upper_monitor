package cn.kim.common.aspect;

import cn.kim.common.BaseData;
import cn.kim.common.annotation.Validate;
import cn.kim.common.attr.Attribute;
import cn.kim.common.attr.MagicValue;
import cn.kim.entity.AnnotationParam;
import cn.kim.service.ValidateService;
import cn.kim.util.AnnotationUtil;
import cn.kim.util.ValidateUtil;
import com.google.common.collect.Maps;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by 余庚鑫 on 2018/4/1.
 * aop验证参数是否可以通过
 * 使用@Validate("SV_TABLE")注解
 */
@Aspect
@Component
public class ValidateAspect extends BaseData {
    private static Logger logger = LogManager.getLogger(ValidateAspect.class.getName());

    @Autowired
    private ValidateService validateService;

    //Controller层切点
    @Pointcut("@annotation(cn.kim.common.annotation.Validate)")
    public void validateAspect() {
    }

    @Around("validateAspect()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        logger.info("验证参数开始!");

        Object[] objs = pjp.getArgs();
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();

        //检测
        Annotation[][] annos = method.getParameterAnnotations();
        boolean flag = AnnotationUtil.validateParameterAnnotation(RequestParam.class, annos);
        //虽然方法加了注解,但是参数么有注解,直接执行方法
        if (!flag) {
            return pjp.proceed();
        }
        //得到标注@RequestParam注解的参数
        List<AnnotationParam> params = AnnotationUtil.getAnnotationParams(RequestParam.class, method, objs);
        for (AnnotationParam param : params) {
            Map<String, Object> resultMap = validateField(AnnotationUtil.getAnnotation(pjp, Validate.class), param.getValue());
            //验证不通过直接返回map到页面上
            if (!isSuccess(resultMap)) {
                return resultState(resultMap);
            }
        }

        return pjp.proceed();
    }

    /**
     * 验证参数
     *
     * @param validate 验证注解
     * @param value    传入的value值
     * @return
     */
    private Map<String, Object> validateField(Validate validate, Object value) {
        //表名
        String[] tables = validate.value();
        //分组
        String[] groups = validate.group();
        //是否必填
        boolean isRequired = validate.required();

        Map<String, Object> resultMap = Maps.newHashMapWithExpectedSize(3);
        resultMap.put(MagicValue.STATUS, Attribute.STATUS_SUCCESS);

        //value值是map 和获取注解的表名不能为空
        if (value instanceof Map && !ValidateUtil.isEmpty(tables)) {
            Map<String, String> errorMap = Maps.newHashMapWithExpectedSize(16);
            //被验证参数
            Map<String, Object> map = (Map<String, Object>) value;

            List<Map<String, Object>> fields = new ArrayList<>();

            Map<String, Object> mapParam = Maps.newHashMapWithExpectedSize(5);
            //循环获取多个表的验证
            for (String table : tables) {
                mapParam.clear();
                //查询组是否存在
                if (!isEmpty(groups)) {
                    List validateGroups = Arrays.asList(groups);
                    mapParam.put("SV_TABLE", table);
                    mapParam.put("SVG_GROUPS", validateGroups);
                    List<Map<String, Object>> groupList = validateService.selectValidateGroupList(mapParam);
                    mapParam.clear();
                    if (!isEmpty(groupList)) {
                        mapParam.put("SVG_GROUPS", validateGroups);
                    }
                }
                mapParam.put("SV_TABLE", table);
                mapParam.put("IS_STATUS", Attribute.STATUS_SUCCESS);
                mapParam.put("SV_IS_STATUS", Attribute.STATUS_SUCCESS);
                mapParam.put("SVR_IS_STATUS", Attribute.STATUS_SUCCESS);
                //查询组
                List<Map<String, Object>> temps = validateService.selectValidateFieldList(mapParam);
                if (!isEmpty(temps)) {
                    fields.addAll(temps);
                }
            }

            //字段为空就说明不需要验证
            if (isEmpty(fields)) {
                return resultMap;
            }

            Map<String, Map<String, Object>> fieldsMap = toMapsKey(fields, "SVF_FIELD");

            //先验证是否必填
            if (isRequired) {
                for (Map<String, Object> field : fieldsMap.values()) {
                    int isFieldRequired = toInt(field.get("SVF_IS_REQUIRED"));
                    if (isFieldRequired == Attribute.STATUS_SUCCESS) {
                        Object val = map.get(toString(field.get("SVF_FIELD")));
                        if (isEmpty(val)) {
                            errorMap.put(toString(field.get("SVF_NAME")), "参数不能为空!");
                            continue;
                        }
                    }
                }
            }

            //验证参数开始
            if (errorMap.size() == 0) {
                for (String key : map.keySet()) {
                    if (fieldsMap.containsKey(key)) {
                        //拿到字段验证详细
                        Map<String, Object> field = fieldsMap.get(key);
                        //拿到被验证的值
                        Object val = map.get(key);

                        String fieldMinLength = toString(field.get("SVF_MIN_LENGTH"));
                        String fieldMaxLength = toString(field.get("SVF_MAX_LENGTH"));
                        String regex = toString(field.get("SVR_REGEX"));
                        String regexMessage = toString(field.get("SVR_REGEX_MESSAGE"));
                        int isFieldRequired = toInt(field.get("SVF_IS_REQUIRED"));

                        //参数不能为空
                        if (isFieldRequired == Attribute.STATUS_SUCCESS) {
                            if (isEmpty(val)) {
                                errorMap.put(toString(field.get("SVF_NAME")), "参数不能为空!");
                                continue;
                            }
                        }

                        //最小字数
                        if (!isEmpty(fieldMinLength) && !isEmpty(val)) {
                            int length = toString(val).length();
                            if (toInt(fieldMinLength) > toString(val).length()) {
                                errorMap.put(toString(field.get("SVF_NAME")), "最小字数为:" + fieldMinLength + ",当前字数为:" + length);
                                continue;
                            }
                        }

                        //最大字数
                        if (!isEmpty(fieldMaxLength) && !isEmpty(val)) {
                            int length = toString(val).length();
                            if (toInt(fieldMaxLength) < toString(val).length()) {
                                errorMap.put(toString(field.get("SVF_NAME")), "最大字数为:" + fieldMaxLength + ",当前字数为:" + length);
                                continue;
                            }
                        }

                        //正则验证
                        if (!isEmpty(regex) && !isEmpty(val)) {
                            if (!toString(val).matches(regex)) {
                                errorMap.put(toString(field.get("SVF_NAME")), regexMessage);
                                continue;
                            }
                        }

                    }
                }
            }


            //说明有错误
            if (errorMap.size() > 0) {
                StringBuilder desc = new StringBuilder();
                errorMap.keySet().forEach(key -> {
                    desc.append(key + ":");
                    desc.append(errorMap.get(key) + "<br/>");
                });

                resultMap.put(MagicValue.STATUS, STATUS_ERROR);
                resultMap.put(MagicValue.DESC, desc.toString());
                resultMap.put(MagicValue.LOG, desc.toString());
            }

        }

        return resultMap;
    }

}

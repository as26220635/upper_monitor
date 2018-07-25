package cn.kim.common.aspect;

import cn.kim.common.BaseData;
import cn.kim.common.annotation.SystemControllerLog;
import cn.kim.common.attr.Attribute;
import cn.kim.common.attr.CacheName;
import cn.kim.common.attr.Tips;
import cn.kim.common.eu.UseType;
import cn.kim.entity.ActiveUser;
import cn.kim.entity.AnnotationParam;
import cn.kim.entity.ResultState;
import cn.kim.service.MenuService;
import cn.kim.util.*;
import com.alibaba.fastjson.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.cache.Cache;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * Created by 余庚鑫 on 2017/7/5.
 * 记录日志AOP
 */
@Aspect
@Component
public class LogAspect extends BaseData {
    private static Logger logger = LogManager.getLogger(LogAspect.class.getName());

    @Autowired
    private MenuService menuService;


    //Service层切点
//    @Pointcut("@annotation(cn.kim.common.annotation.SystemServiceLog)")
//    public  void serviceAspect() {
//    }

    //Controller层切点
    @Pointcut("@annotation(cn.kim.common.annotation.SystemControllerLog)")
    public void controllerAspect() {
    }

    /**
     * 退出方法通知 用于拦截Controller层记录用户的操作
     *
     * @param pjp
     * @return
     * @throws Throwable
     */
    @Around("controllerAspect()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {

        //执行该方法
        Object object = pjp.proceed();

        //如果返回类型是ResultState就记录日志
        if (object instanceof ResultState || object instanceof String || object instanceof JSONObject) {
            HttpServletRequest request = HttpUtil.getRequest();
            //读取session中的用户
            ActiveUser activeUser = AuthcUtil.getCurrentUser();

            //获取注解
            SystemControllerLog systemControllerLog = AnnotationUtil.getAnnotation(pjp, SystemControllerLog.class);
            String event = systemControllerLog.event();
            int useType = systemControllerLog.useType().getType();

            String message = "";
            int result = 0;
            String logType = null;
            if (activeUser != null) {
                logType = activeUser.getType();
            }

            //是否成功
            int code = 0;

            if (useType != UseType.SEE.getType()) {
                //判断返回类型
                if (object instanceof ResultState) {
                    ResultState resultState = (ResultState) object;
                    code = resultState.getCode();
                    if (resultState.getCode() == Attribute.STATUS_SUCCESS) {
                        if (resultState.getLogMessage() != null) {
                            message = HtmlUtils.htmlEscape(HtmlUtils.htmlUnescape(resultState.getLogMessage()));
                        }
                    } else {
                        message = resultState.getMessage();
                    }
                } else if (object instanceof String || object instanceof JSONObject) {
                    //不是个人日志与返回的是String JSON
                    if (useType != UseType.PERSONAL.getType() && isJson(object.toString())) {
                        JSONObject jsonObject = JSONObject.parseObject(object.toString());
                        code = toInt(jsonObject.get("code"));

                        if (isEmpty(code)) {
                            logger.warn("没有找到code,请求方法:"
                                    + (pjp.getTarget().getClass().getName() + "." + pjp.getSignature().getName() + "()")
                                    + ",方法描述:" + event);
                            return object;
                        }

                        if (code == Attribute.STATUS_SUCCESS) {
                            message = jsonObject.getString("logMessage");
                        } else {
                            if (!isEmpty(jsonObject.get("logMessage"))) {
                                message = jsonObject.getString("logMessage");
                            } else {
                                message = jsonObject.getString("message");
                            }
                        }
                    } else {
                        //判断是否2分钟内重复记录日志,重复的话直接返回
                        if (isRepeatVisit(activeUser.getId(), event)) {
                            return object;
                        }

                        //不是Json说明返回的是URL
                        message = "访问链接:" + HttpUtil.getUrl(request, false);

                        code = Attribute.STATUS_SUCCESS;
                    }
                }
            } else {
                Object[] objs = pjp.getArgs();
                MethodSignature signature = (MethodSignature) pjp.getSignature();
                Method method = signature.getMethod();

                //得到标注@PathVariable注解的参数
                List<AnnotationParam> params = AnnotationUtil.getAnnotationParams(PathVariable.class, method, objs);
                if (!isEmpty(params)) {
                    useType = UseType.PERSONAL.getType();

                    String menuId = toString(idDecrypt(params.get(0).getValue()));
                    //查询菜单
                    Map<String, Object> menu = menuService.queryMenuById(menuId);
                    event = "查看" + menu.get("SM_NAME");

                    //判断是否2分钟内重复记录日志,重复的话直接返回
                    if (isRepeatVisit(activeUser.getId(), event)) {
                        return object;
                    }
                }
            }

            if (code == Attribute.STATUS_SUCCESS) {
                result = Attribute.STATUS_SUCCESS;
            } else {
                result = Attribute.STATUS_ERROR;
            }

            //记录日志
            LogUtil.recordLog(request, event, useType, logType, message, result);
        }

        return object;
    }


    /**
     * 判断查看大页面的是否重复记录，访问时间超过2分钟的记录
     *
     * @param userId
     * @param event
     * @return
     */
    public boolean isRepeatVisit(String userId, String event) {
        if (!event.startsWith("查看")) {
            return false;
        }
        //判断缓存中是否存在
        Cache cache = CacheUtil.getCache(CacheName.LOG_RECORD_CACHE);
        String str = isEmpty(cache.get(userId)) ? null : cache.get(userId).toString();
        if (isEmpty(str)) {
            cache.put(userId, event + Attribute.COMPLEX_SPLIT + System.currentTimeMillis());
            return false;
        }

        String[] strs = str.split(Attribute.COMPLEX_SPLIT);

        String ev = strs[0];
        long time = Long.parseLong(strs[1]);
        //判断这个值是否超过2分钟了
        if (ev.equals(event) && System.currentTimeMillis() - time <= 120000) {
            return true;
        }

        cache.remove(userId);
        return false;
    }

}

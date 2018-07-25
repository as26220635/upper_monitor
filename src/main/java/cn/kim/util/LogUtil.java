package cn.kim.util;

import cn.kim.common.attr.ParamTypeResolve;
import cn.kim.entity.ActiveUser;
import cn.kim.service.LogService;
import cn.kim.common.attr.ParamTypeResolve;
import cn.kim.entity.ActiveUser;
import cn.kim.service.LogService;
import com.google.common.collect.Maps;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 余庚鑫 on 2017/3/29.
 * 日志操作类
 */
@Component
public class LogUtil {

    private static Logger logger = LogManager.getLogger(LogUtil.class.getName());
    /**
     * 上一次日志记录到session中的key
     */
    private static final String PREV_LOG_SAVE_KEY = "LOG_SAVE";

    @Autowired
    private LogService logService;
    private static LogUtil logUtil;

    public void setLogService(LogService logService) {
        this.logService = logService;
    }

    @PostConstruct
    public void init() {
        logUtil = this;
        logUtil.logService = this.logService;
    }

    /**
     * 记录日志
     *
     * @param request
     * @param logEvent
     * @param logUseType
     * @param logType
     * @param logTextContent
     * @param logResult      操作结果
     */
    public static void recordLog(HttpServletRequest request, String logEvent, Integer logUseType, String logType, String logTextContent, int logResult) {
        if (ValidateUtil.isEmpty(logTextContent)) {
            return;
        }

        Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(7);

        try {
            ActiveUser activeUser = AuthcUtil.getCurrentUser();
            if (activeUser != null) {
                paramMap.put("SO_ID", activeUser.getId());
            }
            if (request != null) {
                paramMap.put("SL_IP", HttpUtil.getIpAddr(request));
            }

            paramMap.put("SL_EVENT", logEvent);
            paramMap.put("SL_ENTERTIME", DateUtil.getDate());
            paramMap.put("SL_USETYPE", logUseType);
            paramMap.put("SL_TYPE", logType);

            paramMap.put("SL_RESULT", logResult);
            //日志内容
            paramMap.put("SLT_CONTENT", logTextContent);
            //防止重复记录
            if (!toString(paramMap).equals(SessionUtil.get(PREV_LOG_SAVE_KEY))) {
                SessionUtil.set(PREV_LOG_SAVE_KEY, toString(paramMap));
                logUtil.logService.insertLog(paramMap);
                logger.info("身份:" + (activeUser != null ? ParamTypeResolve.getOpeatorTypeName(activeUser.getType()) : "")
                        + ",用户:" + activeUser.getUsername()
                        + ",操作:" + logEvent
                        + ",内容:" + logTextContent
                        + ",结果:" + logResult);
            }
        } catch (Exception e) {
            logger.error("日志操作错误:" + e + "-------------" + e.getMessage());
        }
    }

    public static String toString(Object obj) {
        return TextUtil.toString(obj);
    }
}

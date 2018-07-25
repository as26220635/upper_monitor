package cn.kim.tools;

import cn.kim.common.attr.Attribute;
import cn.kim.common.eu.ProcessType;
import cn.kim.service.ProcessService;
import cn.kim.util.TextUtil;
import cn.kim.util.ValidateUtil;
import com.google.common.collect.Maps;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * Created by 余庚鑫 on 2018/6/11
 * 流程工具
 */
@Component
public class ProcessTool {
    private static Logger logger = LogManager.getLogger(ProcessTool.class.getName());

    @Autowired
    private ProcessService processService;
    private static ProcessTool processTool;

    public void setProcessService(ProcessService processService) {
        this.processService = processService;
    }

    @PostConstruct
    public void init() {
        processTool = this;
        processTool.processService = this.processService;
    }

    /**
     * 获取按钮名称
     *
     * @param type
     * @return
     */
    public static String getProcessButtonTypeName(String type) {
        if (type.equals(ProcessType.SUBMIT.toString())) {
            return "提交";
        } else if (type.equals(ProcessType.BACK.toString())) {
            return "退回";
        } else if (type.equals(ProcessType.WITHDRAW.toString())) {
            return "撤回";
        }
        return "";
    }

    /**
     * 获取流程按钮HTML
     *
     * @param btnTypes
     * @param isTop    是否顶部按钮
     * @return
     */
    public static String getProcessButtonListHtml(String btnTypes, boolean isTop) {
        StringBuilder builder = new StringBuilder();
        if (!ValidateUtil.isEmpty(btnTypes)) {
            for (String btnType : btnTypes.split(Attribute.SERVICE_SPLIT)) {
                builder.append(getProcessButtonHtml(TextUtil.toInt(btnType), isTop));
            }
        }
        return builder.toString();
    }

    /**
     * 获取流程按钮HTML
     *
     * @param btnType
     * @param isTop   是否顶部按钮
     * @return
     */
    public static String getProcessButtonHtml(int btnType, boolean isTop) {
        String cls = !isTop ? "btn-xs" : "";
        if (btnType == ProcessType.SUBMIT.getType()) {
            return "<button type='button' class='btn btn-info " + cls + "' id='PROCESS_SUBMIT'><i class='mdi mdi-arrow-up-thick'></i>提交</button>";
        } else if (btnType == ProcessType.BACK.getType()) {
            return "<button type='button' class='btn btn-danger " + cls + "' id='PROCESS_BACK'><i class='mdi mdi-arrow-down-thick'></i>退回</button>";
        } else if (btnType == ProcessType.WITHDRAW.getType()) {
            return "<button type='button' class='btn btn-danger " + cls + "' id='PROCESS_WITHDRAW'><i class='mdi mdi-arrow-down-thick'></i>撤回</button>";
        }
        return "";
    }

    /**
     * 提交流程
     *
     * @param mapParam
     * @return
     */
    public static Map<String, Object> submitProcess(Map<String, Object> mapParam) {
        return processTool.processService.processSubmit(mapParam);
    }

    /**
     * 撤回流程
     *
     * @param mapParam
     * @return
     */
    public static Map<String, Object> withdrawProcess(Map<String, Object> mapParam) {
        return processTool.processService.processWithdraw(mapParam);
    }

    /**
     * 作废流程
     *
     * @param scheduleId   流程进度ID
     * @param cancelReason 作废原因
     * @return
     */
    public static Map<String, Object> cancelProcess(String scheduleId, String cancelReason) {
        Map<String, Object> mapParam = Maps.newHashMapWithExpectedSize(2);
        mapParam.put("ID", scheduleId);
        mapParam.put("SPSC_REASON", scheduleId);
        return processTool.processService.cancelProcessSchedule(mapParam);
    }

}

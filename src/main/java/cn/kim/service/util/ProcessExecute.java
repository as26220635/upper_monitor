package cn.kim.service.util;

import cn.kim.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by 余庚鑫 on 2018/6/14
 * 流程执行
 */
@Component
public class ProcessExecute extends BaseServiceImpl {

    /**
     * 前进执行
     *
     * @return
     */
    public String advanceExecute(Map<String, Object> mapParam) {
        String error = "";

        return error;
    }

    /**
     * 后退执行
     *
     * @return
     */
    public String retreatExecute(Map<String, Object> mapParam) {
        String error = "";

        return error;
    }
}

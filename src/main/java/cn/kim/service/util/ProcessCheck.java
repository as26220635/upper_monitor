package cn.kim.service.util;

import cn.kim.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by 余庚鑫 on 2018/6/14
 * 流程校验
 */
@Component
public class ProcessCheck extends BaseServiceImpl {

    /**
     * 前进校验
     *
     * @return
     */
    public String advanceCheck(Map<String, Object> mapParam) {
        String error = "";

        return error;
    }

    /**
     * 后退校验
     *
     * @return
     */
    public String retreatCheck(Map<String, Object> mapParam) {
        String error = "";

        return error;
    }
}

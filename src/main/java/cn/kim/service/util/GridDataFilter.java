package cn.kim.service.util;

import cn.kim.common.attr.MagicValue;
import cn.kim.common.attr.TableViewName;
import cn.kim.entity.ActiveUser;
import cn.kim.service.impl.BaseServiceImpl;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import javax.swing.text.TableView;
import java.util.Map;

/**
 * Created by 余庚鑫 on 2018/6/11
 * 自定义过滤
 */
@Component
public class GridDataFilter extends BaseServiceImpl {

    /**
     * 配置列表
     */
    private Map<String, Object> configure;

    /**
     * 请求参数
     */
    private Map<String, Object> requestMap;

    public GridDataFilter(Map<String, Object> configure, Map<String, Object> requestMap) {
        this.configure = configure;
        this.requestMap = requestMap;
    }

    /**
     * 初始化
     *
     * @param configure
     * @param requestMap
     * @return
     */
    @NotNull
    public static GridDataFilter getInstance(Map<String, Object> configure, Map<String, Object> requestMap) {
        return new GridDataFilter(configure, requestMap);
    }

    /**
     * 获取自定义过滤where条件
     *
     * @return
     */
    public String filterWhereSql() {
        StringBuilder resultBuilder = new StringBuilder();

        ActiveUser activeUser = getActiveUser();
        String configureView = toString(this.configure.get("SC_VIEW")).toLowerCase();

        if (TableViewName.V_TEST_PROCESS.equals(configureView)) {
            //测试流程
            resultBuilder.append(" AND SO_ID = " + activeUser.getId());
        }


        return resultBuilder.toString();
    }
}

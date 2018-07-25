package cn.kim.service.impl;

import cn.kim.common.attr.MagicValue;
import cn.kim.common.eu.NameSpace;
import cn.kim.common.eu.ProcessShowStatus;
import cn.kim.entity.*;
import cn.kim.service.DataGridService;
import cn.kim.service.util.GridDataFilter;
import cn.kim.util.CommonUtil;
import cn.kim.util.DictUtil;
import cn.kim.util.TextUtil;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;

import java.security.InvalidKeyException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by 余庚鑫 on 2018/3/26
 */
@Service
public class DataGridServiceImpl extends BaseServiceImpl implements DataGridService {

    @Override
    public Map<String, Object> selectConfigureById(String configureId) {
        Map<String, Object> resultMap = Maps.newHashMapWithExpectedSize(3);
        Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(1);
        //查询配置列表
        paramMap.clear();
        paramMap.put("ID", configureId);
        Map<String, Object> configure = baseDao.selectOne(NameSpace.ConfigureMapper, "selectConfigure", paramMap);
        //查询字段
        paramMap.clear();
        paramMap.put("SC_ID", configureId);
        List<Map<String, Object>> columnList = baseDao.selectList(NameSpace.ConfigureMapper, "selectConfigureColumn", paramMap);
        //查询搜索
        paramMap.clear();
        paramMap.put("SC_ID", configureId);
        List<Map<String, Object>> searchList = baseDao.selectList(NameSpace.ConfigureMapper, "selectConfigureSearch", paramMap);

        resultMap.put("configure", configure);
        resultMap.put("columnList", columnList);
        resultMap.put("searchList", searchList);
        return resultMap;
    }

    @Override
    public DataTablesView<Map<String, Object>> selectByMap(Map<String, Object> mapParam) throws Exception {
        Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(1);
        String configureId = toString(mapParam.get("ID"));
        //查询按钮
        paramMap.clear();
        paramMap.put("ID", mapParam.get("SM_ID"));
        Map<String, Object> menu = baseDao.selectOne(NameSpace.MenuMapper, "selectMenu", paramMap);
        //查询配置列表
        Map<String, Object> configureMap = this.selectConfigureById(configureId);
        //配置列表
        Map<String, Object> configure = (Map<String, Object>) configureMap.get("configure");
        //查询字段
        List<Map<String, Object>> columnList = (List<Map<String, Object>>) configureMap.get("columnList");
        //查询搜索字段
        List<Map<String, Object>> searchList = (List<Map<String, Object>>) configureMap.get("searchList");

        DataTablesView<Map<String, Object>> dataTablesView = new DataTablesView<>();
        QuerySet querySet = new QuerySet();
        //拿到查询符号
        DictType methods = DictUtil.getDictType("SYS_SEARCH_METHOD");
        //list转为map查询
        Map<String, String> methodsMap = methods.getInfos().stream().collect(Collectors.toMap(DictInfo::getSdiCode, DictInfo::getSdiInnercode));
        //设置查询条件
        if (!isEmpty(searchList)) {
            searchList.forEach(search -> {
                String field = toString(search.get("SCS_FIELD"));
                if (mapParam.containsKey(field) && !isEmpty(mapParam.get(field))) {
                    //设置查询条件
                    querySet.set(methodsMap.get(toString(search.get("SCS_METHOD_TYPE"))), field, toString(mapParam.get(field)));
                }
            });
        }
        //是否拥有流程
        boolean isProcess = false;
        //查询是否拥有流程
        String processDefinitionId = toString(menu.get("SPD_ID"));
        if (!isEmpty(processDefinitionId)) {
            paramMap.clear();
            paramMap.put("ID", processDefinitionId);
            Map<String, Object> definition = baseDao.selectOne(NameSpace.ProcessFixedMapper, "selectProcessDefinition", paramMap);

            //流程没有停用
            if (!isDiscontinuation(definition)) {
                String roleId = toString(definition.get("SR_ID"));
                //0 全部 1 待审 2 已审
                String processStatus = toString(mapParam.get("processStatus"));
                //设置流程查询ID
                querySet.setProcessDefinitionId(processDefinitionId);

                //流程过滤
                if (!ProcessShowStatus.ALL.toString().equals(processStatus) || isEmpty(roleId) || !containsRole(roleId)) {
                    ActiveUser activeUser = getActiveUser();

                    String baseWhere = "SELECT SPS_TABLE_ID FROM SYS_PROCESS_SCHEDULE WHERE SPD_ID = '" + processDefinitionId + "' AND SPS_IS_CANCEL = 0 ";
                    //WHERE过滤语句
                    StringBuilder processWhereBuilder = new StringBuilder();
                    //待审SQL语句
                    StringBuilder stayBuilder = new StringBuilder();
                    //查询没有流程和流程为未启动、撤回的
                    stayBuilder.append("SELECT SV.ID AS SPS_TABLE_ID FROM " + toString(configure.get("SC_VIEW")) + " SV " +
                            "   LEFT JOIN SYS_PROCESS_SCHEDULE SPS ON SPS.SPS_TABLE_ID = SV.ID AND SPS.SPS_IS_CANCEL = 0" +
                            "   WHERE SV.SO_ID = '" + activeUser.getId() + "' AND (SPS.ID IS NULL OR SPS.SPS_AUDIT_STATUS = 0 OR (SPS.SPS_AUDIT_STATUS = -1 AND SPS.SPS_BACK_STATUS = 2))");
                    if (!containsRole(roleId)) {
                        //查询自身角色是否在流程中
                        paramMap.clear();
                        paramMap.put("SPD_ID", processDefinitionId);
                        List<Map<String, Object>> stepList = baseDao.selectList(NameSpace.ProcessFixedMapper, "selectProcessStep", paramMap);
                        //获取需要查询的角色
                        stayBuilder.append(" UNION ALL " + baseWhere + " AND SPS_STEP_TYPE = 1 AND SPS_STEP_TRANSACTOR IN (" + TextUtil.toString(getExistRoleList(TextUtil.joinValue(stepList, "SR_ID", SERVICE_SPLIT))) + ") ");

                        stayBuilder.append(" UNION ALL " + baseWhere + " AND SPS_STEP_TYPE = 2 AND SPS_STEP_TRANSACTOR = '" + activeUser.getId() + "' ");
                    }

                    //流程配置了自身角色可以查看全部的话就不进行过滤
                    processWhereBuilder.append(" AND ID IN(");
                    //待审
                    if (isEmpty(processStatus) || ProcessShowStatus.ALL.toString().equals(processStatus) || ProcessShowStatus.STAY.toString().equals(processStatus)) {
                        processWhereBuilder.append(stayBuilder.toString());
                    } else {
                        //已审
                        processWhereBuilder.append("SELECT SPS.SPS_TABLE_ID FROM SYS_PROCESS_SCHEDULE SPS " +
                                "   INNER JOIN SYS_PROCESS_LOG SPL ON SPL.SPS_ID = SPS.ID AND SPL.SPL_SO_ID = '" + activeUser.getId() + "' " +
                                "   WHERE SPS.SPS_IS_CANCEL = 0  AND SPS.SPS_TABLE_ID NOT IN(" + stayBuilder.toString() + ") " +
                                "   GROUP BY SPS.SPS_TABLE_ID");
                    }

                    processWhereBuilder.append(") ");

                    querySet.setWhere(processWhereBuilder.toString());
                }

                isProcess = true;
            }
        }
        //是否开启自定义过滤
        if (!isProcess && toString(configure.get("SC_IS_FILTER")).equals(toString(STATUS_SUCCESS))) {
            querySet.setWhere(GridDataFilter.getInstance(configure, mapParam).filterWhereSql());
        }

        int offset = toInt(mapParam.get("start"));
        int limit = toInt(mapParam.get("length"));

        querySet.setView(toString(configure.get("SC_VIEW")));
        if (limit != -1) {
            querySet.setOffset(offset);
            querySet.setLimit(limit);
        }
        querySet.setOrderByClause(toString(configure.get("SC_ORDER_BY")));

        long count = baseDao.selectOne(NameSpace.DataGridMapper, "countByMap", querySet.getWhereMap());
        dataTablesView.setRecordsTotal(count);
        if (limit != -1) {
            dataTablesView.setTotalPages(CommonUtil.getPage(count, limit));
        }

        List<Map<String, Object>> dataList = baseDao.selectList(NameSpace.DataGridMapper, "selectByMap", querySet.getWhereMap());
        //字典格式化参数
        if (!isEmpty(columnList)) {
            columnList.forEach(column -> {
                String columnSdtCode = toString(column.get("SCC_SDT_CODE"));
                String columnField = toString(column.get("SCC_FIELD"));
                if (!isEmpty(columnSdtCode)) {
                    dataList.forEach(data -> {
                        if (data.containsKey(columnField)) {
                            //查询字典设置格式化值
                            data.put(columnField, DictUtil.getDictName(columnSdtCode, toString(data.get(columnField))));
                        }
                    });
                }
            });

        }
        dataTablesView.setData(dataList);

        return dataTablesView;
    }
}

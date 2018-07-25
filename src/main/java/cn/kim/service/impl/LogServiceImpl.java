package cn.kim.service.impl;

import cn.kim.common.eu.NameSpace;
import cn.kim.entity.DataTablesView;
import cn.kim.entity.QuerySet;
import cn.kim.service.LogService;
import cn.kim.util.CommonUtil;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 余庚鑫 on 2017/4/2.
 */

@Service
public class LogServiceImpl extends BaseServiceImpl implements LogService {

    /**
     * 查询列表
     *
     * @param mapParam
     * @return
     * @throws Exception
     */
    @Override
    public DataTablesView<Map<String, Object>> findByMap(Map<String, Object> mapParam) throws Exception {
        DataTablesView<Map<String, Object>> sysNewsDatatablesView = new DataTablesView<Map<String, Object>>();

        QuerySet querySet = new QuerySet();
        //日志标题
        querySet.set(QuerySet.LIKE, "SL.SL_NAME", mapParam.get("SL_NAME"));
        //发布时间
        querySet.setBetween("SL.SL_ENTERTIME", mapParam.get("START_TIME"), mapParam.get("END_TIME"));
        //状态
        querySet.set(QuerySet.EQ, "SL.SL_RESULT", mapParam.get("SL_RESULT"));

        int offset = toInt(mapParam.get("start"));
        int limit = toInt(mapParam.get("lenght"));
        querySet.setOffset(offset);
        querySet.setLimit(limit);
        querySet.setOrderByClause("SL.ID DESC");

        //查询总数
        int count = baseDao.selectOne(NameSpace.LogMapper, "countByMap", querySet.getWhereMap());
        sysNewsDatatablesView.setRecordsTotal(count);
        sysNewsDatatablesView.setTotalPages(CommonUtil.getPage(count, limit));
        sysNewsDatatablesView.setData(baseDao.selectList(NameSpace.LogMapper, "selectByMap", querySet.getWhereMap()));

        return sysNewsDatatablesView;
    }

    /**
     * 插入日志
     *
     * @param mapParam
     * @throws Exception
     */
    @Override
    public void insertLog(Map<String, Object> mapParam) throws Exception {
        String id = getId();
        Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(9);
        paramMap.put("ID", id);
        paramMap.put("SO_ID", mapParam.get("SO_ID"));
        paramMap.put("SL_NAME", mapParam.get("SL_NAME"));
        paramMap.put("SL_EVENT", mapParam.get("SL_EVENT"));
        paramMap.put("SL_IP", mapParam.get("SL_IP"));
        paramMap.put("SL_RESULT", mapParam.get("SL_RESULT"));
        paramMap.put("SL_ENTERTIME", mapParam.get("SL_ENTERTIME"));
        paramMap.put("SL_TYPE", mapParam.get("SL_TYPE"));
        paramMap.put("SL_USETYPE", mapParam.get("SL_USETYPE"));
        //插入日志
        baseDao.insert(NameSpace.LogMapper, "insertLog", paramMap);

        paramMap.clear();
        paramMap.put("ID", getId());
        paramMap.put("SL_ID", id);
        paramMap.put("SLT_CONTENT", mapParam.get("SLT_CONTENT"));
        //插入日志内容
        baseDao.insert(NameSpace.LogMapper, "insertLogText", paramMap);
    }
}

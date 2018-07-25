package cn.kim.dao.impl;

import cn.kim.common.annotation.Validate;
import cn.kim.common.eu.NameSpace;
import cn.kim.common.eu.ValueRecordType;
import cn.kim.common.sequence.Sequence;
import cn.kim.dao.BaseDao;
import cn.kim.entity.ActiveUser;
import cn.kim.util.AuthcUtil;
import cn.kim.util.DateUtil;
import cn.kim.util.TextUtil;
import cn.kim.util.ValidateUtil;
import com.google.common.collect.Maps;
import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class BaseDaoImpl extends SqlSessionDaoSupport implements BaseDao {

    @Autowired
    private BaseDao sqlSession;

    @Override
    public <T> T selectOne(NameSpace nameSpace, String sqlId) {
        return super.getSqlSession().<T>selectOne(getStatement(nameSpace, sqlId));
    }

    @Override
    public <T> T selectOne(NameSpace nameSpace, String sqlId, Object parameter) {
        return super.getSqlSession().<T>selectOne(getStatement(nameSpace, sqlId), parameter);
    }

    @Override
    public <E> List<E> selectList(NameSpace nameSpace, String sqlId) {
        return super.getSqlSession().selectList(getStatement(nameSpace, sqlId));
    }

    @Override
    public <E> List<E> selectList(NameSpace nameSpace, String sqlId, Object parameter) {
        return super.getSqlSession().selectList(getStatement(nameSpace, sqlId), parameter);
    }

    @Override
    public <E> List<E> selectList(NameSpace nameSpace, String sqlId, Object parameter, int offset, int limit) {
        return super.getSqlSession().selectList(getStatement(nameSpace, sqlId), parameter, new RowBounds(offset, limit));
    }

    @Override
    public int insert(NameSpace nameSpace, String sqlId, Map<String, Object> parameter) throws Exception {
        int count = 0;
        try {
            count = super.getSqlSession().insert(getStatement(nameSpace, sqlId), parameter);

            //记录数据
            String tableName = TextUtil.toString(parameter.get("SVR_TABLE_NAME"));
            //是否不需要记录
            boolean notRecord = TextUtil.toBoolean(parameter.get("NOT_RECORD"));

            if (!notRecord && !ValidateUtil.isEmpty(tableName) && !ValidateUtil.isEmpty(parameter.get("ID"))) {
                this.record(parameter.get("ID"), tableName, null, TextUtil.toJSONString(parameter), ValueRecordType.INSERT.getType());
            }
        } catch (Exception e) {
            throw e;
        }
        return count;
    }

    @Override
    public int update(NameSpace nameSpace, String sqlId, Map<String, Object> parameter) throws Exception {
        int count = 0;
        try {
            Map<String, Object> oldValue = null;
            //记录数据
            String tableName = TextUtil.toString(parameter.get("SVR_TABLE_NAME"));
            //是否不需要记录
            boolean notRecord = TextUtil.toBoolean(parameter.get("NOT_RECORD"));
            //查询旧值
            if (!notRecord && !ValidateUtil.isEmpty(tableName)) {
                oldValue = selectValue(parameter.get("ID"), tableName);
            }
            //更新
            count = super.getSqlSession().update(getStatement(nameSpace, sqlId), parameter);

            //查询更新完后的新值
            if (!notRecord && !ValidateUtil.isEmpty(tableName) && !ValidateUtil.isEmpty(parameter.get("ID"))) {
                Map<String, Object> newValue = Maps.newHashMapWithExpectedSize(16);
                //把更新的字段取出来
                oldValue.keySet().forEach(key -> {
                    if (parameter.containsKey(key)) {
                        newValue.put(key, parameter.get(key));
                    }
                });
                //移除未更新的字段
                oldValue.keySet().removeIf(key -> !newValue.containsKey(key));

                this.record(parameter.get("ID"), tableName, TextUtil.toJSONString(oldValue), TextUtil.toJSONString(newValue), ValueRecordType.UPDATE.getType());
            }
        } catch (Exception e) {
            throw e;
        }
        return count;
    }

    @Override
    public int delete(NameSpace nameSpace, String sqlId, Map<String, Object> parameter) throws Exception {
        int count = 0;
        try {
            count = super.getSqlSession().delete(getStatement(nameSpace, sqlId), parameter);

            //记录数据
            String tableName = TextUtil.toString(parameter.get("SVR_TABLE_NAME"));
            //是否不需要记录
            boolean notRecord = TextUtil.toBoolean(parameter.get("NOT_RECORD"));

            if (!notRecord && !ValidateUtil.isEmpty(tableName) && !ValidateUtil.isEmpty(parameter.get("ID"))) {
                this.record(parameter.get("ID"), tableName, null, null, ValueRecordType.DELETE.getType());
            }
        } catch (Exception e) {
            throw e;
        }
        return count;
    }

    @Override
    public void commit() {
        try {
            if (!super.getSqlSession().getConnection().isClosed()) {
                super.getSqlSession().getConnection().commit();
            }
            if (!sqlSession.getConnection().isClosed()) {
                sqlSession.getConnection().commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void rollback() {
        try {
            if (!super.getSqlSession().getConnection().isClosed()) {
                super.getSqlSession().getConnection().rollback();
            }
            if (!sqlSession.getConnection().isClosed()) {
                sqlSession.getConnection().rollback();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<BatchResult> flushStatements() {
        return super.getSqlSession().flushStatements();
    }

    @Override
    public void close() {
        super.getSqlSession().close();
    }

    @Override
    public void clearCache() {
        super.getSqlSession().clearCache();
    }

    @Override
    public Configuration getConfiguration() {
        return super.getSqlSession().getConfiguration();
    }

    @Override
    public <T> T getMapper(Class<T> type) {
        return super.getSqlSession().getMapper(type);
    }

    @Override
    public Connection getConnection() {
        return super.getSqlSession().getConnection();
    }

    private String getStatement(NameSpace nameSpace, String sqlId) {
        return nameSpace.getValue() + "." + sqlId;
    }

    /**
     * 查询值
     *
     * @param tableId
     * @param tableName
     * @return
     */
    private Map<String, Object> selectValue(Object tableId, Object tableName) {
        Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(2);
        paramMap.put("ID", tableId);
        paramMap.put("SVR_TABLE_NAME", tableName);
        return this.selectOne(NameSpace.ValueRecordMapper, "selectValue", paramMap);
    }

    /**
     * 记录变更参数
     *
     * @param tableId   id
     * @param tableName 表名
     * @param oldValue  旧值 JSONString
     * @param newValue  新值 JSONString
     * @param type      类型
     * @throws Exception
     */
    private void record(Object tableId, Object tableName, Object oldValue, Object newValue, int type) throws Exception {
        if (ValidateUtil.isEmpty(tableId) || ValidateUtil.isEmpty(tableName)) {
            return;
        }
        ActiveUser activeUser = AuthcUtil.getCurrentUser();

        Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(8);
        paramMap.put("ID", Sequence.getId());
        if (!ValidateUtil.isEmpty(activeUser)) {
            paramMap.put("SO_ID", activeUser.getId());
        }
        paramMap.put("SVR_TABLE_NAME", tableName);
        paramMap.put("SVR_TABLE_ID", tableId);
        paramMap.put("SVR_OLD_VALUE", oldValue);
        paramMap.put("SVR_NEW_VALUE", newValue);
        paramMap.put("SVR_ENTRY_TIME", DateUtil.getDate());
        paramMap.put("SVR_TYPE", type);
        paramMap.put("NOT_RECORD", true);
        this.insert(NameSpace.ValueRecordMapper, "insertValueRecord", paramMap);
    }

}

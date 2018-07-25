package cn.kim.dao;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import cn.kim.common.eu.NameSpace;
import cn.kim.common.eu.NameSpace;
import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.session.Configuration;

public interface BaseDao {

    public <T> T selectOne(NameSpace nameSpace, String sqlId);

    public <T> T selectOne(NameSpace nameSpace, String sqlId, Object parameter);

    public <E> List<E> selectList(NameSpace nameSpace, String sqlId);

    public <E> List<E> selectList(NameSpace nameSpace, String sqlId, Object parameter);

    public <E> List<E> selectList(NameSpace nameSpace, String sqlId, Object parameter, int offset, int limit);

    /**
     * SVR_TABLE_NAME 保存参数记录的表名  NOT_RECORD boolean 不需要记录
     *
     * @param nameSpace
     * @param sqlId
     * @param parameter
     * @return
     * @throws Exception
     */
    int insert(NameSpace nameSpace, String sqlId, Map<String, Object> parameter) throws Exception;

    int update(NameSpace nameSpace, String sqlId, Map<String, Object> parameter) throws Exception;

    int delete(NameSpace nameSpace, String sqlId, Map<String, Object> parameter) throws Exception;

    void commit();

    void rollback();

    List<BatchResult> flushStatements();

    void close();

    void clearCache();

    Configuration getConfiguration();

    <T> T getMapper(Class<T> type);

    Connection getConnection();

}

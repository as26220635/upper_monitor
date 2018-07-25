package cn.kim.common;

import cn.kim.dao.BaseDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by 余庚鑫 on 2018/6/15
 * 获取数据库连接
 */
@Component
public class DaoSession {

    private static Logger logger = LogManager.getLogger(DaoSession.class.getName());

    @Autowired
    public BaseDao baseDao;
    public static DaoSession daoSession;

    public void setBaseDao(BaseDao baseDao) {
        this.baseDao = baseDao;
    }

    @PostConstruct
    public void init() {
        daoSession = this;
        daoSession.baseDao = this.baseDao;
    }

}

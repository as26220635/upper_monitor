package cn.kim.common;

import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by 余庚鑫 on 2018/3/22
 */
public class MyServletContextListener implements ServletContextListener {

    /**
     * 启动时
     */
    @Override
    public void contextInitialized(ServletContextEvent arg0) {

        System.out.println("====服务器启动=====");
        // 读取配置文件
        if (ConfigLoad.ROOT_PATH == null) {
            new ConfigLoad();
        }

    }

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {

    }

}


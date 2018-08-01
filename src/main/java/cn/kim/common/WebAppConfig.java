package cn.kim.common;

import cn.kim.common.netty.TCPServerNetty;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * Created by 余庚鑫 on 2018/7/30.
 */
@Configuration
@ComponentScan
@EnableCaching
public class WebAppConfig {
    /**
     * 端口
     */
    @Value("#{config['tcp.service.port']}")
    private int port;

    /**
     * TCP客户端
     *
     * @return
     * @throws IOException
     */
    @Bean("tcpServerNetty")
    public TCPServerNetty tcpServerNetty() throws IOException {
        TCPServerNetty tcpServerNetty = new TCPServerNetty(port);
        return tcpServerNetty;
    }

}
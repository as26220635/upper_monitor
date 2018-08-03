package cn.kim.common;

import cn.kim.common.netty.TCPServerNetty;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

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

    @Bean(destroyMethod = "shutdown")
    public RedissonClient redisson(@Value("classpath:redis/spring-redisson.yaml") Resource configFile) throws IOException {
        Config config = Config.fromYAML(configFile.getInputStream());
        return Redisson.create(config);
    }

    @Bean("cacheManager")
    public CacheManager cacheManager(RedissonClient redissonClient) throws IOException {
        return new RedissonSpringCacheManager(redissonClient, "classpath:redis/spring-cache-config.yaml");
    }

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
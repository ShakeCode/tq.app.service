package com.org.dream;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Date;
import java.util.TimeZone;

/*解决占用问题

1、查找端口占用的进程ID
netstat -ano | findstr 8019

2、查找进程ID 对应的程序
tasklist | findstr 1124

3、终止运行的程序
taskkill /f /t  /im java.exe*/


@EnableKafka
@EnableDiscoveryClient
@EnableCaching
@EnableScheduling
@EnableAsync
@MapperScan("com.org.dream.dao")
@SpringBootApplication(scanBasePackages = {"com.org.dream"}, exclude = DruidDataSourceAutoConfigure.class)
public class Application {
    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

    private static ConfigurableApplicationContext configurableApplicationContext;

    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC+8"));
        LOGGER.error("现在的0时区时间:{}", new Date());
        FastJsonConfig config = new FastJsonConfig();
        config.setSerializerFeatures(SerializerFeature.WriteEnumUsingToString);
        configurableApplicationContext = SpringApplication.run(Application.class, args);
    }
}

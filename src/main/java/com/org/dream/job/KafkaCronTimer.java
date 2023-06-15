package com.org.dream.job;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


/**
 * The type Kafka cron timer.
 */
@Slf4j
@EnableScheduling
@Component
public class KafkaCronTimer {
    /**
     * @KafkaListener注解所标注的方法并不会在IOC容器中被注册为Bean， 而是会被注册在KafkaListenerEndpointRegistry中，
     * 而KafkaListenerEndpointRegistry在SpringIOC中已经被注册为Bean
     **/
    @Autowired
    private KafkaListenerEndpointRegistry registry;

    @Autowired
    private ConsumerFactory consumerFactory;

    /**
     * Delay container factory concurrent kafka listener container factory.监听器容器工厂(设置禁止KafkaListener自启动)
     * @return the concurrent kafka listener container factory
     */
    @Bean
    public ConcurrentKafkaListenerContainerFactory delayContainerFactory() {
        ConcurrentKafkaListenerContainerFactory container = new ConcurrentKafkaListenerContainerFactory();
        container.setConsumerFactory(consumerFactory);
        // 禁止KafkaListener自启动
        container.setAutoStartup(false);
        return container;
    }

    /**
     * On message 1.  监听器
     * @param record the record
     */
    @KafkaListener(id = "timingConsumer", topics = "topicxx", containerFactory = "delayContainerFactory")
    public void onMessage1(ConsumerRecord<?, ?> record) {
        log.info("消费成功：" + record.topic() + "-" + record.partition() + "-" + record.value());
    }

    /**
     * Start listener. 定时启动监听器
     */
    @Scheduled(cron = "0 42 11 * * ? ")
    public void startListener() {
        log.info("启动监听器...");
        // "timingConsumer"是@KafkaListener注解后面设置的监听器ID,标识这个监听器
        if (!registry.getListenerContainer("timingConsumer").isRunning()) {
            registry.getListenerContainer("timingConsumer").start();
        }
        //registry.getListenerContainer("timingConsumer").resume();
    }

    /**
     * Shut down listener.定时停止监听器
     */
    @Scheduled(cron = "0 45 11 * * ? ")
    public void shutDownListener() {
        log.info("关闭监听器...");
        registry.getListenerContainer("timingConsumer").pause();
    }
}
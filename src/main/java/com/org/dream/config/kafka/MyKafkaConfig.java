package com.org.dream.config.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.ConsumerAwareListenerErrorHandler;

/**
 * The type My kafka config.
 */
@Slf4j
@Configuration
public class MyKafkaConfig {
    @Autowired
    private ConsumerFactory consumerFactory;


    /**
     * Consumer error handler consumer aware listener error handler.监听异常处理器
     * @return the consumer aware listener error handler
     */
    @Bean
    public ConsumerAwareListenerErrorHandler consumerErrorHandler() {
        return (message, exception, consumer) -> {
            log.info("消费异常：{}", message.getPayload());
            return null;
        };
    }

    /**
     * Filter container factory concurrent kafka listener container factory.消息过滤器
     * @return the concurrent kafka listener container factory
     */
    @Bean
    public ConcurrentKafkaListenerContainerFactory filterContainerFactory() {
        ConcurrentKafkaListenerContainerFactory factory = new ConcurrentKafkaListenerContainerFactory();
        factory.setConsumerFactory(consumerFactory);
        // 被过滤的消息将被丢弃
        factory.setAckDiscarded(true);
        // 消息过滤策略
        factory.setRecordFilterStrategy(consumerRecord -> {
            if (Integer.parseInt(consumerRecord.value().toString()) % 2 != 0) {
                return false;
            }
            // 返回true消息则被过滤
            return true;
        });
        return factory;
    }

}

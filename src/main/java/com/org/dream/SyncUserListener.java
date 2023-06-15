package com.org.dream;

import com.alibaba.fastjson.JSON;
import com.org.dream.constant.KafkaConstant;
import com.org.dream.domain.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.messaging.handler.annotation.SendTo;

import java.util.List;

/**
 * The type Sync user listener.
 */
@Slf4j
@Configuration
public class SyncUserListener {

    /**
     * Consume.
     * @param message the message
     * @param record  the record
     */
    @KafkaListener(topics = {KafkaConstant.USER_TOPIC, KafkaConstant.PERSON_TOPIC})
    public void consume(String message, ConsumerRecord<String, String> record) {
        log.info("SyncUserListener 接收到消息：{}", message);
        log.info("接收到消息,分区：{}, 主题:{},消息值：{},消息偏移:{}", record.partition(), record.topic(), record.value(), record.offset());
        UserDTO userDTO = JSON.parseObject(message, UserDTO.class);
        log.info("正在为:{}, 处理数据", userDTO.getName());
        log.info("注册成功！");
    }

    /**
     * On message . topic、指定partition、指定offset来消费,@SendTo转发到别应用的主题
     * @param record the record
     */
    @KafkaListener(id = "dream-consumer-1", groupId = KafkaConstant.GROUP,
            topicPartitions = {
                    @TopicPartition(topic = KafkaConstant.USER_TOPIC, partitions = {"0"}),
                    @TopicPartition(topic = KafkaConstant.PERSON_TOPIC, partitions = "0", partitionOffsets = @PartitionOffset(partition = "1", initialOffset = "8"))
            })
    @SendTo("topic2")
    public void onMessage(ConsumerRecord<?, ?> record) {
        log.info("指定partition、指定offset来消费. topic:" + record.topic() + "|partition:" + record.partition() + "|offset:" + record.offset() + "|value:" + record.value());
    }

    /**
     * Batch consume.
     * @param records the records
     */
    @KafkaListener(id = "dream-consumer-2", groupId = KafkaConstant.GROUP, topics = KafkaConstant.USER_TOPIC, errorHandler = "consumerErrorHandler")
    public void batchConsume(List<ConsumerRecord<?, ?>> records) {
        log.info(">>>批量消费一次,records.size: {}", records.size());
        for (ConsumerRecord<?, ?> record : records) {
            log.info(record.value() + "");
        }
    }
}

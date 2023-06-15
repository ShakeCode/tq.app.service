package com.org.dream.controller;


import com.alibaba.fastjson.JSON;
import com.org.dream.constant.KafkaConstant;
import com.org.dream.domain.dto.UserDTO;
import com.org.dream.domain.vo.rsp.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.StringJoiner;

/**
 * The type Message controller.
 */
@Slf4j
@RequestMapping("/v1/msg")
@RestController
@Api(value = "发送消息", tags = "发送消息")
public class MessageController {


    private final KafkaTemplate<String, String> kafkaTemplate;

    /**
     * Instantiates a new Message controller.
     * @param kafkaTemplate the kafka template
     */
    public MessageController(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * Register result vo.
     * @param userDTO the user
     * @return the result vo
     */
    @ApiOperation(value = "发送消息")
    @PostMapping("/send")
    public ResultVO<String> send(@RequestBody UserDTO userDTO) {
        String message = JSON.toJSONString(userDTO);
        log.info("接收到用户信息：{}", message);
        kafkaTemplate.send(KafkaConstant.USER_TOPIC, message);
        // 生产者回调
        kafkaTemplate.send(KafkaConstant.PERSON_TOPIC, message).addCallback(success -> {
            // 消息发送到的topic
            String topic = success.getRecordMetadata().topic();
            // 消息发送到的分区
            int partition = success.getRecordMetadata().partition();
            // 消息在分区内的offset
            long offset = success.getRecordMetadata().offset();
            log.info("发送消息成功:{}", new StringJoiner(
                    "-"
            ).add(topic).add(partition + "").add(offset + "").toString());
        }, failure -> {
            log.info("发送消息失败:{}", failure.getMessage());
        });
        return ResultVO.success();
    }

    /**
     * Send message.
     * @param userDTO the user dto
     */
    @ApiOperation(value = "事务发送消息")
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/transaction/send")
    public void sendMessage(@RequestBody UserDTO userDTO) {
        String message = JSON.toJSONString(userDTO);
        log.info("接收到用户信息：{}", message);
        // 声明事务：后面报错消息不会发出去
        kafkaTemplate.executeInTransaction(operations -> {
            operations.send(KafkaConstant.USER_TOPIC, "app", message);
            throw new RuntimeException("fail");
        });
        // 不声明事务：后面报错但前面消息已经发送成功了
        // kafkaTemplate.send(KafkaConstant.USER_TOPIC, message);
        // throw new RuntimeException("fail");
    }
}

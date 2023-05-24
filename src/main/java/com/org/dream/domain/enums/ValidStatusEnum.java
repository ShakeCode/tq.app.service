package com.org.dream.domain.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@Getter
public enum ValidStatusEnum {

    VALID("1", "有效"),
    IN_VALID("0", "无效");


    @EnumValue
    private String status;

    @JsonValue
    private String remark;

    ValidStatusEnum(String status, String remark) {
        this.status = status;
        this.remark = remark;
    }

    public static class Deserializer extends JsonDeserializer<ValidStatusEnum> {
        @Override
        public ValidStatusEnum deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            String text = jsonParser.getText();
            try {
                for (ValidStatusEnum validStatusEnum : ValidStatusEnum.values()) {
                    if (validStatusEnum.getStatus().equals(Integer.valueOf(text))) {
                        return validStatusEnum;
                    }
                }
                return null;
            } catch (Exception e) {
                log.warn("反序列化枚举类出错", e);
                return null;
            }
        }
    }
}
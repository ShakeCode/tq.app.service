package com.org.dream.domain.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.org.dream.enums.BaseEnum;
import lombok.Getter;

@Getter
public enum StatusEnum implements BaseEnum {
    VALID(1, "有效"),
    IN_VALID(0, "无效");

    @EnumValue
    Integer code;

    @JsonValue
    String description;

    StatusEnum(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

}

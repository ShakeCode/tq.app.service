package com.org.dream.domain.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.Getter;

@Getter
public enum AppEnum implements IEnum<Integer> {
    IPHONE("iphone", "苹果");

    private String code;
    private String desc;

    AppEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public Integer getValue() {
        return null;
    }
}

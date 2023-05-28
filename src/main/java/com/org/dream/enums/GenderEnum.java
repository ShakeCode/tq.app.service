package com.org.dream.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GenderEnum {
    FEMALE(0, "女性"),
    MALE(1, "男性");

    private int value;
    private String name;

}

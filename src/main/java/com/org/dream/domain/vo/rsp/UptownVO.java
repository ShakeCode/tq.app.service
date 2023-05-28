package com.org.dream.domain.vo.rsp;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ToString(callSuper = true)
public class UptownVO {

    private String name;


    private String code;


    private Integer age;
}

package com.org.dream.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.org.dream.config.handler.JSONTypeHandler;
import com.org.dream.domain.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ToString(callSuper = true)
@TableName(value = "t_uptown_t",autoResultMap = true)
public class UptownEntity extends BaseEntity {
    @TableField(value = "name")
    private String name;

    @TableField(value = "code")
    private String code;

    @TableField(value = "age")
    private Integer age;

    @TableField(value = "address")
    private String address;

    @TableField(value = "area_code")
    private String areaCode;

    @TableField(value = "extend_config", typeHandler = JSONTypeHandler.class)
    private Object extendConfig;
}

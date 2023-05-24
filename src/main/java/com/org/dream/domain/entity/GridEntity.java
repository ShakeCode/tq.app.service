package com.org.dream.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.org.dream.domain.entity.base.BaseEntity;
import com.org.dream.domain.enums.StatusEnum;
import com.org.dream.domain.enums.ValidStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ToString(callSuper = true)
@TableName(value = "t_grid_m", autoResultMap = true)
public class GridEntity extends BaseEntity {
    /**
     * 编码
     */
    private String code;

    /**
     * 名称
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String name;

    /**
     * 层级
     */
    private Integer level;

    /**
     * 父级编码
     */
    private String parentCode;

    /**
     * 父级全路径编码
     */
    private String allParentCode;

    /**
     * 状态(0-无效,1-有效)
     */
    /*@JsonDeserialize(using = ValidStatusEnum.Deserializer.class)
    private Boolean status; */

    // private ValidStatusEnum status;
            
    private StatusEnum status;

    /**
     * 是否删除, 此处逻辑删除, 会影响查询更新的方法过滤掉被逻辑删除的数据
     *
     * value表示逻辑未删除值，delval表示逻辑删除设置的值
     */
    // @TableLogic(value = "0", delval = "1")
    private Boolean isDelete;
}

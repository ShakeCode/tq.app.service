package com.org.dream.domain.vo.req;

import com.org.dream.domain.entity.GridEntity;
import com.org.dream.domain.enums.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ToString(callSuper = true)
public class GridInfo extends GridEntity {
    /**
     * 编码
     */
    private String code;

    /**
     * 名称
     */
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
   /* @JsonDeserialize(using = ValidStatusEnum.Deserializer.class)
    private ValidStatusEnum status;*/

    // @JsonDeserialize(using = ValidStatusEnum.Deserializer.class)
    private StatusEnum status;

    /**
     * 是否删除
     */
    private Boolean isDelete;
}

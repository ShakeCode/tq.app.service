package com.org.dream.domain.vo.rsp;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class GridVO {
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
}

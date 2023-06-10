package com.org.dream.domain.vo.req;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Page vo.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel("分页参数实体")
public class PageVO {
    private Integer pageIndex = 1;

    private Integer pageSize = 10;

    private String code;
}

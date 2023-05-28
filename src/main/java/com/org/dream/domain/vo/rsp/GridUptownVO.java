package com.org.dream.domain.vo.rsp;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class GridUptownVO {

    private String uptownCode;

    private String uptownName;

    private String gridName;

}

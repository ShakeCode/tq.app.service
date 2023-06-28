package com.org.dream.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.org.dream.domain.entity.GridEntity;
import com.org.dream.domain.entity.UptownEntity;

import java.util.List;

/**
 * The interface Grid service.
 */
public interface GridService extends IService<GridEntity> {
    /**
     * Query multi value like permission grid list.
     * @param gridCodeList the grid code list
     * @return the list
     */
    List<GridEntity> queryMultiValueLikePermissionGrid(List<String> gridCodeList);
}

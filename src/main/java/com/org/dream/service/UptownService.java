package com.org.dream.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.org.dream.domain.entity.UptownEntity;
import com.org.dream.domain.vo.req.PageVO;

/**
 * The interface Uptown service.
 */
public interface UptownService extends IService<UptownEntity> {

    /**
     * Gets uptown.
     * @param pageVO the page vo
     * @return the uptown
     */
    PageInfo<UptownEntity> getUptown(PageVO pageVO);
}

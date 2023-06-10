package com.org.dream.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.org.dream.constant.RedisConstant;
import com.org.dream.dao.UptownDao;
import com.org.dream.domain.entity.UptownEntity;
import com.org.dream.domain.vo.req.PageVO;
import com.org.dream.service.UptownService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The type Uptown service.
 */
@RequiredArgsConstructor
@Service
public class UptownServiceImpl extends ServiceImpl<UptownDao, UptownEntity> implements UptownService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UptownServiceImpl.class);


    @Cacheable(cacheNames = RedisConstant.UPTOWN_CACHE_KEY, key = "#pageVO.code", condition = "#pageVO.code !=null", unless = "#result == null")
    @Override
    public PageInfo<UptownEntity> getUptown(PageVO pageVO) {
        LOGGER.info("getUptown...");
        PageHelper.startPage(pageVO.getPageIndex(), pageVO.getPageSize());
        LambdaQueryWrapper<UptownEntity> queryWrapper = Wrappers.<UptownEntity>lambdaQuery();
        if (StringUtils.isNotBlank(pageVO.getCode())) {
            queryWrapper.eq(UptownEntity::getCode, pageVO.getCode());
        }
        List<UptownEntity> list = this.list(queryWrapper);
        return PageInfo.of(list);
    }
}

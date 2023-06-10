package com.org.dream.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.org.dream.constant.RedisConstant;
import com.org.dream.domain.entity.UptownEntity;
import com.org.dream.domain.vo.req.PageVO;
import com.org.dream.domain.vo.req.UptownInfo;
import com.org.dream.domain.vo.rsp.ResultVO;
import com.org.dream.service.UptownService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/v1/uptown")
@RequiredArgsConstructor
@RestController
@Api(value = "小区业务相关的api", tags = "小区业务相关的api")
public class UptownController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UptownController.class);

    private final UptownService uptownService;

    @ApiOperation(value = "新增小区")
    @PostMapping("/add")
    public ResultVO<Void> getUptown(@RequestBody UptownInfo uptownInfo) {
        uptownInfo.setExtendConfig(uptownInfo);
        uptownService.save(uptownInfo);
        // uptownService.getBaseMapper().insert(uptownInfo);
        return ResultVO.success();
    }

    @ApiOperation(value = "删除小区")
    @PostMapping("/delete")
    public ResultVO<Void> getUptown(@RequestBody List<Integer> idList) {
        uptownService.getBaseMapper().deleteBatchIds(idList);
        return ResultVO.success();
    }

    @Cacheable(cacheNames = RedisConstant.UPTOWN_CACHE_KEY, key = "#pageVO.code")
    @ApiOperation(value = "查询小区")
    @PostMapping("/list")
    public ResultVO<PageInfo<UptownEntity>> getUptown(@RequestBody PageVO pageVO) {
        LOGGER.info("getUptown...");
        PageHelper.startPage(pageVO.getPageIndex(), pageVO.getPageSize());
        LambdaQueryWrapper<UptownEntity> queryWrapper = Wrappers.<UptownEntity>lambdaQuery()
                .eq(UptownEntity::getCode, pageVO.getCode());
        List<UptownEntity> list = uptownService.list(queryWrapper);
        return ResultVO.successData(PageInfo.of(list));
    }
}

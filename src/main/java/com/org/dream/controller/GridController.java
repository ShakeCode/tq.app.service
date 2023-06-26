package com.org.dream.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.org.dream.constant.RedisConstant;
import com.org.dream.domain.entity.GridEntity;
import com.org.dream.domain.enums.StatusEnum;
import com.org.dream.domain.vo.req.GridInfo;
import com.org.dream.domain.vo.req.PageVO;
import com.org.dream.domain.vo.rsp.ResultVO;
import com.org.dream.exception.PermissionException;
import com.org.dream.i18n.I18nMsgEnum;
import com.org.dream.service.GridService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/v1/grid")
@RestController
@Api(value = "区域业务相关的api", tags = "区域业务相关的api")
public class GridController {
    private static final Logger LOGGER = LoggerFactory.getLogger(GridController.class);

    private final GridService gridService;

    @ApiOperation(value = "新增区域")
    @PostMapping("/add")
    public ResultVO<Void> addGrid(@RequestBody GridInfo gridInfo) {
        gridService.save(gridInfo);
        GridEntity gridEntity = new GridEntity().setCode(UUID.randomUUID().toString())
                .setName("测试区域")
                // .setStatus(ValidStatusEnum.IN_VALID);
                .setStatus(StatusEnum.IN_VALID);

        gridEntity.setCreateDate(new Date());
        gridEntity.setUpdateBy("system");
        gridEntity.setUpdateDate(new Date());
        //  gridService.getBaseMapper().insert(gridEntity);

        // gridService.saveBatch(Lists.newArrayList(gridInfo));
        // gridService.getBaseMapper().insert(gridInfo);
        return ResultVO.success();
    }

    @ApiOperation(value = "更新区域")
    @PostMapping("/update")
    public ResultVO<Void> updateGrid(@RequestBody GridInfo gridInfo) {
        gridService.updateById(gridInfo);
        // gridService.getBaseMapper().updateById(gridInfo);
        // gridService.updateBatchById(Lists.newArrayList(gridInfo));
        return ResultVO.success();
    }

    @ApiOperation(value = "删除区域")
    @PostMapping("/delete")
    public ResultVO<Integer> deleteGrid(@RequestBody List<String> codeList) {
        LambdaQueryWrapper<GridEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(GridEntity::getCode, codeList);
        return ResultVO.successData(gridService.getBaseMapper().delete(wrapper));
    }

    @Cacheable(cacheNames = RedisConstant.GRID_CACHE_KEY, key = "#pageVO.code", unless = "#result == null")
    @ApiOperation(value = "查询区域")
    @PostMapping("/list")
    public ResultVO<PageInfo<GridEntity>> listGrid(@RequestBody PageVO pageVO) {
        LOGGER.info("listGrid...");
        PageHelper.startPage(pageVO.getPageIndex(), pageVO.getPageSize());
        List<GridEntity> list = gridService.getBaseMapper().selectList(new LambdaQueryWrapper<GridEntity>().eq(GridEntity::getCode, pageVO.getCode()));
        if (CollectionUtils.isEmpty(list)) {
            throw new PermissionException(I18nMsgEnum.NO_DATA_PERMISSION);
        }
        PageInfo<GridEntity> pageInfo = PageInfo.of(list);
        return ResultVO.successData(pageInfo);
    }
}

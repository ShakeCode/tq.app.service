package com.org.dream.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.org.dream.dao.GridDao;
import com.org.dream.domain.entity.GridEntity;
import com.org.dream.service.GridService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Grid service.
 */
@RequiredArgsConstructor
@Service
public class GridServiceImpl extends ServiceImpl<GridDao, GridEntity> implements GridService {
    private static final Logger LOGGER = LoggerFactory.getLogger(GridServiceImpl.class);

    @Override
    public List<GridEntity> queryMultiValueLikePermissionGrid(List<String> gridCodeList) {
        LOGGER.info("listPermissionGrid...");
        if (CollectionUtils.isEmpty(gridCodeList)) {
            return Lists.newArrayList();
        }
        // 权限区域编码集合,(数据逗号分隔,结尾逗号,使用尾部带逗号数据模糊匹配，避免数据互相模糊包含匹配导致数据错乱)
        List<String> paramList = gridCodeList.stream().map(data -> data.concat(",")).collect(Collectors.toList());
        return this.getBaseMapper().queryMultiValueLikePermissionGrid(paramList);
    }
}

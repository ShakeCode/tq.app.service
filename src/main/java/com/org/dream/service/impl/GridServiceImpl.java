package com.org.dream.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.org.dream.dao.GridDao;
import com.org.dream.domain.entity.GridEntity;
import com.org.dream.service.GridService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GridServiceImpl extends ServiceImpl<GridDao, GridEntity> implements GridService {
}

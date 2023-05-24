package com.org.dream.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.org.dream.dao.UptownDao;
import com.org.dream.domain.entity.UptownEntity;
import com.org.dream.service.UptownService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UptownServiceImpl extends ServiceImpl<UptownDao, UptownEntity> implements UptownService {
    
}

package com.org.dream.dao;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.org.dream.domain.entity.UptownEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@DS("master")
@Mapper
@Repository
public interface UptownDao extends BaseMapper<UptownEntity> {
}

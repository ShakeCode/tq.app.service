package com.org.dream.dao;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.org.dream.domain.entity.GridEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@DS("master")
@Mapper
@Repository
public interface GridDao extends BaseMapper<GridEntity> {
}

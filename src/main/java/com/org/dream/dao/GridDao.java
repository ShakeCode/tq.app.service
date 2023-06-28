package com.org.dream.dao;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.org.dream.domain.entity.GridEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The interface Grid dao.
 */
@DS("master")
@Mapper
@Repository
public interface GridDao extends BaseMapper<GridEntity> {
    /**
     * Query multi value like permission grid list.  多值区域编码模糊匹配(逗号分隔的字段)
     * @param permissionGridList the permission grid list
     * @return the list
     */
    List<GridEntity> queryMultiValueLikePermissionGrid(@Param("list") List<String> permissionGridList);
}

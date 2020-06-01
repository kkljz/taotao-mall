package com.qtu.mapper;

import com.qtu.entity.ContentCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ContentCategoryMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ContentCategory record);

    int insertSelective(ContentCategory record);

    ContentCategory selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ContentCategory record);

    int updateByPrimaryKey(ContentCategory record);

    int updateStateByParentId(@Param("parentId") Long parentId, @Param("state") int state);

    List<ContentCategory> selectByParentId(Long parentId);

    Long getCountByParentId(Long parentId);
}
package com.qtu.rest.mapper;

import com.qtu.rest.entity.ItemCat;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ItemCatMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ItemCat record);

    int insertSelective(ItemCat record);

    ItemCat selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ItemCat record);

    int updateByPrimaryKey(ItemCat record);

    List<ItemCat> selectAllByParentId(Long parentId);

}
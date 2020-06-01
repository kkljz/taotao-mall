package com.qtu.mapper;

import com.qtu.entity.ItemCat;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemCatMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ItemCat record);

    int insertSelective(ItemCat record);

    ItemCat selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ItemCat record);

    int updateByPrimaryKey(ItemCat record);

    List<ItemCat> selectAllByParentId(Long parentId);
}
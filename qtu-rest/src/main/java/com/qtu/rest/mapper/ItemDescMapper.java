package com.qtu.rest.mapper;

import com.qtu.rest.entity.ItemDesc;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ItemDescMapper {
    int insert(ItemDesc record);

    int insertSelective(ItemDesc record);

    ItemDesc selectByItemId(Long itemId);
}
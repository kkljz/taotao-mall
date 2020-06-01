package com.qtu.mapper;

import com.qtu.entity.ItemDesc;
import com.qtu.entity.ItemParamItem;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemDescMapper {

    int insert(ItemDesc record);

    int insertSelective(ItemDesc record);

    int updateByPrimaryKeySelective(ItemDesc itemDesc);

    ItemDesc selectByItemId(Long itemId);
}
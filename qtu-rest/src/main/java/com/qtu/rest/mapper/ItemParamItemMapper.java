package com.qtu.rest.mapper;

import com.qtu.rest.entity.ItemParamItem;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ItemParamItemMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ItemParamItem record);

    int insertSelective(ItemParamItem record);

    ItemParamItem selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ItemParamItem record);

    int updateByPrimaryKeyWithBLOBs(ItemParamItem record);

    int updateByPrimaryKey(ItemParamItem record);

    ItemParamItem selectByItemId(Long itemId);
}
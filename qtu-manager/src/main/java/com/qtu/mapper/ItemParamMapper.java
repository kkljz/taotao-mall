package com.qtu.mapper;

import com.qtu.bean.ItemParamAndItemCatBean;
import com.qtu.entity.ItemParam;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemParamMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ItemParam record);

    int insertSelective(ItemParam record);

    ItemParam selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ItemParam record);

    int updateByPrimaryKeyWithBLOBs(ItemParam record);

    int updateByPrimaryKey(ItemParam record);

    List<ItemParam> selectAll();

    List<ItemParamAndItemCatBean> selectAllWithItemCat();

    ItemParam selectByItemCatId(Long cid);
}
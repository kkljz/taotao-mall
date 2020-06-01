package com.qtu.service;

import com.github.pagehelper.PageInfo;
import com.qtu.bean.EUDataGridResult;
import com.qtu.entity.Item;
import com.qtu.entity.ItemDesc;
import com.qtu.entity.ItemParam;
import com.qtu.entity.ItemParamItem;
import com.qtu.util.TaotaoResult;

import java.util.Map;

/**
 * @author Hu Shengkai
 * @create 2019-11-27 9:51
 */
public interface ItemService {
    /**
     * 得到商品的分页
     * @param pageNum
     * @param pageSize
     * @return
     */
    EUDataGridResult pageItem(int pageNum, int pageSize);

    /**
     * 得到商品的描述
     * @param itemId
     * @return
     */
    ItemDesc getItemDesc(Long itemId);

    /**
     * 得到商品的规格
     * @param itemId
     * @return
     */
    TaotaoResult getItemParamItem(Long itemId);

    /**
     * 修改商品
     * @param item
     * @param desc
     */
    Map<String,Object> updateItem(Item item, ItemDesc desc, ItemParamItem itemParamItem);

    /**
     * 更新商品状态
     *
     * @param state
     * @return
     */
    boolean updateItemState(Long[] ids ,Byte state);

    /**
     * 添加商品
     * @param item
     * @param desc
     * @return
     */
    long insertItem(Item item, ItemDesc desc, ItemParamItem itemParamItem);
}

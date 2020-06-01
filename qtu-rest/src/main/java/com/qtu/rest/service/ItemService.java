package com.qtu.rest.service;

import com.qtu.rest.entity.Item;
import com.qtu.rest.entity.ItemDesc;
import com.qtu.rest.entity.ItemParamItem;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 商品详情业务
 * @author Hu Shengkai
 * @create 2019-12-07 14:39
 */
@Service
public interface ItemService {

    /**
     * 根据商品id查询商品
     * @param id
     * @return
     */
    Item getItemById(Long id);

    /**
     * 根据商品的id查询相关的商品描述
     * @param itemId
     * @return
     */
    ItemDesc getItemDescByItemId(Long itemId);

    /**
     * 根据商品id 查询相关的商品规格参数
     * @param itemId
     * @return
     */
    ItemParamItem getItemParamByitemId(long itemId);
}

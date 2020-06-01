package com.qtu.portal.service;

import com.qtu.portal.entity.Item;
import com.qtu.portal.entity.ItemDesc;
import org.springframework.stereotype.Service;

/**
 * 商品详情业务
 * @author Hu Shengkai
 * @create 2019-12-07 15:55
 */
public interface ItemService {
    Item getItemById(Long id);

    ItemDesc getItemDescByItemId(Long itemId);

    String getItemParamByItemId(Long itemId);
}

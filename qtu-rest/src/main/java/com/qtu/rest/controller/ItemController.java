package com.qtu.rest.controller;

import com.qtu.rest.entity.Item;
import com.qtu.rest.entity.ItemDesc;
import com.qtu.rest.entity.ItemParamItem;
import com.qtu.rest.service.ItemService;
import com.qtu.util.ExceptionUtil;
import com.qtu.util.TaotaoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Hu Shengkai
 * @create 2019-12-07 15:28
 */
@RestController
@RequestMapping("/items")
public class ItemController {
    @Autowired
    private ItemService itemService;

    /**
     * 根据id查询商品
     * @param itemId
     * @return
     */
    @RequestMapping("/item/{id}")
    public TaotaoResult getItem(@PathVariable("id") Long itemId){
        if (itemId == null){
            return TaotaoResult.build(400,"参数中必须包含id");
        }
        Item item = null;
        try {
            item = itemService.getItemById(itemId);
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
        return TaotaoResult.ok(item);
    }

    /**
     * 得到商品描述
     * @param itemId
     * @return
     */
    @RequestMapping("/itemdesc/{id}")
    public TaotaoResult getItemDesc(@PathVariable("id") Long itemId){
        if (itemId == null){
            return TaotaoResult.build(400,"参数中必须包含id");
        }
        ItemDesc itemDesc = null;
        try {
            itemDesc = itemService.getItemDescByItemId(itemId);
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
        return TaotaoResult.ok(itemDesc);
    }

    /**
     * 得到商品的规格参数
     * @param itemId
     * @return
     */
    @RequestMapping("/itemparam/{id}")
    public TaotaoResult getItemParam(@PathVariable("id") Long itemId){
        if (itemId == null){
            return TaotaoResult.build(400,"参数中必须包含id");
        }
        ItemParamItem itemParamItem = null;
        try {
            itemParamItem = itemService.getItemParamByitemId(itemId);
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
        return TaotaoResult.ok(itemParamItem);
    }
}

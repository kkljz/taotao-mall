package com.qtu.rest.service.impl;

import com.qtu.rest.entity.Item;
import com.qtu.rest.entity.ItemDesc;
import com.qtu.rest.entity.ItemParamItem;
import com.qtu.rest.mapper.ItemDescMapper;
import com.qtu.rest.mapper.ItemMapper;
import com.qtu.rest.mapper.ItemParamItemMapper;
import com.qtu.rest.service.ItemService;
import com.qtu.rest.util.RedisUtil;
import com.qtu.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Hu Shengkai
 * @create 2019-12-07 15:13
 */
@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private ItemDescMapper itemDescMapper;
    @Autowired
    private ItemParamItemMapper itemParamItemMapper;
    @Autowired
    private RedisUtil redisUtil;

    //redis中存储的key
    private String ITEM_KEY = "itemKey";
    private String ITEM_DESC_KEY = "itemDescKey";
    private String ITEM_PARAM_ITEM_KEY = "itemParamItemKey";

    @Override
    public Item getItemById(Long id) {
        Item item = null;
        boolean b = redisUtil.hasKey(ITEM_KEY+id);
        if (b){
            //有缓存直接从缓存中取
            String json = (String) redisUtil.get(ITEM_KEY+id);
            item = JsonUtils.jsonToPojo(json, Item.class);
        }else {
            item = itemMapper.selectByPrimaryKey(id);
            String jsonData = JsonUtils.objectToJson(item);
            redisUtil.set(ITEM_KEY+id,jsonData);//存入redis缓存中
            redisUtil.expire(ITEM_KEY+id,60*60*24);//缓存保留期限为1天
        }
        return item;
    }

    @Override
    public ItemDesc getItemDescByItemId(Long itemId) {
        ItemDesc itemDesc = null;
        boolean b = redisUtil.hasKey(ITEM_DESC_KEY+itemId);
        if (b){
            String json = (String) redisUtil.get(ITEM_DESC_KEY+itemId);
            itemDesc = JsonUtils.jsonToPojo(json,ItemDesc.class) ;
        }else {
            itemDesc = itemDescMapper.selectByItemId(itemId);
            String jsonData = JsonUtils.objectToJson(itemDesc);
            redisUtil.set(ITEM_DESC_KEY+itemId,jsonData);//存入redis缓存中
            redisUtil.expire(ITEM_DESC_KEY+itemId, 60*60*24);//缓存保留期限为1天
        }
        return itemDesc;
    }

    @Override
    public ItemParamItem getItemParamByitemId(long itemId) {
        ItemParamItem itemParamItem = null;
        boolean b = redisUtil.hasKey(ITEM_PARAM_ITEM_KEY+itemId);
        if (b){
            String json = (String) redisUtil.get(ITEM_PARAM_ITEM_KEY+itemId);
            itemParamItem = JsonUtils.jsonToPojo(json, ItemParamItem.class);
        }else {
            itemParamItem = itemParamItemMapper.selectByItemId(itemId);
            String jsonData = JsonUtils.objectToJson(itemParamItem);
            redisUtil.set(ITEM_PARAM_ITEM_KEY+itemId,jsonData);//存入redis缓存中
            redisUtil.expire(ITEM_PARAM_ITEM_KEY+itemId,60*60*24);//缓存保留期限为1天
        }
        return itemParamItem;
    }
}

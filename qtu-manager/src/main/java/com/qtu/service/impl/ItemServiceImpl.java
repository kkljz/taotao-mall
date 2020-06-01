package com.qtu.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qtu.bean.EUDataGridResult;
import com.qtu.entity.Item;
import com.qtu.entity.ItemDesc;
import com.qtu.entity.ItemParam;
import com.qtu.entity.ItemParamItem;
import com.qtu.mapper.ItemDescMapper;
import com.qtu.mapper.ItemMapper;
import com.qtu.mapper.ItemParamItemMapper;
import com.qtu.mapper.ItemParamMapper;
import com.qtu.service.ItemService;
import com.qtu.util.TaotaoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Hu Shengkai
 * @create 2019-11-27 9:52
 */
@Service
@Transactional
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private ItemDescMapper itemDescMapper;
    @Autowired
    private ItemParamItemMapper itemParamItemMapper;
    @Autowired
    private ItemParamMapper itemParamMapper;

    @Override
    public EUDataGridResult pageItem(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Item> itemList = itemMapper.selectAll();
        PageInfo<Item> pageInfo = new PageInfo<>(itemList);

        EUDataGridResult eu = new EUDataGridResult();
        eu.setRows(itemList);
        eu.setTotal(pageInfo.getTotal());
        return eu;
    }

    @Override
    public ItemDesc getItemDesc(Long itemId) {
        return itemDescMapper.selectByItemId(itemId);
    }

    @Override
    public TaotaoResult getItemParamItem(Long itemId) {
        ItemParamItem itemParamItem = itemParamItemMapper.selectByItemId(itemId);
        return TaotaoResult.ok(itemParamItem);
    }

    @Override
    public Map<String,Object> updateItem(Item item, ItemDesc desc, ItemParamItem itemParamItem) {
        Map<String,Object> resultMap = new HashMap<>();
        try {
            item.setUpdated(new Date());
            itemMapper.updateByPrimaryKeySelective(item);//修改产品
            //设置商品描述的商品id
            desc.setItemId(item.getId());
            desc.setUpdated(new Date());
            itemDescMapper.updateByPrimaryKeySelective(desc);//修改商品描述
            //修改商品规格参数
            itemParamItem.setUpdated(new Date());
            itemParamItemMapper.updateByPrimaryKeySelective(itemParamItem);
            resultMap.put("code",200);
        }catch (Exception e){
            resultMap.put("code",400);
            e.printStackTrace();
        }
        return resultMap;
    }

    @Override
    public boolean updateItemState(Long[] ids ,Byte state) {
        boolean flag = true;

        for (int i = 0; i < ids.length; i++) {
            Item item = new Item();
            item.setUpdated(new Date());
            item.setId(ids[i]);
            item.setStatus(state);
            int row = itemMapper.updateByPrimaryKeySelective(item);
            if (row<=0) flag=false;
        }
        return flag;
    }

    @Override
    public long insertItem(Item item, ItemDesc desc, ItemParamItem itemParamItem) {
        item.setCreated(new Date());
        item.setUpdated(new Date());
        item.setStatus((byte) 1);//商品状态为正常
        itemMapper.insertSelective(item);
        //得到添加商品的id
        long itemId = item.getId();

        //设置描述的商品id
        desc.setItemId(itemId);
        desc.setCreated(new Date());
        desc.setUpdated(new Date());
        itemDescMapper.insertSelective(desc);

        //设置商品参数规格的商品id
        itemParamItem.setItemId(itemId);
        itemParamItem.setCreated(new Date());
        itemParamItem.setUpdated(new Date());
        itemParamItemMapper.insertSelective(itemParamItem);

        //返回添加商品的主键id
        return itemId;
    }
}

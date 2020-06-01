package com.qtu.controller;

import com.github.pagehelper.PageInfo;
import com.qtu.bean.EUDataGridResult;
import com.qtu.entity.Item;
import com.qtu.entity.ItemDesc;
import com.qtu.entity.ItemParam;
import com.qtu.entity.ItemParamItem;
import com.qtu.service.ItemService;
import com.qtu.util.TaotaoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Hu Shengkai
 * @create 2019-11-27 9:54
 */
@Controller
@RequestMapping("/item")
public class ItemController {
    @Autowired
    private ItemService itemService;


    /**
     * 得到商品list
     * @param page
     * @param rows
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/item-list",method = RequestMethod.GET)
    public EUDataGridResult listItem(Integer page, Integer rows){
        return itemService.pageItem(page,rows);
    }

    /**
     * 得到商品的描述
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/desc/{id}", method = RequestMethod.GET)
    public ItemDesc getItemDesc(@PathVariable("id") Long id){
        return itemService.getItemDesc(id);
    }

    /**
     * 得到商品规格参数
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/param-item/query/{id}", method = RequestMethod.GET)
    public TaotaoResult getItemParamItem(@PathVariable("id") Long id){
        return itemService.getItemParamItem(id);
    }

    /**
     * 添加商品
     * @param item
     * @param desc
     */
    @ResponseBody
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public Object insertItem( Item item, String desc , String itemParams) {
        ItemDesc itemDesc = new ItemDesc();
        itemDesc.setItemDesc(desc);

        ItemParamItem itemParamItem = new ItemParamItem();
        itemParamItem.setParamData(itemParams);//设置商品规格参数
        long id = itemService.insertItem(item, itemDesc,itemParamItem);
        return id;
    }

    /**
     * 修改商品
     * @param item
     * @param desc
     */
    @ResponseBody
    @RequestMapping(value = "/",method = RequestMethod.PUT)
    public Object updateItem( Item item, String desc , String itemParams, Long itemParamId){
//        System.out.println("item:"+item);
//        System.out.println("desc:"+desc);
        ItemDesc itemDesc = new ItemDesc();
        itemDesc.setItemDesc(desc);

        ItemParamItem itemParamItem = new ItemParamItem();
        itemParamItem.setParamData(itemParams);//设置商品规格参数
        itemParamItem.setId(itemParamId);
        return itemService.updateItem(item,itemDesc,itemParamItem);
    }

    /**
     * 删除
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Object deleteItem(Long[] ids){
        boolean b = itemService.updateItemState(ids, (byte) 3);
        Map<String,Object> map = new HashMap<>();
        if (b){
            map.put("status",200);
        }else {
            map.put("status",400);
        }
        return map;
    }

    /**
     * 下架
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/instock", method = RequestMethod.POST)
    public Object instockItem(Long[] ids){
        boolean b = itemService.updateItemState(ids, (byte) 2);
        Map<String,Object> map = new HashMap<>();
        if (b){
            map.put("status",200);
        }else {
            map.put("status",400);
        }
        return map;
    }

    /**
     * 正常上架
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/reshelf", method = RequestMethod.POST)
    public Object reshelfItem(Long[] ids){
        boolean b = itemService.updateItemState(ids, (byte) 1);
        Map<String,Object> map = new HashMap<>();
        if (b){
            map.put("status",200);
        }else {
            map.put("status",400);
        }
        return map;
    }


}

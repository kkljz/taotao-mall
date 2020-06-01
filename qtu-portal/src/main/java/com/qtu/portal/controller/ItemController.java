package com.qtu.portal.controller;

import com.qtu.portal.entity.Item;
import com.qtu.portal.entity.ItemDesc;
import com.qtu.portal.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Hu Shengkai
 * @create 2019-12-07 17:22
 */
@Controller
@RequestMapping("/item")
public class ItemController {
    @Autowired
    private ItemService itemService;

    /**
     * 显示商品详情
     * @param id
     * @return
     */
    @RequestMapping("/{id}")
    public ModelAndView showItem(@PathVariable("id") Long id){
        ModelAndView mv = new ModelAndView("item");
        Item item = itemService.getItemById(id);
        mv.addObject("item",item);
        return mv;
    }

    /**
     * 显示商品描述
     * @param itemId
     * @return
     */
    @ResponseBody
    @RequestMapping("/desc/{id}")
    public String showItemDesc(@PathVariable("id") Long itemId){
        ItemDesc itemDesc = itemService.getItemDescByItemId(itemId);
        return itemDesc.getItemDesc();
    }

    /**
     * 显示商品规格参数
     * @param itemId
     * @return
     */
    @ResponseBody
    @RequestMapping("/param/{id}")
    public String showItemParam(@PathVariable("id") Long itemId){
        //取规格参数,并返回
        return itemService.getItemParamByItemId(itemId);
    }

}

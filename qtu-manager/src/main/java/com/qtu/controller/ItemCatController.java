package com.qtu.controller;

import com.qtu.bean.EUTreeNode;
import com.qtu.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 商品类目
 * @author Hu Shengkai
 * @create 2019-11-27 17:44
 */
@Controller
@RequestMapping("/item/cat")
public class ItemCatController {
    @Autowired
    private ItemCatService itemCatService;

    /**
     * 获取商品类目，根据parentId
     * @param parentId
     * @return
     */
    @ResponseBody
    @RequestMapping("/list")
    public List<EUTreeNode> listItemCat(@RequestParam(value = "id",defaultValue = "0") Long parentId){
        System.out.println("parentId:"+parentId);
        return itemCatService.listItemCatByParentId(parentId);
    }
}

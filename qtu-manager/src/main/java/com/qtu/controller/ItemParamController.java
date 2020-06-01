package com.qtu.controller;

import com.qtu.bean.EUDataGridResult;
import com.qtu.entity.ItemParam;
import com.qtu.service.ItemParamService;
import com.qtu.util.TaotaoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 商品规则参数
 * @author Hu Shengkai
 * @create 2019-11-27 15:21
 */
@Controller
@RequestMapping("/item/param")
public class ItemParamController {
    @Autowired
    private ItemParamService itemParamService;

    /**
     * 获取所有商品规格参数
     * @param page
     * @param rows
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public EUDataGridResult listParam(int page, int rows){
        return itemParamService.listParamWithCat(page,rows);
    }

    /**
     * 判断该类型商品的规格参数模板是否存在
     * @param cid
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{cid}",method = RequestMethod.GET)
    public TaotaoResult checkItemParam(@PathVariable("cid") Long cid){
        return itemParamService.checkItemParam(cid);
    }

    /**
     * 添加该类型商品的规格参数模板
     * @param cid
     * @param itemParam
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/save/{cid}", method = RequestMethod.POST)
    public TaotaoResult saveItemParam(@PathVariable("cid") Long cid ,ItemParam itemParam){
        itemParam.setItemCatId(cid);
        return itemParamService.insertItemParam(itemParam);
    }

    /**
     * 删除
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public TaotaoResult deleteItemParam(Long[] ids){
        return itemParamService.deleteItemParam(ids);
    }
}

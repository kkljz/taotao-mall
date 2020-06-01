package com.qtu.search.controller;

import com.qtu.bean.SearchBean;
import com.qtu.bean.SearchResult;
import com.qtu.search.entity.Item;
import com.qtu.search.service.SolrService;
import com.qtu.util.ExceptionUtil;
import com.qtu.util.TaotaoResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author Hu Shengkai
 * @create 2019-12-06 19:27
 */
@RestController
@RequestMapping("/search")
public class ItemController {
    @Autowired
    private SolrService solrService;

    /**
     * 把所有数据导入索引库
     * @return
     */
    @RequestMapping(value = "/importAll",method = RequestMethod.POST)
    public TaotaoResult importAll(){
        try {
            solrService.addAllByPage();
            return TaotaoResult.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
    }

    /**
     * 把一条数据导入索引库
     * @return
     */
    @RequestMapping(value = "/importOne", method = RequestMethod.POST)
    public TaotaoResult importOne(Item item){
        try {
            solrService.add(item);
            return TaotaoResult.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
    }


    /**
     * 查询
     * @param searchBean
     * @return
     */
    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public TaotaoResult searchItem(SearchBean searchBean){
        //查询条件不能为空
        if (StringUtils.isBlank(searchBean.getKeywords())){
            return TaotaoResult.build(400,"查询条件不能为空");
        }
        SearchResult searchResult = new SearchResult();
        try {
            searchResult = solrService.search(searchBean);
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(500,ExceptionUtil.getStackTrace(e));
        }
        return TaotaoResult.ok(searchResult);
    }
}

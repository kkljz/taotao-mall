package com.qtu.portal.controller;

import com.qtu.bean.SearchBean;
import com.qtu.bean.SearchResult;
import com.qtu.portal.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * @author Hu Shengkai
 * @create 2019-12-07 9:14
 */
@Controller
@RequestMapping("/search")
public class SearchController {
    @Autowired
    private SearchService searchService;

    @RequestMapping("/query")
    public ModelAndView searchItemList(@RequestParam("q")String keyWord, Integer page){
        ModelAndView mv = new ModelAndView("search");
        SearchBean searchBean = new SearchBean();
        searchBean.setKeywords(keyWord);
        searchBean.setPageNo(page);
        SearchResult searchResult = searchService.searchItemList(searchBean);
        mv.addObject("itemList", searchResult.getItemList());
        mv.addObject("query", keyWord);
        mv.addObject("totalPages", searchResult.getTotalPages());
        mv.addObject("page", page);
        return mv;
    }
}

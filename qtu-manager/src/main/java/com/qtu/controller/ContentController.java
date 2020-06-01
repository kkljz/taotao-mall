package com.qtu.controller;

import com.qtu.bean.EUDataGridResult;
import com.qtu.entity.Content;
import com.qtu.service.ContentService;
import com.qtu.util.TaotaoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Hu Shengkai
 * @create 2019-12-02 18:02
 */
@Controller
@RequestMapping("/content")
public class ContentController  {

    @Autowired
    private ContentService contentService;

    @ResponseBody
    @RequestMapping("/list")
    public EUDataGridResult listContent(Integer page, Integer rows, Long categoryId){
        return contentService.listContentByCategoryId(page, rows, categoryId);
    }

    @ResponseBody
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public TaotaoResult addContent(Content content){
        return contentService.addContent(content);
    }

    @ResponseBody
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public TaotaoResult updateContent(Content content){
        return contentService.updateContent(content);
    }

    @ResponseBody
    @RequestMapping("/delete")
    public TaotaoResult deleteContent(Long[] ids){
        return contentService.deleteContents(ids);
    }
}

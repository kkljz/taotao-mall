package com.qtu.rest.controller;

import com.qtu.rest.service.ContentService;
import com.qtu.util.TaotaoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Hu Shengkai
 * @create 2019-12-04 22:04
 */
@RestController
@RequestMapping("/api/content")
public class ContentController {
    @Autowired
    private ContentService contentService;

    /**
     * 根据分类id查询相应的内容
     * @param categoryId
     * @return
     */
    @RequestMapping("/{categoryId}")
    public TaotaoResult listContent(@PathVariable("categoryId")Long categoryId){
        return contentService.listContentByCategoryId(categoryId);
    }
}

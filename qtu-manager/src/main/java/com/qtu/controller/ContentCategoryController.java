package com.qtu.controller;

import com.qtu.bean.EUTreeNode;
import com.qtu.entity.ContentCategory;
import com.qtu.service.ContentCategoryService;
import com.qtu.util.TaotaoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author Hu Shengkai
 * @create 2019-12-02 15:56
 */
@Controller
@RequestMapping("/content/category")
public class ContentCategoryController {
    @Autowired
    private ContentCategoryService contentCategoryService;

    /**
     * 根据父id得到子内容分类
     * @param parentId
     * @return
     */
    @ResponseBody
    @RequestMapping("/list")
    public List<EUTreeNode> getContentCategoryList(@RequestParam(value = "id", defaultValue = "0") Long parentId){
        return contentCategoryService.getCategoryList(parentId);
    }

    /**
     * 添加内容分类
     * @param parentId
     * @param name
     * @return
     */
    @ResponseBody
    @RequestMapping("/add")
    public TaotaoResult addContentCategory(Long parentId, String name){
        ContentCategory category = new ContentCategory();
        category.setParentId(parentId);
        category.setName(name);
        return contentCategoryService.addCategory(category);
    }

    /**
     * 内容分类重命名
     * @param id
     * @param name
     * @return
     */
    @ResponseBody
    @RequestMapping("/updateName")
    public TaotaoResult updateCategoryName(Long id, String name){
        return contentCategoryService.updateCategoryName(id,name);
    }

    @ResponseBody
    @RequestMapping("/delete")
    public TaotaoResult deleteCategory(Long id){
        return contentCategoryService.deleteCategory(id);
    }


}

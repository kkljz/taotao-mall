package com.qtu.service;

import com.qtu.bean.EUTreeNode;
import com.qtu.entity.ContentCategory;
import com.qtu.util.TaotaoResult;

import java.util.List;

/**
 * @author Hu Shengkai
 * @create 2019-12-02 15:48
 */
public interface ContentCategoryService {
    /**
     * 通过parentId查询得到商品分类的list
     * @param parentId
     * @return
     */
    List<EUTreeNode> getCategoryList(Long parentId);

    /**
     * 添加内容分类
     * @param contentCategory
     */
    TaotaoResult addCategory(ContentCategory contentCategory);

    /**
     * 内容分类重命名
     * @param id
     * @param name
     * @return
     */
    TaotaoResult updateCategoryName(Long id, String name);

    TaotaoResult deleteCategory(Long id);
}

package com.qtu.service;

import com.qtu.bean.EUTreeNode;

import java.util.List;

/**
 * @author Hu Shengkai
 * @create 2019-11-27 17:45
 */
public interface ItemCatService {
    /**
     * 根据parentId得到子分类
     * @param parentId
     * @return
     */
    List<EUTreeNode> listItemCatByParentId(Long parentId);
}

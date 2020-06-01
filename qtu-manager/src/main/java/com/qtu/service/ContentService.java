package com.qtu.service;

import com.qtu.bean.EUDataGridResult;
import com.qtu.entity.Content;
import com.qtu.util.TaotaoResult;

/**
 * @author Hu Shengkai
 * @create 2019-12-02 17:53
 */
public interface ContentService {
    /**
     * 根据内容分类id 得到所属的内容信息
     * @param categoryId
     * @return
     */
    EUDataGridResult listContentByCategoryId(int pageNum, int pageSize, Long categoryId);

    TaotaoResult addContent(Content content);

    TaotaoResult updateContent(Content content);

    /**
     * 根据id数组，实现批量删除
     * @param ids
     * @return
     */
    TaotaoResult deleteContents(Long[] ids);
}

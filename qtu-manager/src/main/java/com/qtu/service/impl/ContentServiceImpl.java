package com.qtu.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qtu.bean.EUDataGridResult;
import com.qtu.entity.Content;
import com.qtu.mapper.ContentMapper;
import com.qtu.service.ContentService;
import com.qtu.util.TaotaoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 内容管理
 * @author Hu Shengkai
 * @create 2019-12-02 17:54
 */
@Service
@Transactional
public class ContentServiceImpl implements ContentService {
    @Autowired
    private ContentMapper contentMapper;

    @Override
    public EUDataGridResult listContentByCategoryId(int pageNum, int pageSize, Long categoryId) {
        PageHelper.startPage(pageNum, pageSize);
        List<Content> list = contentMapper.selectByCategoryId(categoryId);
        PageInfo<Content> pageInfo = new PageInfo<>(list);

        EUDataGridResult euDate = new EUDataGridResult();
        euDate.setRows(list);
        euDate.setTotal(pageInfo.getTotal());
        return euDate;
    }

    @Override
    public TaotaoResult addContent(Content content) {
        content.setUpdated(new Date());
        content.setCreated(new Date());
        contentMapper.insertSelective(content);
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult updateContent(Content content) {
        content.setUpdated(new Date());
        contentMapper.updateByPrimaryKeySelective(content);
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult deleteContents(Long[] ids) {
        for (Long id : ids) {
            contentMapper.deleteByPrimaryKey(id);
        }
        return TaotaoResult.build(200,"删除成功");
    }


}

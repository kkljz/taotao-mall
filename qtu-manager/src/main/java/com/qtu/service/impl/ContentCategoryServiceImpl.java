package com.qtu.service.impl;

import com.qtu.bean.EUTreeNode;
import com.qtu.entity.ContentCategory;
import com.qtu.mapper.ContentCategoryMapper;
import com.qtu.service.ContentCategoryService;
import com.qtu.util.TaotaoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Hu Shengkai
 * @create 2019-12-02 15:48
 */
@Service
@Transactional
public class ContentCategoryServiceImpl implements ContentCategoryService {
    @Autowired
    private ContentCategoryMapper contentCategoryMapper;

    @Override
    public List<EUTreeNode> getCategoryList(Long parentId) {
        List<ContentCategory> list = contentCategoryMapper.selectByParentId(parentId);
        List<EUTreeNode> resultList = new ArrayList<>();
        for (ContentCategory category : list) {
            EUTreeNode euTreeNode = new EUTreeNode();
            euTreeNode.setId(category.getId());
            euTreeNode.setText(category.getName());
            euTreeNode.setState(category.getIsParent()?"closed":"open");
            resultList.add(euTreeNode);
        }
        return resultList;
    }

    @Override
    public TaotaoResult addCategory(ContentCategory contentCategory) {
        contentCategory.setCreated(new Date());
        contentCategory.setUpdated(new Date());
        contentCategory.setIsParent(false);
        contentCategory.setSortOrder(1);
        contentCategory.setStatus(1);//状态。可选值:1(正常),2(删除)
        contentCategoryMapper.insertSelective(contentCategory);

        //查询到新增节点的父节点
        ContentCategory parent = contentCategoryMapper.selectByPrimaryKey(contentCategory.getParentId());
        if (!parent.getIsParent()){
            //如果父节点的IsParent属性为false，就给其更新为true
            parent.setIsParent(true);
            contentCategoryMapper.updateByPrimaryKeySelective(parent);
        }

        //返回结果
        return TaotaoResult.ok(contentCategory);
    }

    @Override
    public TaotaoResult updateCategoryName(Long id, String name) {
        ContentCategory category = new ContentCategory();
        category.setId(id);
        category.setName(name);
        contentCategoryMapper.updateByPrimaryKeySelective(category);
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult deleteCategory(Long id) {
        ContentCategory contentCategory = contentCategoryMapper.selectByPrimaryKey(id);
        contentCategory.setStatus(2);//状态。可选值:1(正常),2(删除)
        contentCategoryMapper.updateByPrimaryKeySelective(contentCategory);

        if (contentCategory.getIsParent()){
            contentCategoryMapper.updateStateByParentId(id,2);//将被删除节点的子节点全部删除
        }

        //查询删除节点的父节点，查看它还有多少个子节点，如果没有子节点，就把isParent属性设置为false
        Long parentId = contentCategory.getParentId();
        Long count = contentCategoryMapper.getCountByParentId(parentId);
        if (count==0){
            ContentCategory parent = contentCategoryMapper.selectByPrimaryKey(parentId);
            parent.setIsParent(false);
            contentCategoryMapper.updateByPrimaryKeySelective(parent);
        }
        return TaotaoResult.ok();
    }
}

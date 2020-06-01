package com.qtu.service.impl;

import com.qtu.bean.EUTreeNode;
import com.qtu.entity.ItemCat;
import com.qtu.mapper.ItemCatMapper;
import com.qtu.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Hu Shengkai
 * @create 2019-11-27 17:47
 */
@Service
@Transactional
public class ItemCatServiceImpl implements ItemCatService {

    @Autowired
    private ItemCatMapper itemCatMapper;

    /**
     * 根据parentId得到子分类
     * @param parentId
     * @return
     */
    @Override
    public List<EUTreeNode> listItemCatByParentId(Long parentId) {
        List<EUTreeNode> resultList = new ArrayList<>();
        List<ItemCat> list = itemCatMapper.selectAllByParentId(parentId);

        for (ItemCat cat : list) {
            EUTreeNode euTreeNode = new EUTreeNode();
            euTreeNode.setId(cat.getId());
            euTreeNode.setText(cat.getName());
            euTreeNode.setState(cat.getIsParent()?"closed":"open");
            resultList.add(euTreeNode);
        }

        return resultList;
    }
}

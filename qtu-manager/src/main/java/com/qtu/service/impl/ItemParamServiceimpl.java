package com.qtu.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qtu.bean.EUDataGridResult;
import com.qtu.bean.ItemParamAndItemCatBean;
import com.qtu.entity.ItemParam;
import com.qtu.mapper.ItemParamMapper;
import com.qtu.service.ItemParamService;
import com.qtu.util.TaotaoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 规格参数service
 * @author Hu Shengkai
 * @create 2019-11-27 15:18
 */
@Service
@Transactional
public class ItemParamServiceimpl implements ItemParamService {
    @Autowired
    private ItemParamMapper itemParamMapper;

    @Override
    public EUDataGridResult listParamWithCat(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<ItemParamAndItemCatBean> itemParamList = itemParamMapper.selectAllWithItemCat();
        PageInfo<ItemParamAndItemCatBean> pageInfo = new PageInfo<>(itemParamList);

        EUDataGridResult eu = new EUDataGridResult();
        eu.setTotal(pageInfo.getTotal());
        eu.setRows(itemParamList);
        return eu;
    }

    @Override
    public TaotaoResult checkItemParam(Long cid) {
        ItemParam itemParam = itemParamMapper.selectByItemCatId(cid);
        if (itemParam!=null){
            return TaotaoResult.ok(itemParam);
        }
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult insertItemParam(ItemParam itemParam) {
        itemParam.setCreated(new Date());
        itemParam.setUpdated(new Date());
        itemParamMapper.insertSelective(itemParam);
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult deleteItemParam(Long[] ids) {
        for (Long id : ids) {
            itemParamMapper.deleteByPrimaryKey(id);
        }
        return TaotaoResult.build(200,"删除成功");
    }
}

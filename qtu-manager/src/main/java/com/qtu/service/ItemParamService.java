package com.qtu.service;

import com.qtu.bean.EUDataGridResult;
import com.qtu.entity.ItemParam;
import com.qtu.util.TaotaoResult;

/**
 * 商品规格
 * @author Hu Shengkai
 * @create 2019-11-27 15:16
 */
public interface ItemParamService {
    /**
     * 得到所有的规格模板
     * @param pageNum
     * @param pageSize
     * @return
     */
    EUDataGridResult listParamWithCat(int pageNum, int pageSize);

    /**
     * 判断某一类商品的规格模板是否存在
     * @param cid
     * @return
     */
    TaotaoResult checkItemParam(Long cid);

    /**
     * 添加某一类商品的规格模板
     * @param itemParam
     * @return
     */
    TaotaoResult insertItemParam(ItemParam itemParam);

    TaotaoResult deleteItemParam(Long[] ids);
}

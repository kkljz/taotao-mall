package com.qtu.portal.service;

import com.qtu.bean.SearchBean;
import com.qtu.bean.SearchResult;

import java.util.Map;

/**
 * @author Hu Shengkai
 * @create 2019-12-07 9:18
 */
public interface SearchService {
    /**
     * 搜索商品
     * @param bean
     * @return
     */
    SearchResult searchItemList(SearchBean bean);
}

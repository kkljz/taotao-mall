package com.qtu.search.service;

import com.qtu.bean.SearchBean;
import com.qtu.bean.SearchResult;
import com.qtu.search.entity.Item;
import org.springframework.data.solr.core.query.result.ScoredPage;

import java.util.Collection;
import java.util.Map;

/**
 * @author Hu Shengkai
 * @create 2019-12-06 15:17
 */
public interface SolrService {
    /**
     * 添加
     * @param item
     */
    public void add(Item item) ;
    /**
     * 添加多个
     * @param items
     */
    public void adds(Collection<Item> items);

    /**
     * 将所有数据分段插入索引库
     */
    public void addAllByPage();


    /**
     * 根据id搜索
     * @param id
     * @return
     */
    public Item searchById(int id) ;
    /**
     * 根据id删除
     * @param id
     */
    public void deleteById(String id);
    /**
     * 分页查询
     * @return
     */
    public ScoredPage<Item> pageQuery(long start, int size);
    /**
     * 删除所有
     */
    public void deleteAll() ;

    /**
     * 搜索
     * @return
     */
    public SearchResult search(SearchBean bean);
}

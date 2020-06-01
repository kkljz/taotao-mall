package com.qtu.search.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qtu.bean.SearchBean;
import com.qtu.bean.SearchResult;
import com.qtu.search.entity.Item;
import com.qtu.search.mapper.ItemMapper;
import com.qtu.search.service.SolrService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.*;
import org.springframework.data.solr.core.query.result.HighlightEntry;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.data.solr.core.query.result.ScoredPage;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.*;

/**
 * @author Hu Shengkai
 * @create 2019-12-06 15:22
 */
@Service
public class SolrServiceImpl implements SolrService {
    @Resource
    private SolrTemplate solrTemplate;
    @Resource
    private ItemMapper itemMapper;

    @Override
    public void add(Item item) {
        solrTemplate.saveBean("qtu", item);
        solrTemplate.commit("qtu");
    }

    @Override
    public void adds(Collection<Item> items) {
        solrTemplate.saveBeans("qtu",items);
        solrTemplate.commit("qtu");
    }

    @Override
    public void addAllByPage() {
        //分页插入
        int pageNo = 1;
        int pageSize = 500;
        int pages = 0;
        PageHelper.startPage(pageNo,pageSize);
        List<Item> list = itemMapper.selectAll();
        PageInfo<Item> pageInfo = new PageInfo<>(list);
        pages = pageInfo.getPages();
        while ( pageNo <= pages ) {
            PageHelper.startPage(pageNo, pageSize);
            list = itemMapper.selectAll();
            solrTemplate.saveBeans("qtu",list);
            solrTemplate.commit("qtu");
            pageNo++;
        }
    }

    @Override
    public Item searchById(int id) {
        Optional<Item> item = solrTemplate.getById("qtu", id, Item.class);
        return item.get();
    }

    @Override
    public void deleteById(String id) {
        solrTemplate.deleteByIds("qtu", id);
        solrTemplate.commit("qtu");
    }

    @Override
    public ScoredPage<Item> pageQuery(long start, int size) {
        Query q = new SimpleQuery();
        q.setRows(size);
        q.setOffset(start);
        ScoredPage<Item> forPage = solrTemplate.queryForPage("qtu", q, Item.class);
        return forPage;
    }

    @Override
    public SearchResult search(SearchBean bean) {
        SearchResult searchResult = new SearchResult();
        try {
            //1.先获取从页面传递过来的参数的值   通过KEY获取
            String keywords = (String)bean.getKeywords();//获取主查询的条件

            //2.设置主查询的条件
            HighlightQuery query =  new SimpleHighlightQuery();
            Criteria criteria = new Criteria("title");
            criteria.is(keywords);
            query.addCriteria(criteria);

            //4.设置过滤条件  商品分类的过滤
            if (!StringUtils.isEmpty(bean.getCategory())) {
                Criteria fiterCriteria = new Criteria("item_category").is(bean.getCategory());
                FilterQuery filterQuery = new SimpleFilterQuery(fiterCriteria);
                query.addFilterQuery(filterQuery);
            }

            //7.按照价格筛选
            if (StringUtils.isNotBlank((CharSequence) bean.getPrice())){
                //item_price:[10 TO 20]
                String[] split = bean.getPrice().toString().split("-");
                SimpleFilterQuery filterQuery = new SimpleFilterQuery();
                Criteria itemPrice = new Criteria("price");
                //如果有* 语法是不支持的
                if(!split[1].equals("*")){
                    itemPrice.between(split[0],split[1],true,true);
                }else {
                    itemPrice.greaterThanEqual(split[0]);
                }
                filterQuery.addCriteria(itemPrice);
                query.addFilterQuery(filterQuery);
            }
            //8.分页查询
            Integer pageNo = bean.getPageNo() ;//提取页面

            if (pageNo==null){
                pageNo =1;
            }
            Integer pageSize = bean.getPageSize();//每页记录数
            if (pageSize==null){
                pageSize=20;
            }
            query.setOffset((long) ((pageNo-1)*pageSize));//从第几条记录查询
            query.setRows(pageSize);

            //9.排序
            String sortValue = bean.getSort();
            String sortField = bean.getSortField();//排序字段
            if (StringUtils.isNotBlank(sortField)){
                if (sortValue.equals("ASC")){
                    Sort sort = new Sort(Sort.Direction.ASC,  sortField);
                    query.addSort(sort);
                }
                if (sortValue.equals("DESC")){
                    Sort sort = new Sort(Sort.Direction.DESC,sortField);
                    query.addSort(sort);
                }
            }
            //10.执行查询 获取高亮数据
            HighlightPage<Item> highlightPage = solrTemplate.queryForHighlightPage("qtu",query, Item.class);
            List<HighlightEntry<Item>> highlighted = highlightPage.getHighlighted();
            List<Item> tbItems = highlightPage.getContent();//获取高亮的文档的集合
            //11.执行查询
            System.out.println("结果"+tbItems.size());
            //12.获取结果集  返回

            searchResult.setItemList(tbItems);
            searchResult.setTotalPages(highlightPage.getTotalPages());//返回总页数
            searchResult.setTotal(highlightPage.getTotalElements());//返回总记录数
        } catch (Exception e) {
            e.printStackTrace();
        }
        return searchResult;
    }

    @Override
    public void deleteAll() {
        SimpleQuery query = new SimpleQuery("*:*");
        solrTemplate.delete("qtu",query);
        solrTemplate.commit("qtu");
    }
}

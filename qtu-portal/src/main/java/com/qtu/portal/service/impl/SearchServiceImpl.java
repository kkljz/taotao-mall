package com.qtu.portal.service.impl;

import com.qtu.bean.SearchBean;
import com.qtu.bean.SearchResult;
import com.qtu.portal.service.SearchService;
import com.qtu.util.HttpClientUtil;
import com.qtu.util.TaotaoResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.SQLOutput;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Hu Shengkai
 * @create 2019-12-07 9:40
 */
@Service
public class SearchServiceImpl  implements SearchService {
    @Value("${url.base.search}")
    private String SEARCH_BASE_URL;

    @Override
    public SearchResult searchItemList(SearchBean bean) {
        System.out.println("搜索服务地址："+SEARCH_BASE_URL);

        //查询参数
        Map<String,String> paramMap = new HashMap<>();
        paramMap.put("keywords",bean.getKeywords());
        if (bean.getPageNo()!=null){
            paramMap.put("pageNo", bean.getPageNo().toString());
        }
        if (bean.getPageSize() != null) {
            paramMap.put("pageSize", bean.getPageSize().toString());
        }

        //向搜索服务发送请求
        String result = HttpClientUtil.doGet(SEARCH_BASE_URL, paramMap);
        //转换成TaotaoResult
        TaotaoResult taotaoResult = TaotaoResult.formatToPojo(result,SearchResult.class);
        SearchResult searchResult = null;
        //查询成功
        if (taotaoResult.getStatus() == 200){
            //取查询结果
            searchResult = (SearchResult) taotaoResult.getData();
        }
        return searchResult;
    }
}

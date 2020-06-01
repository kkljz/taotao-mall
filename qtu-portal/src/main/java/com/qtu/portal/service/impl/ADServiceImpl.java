package com.qtu.portal.service.impl;

import com.qtu.portal.bean.ADItem;
import com.qtu.portal.entity.Content;
import com.qtu.portal.service.ADService;
import com.qtu.util.HttpClientUtil;
import com.qtu.util.JsonUtils;
import com.qtu.util.TaotaoResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 大广告
 * @author Hu Shengkai
 * @create 2019-12-04 22:15
 */
@Service
public class ADServiceImpl implements ADService {
    //restful服务基础url
    @Value("${url.base.rest}")
    private String REST_BASE_URL;
    //首页大广告位url
    @Value("${url.ad1.index}")
    private String INDEX_AD1_URL;

    @Override
    public String queryIndexAD() {
        //调用服务层的服务查询打广告位的数据
        String result = HttpClientUtil.doGet(REST_BASE_URL + INDEX_AD1_URL);
        if (StringUtils.isEmpty(result)){
            return "";
        }
        //把json数据转换成对象
        TaotaoResult taotaoResult = TaotaoResult.formatToList(result, Content.class);
        List<ADItem> list = new ArrayList<>();

        if (taotaoResult.getStatus() == 200){
            List<Content> contentList = (List<Content>) taotaoResult.getData();
            for (Content content : contentList) {
                ADItem item = new ADItem();
                item.setHeight(240);
                item.setWidth(670);
                item.setSrc(content.getPic());
                item.setHeightB(240);
                item.setWidthB(550);
                item.setSrcB(content.getPic2());
                item.setAlt(content.getTitleDesc());
                item.setHref(content.getUrl());
                list.add(item);
            }
        }

        return JsonUtils.objectToJson(list);
    }
}

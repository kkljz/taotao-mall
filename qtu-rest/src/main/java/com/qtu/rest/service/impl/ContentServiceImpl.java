package com.qtu.rest.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.qtu.rest.entity.Content;
import com.qtu.rest.mapper.ContentMapper;
import com.qtu.rest.service.ContentService;
import com.qtu.rest.util.RedisUtil;
import com.qtu.util.JsonUtils;
import com.qtu.util.TaotaoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Hu Shengkai
 * @create 2019-12-04 22:02
 */
@Service
@Transactional
public class ContentServiceImpl implements ContentService {
    @Autowired
    private ContentMapper contentMapper;
    @Autowired
    private RedisUtil redisUtil;

    private String CONTENT_KEY = "contentList"; //在redis中存的key

    @Override
    public TaotaoResult listContentByCategoryId(Long categoryId) {
        List<Content> list = null;
        boolean b = redisUtil.hasKey(CONTENT_KEY);
        //判断redis中是否存在
        if (b){
            //存在，直接从redis中取，并转换为list集合
            String json = (String) redisUtil.get(CONTENT_KEY);
            list = (List<Content>) JsonUtils.jsonToList(json, Content.class);
        }else {
            //不存在，从数据库中取，并转换为json字符串，存取到redis中
            list = contentMapper.selectAllByCategoryId(categoryId);
            redisUtil.set(CONTENT_KEY , JsonUtils.objectToJson(list));
            //指定缓存保存时间为1天
            redisUtil.expire(CONTENT_KEY,60*60*24);
        }
        return TaotaoResult.ok(list);
    }
}

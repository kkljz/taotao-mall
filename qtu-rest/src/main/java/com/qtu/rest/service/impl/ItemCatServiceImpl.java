package com.qtu.rest.service.impl;

import com.qtu.rest.bean.ItemCatData;
import com.qtu.rest.bean.ItemCatResult;
import com.qtu.rest.entity.ItemCat;
import com.qtu.rest.mapper.ItemCatMapper;
import com.qtu.rest.service.ItemCatService;
import com.qtu.rest.util.RedisUtil;
import com.qtu.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Hu Shengkai
 * @create 2019-12-04 14:16
 */
@Service
@Transactional
public class ItemCatServiceImpl implements ItemCatService {
    @Autowired
    private ItemCatMapper itemCatMapper;
    @Autowired
    private RedisUtil redisUtil;

    private String Item_Cat_Result_KEY = "ItemCatResult"; //itemcat(商品类型)在redis中存的key

    @Override
    public ItemCatResult getAllItemCat() {
        ItemCatResult itemCatResult = null;
        boolean b = redisUtil.hasKey(Item_Cat_Result_KEY);
        if (b){
            //Redis中存在
            String jsonData  = (String) redisUtil.get(Item_Cat_Result_KEY);
            itemCatResult = JsonUtils.jsonToPojo(jsonData, ItemCatResult.class);
        }else {
            //redis中不存在，取数据库中查询
            itemCatResult = new ItemCatResult();
            List<ItemCat> itemCatList = itemCatMapper.selectAllByParentId(0L);
            for (ItemCat cat1 : itemCatList) {
                //封装一级目录
                ItemCatData itemCatData1 = new ItemCatData();
                itemCatData1.setUrl("/products/" + cat1.getId() + ".html");
                itemCatData1.setName("<a href='" + itemCatData1.getUrl() + "'>" + cat1.getName() + "</a>");
                itemCatResult.getData().add(itemCatData1);

                //如果一级目录是父目录
                if (cat1.getIsParent()){
                    List<ItemCat> itemCatList1 = itemCatMapper.selectAllByParentId(cat1.getId());//二级目录
                    List<ItemCatData> dataList1 = new ArrayList<>();
                    //将所有二级目录 加到 一级目录下的list中
                    itemCatData1.setItems(dataList1);
                    for (ItemCat cat2 : itemCatList1) {
                        //二级目录的数据封装
                        ItemCatData itemCatData2 = new ItemCatData();
                        itemCatData2.setUrl("/products/" + cat2.getId() + ".html");
                        itemCatData2.setName(cat2.getName());
                        dataList1.add(itemCatData2);

                        //如果二级目录是父目录
                        if (cat2.getIsParent()){
                            List<ItemCat> itemCatList2 = itemCatMapper.selectAllByParentId(cat2.getId());//三级目录
                            List<String> dataList2 = new ArrayList<>();
                            itemCatData2.setItems(dataList2);
                            for (ItemCat cat3 : itemCatList2) {
                                //三级目录的数据封装
                                dataList2.add("/products/" + cat3.getId() + ".html|" + cat3.getName());
                            }
                        }
                    }
                }
                if (itemCatResult.getData().size() >= 14) {
                    break;
                }
            }

            //对象转化为json，存到redis中
            String json = JsonUtils.objectToJson(itemCatResult);
            redisUtil.set(Item_Cat_Result_KEY,json);
            //指定缓存保存时间为1天
            redisUtil.expire(Item_Cat_Result_KEY,60*60*24);
        }

        return itemCatResult;
    }
}

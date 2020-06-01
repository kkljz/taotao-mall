package com.qtu.portal.service.impl;

import com.qtu.portal.entity.Item;
import com.qtu.portal.entity.ItemDesc;
import com.qtu.portal.entity.ItemParamItem;
import com.qtu.portal.service.ItemService;
import com.qtu.util.HttpClientUtil;
import com.qtu.util.JsonUtils;
import com.qtu.util.TaotaoResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author Hu Shengkai
 * @create 2019-12-07 16:31
 */
@Service
public class ItemServiceImpl implements ItemService {
    @Value("${url.base.rest}")
    private String REST_BASE_URL;
    @Value("${url.items.item}")
    private String ITEM_URL;
    @Value("${url.items.itemdesc}")
    private String ITEM_DESC_URL;
    @Value("${url.items.itemparam}")
    private String ITEM_PARAM_URL;


    @Override
    public Item getItemById(Long id) {
        //查询商品信息
        String resultJson = HttpClientUtil.doGet(REST_BASE_URL + ITEM_URL + id);
//        System.out.println("resultJson:"+resultJson);
        TaotaoResult taotaoResult = TaotaoResult.formatToPojo(resultJson, Item.class);
        Item item = null;
//        System.out.println("taotalResult的值:"+taotaoResult);
        if (taotaoResult.getStatus() == 200) {
            item = (Item) taotaoResult.getData();
        }
        return item;
    }

    @Override
    public ItemDesc getItemDescByItemId(Long itemId) {
        //查询商品描述信息
        String resultJson = HttpClientUtil.doGet(REST_BASE_URL + ITEM_DESC_URL + itemId);
//        System.out.println("resultJson:"+resultJson);
        TaotaoResult taotaoResult = TaotaoResult.formatToPojo(resultJson, ItemDesc.class);
//        System.out.println("taotalResult的值:"+taotaoResult);
        ItemDesc itemDesc = null;
        if (taotaoResult.getStatus() == 200) {
            itemDesc = (ItemDesc) taotaoResult.getData();
        }
        return itemDesc;
    }

    @Override
    public String getItemParamByItemId(Long itemId) {
        //查询商品规格参数信息
        String resultJson = HttpClientUtil.doGet(REST_BASE_URL + ITEM_PARAM_URL + itemId);
        System.out.println("resultJson:"+resultJson);
        TaotaoResult taotaoResult = TaotaoResult.formatToPojo(resultJson, ItemParamItem.class);
        System.out.println("taotaoResult:"+taotaoResult);
        String resultHtml = "";
        if (taotaoResult.getStatus() == 200){
            ItemParamItem itemParamItem = (ItemParamItem) taotaoResult.getData();
            System.out.println("itemParamItem:"+itemParamItem);
            //取出规格参数信息
            String paramData = itemParamItem.getParamData();
            //把规格参数转换成java对象
            List<Map> paramList = JsonUtils.jsonToList(paramData, Map.class);
            //拼装html
            resultHtml = "<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"0\" class=\"Ptable\"> <tbody>\n";
            for (Map map : paramList) {
                resultHtml += "<tr><th class='tdTitle' colspan='2'>" + map.get("group") + "</th></tr>";
                List<Map> params = (List<Map>) map.get("params");
                for (Map map2 : params) {
                    resultHtml += "<tr><td class=\"tdTitle\">" + map2.get("k") + "</td><td>" + map2.get("v") + "</td></tr>";
                }

            }
            resultHtml += "</tbody></table>";

        }
        return resultHtml;
    }
}

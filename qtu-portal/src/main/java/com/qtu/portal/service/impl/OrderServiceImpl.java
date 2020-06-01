package com.qtu.portal.service.impl;

import com.qtu.portal.bean.OrderBean;
import com.qtu.portal.service.OrderService;
import com.qtu.util.HttpClientUtil;
import com.qtu.util.JsonUtils;
import com.qtu.util.TaotaoResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author Hu Shengkai
 * @create 2019-12-13 14:46
 */
@Service
public class OrderServiceImpl implements OrderService {

    //订单服务url
    @Value("${ORDER_BASE_URL}")
    private String ORDER_BASE_URL;
    //新加订单服务
    @Value("${ORDER_CREATE}")
    private String ORDER_CREATE;

    @Override
    public TaotaoResult createOrder(OrderBean orderBean) {
        String orderJson = JsonUtils.objectToJson(orderBean);
        String resultJson = HttpClientUtil.doPostJson(ORDER_BASE_URL + ORDER_CREATE, orderJson);
        System.out.println("resultJson:"+resultJson);
        TaotaoResult result = TaotaoResult.formatToPojo(resultJson, String.class);
        System.out.println("TaotaoResult:"+result);
        return result;
    }
}

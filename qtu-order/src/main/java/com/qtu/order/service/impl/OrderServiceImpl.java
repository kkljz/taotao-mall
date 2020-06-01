package com.qtu.order.service.impl;

import com.auth0.jwt.internal.org.apache.commons.lang3.StringUtils;
import com.qtu.order.entity.Order;
import com.qtu.order.entity.OrderItem;
import com.qtu.order.entity.OrderShipping;
import com.qtu.order.mapper.OrderItemMapper;
import com.qtu.order.mapper.OrderMapper;
import com.qtu.order.mapper.OrderShippingMapper;
import com.qtu.order.service.OrderService;
import com.qtu.order.util.RedisUtil;
import com.qtu.util.TaotaoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author Hu Shengkai
 * @create 2019-12-13 9:30
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderItemMapper orderItemMapper;
    @Autowired
    private OrderShippingMapper orderShippingMapper;
    @Autowired
    private RedisUtil redisUtil;


    //订单号生成key
    @Value("${ORDER_ID_KEY}")
    private String ORDER_ID_KEY;
    //订单号起始值
    @Value("${ORDER_ID_BEGIN}")
    private Long ORDER_ID_BEGIN;



    @Override
    public TaotaoResult insertOrder(Order order, List<OrderItem> orderItemList, OrderShipping orderShipping) {
       /*
        1.生成订单id
        2.补全订单属性
        3.遍历商品详情list，把商品插入到商品订单明细表
        4.插入物流表
        */
       //生成订单号
        String orderIdStr = (String) redisUtil.get(ORDER_ID_KEY);
        Long orderId =null;
        if (StringUtils.isEmpty(orderIdStr)){
            //没有订单号，就进行订单号初始化
            redisUtil.set(ORDER_ID_KEY , ORDER_ID_BEGIN.toString());
            orderId = ORDER_ID_BEGIN;
        }else {
            orderId = redisUtil.incr(ORDER_ID_KEY,1L);
        }
        //补全订单属性
        //设置订单号
        order.setOrderId(orderId.toString());
        Date date = new Date();
        //订单创建时间
        order.setCreateTime(date);
        //订单更新时间
        order.setUpdateTime(date);
        //状态：1、未付款，2、已付款，3、未发货，4、已发货，5、交易成功，6、交易关闭
        order.setStatus(1);
        //插入订单表
        orderMapper.insert(order);

        //插入订单详情
        for (OrderItem orderItem : orderItemList) {
            //设置订单id
            orderItem.setOrderId(orderId.toString());
            orderItemMapper.insert(orderItem);
        }

        //插入物流表
        orderShipping.setOrderId(orderId.toString());
        orderShipping.setCreated(new Date());
        orderShipping.setUpdated(new Date());
        orderShippingMapper.insert(orderShipping);

        return TaotaoResult.ok(orderId.toString());
    }
}

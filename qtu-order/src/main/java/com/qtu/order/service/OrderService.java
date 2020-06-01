package com.qtu.order.service;

import com.qtu.order.entity.Order;
import com.qtu.order.entity.OrderItem;
import com.qtu.order.entity.OrderShipping;
import com.qtu.util.TaotaoResult;

import java.util.List;

/**
 * @author Hu Shengkai
 * @create 2019-12-13 9:24
 */
public interface OrderService {

    /**
     * 添加订单
     * @param order
     * @param orderItemList
     * @param orderShipping
     * @return
     */
    TaotaoResult insertOrder(Order order, List<OrderItem> orderItemList, OrderShipping orderShipping);
}

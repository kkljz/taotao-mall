package com.qtu.portal.service;

import com.qtu.portal.bean.OrderBean;
import com.qtu.util.TaotaoResult;

/**
 * @author Hu Shengkai
 * @create 2019-12-13 14:44
 */
public interface OrderService {
    /**
     * 新加订单
     * @return
     */
    TaotaoResult createOrder(OrderBean orderBean);
}

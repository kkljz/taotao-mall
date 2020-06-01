package com.qtu.order.controller;

import com.qtu.order.bean.OrderBean;
import com.qtu.order.service.OrderService;
import com.qtu.util.ExceptionUtil;
import com.qtu.util.TaotaoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Hu Shengkai
 * @create 2019-12-13 10:16
 */
@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @ResponseBody
    @RequestMapping("/add")
    public TaotaoResult createOrder(@RequestBody OrderBean orderBean){
        TaotaoResult taotaoResult = null;
        try {
            taotaoResult = orderService.insertOrder(orderBean, orderBean.getOrderItems(), orderBean.getOrderShipping());
        } catch (Exception e) {
            e.printStackTrace();
            taotaoResult = TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
        return taotaoResult;
    }
}

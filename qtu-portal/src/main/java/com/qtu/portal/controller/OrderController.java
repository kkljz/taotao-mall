package com.qtu.portal.controller;

import com.qtu.portal.bean.CheckLoginBean;
import com.qtu.portal.bean.OrderBean;
import com.qtu.portal.entity.Order;
import com.qtu.portal.entity.User;
import com.qtu.portal.service.CartService;
import com.qtu.portal.service.OrderService;
import com.qtu.util.ExceptionUtil;
import com.qtu.util.TaotaoResult;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 订单
 * @author Hu Shengkai
 * @create 2019-12-13 14:35
 */
@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private CartService cartService;
    @Autowired
    private OrderService orderService;

    /**
     * 显示订单完整信息页面
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("/order-cart")
    public String showOrderCart(HttpServletRequest request, Model model){
        CheckLoginBean checkLoginBean = cartService.getCartList(request);
        model.addAttribute("cartList",checkLoginBean.getCartList());
        return "order-cart";
    }

    @RequestMapping("/create")
    public String createOrder(HttpServletRequest request, OrderBean orderBean, Model model){
        //从request中将当前登录用户取出
        User user = (User) request.getAttribute("user");
        orderBean.setUserId(user.getId());
        orderBean.setBuyerNick(user.getUsername());
        TaotaoResult result = null;
        try {
            //提交订单
            result = orderService.createOrder(orderBean);
            if (result.getStatus() == 200){
                //清空购物车
                cartService.cleanCartList(request);

                model.addAttribute("orderId", result.getData());
                model.addAttribute("payment", orderBean.getPayment());
                //两天后送达
                DateTime dateTime = new DateTime();
                dateTime = dateTime.plusDays(2);
                model.addAttribute("date", dateTime.toString("yyyy-MM-dd"));
                return "success";
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
        model.addAttribute("message",result.getMsg());
        return "error/exception";
    }
}

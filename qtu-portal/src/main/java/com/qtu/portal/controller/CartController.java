package com.qtu.portal.controller;

import com.qtu.portal.bean.CheckLoginBean;
import com.qtu.portal.entity.CartItem;
import com.qtu.portal.service.CartService;
import com.qtu.util.ExceptionUtil;
import com.qtu.util.TaotaoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 购物车
 * @author Hu Shengkai
 * @create 2019-12-11 14:38
 */
@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    /**
     * 添加商品进购物车
     * @param itemId
     * @param model
     * @param num
     * @param response
     * @param request
     * @return
     */
    @RequestMapping("/add/{itemId}")
    public String addItem(@PathVariable("itemId") Long itemId, Model model,
                          @RequestParam(defaultValue = "1") Integer num,
                          HttpServletResponse response, HttpServletRequest request) {
        TaotaoResult taotaoResult = null;
        try {
            taotaoResult = cartService.addItemToCart(request, response, itemId, num);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message",ExceptionUtil.getStackTrace(e));
            return "error/exception";
        }
        /*if (taotaoResult!=null){
            model.addAttribute("cartList",taotaoResult.getData());
        }*/
        return "redirect:/cart/list";
    }

    /**
     * 获取购物车列表
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/list")
    public String getCartList(HttpServletRequest request, Model model){
        CheckLoginBean checkLoginBean = cartService.getCartList(request);
        List<CartItem> cartList = checkLoginBean.getCartList();
        model.addAttribute("cartList",cartList);
        return "cart";
    }

    /**
     * 修改购物车商品数量
     * @param itemId
     * @param numValue
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/update/num/{itemId}/{numValue}", method = RequestMethod.POST)
    public TaotaoResult updateItemNum(@PathVariable("itemId") Long itemId , @PathVariable("numValue") Integer numValue ,
                                      HttpServletRequest request, HttpServletResponse response){
        TaotaoResult taotaoResult = cartService.updateItemNum(request, response, itemId, numValue);
        return taotaoResult;
    }

    /**
     * 从购物车中删除商品
     * @param itemId
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping("/delete/{itemId}")
    public TaotaoResult deleteItemInCart(@PathVariable("itemId") Long itemId , HttpServletRequest request, HttpServletResponse response){
        TaotaoResult taotaoResult = cartService.deleteItemInList(request, response, itemId);
        return taotaoResult;
    }
}

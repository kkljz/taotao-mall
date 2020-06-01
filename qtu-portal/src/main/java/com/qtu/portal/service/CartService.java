package com.qtu.portal.service;

import com.qtu.portal.bean.CheckLoginBean;
import com.qtu.portal.entity.CartItem;
import com.qtu.util.TaotaoResult;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 购物车业务
 * @author Hu Shengkai
 * @create 2019-12-11 11:43
 */
public interface CartService {

    /**
     * 添加商品进购物车
     * @param request
     * @param response
     * @param itemId
     * @param num
     * @return
     */
    TaotaoResult addItemToCart(HttpServletRequest request, HttpServletResponse response, Long itemId, Integer num);

    /**
     * 根据商品id获取商品信息
     * @param itemId
     * @return
     */
    CartItem getCartItemById(Long itemId);

    /**
     * 获取购物车
     * @param request
     * @return
     */
//    List<CartItem> getCart(HttpServletRequest request);

    /**
     * 从redis中获取购物车
     * @param redisKey
     * @return
     */
    List<CartItem> getCartFromRedis(String redisKey);

    /**
     * 修改购物车商品数量
     * @param itemId
     * @param num
     * @return
     */
    TaotaoResult updateItemNum(HttpServletRequest request, HttpServletResponse response, Long itemId, int num);

    /**
     * 根据商品id删除购物车中的商品
     * @param request
     * @param response
     * @param itemId
     * @return
     */
    TaotaoResult deleteItemInList(HttpServletRequest request, HttpServletResponse response, Long itemId);

    TaotaoResult cleanCartList(HttpServletRequest request);

    /**
     * 获取购物车商品列表
     * @return
     */
    CheckLoginBean getCartList(HttpServletRequest request);
}

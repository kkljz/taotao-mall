package com.qtu.portal.service.impl;

import com.qtu.portal.bean.CheckLoginBean;
import com.qtu.portal.entity.CartItem;
import com.qtu.portal.entity.Item;
import com.qtu.portal.entity.User;
import com.qtu.portal.service.CartService;
import com.qtu.portal.util.RedisUtil;
import com.qtu.util.CookieUtils;
import com.qtu.util.HttpClientUtil;
import com.qtu.util.JsonUtils;
import com.qtu.util.TaotaoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Hu Shengkai
 * @create 2019-12-11 11:46
 */
@Service
public class CartServiceImpl implements CartService {
    //rest服务url
    @Value("${url.base.rest}")
    private String REST_BASE_URL;

    //商品服务url
    @Value("${url.items.item}")
    private String ITEM_URL;

    //COOKIE中购物车商品对应的key
    @Value("${cart.items.list.key}")
    private String CART_ITEMS_LIST_KEY;

    //购物车cookie生存期
    @Value("${cart.items.expire.time}")
    private int CART_ITEMS_EXPIRE_TIME;

    //用户登录后保存的token name
    @Value("${QTU_USER_TOKEN}")
    private String QTU_USER_TOKEN;

    //单点登录url
    @Value("${url.base.sso}")
    private String SSO_BASE_URL;

    //购物车页面的sessionId名
    @Value("${SESSIONID}")
    private String SESSIONID;

    //保存到redis中的用户购物车key
    @Value("${USER_CART_LIST_KEY}")
    private String USER_CART_LIST_KEY;

    @Autowired
    private RedisUtil redisUtil;


    @Override
    public TaotaoResult addItemToCart(HttpServletRequest request, HttpServletResponse response , Long itemId , Integer num) {
        //根据id查询商品的详细信息
        CartItem cartItem = getCartItemById(itemId);
        if (cartItem == null){
            return TaotaoResult.build(400,"未找到此商品");
        }
        //得到购物车，登录状态，登录用户
        CheckLoginBean checkLoginBean = getCartList(request);
        List<CartItem> cartList = checkLoginBean.getCartList();
        boolean isLogin = checkLoginBean.getIsLogin();
        User user = checkLoginBean.getUser();
        //判断购物车中是否存在此商品
        boolean flag = false;
        for (CartItem item : cartList) {
            if (item.getId().equals(itemId)){
                //存在,增加数量
                item.setNum(item.getNum()+num);
                flag = true;
                break;
            }
        }
        //如果商品不存在于购物车则向购物车商品列表中添加一个商品
        if (!flag){
            cartItem.setNum(num);
            cartList.add(cartItem);
        }
        String jsonData = JsonUtils.objectToJson(cartList);
        //将购物车存入redis中
        if (isLogin){
            redisUtil.set(USER_CART_LIST_KEY + ":" + user.getId() , jsonData , 60*60*24);
        }else {
            String sessionIdValue = CookieUtils.getCookieValue(request, SESSIONID);
            redisUtil.set(sessionIdValue , jsonData , 60*60*24);
        }
        return TaotaoResult.ok(cartList);
    }

    @Override
    public CartItem getCartItemById(Long itemId) {
        String json = HttpClientUtil.doGet(REST_BASE_URL + ITEM_URL + itemId);
        TaotaoResult result = TaotaoResult.formatToPojo(json, Item.class);
        if (result.getStatus() == 200){
            CartItem cartItem = new CartItem();
            Item item = (Item) result.getData();
            //封装cartItem
            cartItem.setId(item.getId());
            cartItem.setImage(item.getImage());
            cartItem.setNum(item.getNum());
            cartItem.setPrice(item.getPrice());
            cartItem.setTitle(item.getTitle());
            return cartItem;
        }
        return null;
    }

//    @Override
//    public List<CartItem> getCart(HttpServletRequest request) {
//        String cookieValue = CookieUtils.getCookieValue(request, CART_ITEMS_LIST_KEY, true);
//        List<CartItem> cartItemList;
//        if (StringUtils.isEmpty(cookieValue)){
//            cartItemList = new ArrayList<>();
//        }else {
//            cartItemList = JsonUtils.jsonToList(cookieValue, CartItem.class);
//        }
//        return cartItemList;
//    }

    @Override
    public List<CartItem> getCartFromRedis(String redisKey) {
        boolean b = redisUtil.hasKey(redisKey);
        if (b){
            String cartJson = (String) redisUtil.get(redisKey);
            List<CartItem> cartList = JsonUtils.jsonToList(cartJson, CartItem.class);
            return cartList;
        }else {
            return new ArrayList<>();
        }
    }

    @Override
    public TaotaoResult updateItemNum(HttpServletRequest request, HttpServletResponse response, Long itemId, int num) {
        //根据id查询商品的详细信息
        CartItem cartItem = getCartItemById(itemId);
        if (cartItem == null){
            return TaotaoResult.build(400,"未找到此商品");
        }
        //得到购物车，登录状态，登录用户
        CheckLoginBean checkLoginBean = getCartList(request);
        List<CartItem> cartList = checkLoginBean.getCartList();
        boolean isLogin = checkLoginBean.getIsLogin();
        User user = checkLoginBean.getUser();
        //判断购物车中是否存在此商品
        boolean flag = false;
        for (CartItem item : cartList) {
            if (item.getId().equals(itemId)){
                flag = true;
                //存在,修改数量
                if (num == 0){
                    //数量为0，从购物车中移除此商品
                    cartList.remove(item);
                }else {
                    item.setNum(num);
                }
                break;
            }
        }
        if (!flag) {
            return TaotaoResult.build(400,"购物车中未找到此商品");
        }
        String jsonData = JsonUtils.objectToJson(cartList);
        //将购物车存入redis中
        if (isLogin){
            redisUtil.set(USER_CART_LIST_KEY + ":" + user.getId() , jsonData , 60*60*24);
        }else {
            String sessionIdValue = CookieUtils.getCookieValue(request, SESSIONID);
            redisUtil.set(sessionIdValue , jsonData , 60*60*24);
        }
        return TaotaoResult.ok(cartList);
    }

    @Override
    public TaotaoResult deleteItemInList(HttpServletRequest request, HttpServletResponse response, Long itemId) {
        //根据id查询商品的详细信息
        CartItem cartItem = getCartItemById(itemId);
        if (cartItem == null){
            return TaotaoResult.build(400,"未找到此商品");
        }
        //得到购物车，登录状态，登录用户
        CheckLoginBean checkLoginBean = getCartList(request);
        List<CartItem> cartList = checkLoginBean.getCartList();
        boolean isLogin = checkLoginBean.getIsLogin();
        User user = checkLoginBean.getUser();
        //判断购物车中是否存在此商品
        boolean flag = false;
        for (CartItem item : cartList) {
            if (item.getId().equals(itemId)){
                flag = true;
                cartList.remove(item);//从购物车中删除
                break;
            }
        }
        if (!flag) {
            return TaotaoResult.build(400,"购物车中未找到此商品");
        }
        //将购物车存入redis中
        String json = JsonUtils.objectToJson(cartList);
        if (isLogin){
            redisUtil.set(USER_CART_LIST_KEY + ":" + user.getId() , json , 60*60*24);
        }else {
            String sessionIdValue = CookieUtils.getCookieValue(request, SESSIONID);
            redisUtil.set(sessionIdValue , json , 60*60*24);
        }
        return TaotaoResult.ok(cartList);
    }

    @Override
    public TaotaoResult cleanCartList(HttpServletRequest request) {
        String token = CookieUtils.getCookieValue(request, QTU_USER_TOKEN);
        if (StringUtils.isEmpty(token)){
            return TaotaoResult.build(400,"用户未登录");
        }
        String json = HttpClientUtil.doGet(SSO_BASE_URL+"/user/getUser/" + token);
        TaotaoResult result = TaotaoResult.formatToPojo(json, User.class);
        User user = null;
        if (result != null && result.getStatus() == 200){
            user = (User) result.getData();
            if (user == null){
                //登录过时
                return TaotaoResult.build(400,"登录过时");
            }else {
                //清空购物车
                redisUtil.del(USER_CART_LIST_KEY + ":" + user.getId());
                return TaotaoResult.ok();
            }
        }
        return TaotaoResult.build(400,"出现异常，清除失败");
    }

    @Override
    public CheckLoginBean getCartList(HttpServletRequest request) {
        //声明购物车
        List<CartItem> cartList;
        boolean isLogin = true;
        User user = null;
        //判断当前是否有人登录
        String token = CookieUtils.getCookieValue(request, QTU_USER_TOKEN);
        if (StringUtils.isEmpty(token)){
            //无人登录
            isLogin = false;
        }else {
            System.out.println(SSO_BASE_URL+"/user/getUser/" + token);
            String json = HttpClientUtil.doGet(SSO_BASE_URL+"/user/getUser/" + token);
            System.out.println(json);
            TaotaoResult result = TaotaoResult.formatToPojo(json, User.class);
            if (result.getStatus() ==200){
                user = (User) result.getData();
                if (user == null){
                    //登录过时
                    isLogin = false;
                }
            }else {
                isLogin = false;
            }
        }
        //根据是否登录来选择存储购物车的方式
        if (isLogin){//已登录
            cartList = getCartFromRedis(USER_CART_LIST_KEY + ":" + user.getId());

            //得到未登录之前的购物车的sessionId
            String sessionIdValue = CookieUtils.getCookieValue(request, SESSIONID);

            //合并购物车
            if (!StringUtils.isEmpty(sessionIdValue)){
                List<CartItem> beforCartList = getCartFromRedis(sessionIdValue);
                for (CartItem bItem : beforCartList) {
                    boolean f = false;
                    for (CartItem aItem : cartList) {
                        //如果新购物车中存在和老购物车一样的商品，则合并数量num
                        if (bItem.getId().equals(aItem.getId())){
                            f = true;
                            aItem.setNum(aItem.getNum() + bItem.getNum());
                            break;
                        }
                    }
                    if (!f){
                        cartList.add(bItem);
                    }
                }
                //将购物车存入redis中
                String json = JsonUtils.objectToJson(cartList);
                redisUtil.set(USER_CART_LIST_KEY + ":" + user.getId() , json , 60*60*24);

                //清空浏览器购物车
                redisUtil.del(sessionIdValue);
            }

        }else {
            //当前无人登录，从redis中取sessionId 的购物车
            String sessionIdValue = CookieUtils.getCookieValue(request, SESSIONID);
            cartList = getCartFromRedis(sessionIdValue);
        }

        CheckLoginBean checkLoginBean = new CheckLoginBean();
        checkLoginBean.setCartList(cartList);
        checkLoginBean.setIsLogin(isLogin);
        checkLoginBean.setUser(user);
        return checkLoginBean;
    }
}

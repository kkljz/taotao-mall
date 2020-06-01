package com.qtu.portal.bean;

import com.qtu.portal.entity.CartItem;
import com.qtu.portal.entity.User;
import lombok.Data;

import java.util.List;

/**
 * @author Hu Shengkai
 * @create 2019-12-12 14:16
 */
@Data
public class CheckLoginBean {
    private Boolean isLogin;
    private List<CartItem> cartList;
    private User user;
}

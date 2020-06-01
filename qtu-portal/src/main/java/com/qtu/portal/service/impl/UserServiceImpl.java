package com.qtu.portal.service.impl;

import com.qtu.portal.entity.User;
import com.qtu.portal.service.UserService;
import com.qtu.util.HttpClientUtil;
import com.qtu.util.TaotaoResult;
import org.springframework.stereotype.Service;

/**
 * @author Hu Shengkai
 * @create 2019-12-10 17:33
 */
@Service
public class UserServiceImpl implements UserService {

    private String SSO_USER_GETUSER = "http://sso.qtu.com/user/getUser/";
    @Override
    public User getUserByToken(String token) {
        String json = HttpClientUtil.doGet(SSO_USER_GETUSER + token);
        TaotaoResult result = TaotaoResult.formatToPojo(json, User.class);
        if (result.getStatus() == 200){
            //取user对象返回
            return (User) result.getData();
        }
        return null;
    }
}

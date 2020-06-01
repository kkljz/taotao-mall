package com.qtu.portal.interceptor;

import com.qtu.portal.entity.User;
import com.qtu.portal.service.UserService;
import com.qtu.util.CookieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Hu Shengkai
 * @create 2019-12-10 17:27
 */
@Component
public class OrderInterceptor implements HandlerInterceptor {

    @Autowired
    private UserService userService;

    private String SSO_LOGIN_URL = "http://sso.qtu.com/login";//单点登录系统的登录界面

    private String QTU_USER_TOKEN = "QTU_USER_TOKEN";//用户token keyname

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //从cookie中取token
        String token = CookieUtils.getCookieValue(request, QTU_USER_TOKEN);
        if (token == null || token.equals("")){
            //返回到登录界面
            response.sendRedirect(SSO_LOGIN_URL+"?redirect="+request.getRequestURL());
            return false;
        }else {
            User user = userService.getUserByToken(token);
            //判断登录是否过时
            if (user == null){
                //返回到登录界面
                response.sendRedirect(SSO_LOGIN_URL+"?redirect="+request.getRequestURL());
                return false;
            }

            //取到用户信息，放行
            request.setAttribute("user",user);
            return true;
        }
    }
}

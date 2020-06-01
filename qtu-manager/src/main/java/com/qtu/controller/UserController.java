package com.qtu.controller;

import com.qtu.entity.User;
import com.qtu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Hu Shengkai
 * @create 2019-11-26 21:06
 */
@Controller
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Object login(String username, String password, HttpSession session){
        User user = userService.login(username, password);
        Map<String,Object> map = new HashMap<>();
        if (user != null) {
            map.put("code",200);
            session.setAttribute("user",user);
        }else {
            map.put("code",400);
        }
        return map;
    }
}

package com.qtu.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Hu Shengkai
 * @create 2019-12-04 10:07
 */
@Controller
public class PageController {

    @RequestMapping("/register")
    public String goRegister(){
        return "register";
    }

    @RequestMapping("/login")
    public String goLogin(String redirect, Model model){
        model.addAttribute("redirect",redirect);
        return "login";
    }
}

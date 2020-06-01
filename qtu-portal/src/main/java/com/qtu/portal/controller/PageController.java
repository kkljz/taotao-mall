package com.qtu.portal.controller;

import com.qtu.portal.service.ADService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Hu Shengkai
 * @create 2019-12-04 10:07
 */
@Controller
public class PageController {
    @Autowired
    private ADService adService;

    @RequestMapping("/page/{view}")
    public String page(@PathVariable("view") String view){
        return view;
    }

    @RequestMapping("/")
    public String goIndex(Model model){
        String adResult = adService.queryIndexAD();
        model.addAttribute("ad1",adResult);
        return "index";
    }

    @RequestMapping("/register")
    public String goRegister(){
        return "register";
    }

    @RequestMapping("/cart")
    public String goCart(){
        return "cart";
    }

    @RequestMapping("/login")
    public String goLogin(){
        return "login";
    }
}

package com.qtu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Hu Shengkai
 * @create 2019-11-26 21:47
 */
@Controller
public class PageController {
    @RequestMapping("{path}")
    public String page(@PathVariable String path){
        return path;
    }
}

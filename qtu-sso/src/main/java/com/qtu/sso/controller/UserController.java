package com.qtu.sso.controller;

import com.qtu.sso.entity.User;
import com.qtu.sso.service.UserService;
import com.qtu.util.CookieUtils;
import com.qtu.util.ExceptionUtil;
import com.qtu.util.JsonUtils;
import com.qtu.util.TaotaoResult;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Hu Shengkai
 * @create 2019-12-09 16:24
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping("/check/{param}/{type}")
    public Object checkData(@PathVariable("param") String param, @PathVariable("type") Integer type, String callback){
        TaotaoResult taotaoResult = userService.checkData(param, type);
        String json = JsonUtils.objectToJson(taotaoResult);
        //支持jsonp
        if (null != callback && !"".equals(callback)){
            return callback+"("+json+")";
        }
        return json;
    }

    @ResponseBody
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Object register(String callback, User user){
        TaotaoResult taotaoResult = userService.insertUser(user);
        String json = JsonUtils.objectToJson(taotaoResult);
        //支持jsonp
        if (null != callback && !"".equals(callback)){
            return callback+"("+json+")";
        }
        return taotaoResult;
    }

    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Object login(String username, String password, HttpServletRequest request, HttpServletResponse response){
        TaotaoResult taotaoResult = null;
        try {
            taotaoResult = userService.userLogin(username, password);
            //把令牌存储起来
            CookieUtils.setCookie(request,response,"QTU_USER_TOKEN",taotaoResult.getData().toString(), 60*30);

        }catch (Exception e){
            taotaoResult = TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
        return taotaoResult;
    }

    /**
     * 根据token获取user
     * @param ticket
     * @return
     */
    @ResponseBody
    @RequestMapping("/getUser/{ticket}")
    public Object getUserByToken(@PathVariable("ticket") String ticket, String callback){
        TaotaoResult result = null;
        try {
            result = userService.getUserByToken(ticket);
        }catch (Exception e){
            result = TaotaoResult.build(500,ExceptionUtil.getStackTrace(e));
        }
        if (callback != null && !callback.equals("")){
            String json = JsonUtils.objectToJson(result);
            String callJSon = callback + "("+ json +")";
            return callJSon;
        }
        return result;
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request , HttpServletResponse response){
        userService.logout(request,response);
        return "redirect:http://www.qtu.com/";
    }
}

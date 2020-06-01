package com.qtu.sso.service;

import com.qtu.sso.entity.User;
import com.qtu.util.TaotaoResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Hu Shengkai
 * @create 2019-12-09 16:00
 */
public interface UserService {

    /**
     * 数据校验，type是校验类型，1代表username，2代表phone，3代表email
     * @param param
     * @param type
     * @return
     */
    TaotaoResult checkData(String param, Integer type);

    /**
     * 添加用户
     * @param user
     * @return
     */
    TaotaoResult insertUser(User user);

    TaotaoResult userLogin(String username, String password);

    TaotaoResult getUserByToken(String token);

    void logout(HttpServletRequest request, HttpServletResponse response);
}

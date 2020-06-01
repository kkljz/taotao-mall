package com.qtu.portal.service;


import com.qtu.portal.entity.User;

/**
 * @author Hu Shengkai
 * @create 2019-12-10 17:30
 */
public interface UserService {
    User getUserByToken(String token);
}

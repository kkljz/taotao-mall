package com.qtu.service;

import com.qtu.entity.User;

/**
 * @author Hu Shengkai
 * @create 2019-11-26 22:05
 */
public interface UserService {
    User login(String username, String password);
}

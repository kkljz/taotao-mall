package com.qtu.service.impl;

import com.qtu.entity.User;
import com.qtu.mapper.UserMapper;
import com.qtu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Hu Shengkai
 * @create 2019-11-26 22:06
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User login(String username, String password) {
        User user = userMapper.selectByUsernameAndPassword(username, password);
        return user;
    }
}

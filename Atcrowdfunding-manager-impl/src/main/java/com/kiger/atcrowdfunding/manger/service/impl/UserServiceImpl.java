package com.kiger.atcrowdfunding.manger.service.impl;

import com.kiger.atcrowdfunding.bean.Param;
import com.kiger.atcrowdfunding.bean.User;
import com.kiger.atcrowdfunding.exception.LoginFailException;
import com.kiger.atcrowdfunding.manager.dao.UserMapper;
import com.kiger.atcrowdfunding.manager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @ClassName UserServiceImpl
 * @Description TODO
 * @Author zk_kiger
 * @Date 2019/9/10 18:21
 * @Version 1.0
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;


    public User queryUserLogin(Map<String, Object> paramMap) {

        User user = userMapper.queryUserLogin(paramMap);

        if (user == null) {
            throw new LoginFailException("用户账号或密码不正确!");
        }

        return user;
    }
}

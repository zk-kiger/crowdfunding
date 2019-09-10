package com.kiger.atcrowdfunding.manager.service;

import com.kiger.atcrowdfunding.bean.User;

import java.util.Map;

/**
 * @ClassName UserService
 * @Description TODO
 * @Author zk_kiger
 * @Date 2019/9/10 18:20
 * @Version 1.0
 */

public interface UserService {

    User queryUserLogin(Map<String, Object> paramMap);
}

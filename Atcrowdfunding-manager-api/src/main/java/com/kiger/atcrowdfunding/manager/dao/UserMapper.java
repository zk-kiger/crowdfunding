package com.kiger.atcrowdfunding.manager.dao;

import com.kiger.atcrowdfunding.bean.User;
import java.util.List;
import java.util.Map;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    User selectByPrimaryKey(Integer id);

    List<User> selectAll();

    int updateByPrimaryKey(User record);

    User queryUserLogin(Map<String, Object> paramMap);
}
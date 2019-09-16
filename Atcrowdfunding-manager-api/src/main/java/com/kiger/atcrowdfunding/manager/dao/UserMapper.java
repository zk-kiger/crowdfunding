package com.kiger.atcrowdfunding.manager.dao;

import com.kiger.atcrowdfunding.bean.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    User selectByPrimaryKey(Integer id);

    List<User> selectAll();

    int updateByPrimaryKey(User record);

    User queryUserLogin(Map<String, Object> paramMap);

//    List<User> queryList(@Param("startIndex") Integer startIndex,
//                   @Param("pagesize") Integer pagesize);

//    Integer queryCount();

    List<User> queryList(Map<String, Object> paramMap);

    Integer queryCount(Map<String, Object> paramMap);

}
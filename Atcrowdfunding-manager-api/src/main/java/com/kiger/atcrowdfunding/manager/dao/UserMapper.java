package com.kiger.atcrowdfunding.manager.dao;

import com.kiger.atcrowdfunding.bean.Role;
import com.kiger.atcrowdfunding.bean.User;
import com.kiger.atcrowdfunding.vo.Data;
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

    //    int deleteBatchUserByVO(Data data);
    int deleteBatchUserByVO(List<User> datas);

    List<Role> queryAllRole();

    List<Integer> queryRoleByUserId(Integer id);

    int saveUserRoleRelationship(@Param("userid") Integer userid,@Param("data") Data data);

    int deleteUserRoleRelationship(@Param("userid") Integer userid,@Param("data") Data data);
}
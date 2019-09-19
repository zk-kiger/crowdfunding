package com.kiger.atcrowdfunding.manager.service;

import com.kiger.atcrowdfunding.bean.Role;
import com.kiger.atcrowdfunding.bean.User;
import com.kiger.atcrowdfunding.util.Page;
import com.kiger.atcrowdfunding.vo.Data;

import java.util.List;
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

//    @Deprecated
//    Page queryPage(Integer pageno, Integer pagesize);

    int saveUser(User user);

    Page queryPage(Map<String, Object> paramMap);

    User getUserById(Integer id);

    int updateUser(User user);

    int deleteUser(Integer id);

    int deleteBatchUser(Integer[] ids);

    int deleteBatchUserByVO(Data data);

    List<Role> queryAllRole();

    List<Integer> queryRoleByUserId(Integer id);

    int saveUserRoleRelationship(Integer userid, Data data);

    int deleteUserRoleRelationship(Integer userid, Data data);
}

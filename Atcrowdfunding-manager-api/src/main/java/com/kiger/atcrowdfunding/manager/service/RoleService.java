package com.kiger.atcrowdfunding.manager.service;

import com.kiger.atcrowdfunding.bean.Role;
import com.kiger.atcrowdfunding.util.Page;
import com.kiger.atcrowdfunding.vo.Data;

import java.util.Map;

/**
 * @ClassName RoleService
 * @Description TODO
 * @Author zk_kiger
 * @Date 2019/9/19 19:14
 * @Version 1.0
 */

public interface RoleService {

    void saveRole(Role role);

    Page queryPage(Map paramMap);

    int deleteRole(Integer id);

    Role getRoleById(Integer id);

    int updateRole(Role role);

    int deleteBatchRole(Data data);

    int saveRolePermissionRelationship(Integer roleid, Data datas);
}

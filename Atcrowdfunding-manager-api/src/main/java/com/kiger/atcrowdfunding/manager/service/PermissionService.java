package com.kiger.atcrowdfunding.manager.service;

import com.kiger.atcrowdfunding.bean.Permission;

import java.util.List;

/**
 * @ClassName PermissionService
 * @Description TODO
 * @Author zk_kiger
 * @Date 2019/9/22 19:40
 * @Version 1.0
 */

public interface PermissionService {

    Permission getRootPermission();

    List<Permission> getChildrenPermisssionByPid(Integer id);

    List<Permission> queryAllPermission();

    int savePermission(Permission permission);

    Permission getPermissionById(Integer id);

    int updatePermission(Permission permission);

    int deletePermission(Integer id);

    List<Integer> queryPermissionIdsByRoleid(Integer roleid);
}

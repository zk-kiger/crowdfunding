package com.kiger.atcrowdfunding.manager.dao;

import com.kiger.atcrowdfunding.bean.Permission;
import java.util.List;

public interface PermissionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Permission record);

    Permission selectByPrimaryKey(Integer id);

    List<Permission> selectAll();

    int updateByPrimaryKey(Permission record);

    Permission getRootPermission();

    List<Permission> getChildrenPermisssionByPid(Integer id);

    List<Permission> queryAllPermission();

    List<Integer> queryPermissionIdsByRoleid(Integer roleid);

}
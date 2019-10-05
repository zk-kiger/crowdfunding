package com.kiger.atcrowdfunding.manger.service.impl;

import com.kiger.atcrowdfunding.bean.Permission;
import com.kiger.atcrowdfunding.manager.dao.PermissionMapper;
import com.kiger.atcrowdfunding.manager.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName PermissionServiceImpl
 * @Description TODO
 * @Author zk_kiger
 * @Date 2019/9/22 19:41
 * @Version 1.0
 */

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    public Permission getRootPermission() {
        return permissionMapper.getRootPermission();
    }

    public List<Permission> getChildrenPermisssionByPid(Integer id) {
        return permissionMapper.getChildrenPermisssionByPid(id);
    }

    public List<Permission> queryAllPermission() {
        return permissionMapper.queryAllPermission();
    }

    public int savePermission(Permission permission) {
        return permissionMapper.insert(permission);
    }

    public Permission getPermissionById(Integer id) {
        return permissionMapper.selectByPrimaryKey(id);
    }

    public int updatePermission(Permission permission) {
        return permissionMapper.updateByPrimaryKey(permission);
    }

    public int deletePermission(Integer id) {
        return permissionMapper.deleteByPrimaryKey(id);
    }

    public List<Integer> queryPermissionIdsByRoleid(Integer roleid) {
        return permissionMapper.queryPermissionIdsByRoleid(roleid);
    }
}

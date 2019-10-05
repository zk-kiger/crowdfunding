package com.kiger.atcrowdfunding.controller;

import com.kiger.atcrowdfunding.bean.Permission;
import com.kiger.atcrowdfunding.manager.service.PermissionService;
import com.kiger.atcrowdfunding.util.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName permission
 * @Description TODO
 * @Author zk_kiger
 * @Date 2019/9/22 19:38
 * @Version 1.0
 */

@Controller
@RequestMapping("/permission")
public class permissionController {

    @Autowired
    private PermissionService permissionService;

    @RequestMapping("/index")
    public String index() {
        return "permission/index";
    }

    @RequestMapping("/add")
    public String add() {
        return "permission/add";
    }

    @RequestMapping("/update")
    public String update(Integer id, Map map) {
        Permission permission = permissionService.getPermissionById(id);
        map.put("permission", permission);
        return "permission/update";
    }

    @ResponseBody
    @RequestMapping("/deletePermission")
    public Object deletePermission(Integer id) {
        AjaxResult result = new AjaxResult();
        try {

            int count = permissionService.deletePermission(id);
            result.setSuccess(count == 1);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("删除许可树数据失败!");
            e.printStackTrace();
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/doUpdate")
    public Object doUpdate(Permission permission) {
        AjaxResult result = new AjaxResult();
        try {

            int count = permissionService.updatePermission(permission);
            result.setSuccess(count == 1);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("修改许可树数据失败!");
            e.printStackTrace();
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/doAdd")
    public Object doAdd(Permission permission) {
        AjaxResult result = new AjaxResult();
        try {

            int count = permissionService.savePermission(permission);

            result.setSuccess(count == 1);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("保存许可树数据失败!");
            e.printStackTrace();
        }
        return result;
    }

    // Demo5 - map集合优化
    @ResponseBody
    @RequestMapping("/loadData")
    public Object loadData() {
        AjaxResult result = new AjaxResult();
        try {
            List<Permission> root = new ArrayList<Permission>();

            List<Permission> childredPermission = permissionService.queryAllPermission();
            Map<Integer, Permission> map = new HashMap<Integer, Permission>();

            for (Permission innerpermission :
                    childredPermission) {
                map.put(innerpermission.getId(), innerpermission);
            }

            for (Permission permission:
                    childredPermission) {
                // 通过子查找父
                Permission child = permission;
                if (child.getPid() == null) {
                    root.add(child);
                } else {
                    // 父结点
                    Permission parent = map.get(child.getPid());
                    parent.getChildren().add(child);
                }
            }

            result.setSuccess(true);
            result.setData(root);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("查询数据失败!");
            e.printStackTrace();
        }
        return result;
    }

    // Demo4 - 一次查表优化
    /*@ResponseBody
    @RequestMapping("/loadData")
    public Object loadData() {
        AjaxResult result = new AjaxResult();
        try {
            List<Permission> root = new ArrayList<Permission>();

            List<Permission> childredPermission = permissionService.queryAllPermission();
            for (Permission permission:
                 childredPermission) {
                // 通过子查找父
                Permission child = permission;
                if (child.getPid() == null) {
                    root.add(child);
                } else {
                    // 父结点
                    for (Permission innerpermission :
                            childredPermission) {
                        if (child.getPid() == innerpermission.getId()) {
                            Permission parent = innerpermission;
                            parent.getChildren().add(child);
                            break;
                        }
                    }
                }
            }

            result.setSuccess(true);
            result.setData(root);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("查询数据失败!");
            e.printStackTrace();
        }
        return result;
    }*/

    // Demo3 - 采用递归调用解决许可树多层次问题
    /*@ResponseBody
    @RequestMapping("/loadData")
    public Object loadData() {
        AjaxResult result = new AjaxResult();
        try {
            List<Permission> root = new ArrayList<Permission>();

            // 父
            Permission permission = permissionService.getRootPermission();
            permission.setOpen(true);
            root.add(permission);

            queryChildpermission(permission);

            result.setSuccess(true);
            result.setData(root);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("查询数据失败!");
            e.printStackTrace();
        }
        return result;
    }
    // 递归调用组合许可树
    private void queryChildpermission(Permission permission) {
        List<Permission> children = permissionService.getChildrenPermisssionByPid(permission.getId());

        // 组合父子关系
        permission.setChildren(children);

        for (Permission innerChildren :
                children) {
            queryChildpermission(innerChildren);
        }
    }*/

    // Demo2 - 从数据库中查询数据，显示许可树
    /*@ResponseBody
    @RequestMapping("/loadData")
    public Object loadData() {
        AjaxResult result = new AjaxResult();
        try {
            List<Permission> root = new ArrayList<Permission>();

            // 父
            Permission permission = permissionService.getRootPermission();
            permission.setOpen(true);
            root.add(permission);

            // 子
            List<Permission> children = permissionService.getChildrenPermisssionByPid(permission.getId());

            //设置父子关系
            permission.setChildren(children);

            for (Permission child :
                    children) {
                child.setOpen(true);
                List<Permission> innerChildren = permissionService.getChildrenPermisssionByPid(child.getId());
                child.setChildren(innerChildren);
            }

            result.setSuccess(true);
            result.setData(root);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("查询数据失败!");
            e.printStackTrace();
        }
        return result;
    }*/

    // Demo1 - 模拟数据显示许可树
    /*@ResponseBody
    @RequestMapping("/loadData")
    public Object loadData() {
        AjaxResult result = new AjaxResult();
        try {
            List<Permission> root = new ArrayList<Permission>();

            Permission permission = new Permission();
            permission.setName("系统权限菜单");
            permission.setOpen(true);

            List<Permission> children = new ArrayList<Permission>();

            Permission permission1 = new Permission();
            permission1.setName("控制面板");
            Permission permission2 = new Permission();
            permission2.setName("权限管理");
            children.add(permission1);
            children.add(permission2);

            permission.setChildren(children);

            result.setSuccess(true);
            root.add(permission);
            result.setData(root);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("查询数据失败!");
            e.printStackTrace();
        }
        return result;
    }*/

}

package com.kiger.atcrowdfunding.controller;

import com.kiger.atcrowdfunding.bean.Permission;
import com.kiger.atcrowdfunding.bean.Role;
import com.kiger.atcrowdfunding.manager.service.PermissionService;
import com.kiger.atcrowdfunding.manager.service.RoleService;
import com.kiger.atcrowdfunding.util.AjaxResult;
import com.kiger.atcrowdfunding.util.Page;
import com.kiger.atcrowdfunding.util.StringUtil;
import com.kiger.atcrowdfunding.vo.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName RoleController
 * @Description TODO
 * @Author zk_kiger
 * @Date 2019/9/18 22:40
 * @Version 1.0
 */

@Controller
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;

    @RequestMapping("/index")
    public String index() {
        return "role/index";
    }

    @RequestMapping("/add")
    public String add() {
        return "role/add";
    }

    @RequestMapping("/edit")
    public String edit(Integer id, Map map) {
        Role role = roleService.getRoleById(id);
        map.put("role", role);
        return "role/edit";
    }

    @RequestMapping("/assign")
    public String assign() {
        return "role/assignPermission";
    }

    @RequestMapping("/loadDataAsync")
    @ResponseBody
    public Object loadDataAsync(Integer roleid) {

        List<Permission> root = new ArrayList<Permission>();

        List<Permission> childredPermission = permissionService.queryAllPermission();

        // 根据角色id查询该角色之前所分配过的许可
        List<Integer> permissionIdsForRoleid = permissionService.queryPermissionIdsByRoleid(roleid);

        Map<Integer, Permission> map = new HashMap<Integer, Permission>();

        for (Permission innerpermission :
                childredPermission) {
            map.put(innerpermission.getId(), innerpermission);
            // 如果许可id在角色id的许可中出现,那么checked属性true
            if (permissionIdsForRoleid.contains(innerpermission.getId())) {
                innerpermission.setChecked(true);
            }
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

        return root;
    }

    @RequestMapping("/doAssignPermission")
    @ResponseBody
    public Object doAssignPermission(Integer roleid, Data datas) {

        AjaxResult result = new AjaxResult();

        try {
            int count = roleService.saveRolePermissionRelationship(roleid, datas);
            result.setSuccess(count == datas.getIds().size());
        } catch (Exception e) {
            result.setMessage("修改角色数据失败!");
            result.setSuccess(false);
            e.printStackTrace();
        }

        return result;
    }

    @RequestMapping("/deleteBatch")
    @ResponseBody
    public Object deleteBatch(Data data) {

        AjaxResult result = new AjaxResult();

        try {
            int count = roleService.deleteBatchRole(data);
            result.setSuccess(count == data.getRoles().size());
        } catch (Exception e) {
            result.setMessage("批量删除角色数据失败!");
            result.setSuccess(false);
            e.printStackTrace();
        }

        return result;
    }

    // 修改
    @RequestMapping("/doEdit")
    @ResponseBody
    public Object doEdit(Role role) {

        AjaxResult result = new AjaxResult();

        try {
            int count = roleService.updateRole(role);
            result.setSuccess(count == 1);
        } catch (Exception e) {
            result.setMessage("修改角色数据失败!");
            result.setSuccess(false);
            e.printStackTrace();
        }

        return result;
    }

    // 删除
    @RequestMapping("/doDelete")
    @ResponseBody
    public Object doDelete(Integer id) {

        AjaxResult result = new AjaxResult();

        try {
            int count = roleService.deleteRole(id);
            result.setSuccess(count == 1);
        } catch (Exception e) {
            result.setMessage("删除角色数据失败!");
            result.setSuccess(false);
            e.printStackTrace();
        }

        return result;
    }

    // 新增
    @RequestMapping("/doAdd")
    @ResponseBody
    public Object doAdd(Role role) {

        AjaxResult result = new AjaxResult();

        try {
            roleService.saveRole(role);
            result.setSuccess(true);
        } catch (Exception e) {
            result.setMessage("保存角色数据失败!");
            result.setSuccess(false);
        }

        return result;
    }

    // 分页查询
    @RequestMapping("/doIndex")
    @ResponseBody
    public Object doIndex(
            @RequestParam(value = "pageno", required = false, defaultValue = "1") Integer pageno,
            @RequestParam(value = "pagesize", required = false, defaultValue = "10") Integer pagesize,
            String queryTest) {

        AjaxResult result = new AjaxResult();

        try {
            Map paramMap = new HashMap();
            paramMap.put("pageno", pageno);
            paramMap.put("pagesize", pagesize);

            if (StringUtil.isNotEmpty(queryTest)) {
                if (queryTest.contains("%")) {
                    queryTest.replaceAll("%", "\\%");
                }
                paramMap.put("queryTest", queryTest);
            }

            Page page = roleService.queryPage(paramMap);

            result.setSuccess(true);
            result.setPage(page);

        } catch (Exception e) {
            result.setMessage("查询数据失败!");
            result.setSuccess(false);
            e.printStackTrace();
        }


        return result;
    }


}

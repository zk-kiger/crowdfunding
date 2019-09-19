package com.kiger.atcrowdfunding.controller;

import com.kiger.atcrowdfunding.bean.Role;
import com.kiger.atcrowdfunding.bean.User;
import com.kiger.atcrowdfunding.manager.service.UserService;
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
 * @ClassName UserController
 * @Description TODO
 * @Author zk_kiger
 * @Date 2019/9/15 18:32
 * @Version 1.0
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/index")
    public String index() {
        return "user/index";
    }

    @RequestMapping("/toAdd")
    public String toAdd() {
        return "user/add";
    }

    @RequestMapping("/toUpdate")
    public String toUpdate(Integer id, Map map) {
        User user = userService.getUserById(id);
        map.put("user", user);
        return "user/update";
    }

    @RequestMapping("/assignRole")
    public String assignRole(Integer id, Map map) {

        List<Role> allRoleList = userService.queryAllRole();
        List<Integer> roleIds = userService.queryRoleByUserId(id);

        // 未分配的角色
        List<Role> leftRoleList = new ArrayList<Role>();
        // 已分配的角色
        List<Role> rightRoleList = new ArrayList<Role>();

        for (Role role : allRoleList
             ) {
            if (roleIds.contains(role.getId())) {
                rightRoleList.add(role);
            } else {
                leftRoleList.add(role);
            }
        }

        map.put("leftRoleList", leftRoleList);
        map.put("rightRoleList", rightRoleList);

        return "user/assignrole";
    }

    // 分配角色
    @RequestMapping("/doAssignRole")
    @ResponseBody
    public Object doAssignRole(Integer userid, Data data) {
        AjaxResult result = new AjaxResult();
        try {

            userService.saveUserRoleRelationship(userid, data);
            result.setSuccess(true);

        } catch (Exception e) {
            result.setSuccess(false);
            e.printStackTrace();
            result.setMessage("分配角色失败!");
        }
        // 讲对象序列化为JSON字符串,以流的方式返回
        return result;
    }

    // 删除角色
    @RequestMapping("/doUnAssignRole")
    @ResponseBody
    public Object doUnAssignRole(Integer userid, Data data) {
        AjaxResult result = new AjaxResult();
        try {

            userService.deleteUserRoleRelationship(userid, data);
            result.setSuccess(true);

        } catch (Exception e) {
            result.setSuccess(false);
            e.printStackTrace();
            result.setMessage("取消角色失败!");
        }
        // 讲对象序列化为JSON字符串,以流的方式返回
        return result;
    }

    // 批量删除 - 接收多条数据
    @RequestMapping("/deleteBatch")
    @ResponseBody
    public Object deleteBatch(Data data) {
        AjaxResult result = new AjaxResult();
        try {

            int count = userService.deleteBatchUserByVO(data);
            result.setSuccess(count == data.getDatas().size());

        } catch (Exception e) {
            result.setSuccess(false);
            e.printStackTrace();
            result.setMessage("批量删除数据失败!");
        }
        // 讲对象序列化为JSON字符串,以流的方式返回
        return result;
    }


    // 批量删除 - 接收一个参数名带多个值
    /*@RequestMapping("/deleteBatch")
    @ResponseBody
    public Object deleteBatch(Integer[] id) {
        AjaxResult result = new AjaxResult();
        try {

            int count = userService.deleteBatchUser(id);

            result.setSuccess(count == id.length);
        } catch (Exception e) {
            result.setSuccess(false);
            e.printStackTrace();
            result.setMessage("批量删除数据失败!");
        }
        // 讲对象序列化为JSON字符串,以流的方式返回
        return result;
    }*/

    // 删除
    @RequestMapping("/delete")
    @ResponseBody
    public Object delete(Integer id) {
        AjaxResult result = new AjaxResult();
        try {

            int count = userService.deleteUser(id);

            result.setSuccess(count == 1);
        } catch (Exception e) {
            result.setSuccess(false);
            e.printStackTrace();
            result.setMessage("删除数据失败!");
        }
        // 讲对象序列化为JSON字符串,以流的方式返回
        return result;
    }

    // 更新
    @RequestMapping("/update")
    @ResponseBody
    public Object update(User user) {
        AjaxResult result = new AjaxResult();
        try {

            int count = userService.updateUser(user);

            result.setSuccess(count == 1);
        } catch (Exception e) {
            result.setSuccess(false);
            e.printStackTrace();
            result.setMessage("更新数据失败!");
        }
        // 讲对象序列化为JSON字符串,以流的方式返回
        return result;
    }

    // 添加
    @RequestMapping("/add")
    @ResponseBody
    public Object add(User user) {
        AjaxResult result = new AjaxResult();
        try {

            int count = userService.saveUser(user);

            result.setSuccess(count == 1);
        } catch (Exception e) {
            result.setSuccess(false);
            e.printStackTrace();
            result.setMessage("保存数据失败!");
        }
        // 讲对象序列化为JSON字符串,以流的方式返回
        return result;
    }

    // 条件查询
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

            Page page = userService.queryPage(paramMap);

            result.setSuccess(true);
            result.setPage(page);
        } catch (Exception e) {
            result.setSuccess(false);
            e.printStackTrace();
            result.setMessage("查询数据失败!");
        }
        // 讲对象序列化为JSON字符串,以流的方式返回
        return result;
    }

    // 异步请求
    /*@RequestMapping("/index")
    @ResponseBody
    public Object index(
            @RequestParam(value = "pageno",required = false,defaultValue = "1") Integer pageno,
            @RequestParam(value = "pagesize",required = false,defaultValue = "10") Integer pagesize) {
        AjaxResult result = new AjaxResult();
        try {
            Page page = userService.queryPage(pageno, pagesize);

            result.setSuccess(true);
            result.setPage(page);
        } catch (Exception e) {
            result.setSuccess(false);
            e.printStackTrace();
            result.setMessage("查询数据失败!");
        }
        // 讲对象序列化为JSON字符串,以流的方式返回
        return result;
    }*/

    // 同步请求
    /*@RequestMapping("/index")
    public String index(
        @RequestParam(value = "pageno",required = false,defaultValue = "1") Integer pageno,
        @RequestParam(value = "pagesize",required = false,defaultValue = "10") Integer pagesize,
        Map map) {
        Page page = userService.queryPage(pageno, pagesize);

        map.put("page", page);

        return "user/index";
    }*/


}

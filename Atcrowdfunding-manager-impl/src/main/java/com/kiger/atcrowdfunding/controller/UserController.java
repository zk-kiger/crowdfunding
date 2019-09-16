package com.kiger.atcrowdfunding.controller;

import com.kiger.atcrowdfunding.manager.service.UserService;
import com.kiger.atcrowdfunding.util.AjaxResult;
import com.kiger.atcrowdfunding.util.Page;
import com.kiger.atcrowdfunding.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
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

    @RequestMapping("/toIndex")
    public String toIndex() {


        return "user/index";
    }

    // 条件查询
    @RequestMapping("/index")
    @ResponseBody
    public Object index(
            @RequestParam(value = "pageno",required = false,defaultValue = "1") Integer pageno,
            @RequestParam(value = "pagesize",required = false,defaultValue = "10") Integer pagesize,
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

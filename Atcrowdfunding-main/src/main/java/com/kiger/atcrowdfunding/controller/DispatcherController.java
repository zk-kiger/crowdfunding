package com.kiger.atcrowdfunding.controller;

import com.kiger.atcrowdfunding.bean.User;
import com.kiger.atcrowdfunding.manager.service.UserService;
import com.kiger.atcrowdfunding.util.AjaxResult;
import com.kiger.atcrowdfunding.util.Const;
import com.kiger.atcrowdfunding.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName DispatcherController
 * @Description TODO
 * @Author zk_kiger
 * @Date 2019/9/10 16:52
 * @Version 1.0
 */

@Controller
public class DispatcherController {

    @Autowired
    private UserService userService;

    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/main")
    public String main() {
        return "main";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        // 销毁session对象，清理session域
        session.invalidate();

        // 重定向，会刷新页面访问index.htm，而返回 index 会访问logout.do请求
        return "redirect:/index.htm";
    }


    // 异步请求
    // @ResponseBody 结合Jackson组件，将返回结果转换为字符串，将JSON串以流的形式返回给客户端
    @ResponseBody
    @RequestMapping("/doLogin")
    public Object doLogin(String loginacct, String userpswd, String type, HttpSession session) {

        AjaxResult result = new AjaxResult();

        try {
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("loginacct", loginacct);
            paramMap.put("userpswd", MD5Util.digest(userpswd));
            paramMap.put("type", type);

            User user = userService.queryUserLogin(paramMap);

            session.setAttribute(Const.LOGIN_USER, user);

            result.setSuccess(true);
        } catch (Exception e) {
            result.setMessage("登录失败!");
            result.setSuccess(false);
        }

        // 重定向main页面，不使用转发，防止刷新页面之后重复提交表单(doLogin和转发是同一个页面)
        // 重定向到main页面，再次刷新也不会提交表单
        return result;
    }


    // 同步请求
    /*@RequestMapping("/doLogin")
    public String doLogin(String loginacct, String userpswd, String type, HttpSession session) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("loginacct", loginacct);
        paramMap.put("userpswd", userpswd);
        paramMap.put("type", type);

        User user = userService.queryUserLogin(paramMap);

        session.setAttribute(Const.LOGIN_USER, user);

        // 重定向main页面，不使用转发，防止刷新页面之后重复提交表单(doLogin和转发是同一个页面)
        // 重定向到main页面，再次刷新也不会提交表单
        return "redirect:/main.htm";
    }*/

}

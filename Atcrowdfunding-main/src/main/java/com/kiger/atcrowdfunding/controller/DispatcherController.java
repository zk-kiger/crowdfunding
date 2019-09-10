package com.kiger.atcrowdfunding.controller;

import com.kiger.atcrowdfunding.bean.User;
import com.kiger.atcrowdfunding.manager.service.UserService;
import com.kiger.atcrowdfunding.util.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @RequestMapping("/doLogin")
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
    }

}

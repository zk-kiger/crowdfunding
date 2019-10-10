package com.kiger.atcrowdfunding.interceptor;

import com.kiger.atcrowdfunding.bean.Member;
import com.kiger.atcrowdfunding.bean.User;
import com.kiger.atcrowdfunding.util.Const;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Set;

/**
 * @ClassName LoginInterceptor
 * @Description TODO
 * @Author zk_kiger
 * @Date 2019/10/3 13:48
 * @Version 1.0
 */

public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 1.定义那些路径是不需要拦截的
        Set<String> uri = new HashSet<>();
        uri.add("/user/reg.do");
        uri.add("/user/reg.htm");
        uri.add("/login.htm");
        uri.add("/doLogin.do");
        uri.add("/logout.do");
        uri.add("/index.htm");

        // 获取请求路径
        String servletPath = request.getServletPath();

        if (uri.contains(servletPath)) {
            return true;
        }


        // 2.判断用户是否登录,如果登录放行
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute(Const.LOGIN_USER);
        Member member = (Member)session.getAttribute(Const.LOGIN_MEMBER);
        if (user != null || member != null) {
            return true;
        } else {
            response.sendRedirect(request.getContextPath() + "/login.htm");
            return false;
        }


    }
}

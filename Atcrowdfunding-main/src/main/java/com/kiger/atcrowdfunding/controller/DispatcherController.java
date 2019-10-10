package com.kiger.atcrowdfunding.controller;

import com.kiger.atcrowdfunding.bean.Member;
import com.kiger.atcrowdfunding.bean.Permission;
import com.kiger.atcrowdfunding.bean.User;
import com.kiger.atcrowdfunding.manager.service.UserService;
import com.kiger.atcrowdfunding.potal.service.MemberService;
import com.kiger.atcrowdfunding.util.AjaxResult;
import com.kiger.atcrowdfunding.util.Const;
import com.kiger.atcrowdfunding.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

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

    @Autowired
    private MemberService memberService;

    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    @RequestMapping("/login")
    public String login(HttpServletRequest request , HttpSession session) {
        //判断是否需要自动登录
        //如果之前登录过，cookie中存放了用户信息，需要获取cookie中的信息，并进行数据库的验证

        boolean needLogin = true;
        String logintype = null ;
        Cookie[] cookies = request.getCookies();
        if(cookies != null){ //如果客户端禁用了Cookie，那么无法获取Cookie信息

            for (Cookie cookie : cookies) {
                if("logincode".equals(cookie.getName())){
                    String logincode = cookie.getValue();
                    System.out.println("获取到Cookie中的键值对"+cookie.getName()+"===== " + logincode);
                    //loginacct=admin&userpwd=21232f297a57a5a743894a0e4a801fc3&logintype=1
                    String[] split = logincode.split("&");
                    if(split.length == 3){
                        String loginacct = split[0].split("=")[1];
                        String userpswd = split[1].split("=")[1];
                        logintype = split[2].split("=")[1];

                        if("user".equals(logintype)){
                            Map<String, Object> paramMap = new HashMap<>();
                            paramMap.put("loginacct", loginacct);
                            paramMap.put("userpswd", userpswd);
                            paramMap.put("type", logintype);

                            User dbLogin = userService.queryUserLogin(paramMap);

                            if(dbLogin!=null){
                                session.setAttribute(Const.LOGIN_USER, dbLogin);
                                needLogin = false ;
                            }

                            // 将许可url存入session域中
                            // ---------------------------------------
                            // 当前用户所拥有的许可权限
                            List<Permission> myPermissions = userService.queryPermissionByUserid(dbLogin.getId());

                            Permission permissionRoot = null;

                            Map<Integer, Permission> map = new HashMap<Integer, Permission>();
                            // 用于拦截器拦截许可权限
                            Set<String> myUris = new HashSet<>();

                            for (Permission innerpermission :
                                    myPermissions) {
                                map.put(innerpermission.getId(), innerpermission);
                                myUris.add("/" + innerpermission.getUrl());
                            }

                            session.setAttribute(Const.MY_URIS, myUris);

                            for (Permission permission :
                                    myPermissions) {
                                // 通过子查找父
                                Permission child = permission;
                                if (child.getPid() == null) {
                                    permissionRoot = permission;
                                } else {
                                    // 父结点
                                    Permission parent = map.get(child.getPid());
                                    parent.getChildren().add(child);
                                }
                            }

                            session.setAttribute("permissionRoot", permissionRoot);
                            // ---------------------------------------

                        }else if("member".equals(logintype)){
                            Map<String, Object> paramMap = new HashMap<>();
                            paramMap.put("loginacct", loginacct);
                            paramMap.put("userpswd", userpswd);
                            paramMap.put("type", logintype);

                            Member dbLogin = memberService.queryMemberLogin(paramMap);

                            if(dbLogin!=null){
                                session.setAttribute(Const.LOGIN_MEMBER, dbLogin);
                                needLogin = false ;
                            }
                        }

                    }
                }
            }
        }

        if(needLogin){
            return "login";
        }else{
            if("user".equals(logintype)){
                return "redirect:/main.htm";
            }else if("member".equals(logintype)){
                return "redirect:/member.htm";
            }
        }
        return "login";
    }

    @RequestMapping("/member")
    public String member() {
        return "member/member";
    }

    @RequestMapping("/main")
    public String main() {
        return "main";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        // 销毁session对象，清理session域
        session.invalidate();

        // 重定向，会刷新页面访问index.htm，而返回 index(相当于转发操作) 会访问logout.do请求
        return "redirect:/index.htm";
    }


    // 异步请求
    // @ResponseBody 结合Jackson组件，将返回结果转换为字符串，将JSON串以流的形式返回给客户端
    @ResponseBody
    @RequestMapping("/doLogin")
    public Object doLogin(String loginacct, String userpswd
            , String type, String rememberme
            , HttpServletResponse response, HttpSession session) {

        AjaxResult result = new AjaxResult();

        try {
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("loginacct", loginacct);
            paramMap.put("userpswd", MD5Util.digest(userpswd));
            paramMap.put("type", type);

            if ("member".equals(type)) {

                Member member = memberService.queryMemberLogin(paramMap);

                session.setAttribute(Const.LOGIN_MEMBER, member);

                if ("1".equals(rememberme)) {
                    String logincode = "\"loginacct="+ member.getLoginacct() +"&userpwd="+member.getUserpswd()+"&logintype=member\"";
                    //loginacct=zhangsan&userpwd=21232f297a57a5a743894a0e4a801fc3&logintype=member
                    System.out.println("用户-存放到Cookie中的键值对:logincode = " + logincode);
                    Cookie cookie = new Cookie("logincode", logincode);
                    cookie.setMaxAge(60*60*24*24);  // 2周Cookie
                    cookie.setPath("/");    // 表示任何请求路径都可以访问cookie
                    response.addCookie(cookie);
                }

            } else if ("user".equals(type)) {
                User user = userService.queryUserLogin(paramMap);

                session.setAttribute(Const.LOGIN_USER, user);

                if ("1".equals(rememberme)) {
                    String logincode = "\"loginacct="+ user.getLoginacct() +"&userpwd="+ user.getUserpswd() +"&logintype=user\"";
                    //loginacct=zhangsan&userpwd=21232f297a57a5a743894a0e4a801fc3&logintype=member
                    System.out.println("用户-存放到Cookie中的键值对:logincode = " + logincode);
                    Cookie cookie = new Cookie("logincode", logincode);
                    cookie.setMaxAge(60*60*24*24);  // 2周Cookie
                    cookie.setPath("/");
                    response.addCookie(cookie);
                }

                // 将许可url存入session域中
                // ---------------------------------------
                // 当前用户所拥有的许可权限
                List<Permission> myPermissions = userService.queryPermissionByUserid(user.getId());

                Permission permissionRoot = null;

                Map<Integer, Permission> map = new HashMap<Integer, Permission>();
                // 用于拦截器拦截许可权限
                Set<String> myUris = new HashSet<>();

                for (Permission innerpermission :
                        myPermissions) {
                    map.put(innerpermission.getId(), innerpermission);
                    myUris.add("/" + innerpermission.getUrl());
                }

                session.setAttribute(Const.MY_URIS, myUris);

                for (Permission permission :
                        myPermissions) {
                    // 通过子查找父
                    Permission child = permission;
                    if (child.getPid() == null) {
                        permissionRoot = permission;
                    } else {
                        // 父结点
                        Permission parent = map.get(child.getPid());
                        parent.getChildren().add(child);
                    }
                }

                session.setAttribute("permissionRoot", permissionRoot);
                // ---------------------------------------
            } else {

            }

            result.setData(type);
            result.setSuccess(true);
        } catch (Exception e) {
            result.setMessage("登录失败!");
            result.setSuccess(false);
        }

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

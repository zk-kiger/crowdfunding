package com.kiger.atcrowdfunding.listener; /**
 * @ClassName ${NAME}
 * @Description TODO
 * @Author zk_kiger
 * @Date 2019/9/9 20:52
 * @Version 1.0
 */

import com.kiger.atcrowdfunding.bean.Permission;
import com.kiger.atcrowdfunding.manager.service.PermissionService;
import com.kiger.atcrowdfunding.util.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionBindingEvent;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StartSystemListener implements ServletContextListener {

    // Public constructor is required by servlet spec
    public StartSystemListener() {
    }

    // -------------------------------------------------------
    // ServletContextListener implementation
    // -------------------------------------------------------
    public void contextInitialized(ServletContextEvent sce) {
      /* This method is called when the servlet context is
         initialized(when the Web application is deployed). 
         You can initialize servlet context related data here.
      */
        // 1.将项目的虚拟路径放置到application域中
        ServletContext application = sce.getServletContext();
        String contextPath = application.getContextPath();
        application.setAttribute("APP_PATH", contextPath);
        System.out.printf("APP_PATH...");

        // 2.加载所有的许可路径
        ApplicationContext ioc = WebApplicationContextUtils.getWebApplicationContext(application);
        PermissionService permissionService = ioc.getBean(PermissionService.class);
        List<Permission> queryAllPermission = permissionService.queryAllPermission();

        Set<String> allURIs = new HashSet<>();

        for (Permission permission : queryAllPermission
            ) {
            allURIs.add("/" + permission.getUrl());
        }
        application.setAttribute(Const.ALL_PERMISSION_URI, allURIs);
    }

    public void contextDestroyed(ServletContextEvent sce) {
      /* This method is invoked when the Servlet Context 
         (the Web application) is undeployed or 
         Application Server shuts down.
      */
    }

}

package com.kiger.atcrowdfunding.test;

import com.kiger.atcrowdfunding.bean.User;
import com.kiger.atcrowdfunding.manager.service.UserService;
import com.kiger.atcrowdfunding.util.MD5Util;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @ClassName Test_1
 * @Description TODO
 * @Author zk_kiger
 * @Date 2019/9/15 21:59
 * @Version 1.0
 */

public class Test_1 {

    public static void main(String[] args) {
        ApplicationContext ioc = new ClassPathXmlApplicationContext("spring/spring-*.xml");

        UserService userService = ioc.getBean(UserService.class);

        for (int i = 1; i <= 100; i++) {
            User user = new User();
            user.setLoginacct("Test" + i);
            user.setUserpswd(MD5Util.digest("123"));
            user.setUsername("Test" + i);
            user.setEmail("Test" + i + "@qq.com");
            user.setCreatetime("2019-09-15 22:06:00");
            userService.saveUser(user);
        }
    }

}

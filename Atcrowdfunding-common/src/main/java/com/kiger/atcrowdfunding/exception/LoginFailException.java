package com.kiger.atcrowdfunding.exception;

/**
 * @ClassName LoginFailException
 * @Description TODO
 * @Author zk_kiger
 * @Date 2019/9/10 20:09
 * @Version 1.0
 */

public class LoginFailException extends RuntimeException {

    public LoginFailException(String message) {
        super(message);
    }

}

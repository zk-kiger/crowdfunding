package com.kiger.atcrowdfunding.util;

/**
 * @ClassName AjaxResult
 * @Description TODO
 * @Author zk_kiger
 * @Date 2019/9/10 21:31
 * @Version 1.0
 */

public class AjaxResult {

    private boolean success;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}

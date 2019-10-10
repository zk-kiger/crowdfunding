package com.kiger.atcrowdfunding.potal.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

/**
 * @ClassName PassListener
 * @Description 实名认证审核通过执行的操作
 * @Author zk_kiger
 * @Date 2019/10/9 21:25
 * @Version 1.0
 */

public class PassListener implements ExecutionListener {
    public void notify(DelegateExecution execution) throws Exception {
        System.out.println("PassListener");
    }
}

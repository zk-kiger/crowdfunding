package com.kiger.atcrowdfunding.acticiti.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

/**
 * @ClassName YesListener
 * @Description TODO
 * @Author zk_kiger
 * @Date 2019/10/7 21:16
 * @Version 1.0
 */
// 流程监听器
public class YesListener implements ExecutionListener {
    @Override
    public void notify(DelegateExecution execution) throws Exception {
        System.out.println("审批通过...");
    }
}

package com.kiger.atcrowdfunding.test;

import com.kiger.atcrowdfunding.acticiti.listener.NoListener;
import com.kiger.atcrowdfunding.acticiti.listener.YesListener;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName TestActiviti
 * @Description TODO
 * @Author zk_kiger
 * @Date 2019/10/6 14:45
 * @Version 1.0
 */

public class TestActiviti {

    ApplicationContext ioc = new ClassPathXmlApplicationContext("spring/spring-*.xml");
    ProcessEngine processEngine = ioc.getBean(ProcessEngine.class);

    // 12.测试流程监听器
    @Test
    public void test12() {
        ProcessDefinition processDefinition = processEngine.getRepositoryService().createProcessDefinitionQuery().latestVersion().singleResult();

        RuntimeService runtimeService = processEngine.getRuntimeService();

        Map<String, Object> varibales = new HashMap<>();
        varibales.put("yesListener", new YesListener());
        varibales.put("noListener", new NoListener());

        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinition.getId(), varibales);
        System.out.println("processInstance = " + processInstance);
    }
    @Test
    public void test12_1() {
        TaskService taskService = processEngine.getTaskService();
        TaskQuery taskQuery = taskService.createTaskQuery();
        List<Task> list = taskQuery.taskAssignee("zhangsan").list();

        for (Task task : list
        ) {
            taskService.setVariable(task.getId(), "flag", "true");
            taskService.complete(task.getId());
        }
    }

    // 11.网关 -- 包含网关(排他+并行)
    @Test
    public void test11() {
        ProcessDefinition processDefinition = processEngine.getRepositoryService().createProcessDefinitionQuery().latestVersion().singleResult();

        RuntimeService runtimeService = processEngine.getRuntimeService();

        Map<String, Object> varibales = new HashMap<>();
        varibales.put("days", "5");
        varibales.put("cost", "8000");

        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinition.getId(), varibales);
        System.out.println("processInstance = " + processInstance);

    }
    @Test
    public void test11_1() {
        TaskService taskService = processEngine.getTaskService();
        TaskQuery taskQuery = taskService.createTaskQuery();
        List<Task> list = taskQuery.taskAssignee("lisi").list();

        for (Task task : list
        ) {
            taskService.complete(task.getId());
        }
    }

    // 10.网关 -- 并行网关(会签)
    @Test
    public void test10() {
        ProcessDefinition processDefinition = processEngine.getRepositoryService().createProcessDefinitionQuery().latestVersion().singleResult();

        RuntimeService runtimeService = processEngine.getRuntimeService();

        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinition.getId());
        System.out.println("processInstance = " + processInstance);

    }
    @Test   // 项目经理和财务经理都审批后,流程结束;如果只有一个经理审批,流程需要等待.
    public void test10_1() {
        TaskService taskService = processEngine.getTaskService();
        TaskQuery taskQuery = taskService.createTaskQuery();
        List<Task> list = taskQuery.taskAssignee("zhangsan").list();

        for (Task task : list
        ) {
            taskService.complete(task.getId());
        }
    }

    // 9.网关 -- 排他网关(互斥)
    @Test
    public void test9() {
        ProcessDefinition processDefinition = processEngine.getRepositoryService().createProcessDefinitionQuery().latestVersion().singleResult();

        RuntimeService runtimeService = processEngine.getRuntimeService();

        Map<String, Object> varibales = new HashMap<>();
        varibales.put("days", "5");

        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinition.getId(), varibales);
        System.out.println("processInstance = " + processInstance);

    }
    @Test
    public void test9_1() {
        TaskService taskService = processEngine.getTaskService();
        TaskQuery taskQuery = taskService.createTaskQuery();
        List<Task> list = taskQuery.taskAssignee("lisi").list();

        for (Task task : list
                ) {
            taskService.complete(task.getId());
        }
    }


    // 8.流程变量
    // 如果存在流程变量,那么在流程实例化时，要给流程变量赋值，否则会报错
    @Test
    public void test8() {
        ProcessDefinition processDefinition = processEngine.getRepositoryService().createProcessDefinitionQuery().latestVersion().singleResult();

        RuntimeService runtimeService = processEngine.getRuntimeService();

        Map<String, Object> varibales = new HashMap<>();
        varibales.put("tl", "zhangsan");
        varibales.put("pm", "lisi");

        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinition.getId(), varibales);
        System.out.println("processInstance = " + processInstance);
    }

    // 7.领取任务
    @Test
    public void test7() {

        TaskService taskService = processEngine.getTaskService();
        TaskQuery taskQuery = taskService.createTaskQuery();

        // 查询小组任务
        List<Task> list = taskQuery.taskCandidateGroup("tl").list();

        long count = taskQuery.taskAssignee("zhangsan").count();
        System.out.println("zhangsan领取前的任务数量 = " + count);

        for (Task task :
                list) {
            System.out.println("id = " + task.getId());
            System.out.println("name = " + task.getName());
            // 指派任务给zhangsan
             taskService.claim(task.getId(), "zhangsan");
        }

        taskQuery = taskService.createTaskQuery();
        count = taskQuery.taskAssignee("zhangsan").count();
        System.out.println("zhangsan领取前的任务数量 = " + count);

    }

    // 6.历史数据查询
    @Test
    public void test6() {

        HistoryService historyService = processEngine.getHistoryService();

        HistoricProcessInstanceQuery historicProcessInstanceQuery = historyService.createHistoricProcessInstanceQuery();

        HistoricProcessInstance historicProcessInstance = historicProcessInstanceQuery.processInstanceId("301").finished().singleResult();

        System.out.println("historicProcessInstance = " + historicProcessInstance);
    }

    // 5.查询流程实例的任务数据
    @Test
    public void test5() {

        TaskService taskService = processEngine.getTaskService();
        TaskQuery taskQuery = taskService.createTaskQuery();
        List<Task> list1 = taskQuery.taskAssignee("zhangsan").list();
        List<Task> list2 = taskQuery.taskAssignee("lisi").list();

        // zhangsna的任务:
        System.out.println("zhangsan的任务: ");
        for (Task task :
                list1) {
            System.out.println("id = " + task.getId());
            System.out.println("name = " + task.getName());
            // zhangsan完成任务
            taskService.complete(task.getId());
        }
        System.out.println("--------------------------------------");

        // lisi的任务:
        System.out.println("lisi的任务: ");
        for (Task task :
                list2) {
            System.out.println("id = " + task.getId());
            System.out.println("name = " + task.getName());
            taskService.complete(task.getId());
        }
        System.out.println("===================================================");



        list1 = taskQuery.taskAssignee("zhangsan").list();
        list2 = taskQuery.taskAssignee("lisi").list();

        // zhangsna的任务:
        System.out.println("zhangsan的任务: ");
        for (Task task :
                list1) {
            System.out.println("id = " + task.getId());
            System.out.println("name = " + task.getName());
            // zhangsan完成任务
            taskService.complete(task.getId());
        }
        System.out.println("--------------------------------------");

        // lisi的任务:
        System.out.println("lisi的任务: ");
        for (Task task :
                list2) {
            System.out.println("id = " + task.getId());
            System.out.println("name = " + task.getName());
        }

    }

    // 4.启动流程实例
    /**
     * act_hi_actinst,   历史的活动任务表
     * act_hi_procinst,  历史的流程实例表
     * act_hi_taskinst,  历史的流程任务表
     * act_ru_execution, 正在运行的任务表
     * act_ru_task,      正在运行的任务数据表
     */
    @Test
    public void test4() {
        // 获取最后一次流程定义
        ProcessDefinition processDefinition = processEngine.getRepositoryService().createProcessDefinitionQuery().latestVersion().singleResult();

        // 创建流程实例
        RuntimeService runtimeService = processEngine.getRuntimeService();
        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinition.getId());
        System.out.println("processInstance = " + processInstance);

    }

    // 3.查询部署流程定义
    @Test
    public void test3() {

        RepositoryService repositoryService = processEngine.getRepositoryService();

        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();

        // 查询所有流程定义
        List<ProcessDefinition> list = processDefinitionQuery.list();
        for (ProcessDefinition processDefinition :
                list) {
            System.out.println("Id = " + processDefinition.getId());
            System.out.println("Key = " + processDefinition.getKey());
            System.out.println("Name = " + processDefinition.getName());
            System.out.println("Version = " + processDefinition.getVersion());
            System.out.println("--------------------------");
        }

        // 查询流程定义的个数
        long count = processDefinitionQuery.count();
        System.out.println("count = " + count);
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

        // 最后一次部署的流程定义
        ProcessDefinition processDefinition = processDefinitionQuery.latestVersion().singleResult();
        System.out.println("Id = " + processDefinition.getId());
        System.out.println("Key = " + processDefinition.getKey());
        System.out.println("Name = " + processDefinition.getName());
        System.out.println("Version = " + processDefinition.getVersion());
    }

    // 2.部署流程定义
    @Test
    public void test2() {
        RepositoryService repositoryService = processEngine.getRepositoryService();
        Deployment deploy = repositoryService.createDeployment().addClasspathResource("testActiviti/MyProcess8.bpmn").deploy();
        System.out.println("deploy = " + deploy);
    }

    // 1.创建流程引擎,创建23张表
    @Test
    public void test1() {
        System.out.println("processEngine = " + processEngine);
    }

}

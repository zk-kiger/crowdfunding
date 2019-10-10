package com.kiger.atcrowdfunding.controller;

import com.kiger.atcrowdfunding.util.AjaxResult;
import com.kiger.atcrowdfunding.util.Page;
import com.kiger.atcrowdfunding.util.StringUtil;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName ProcessController
 * @Description TODO
 * @Author zk_kiger
 * @Date 2019/10/7 22:07
 * @Version 1.0
 */

@Controller
@RequestMapping("/process")
public class ProcessController {

    @Autowired
    private RepositoryService repositoryService;

    @RequestMapping("/index")
    public String index() {
        return "process/index";
    }

    @RequestMapping("/showimg")
    public String showimg() {
        return "process/showimg";
    }

    @ResponseBody
    @RequestMapping("/doShowimg")
    public void doShowimg(String id, HttpServletResponse response) {
        try {
            ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(id).singleResult();

            InputStream resourceAsStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), processDefinition.getDiagramResourceName());

            ServletOutputStream outputStream = response.getOutputStream();

            IOUtils.copy(resourceAsStream, outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @ResponseBody
    @RequestMapping("/doDelete")
    public Object doDelete(String id) {    // 流程定义id

        AjaxResult result = new AjaxResult();

        try {
            // 根据流程定义id获得流程定义对象
            ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(id).singleResult();

            // 删除部署表数据级联删除
            repositoryService.deleteDeployment(processDefinition.getDeploymentId(), true);

            result.setSuccess(true);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("删除流程定义失败");
            e.printStackTrace();
        }

        return result;
    }

    @ResponseBody
    @RequestMapping("/deploy")
    public Object deploy(HttpServletRequest request) {
        AjaxResult result = new AjaxResult();
        try {
            // 获取表单中的文件对象
            MultipartHttpServletRequest mreq = (MultipartHttpServletRequest) request;
            MultipartFile processDefFile = mreq.getFile("processDefFile");

            // 部署文件
            repositoryService.createDeployment()
                    .addInputStream(processDefFile.getOriginalFilename(), processDefFile.getInputStream())
                    .deploy();

            result.setSuccess(true);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("部署流程定义失败");
            e.printStackTrace();
        }

        return result;
    }

    @ResponseBody
    @RequestMapping("/doIndex")
    public Object doIndex(
            @RequestParam(value = "pageno", required = false, defaultValue = "1") Integer pageno,
            @RequestParam(value = "pagesize", required = false, defaultValue = "10") Integer pagesize
            ) {

        AjaxResult result = new AjaxResult();
        try {
            // 获取流程定义查询对象
            ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();

            Page page = new Page(pageno, pagesize);

            // 分页查询流程定义
            List<ProcessDefinition> listPage = processDefinitionQuery.listPage(page.getStartIndex(), pagesize);
            // !!!查询流程定义集合数据，可能出现了自关联，导致Jackson组件无法将集合序列化为json串
            List<Map<String, Object>> myListPage = new ArrayList<Map<String, Object>>();
            for (ProcessDefinition processDefinition:
                 listPage) {
                Map<String, Object> pd = new HashMap<String, Object>();
                pd.put("id", processDefinition.getId());
                pd.put("name", processDefinition.getName());
                pd.put("key", processDefinition.getKey());
                pd.put("version", processDefinition.getVersion());
                myListPage.add(pd);
            }
            page.setData(myListPage);

            // 查询总的流程定义数量
            long count = processDefinitionQuery.count();
            page.setTotalSize((int) count);

            result.setSuccess(true);
            result.setPage(page);
        } catch (Exception e) {
            result.setSuccess(false);
            e.printStackTrace();
            result.setMessage("查询数据失败!");
        }
        // 讲对象序列化为JSON字符串,以流的方式返回
        return result;
    }

}

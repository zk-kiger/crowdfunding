package com.kiger.atcrowdfunding.controller;

import com.kiger.atcrowdfunding.bean.Cert;
import com.kiger.atcrowdfunding.manager.service.CertService;
import com.kiger.atcrowdfunding.util.AjaxResult;
import com.kiger.atcrowdfunding.util.Page;
import com.kiger.atcrowdfunding.util.StringUtil;
import com.kiger.atcrowdfunding.vo.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName CertController
 * @Description TODO
 * @Author zk_kiger
 * @Date 2019/10/5 12:04
 * @Version 1.0
 */

@Controller
@RequestMapping("/cert")
public class CertController {

    @Autowired
    private CertService certService;

    @RequestMapping("/index")
    public String index() {
        return "cert/index";
    }

    @RequestMapping("/add")
    public String add() {
        return "cert/add";
    }

    @RequestMapping("/edit")    // 需要回显
    public String edit(Integer id, Model model) {

        // 根据主键查询资质信息
        Cert cert = certService.queryById(id);
        model.addAttribute("cert", cert);

        return "cert/edit";
    }

    // 修改资质信息
    @ResponseBody
    @RequestMapping("/doEdit")
    public Object doEdit(Cert cert) {
        AjaxResult result = new AjaxResult();

        try {
            int count = certService.updateCert(cert);
            result.setSuccess(count == 1);
        } catch (Exception e) {
            result.setMessage("修改资质数据失败!");
            result.setSuccess(false);
            e.printStackTrace();
        }

        return result;
    }

    // 保存资质信息
    @ResponseBody
    @RequestMapping("/doAdd")
    public Object doAdd(Cert cert) {

        AjaxResult result = new AjaxResult();

        try {
            certService.saveCert(cert);
            result.setSuccess(true);
        } catch (Exception e) {
            result.setMessage("保存资质数据失败!");
            e.printStackTrace();
            result.setSuccess(false);
        }

        return result;
    }

    // 批量删除资质信息
    @ResponseBody
    @RequestMapping("/deleteBatch")
    public Object deleteBatch(Data data) {
        AjaxResult result = new AjaxResult();

        try {
            int count = certService.deleteBatchCert(data);
            result.setSuccess(count == data.getCerts().size());
        } catch (Exception e) {
            result.setMessage("批量删除资质数据失败!");
            e.printStackTrace();
            result.setSuccess(false);
        }

        return result;
    }

    // 删除资质信息
    @ResponseBody
    @RequestMapping("/doDelete")
    public Object doDelete(Integer id) {
        AjaxResult result = new AjaxResult();

        try {
            int count = certService.deleteCert(id);
            result.setSuccess(count == 1);
        } catch (Exception e) {
            result.setMessage("删除资质数据失败!");
            e.printStackTrace();
            result.setSuccess(false);
        }

        return result;
    }

    // 分页查询资质数据
    @ResponseBody
    @RequestMapping("/pageQuery")
    public Object pageQuery(
            @RequestParam(value = "pageno", required = false, defaultValue = "1") Integer pageno,
            @RequestParam(value = "pagesize", required = false, defaultValue = "10") Integer pagesize,
            String queryTest) {

        AjaxResult result = new AjaxResult();

        try {

            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("pageno", pageno);
            paramMap.put("pagesize", pagesize);
            if (StringUtil.isNotEmpty(queryTest)) {
                if (queryTest.contains("%")) {
                    queryTest.replaceAll("%", "\\%");
                }
                paramMap.put("queryTest", queryTest);
            }

            Page page = certService.pageQuery(paramMap);

            result.setPage(page);
            result.setSuccess(true);

        } catch (Exception e) {
            result.setMessage("查询数据失败!");
            e.printStackTrace();
            result.setSuccess(false);
        }

        return result;
    }

}

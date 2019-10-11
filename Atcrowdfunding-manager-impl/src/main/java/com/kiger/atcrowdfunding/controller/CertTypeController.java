package com.kiger.atcrowdfunding.controller;

import com.kiger.atcrowdfunding.bean.Cert;
import com.kiger.atcrowdfunding.manager.service.CertService;
import com.kiger.atcrowdfunding.manager.service.CertTypeService;
import com.kiger.atcrowdfunding.util.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName CertTypeController
 * @Description TODO
 * @Author zk_kiger
 * @Date 2019/10/10 21:38
 * @Version 1.0
 */

@Controller
@RequestMapping("/certtype")
public class CertTypeController {

    @Autowired
    private CertTypeService certTypeService;
    @Autowired
    private CertService certService;

    @RequestMapping("/index")
    public String index(Map map) {

        // 查询所有资质
        List<Cert> allCerts = certService.queryAllCert();
        map.put("allCert", allCerts);

        // 查询资质与账户类型之间关系(表示之前给账户类型分配过的资质)
        List<Map<String, Object>> certAccttypeList = certTypeService.queryCertAccttype();
        map.put("certAccttypeList", certAccttypeList);

        return "certtype/index";
    }

    @RequestMapping("/insertAcctTypeCert")
    @ResponseBody
    public Object insertAcctTypeCert(Integer certid, String accttype) {
        AjaxResult result = new AjaxResult();

        try {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("certid", certid);
            paramMap.put("accttype", accttype);
            int count = certTypeService.insertAcctTypeCert(paramMap);
            result.setSuccess(count == 1);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("添加数据失败");
            e.printStackTrace();
        }

        return result;
    }


    @RequestMapping("/deleteAcctTypeCert")
    @ResponseBody
    public Object deleteAcctTypeCert(Integer certid, String accttype) {
        AjaxResult result = new AjaxResult();

        try {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("certid", certid);
            paramMap.put("accttype", accttype);
            int count = certTypeService.deleteAcctTypeCert(paramMap);
            result.setSuccess(count == 1);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("删除数据失败");
            e.printStackTrace();
        }

        return result;
    }

}

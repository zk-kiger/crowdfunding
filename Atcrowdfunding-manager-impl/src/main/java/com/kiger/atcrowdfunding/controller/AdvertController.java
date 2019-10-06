package com.kiger.atcrowdfunding.controller;

import com.kiger.atcrowdfunding.bean.Advertisement;
import com.kiger.atcrowdfunding.bean.User;
import com.kiger.atcrowdfunding.manager.service.AdvertService;
import com.kiger.atcrowdfunding.util.AjaxResult;
import com.kiger.atcrowdfunding.util.Const;
import com.kiger.atcrowdfunding.util.Page;
import com.kiger.atcrowdfunding.util.StringUtil;
import com.kiger.atcrowdfunding.vo.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @ClassName AdvertController
 * @Description TODO
 * @Author zk_kiger
 * @Date 2019/10/5 13:23
 * @Version 1.0
 */

@Controller
@RequestMapping("/advert")
public class AdvertController {

    @Autowired
    private AdvertService advertService;

    @RequestMapping("/index")
    public String index() {
        return "advert/index";
    }

    @RequestMapping("/add")
    public String add() {
        return "advert/add";
    }

    @ResponseBody
    @RequestMapping("/batchDelete")
    public Object batchDelete(Data ds) {
        AjaxResult result = new AjaxResult();

        try {
            int count = advertService.deleteAdverts(ds);
            if (count == ds.getDatas().size()) {
                result.setSuccess(true);
            } else {
                result.setSuccess(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
        }

        return result;
    }

    @ResponseBody
    @RequestMapping("/delete")
    public Object delete(Integer id) {
        AjaxResult result = new AjaxResult();

        try {
            int count = advertService.deleteAdvert(id);
            if (count == 1) {
                result.setSuccess(true);
            } else {
                result.setSuccess(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
        }

        return result;
    }

    @ResponseBody
    @RequestMapping("/doAdd")
    public Object doAdd(HttpServletRequest request, Advertisement advert, HttpSession session) {
        AjaxResult result = new AjaxResult();


        try {
            MultipartHttpServletRequest mreq = (MultipartHttpServletRequest) request;

            MultipartFile mfile = mreq.getFile("advpic");

            String name = mfile.getOriginalFilename();//java.jpg
            String extname = name.substring(name.lastIndexOf(".")); // .jpg

            String iconpath = UUID.randomUUID().toString() + extname; //232243343.jpg

            ServletContext servletContext = session.getServletContext();
            String realpath = servletContext.getRealPath("/pics");

            String path = realpath + "\\adv\\" + iconpath;

            mfile.transferTo(new File(path));

            User user = (User) session.getAttribute(Const.LOGIN_USER);
            advert.setUserid(user.getId());
            advert.setStatus("1");
            advert.setIconpath(iconpath);

            int count = advertService.insertAdvert(advert);
            result.setSuccess(count == 1);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
        }

        return result;
    }

    @ResponseBody
    @RequestMapping("/pageQuery")
    public Object pageQuery(String pagetext, Integer pageno, Integer pagesize) {
        AjaxResult result = new AjaxResult();

        try {
            // 查询资质数据
            Map<String, Object> advertMap = new HashMap<String, Object>();
            advertMap.put("pageno", pageno);
            advertMap.put("pagesize", pagesize);
            if (StringUtil.isNotEmpty(pagetext)) {
                pagetext = pagetext.replaceAll("%", "\\%");
            }
            advertMap.put("pagetext", pagetext);

            // 分页查询
            Page page = advertService.pageQuery(advertMap);
            result.setPage(page);
            result.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
        }

        return result;
    }

}

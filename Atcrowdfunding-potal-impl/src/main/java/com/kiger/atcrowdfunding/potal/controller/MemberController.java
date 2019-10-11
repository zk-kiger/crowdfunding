package com.kiger.atcrowdfunding.potal.controller;

import com.kiger.atcrowdfunding.bean.Cert;
import com.kiger.atcrowdfunding.bean.Member;
import com.kiger.atcrowdfunding.bean.MemberCert;
import com.kiger.atcrowdfunding.bean.Ticket;
import com.kiger.atcrowdfunding.manager.service.CertService;
import com.kiger.atcrowdfunding.potal.listener.PassListener;
import com.kiger.atcrowdfunding.potal.listener.RefuseListener;
import com.kiger.atcrowdfunding.potal.service.MemberService;
import com.kiger.atcrowdfunding.potal.service.TicketService;
import com.kiger.atcrowdfunding.util.AjaxResult;
import com.kiger.atcrowdfunding.util.Const;
import com.kiger.atcrowdfunding.vo.Data;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.*;

/**
 * @ClassName MemberController
 * @Description TODO
 * @Author zk_kiger
 * @Date 2019/10/10 15:30
 * @Version 1.0
 */
@Controller
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private MemberService memberService;
    @Autowired
    private TicketService ticketService;
    @Autowired
    private CertService certService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;

    @RequestMapping("/accttype")
    public String acctype() {
        return "member/accttype";
    }

    @RequestMapping("/basicinfo")
    public String basicinfo() {
        return "member/basicinfo";
    }

    @RequestMapping("/uploadCert")
    public String uploadCert() {
        return "member/uploadCert";
    }

    @RequestMapping("/checkEmail")
    public String checkEmail() {
        return "member/checkEmail";
    }

    @RequestMapping("/checkAuthCode")
    public String checkAuthCode() {
        return "member/checkAuthCode";
    }

    @RequestMapping("/apply")
    public String apply(HttpSession session) {
        // 获取会员信息
        Member loginMember = (Member)session.getAttribute(Const.LOGIN_MEMBER);
        // 获取ticket信息
        Ticket ticket = ticketService.getTicketByMemberId(loginMember.getId());

        if (ticket == null) {
            ticket = new Ticket();
            ticket.setMemberid(loginMember.getId());
            ticket.setPstep("apply");
            ticket.setStatus("0");

            ticketService.saveTicket(ticket);
        } else {
            String pstep = ticket.getPstep();

            if ("accttype".equals(pstep)) {
                return "redirect:/member/basicinfo.htm";
            } else if("basicinfo".equals(pstep)) {

                // 根据当前用户查询账户类型,然后根据账户类型查找需要上传的资质
                List<Cert> queryCertByAccttype = certService.queryCertAccttype(loginMember.getAccttype());

                session.setAttribute("queryCertByAccttype",queryCertByAccttype);

                return "redirect:/member/uploadCert.htm";
            } else if("uploadCert".equals(pstep)) {

                return "redirect:/member/checkEmail.htm";
            } else if ("checkEmail".equals(pstep)) {

                return "redirect:/member/checkAuthCode.htm";
            }
        }

        return "member/accttype";
    }

    @RequestMapping("/finishApply")
    @ResponseBody
    public Object finishApply(HttpSession session, String authcode) {
        AjaxResult result = new AjaxResult();

        try {

            // 获取登录会员信息
            Member loginMember = (Member)session.getAttribute(Const.LOGIN_MEMBER);

            // 让当前系统用户完成验证码审核任务
            Ticket ticket = ticketService.getTicketByMemberId(loginMember.getId());
            if (ticket.getAuthcode().equals(authcode)) {
                // 完成审核验证码任务
                Task task = taskService.createTaskQuery().processInstanceId(ticket.getPiid()).taskAssignee(loginMember.getLoginacct()).singleResult();
                taskService.complete(task.getId());

                // 更新用户申请状态
                loginMember.setAuthstatus("1");
                memberService.updateAuthStatus(loginMember);

                // 记录流程单
                ticket.setPstep("finishApply");
                ticketService.updatePstep(ticket);

                result.setSuccess(true);
            } else {
                result.setSuccess(false);
                result.setMessage("验证码不正确");
            }

        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("验证码不正确");
            e.printStackTrace();
        }

        return result;
    }

    // 开启邮箱流程
    @RequestMapping("/startProcess")
    @ResponseBody
    public Object startProcess(HttpSession session, String email) {
        AjaxResult result = new AjaxResult();

        try {

            // 获取登录会员信息
            Member loginMember = (Member)session.getAttribute(Const.LOGIN_MEMBER);

            // 如果用户输入新的邮箱,将旧的邮箱替换
            if (!loginMember.getEmail().equals(email)) {
                loginMember.setEmail(email);
                memberService.updateEmail(loginMember);
            }

            // 启动实名认证流程 - 系统自动发送邮件,生成验证码
            ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId("auth").singleResult();

            // toEmail authcode loginacct flag(审核实名认证时提供) passListener refuseListener

            StringBuilder authcode = new StringBuilder();
            for (int i = 0; i < 4; i++) {
                authcode.append(new Random().nextInt(10));
            }

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("toEmail", email);
            map.put("authcode", authcode.toString());
            map.put("loginacct", loginMember.getLoginacct());
            map.put("passListener", new PassListener());
            map.put("refuseListener", new RefuseListener());

            ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinition.getId(), map);


            // 记录流程单
            Ticket ticket = ticketService.getTicketByMemberId(loginMember.getId());
            ticket.setPstep("checkEmail");
            ticket.setPiid(processInstance.getId());
            ticket.setAuthcode(authcode.toString());
            ticketService.updatePiidAndPstep(ticket);

            result.setSuccess(true);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("账户类型更新失败!");
            e.printStackTrace();
        }

        return result;
    }

    // 保存会员资质关系
    @RequestMapping("/doUploadCert")
    @ResponseBody
    public Object doUploadCert(HttpSession session, Data ds) {
        AjaxResult result = new AjaxResult();

        try {

            // 获取登录会员信息
            Member loginMember = (Member)session.getAttribute(Const.LOGIN_MEMBER);


            // 保存会员资质关系数据
            String realPath = session.getServletContext().getRealPath("/pics");

            List<MemberCert> certimgs = ds.getCertimgs();
            for(MemberCert memberCert : certimgs) {

                // 资质文件上传,将上传的临时文件存储到服务器指定目录下
                MultipartFile fileImg = memberCert.getFileImg();
                String extendName = fileImg.getOriginalFilename().substring(fileImg.getOriginalFilename().lastIndexOf("."));
                String tempName = UUID.randomUUID().toString() + extendName;
                String fileName = realPath + "\\cert\\" + tempName;
                fileImg.transferTo(new File(fileName));

                // 封装数据,保存数据库
                memberCert.setIconpath(tempName);
                memberCert.setMemberid(loginMember.getId());
            }

            certService.saveMemberCert(certimgs);

            // 记录流程单
            Ticket ticket = ticketService.getTicketByMemberId(loginMember.getId());
            ticket.setPstep("uploadCert");
            ticketService.updatePstep(ticket);

            result.setSuccess(true);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("账户类型更新失败!");
            e.printStackTrace();
        }

        return result;
    }

    // 更新账户信息
    @RequestMapping("/updateBasicinfo")
    @ResponseBody
    public Object updateBasicinfo(HttpSession session, Member member) {
        AjaxResult result = new AjaxResult();

        try {

            // 获取登录会员信息
            Member loginMember = (Member)session.getAttribute(Const.LOGIN_MEMBER);
            loginMember.setRealname(member.getRealname());
            loginMember.setCardnum(member.getCardnum());
            loginMember.setTel(member.getTel());

            int count = memberService.updateBasicinfo(loginMember);

            // 记录流程单
            Ticket ticket = ticketService.getTicketByMemberId(loginMember.getId());
            ticket.setPstep("basicinfo");
            ticketService.updatePstep(ticket);

            if (count == 1) {
                result.setSuccess(true);
            }
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("账户类型更新失败!");
            e.printStackTrace();
        }

        return result;
    }

    // 更新账户类型
    @RequestMapping("/updateAcctType")
    @ResponseBody
    public Object updateAcctType(HttpSession session, String accttype) {
        AjaxResult result = new AjaxResult();

        try {

            // 获取登录会员信息
            Member loginMember = (Member)session.getAttribute(Const.LOGIN_MEMBER);
            loginMember.setAccttype(accttype);
            // 更新账户类型
            int count = memberService.updateAcctType(loginMember);

            // 记录流程单
            Ticket ticket = ticketService.getTicketByMemberId(loginMember.getId());
            ticket.setPstep("accttype");
            ticketService.updatePstep(ticket);

            if (count == 1) {
                result.setSuccess(true);
            }
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("账户类型更新失败!");
            e.printStackTrace();
        }

        return result;
    }

}

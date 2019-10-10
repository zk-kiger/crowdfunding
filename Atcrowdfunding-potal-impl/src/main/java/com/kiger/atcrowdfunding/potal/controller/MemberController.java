package com.kiger.atcrowdfunding.potal.controller;

import com.kiger.atcrowdfunding.bean.Member;
import com.kiger.atcrowdfunding.bean.Ticket;
import com.kiger.atcrowdfunding.potal.service.MemberService;
import com.kiger.atcrowdfunding.potal.service.TicketService;
import com.kiger.atcrowdfunding.util.AjaxResult;
import com.kiger.atcrowdfunding.util.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

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

    @RequestMapping("/accttype")
    public String acctype() {
        return "member/accttype";
    }

    @RequestMapping("/basicinfo")
    public String basicinfo() {
        return "member/basicinfo";
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
                return "redirect:/member/uploadCert.htm";
            }
        }

        return "member/accttype";
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

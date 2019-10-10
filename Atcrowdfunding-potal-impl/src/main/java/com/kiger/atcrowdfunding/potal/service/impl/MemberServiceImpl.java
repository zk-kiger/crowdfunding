package com.kiger.atcrowdfunding.potal.service.impl;

import com.kiger.atcrowdfunding.bean.Member;
import com.kiger.atcrowdfunding.potal.dao.MemberMapper;
import com.kiger.atcrowdfunding.potal.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @ClassName MerberServiceImpl
 * @Description TODO
 * @Author zk_kiger
 * @Date 2019/10/8 20:51
 * @Version 1.0
 */

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberMapper memberMapper;


    public Member queryMemberLogin(Map<String, Object> paramMap) {
        return memberMapper.queryMemberLogin(paramMap);
    }

    public int updateAcctType(Member loginMember) {
        return memberMapper.updateAcctType(loginMember);
    }

    public int updateBasicinfo(Member loginMember) {
        return memberMapper.updateBasicinfo(loginMember);
    }
}

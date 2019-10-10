package com.kiger.atcrowdfunding.potal.service;

import com.kiger.atcrowdfunding.bean.Member;

import java.util.Map;

/**
 * @ClassName MemberService
 * @Description TODO
 * @Author zk_kiger
 * @Date 2019/10/8 20:49
 * @Version 1.0
 */

public interface MemberService {

    Member queryMemberLogin(Map<String, Object> paramMap);

    int updateAcctType(Member loginMember);

    int updateBasicinfo(Member loginMember);
}

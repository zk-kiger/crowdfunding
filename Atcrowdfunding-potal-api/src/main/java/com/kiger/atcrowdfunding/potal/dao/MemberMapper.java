package com.kiger.atcrowdfunding.potal.dao;

import com.kiger.atcrowdfunding.bean.Member;

import java.util.List;
import java.util.Map;

public interface MemberMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Member record);

    Member selectByPrimaryKey(Integer id);

    List<Member> selectAll();

    int updateByPrimaryKey(Member record);

    Member queryMemberLogin(Map<String, Object> paramMap);

    int updateAcctType(Member loginMember);

    int updateBasicinfo(Member loginMember);
}
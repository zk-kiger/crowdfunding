package com.kiger.atcrowdfunding.manager.dao;

import com.kiger.atcrowdfunding.bean.Cert;
import com.kiger.atcrowdfunding.bean.MemberCert;

import java.util.List;
import java.util.Map;

public interface CertMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Cert record);

    Cert selectByPrimaryKey(Integer id);

    List<Cert> selectAll();

    int updateByPrimaryKey(Cert record);

    List queryList(Map<String, Object> paramMap);

    Integer queryCount(Map<String, Object> paramMap);

    int deleteBatchCert(List<Cert> certs);

    List<Cert> queryCertAccttype(String accttype);

    void insertMemberCert(MemberCert memberCert);
}
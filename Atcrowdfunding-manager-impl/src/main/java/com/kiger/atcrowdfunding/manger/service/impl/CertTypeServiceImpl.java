package com.kiger.atcrowdfunding.manger.service.impl;

import com.kiger.atcrowdfunding.manager.dao.AccountTypeCertMapper;
import com.kiger.atcrowdfunding.manager.dao.CertMapper;
import com.kiger.atcrowdfunding.manager.service.CertTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @ClassName CertTypeServiceImpl
 * @Description TODO
 * @Author zk_kiger
 * @Date 2019/10/10 21:40
 * @Version 1.0
 */

@Service
public class CertTypeServiceImpl implements CertTypeService {

    @Autowired
    private AccountTypeCertMapper accountTypeCertMapper;


    public List<Map<String, Object>> queryCertAccttype() {
        return accountTypeCertMapper.queryCertAccttype();
    }

    public int insertAcctTypeCert(Map<String, Object> paramMap) {
        return accountTypeCertMapper.insertAcctTypeCert(paramMap);
    }

    public int deleteAcctTypeCert(Map<String, Object> paramMap) {
        return accountTypeCertMapper.deleteAcctTypeCert(paramMap);
    }
}

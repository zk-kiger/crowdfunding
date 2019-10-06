package com.kiger.atcrowdfunding.manager.service;

import com.kiger.atcrowdfunding.bean.Cert;
import com.kiger.atcrowdfunding.util.Page;
import com.kiger.atcrowdfunding.vo.Data;

import java.util.Map;

/**
 * @ClassName CertService
 * @Description TODO
 * @Author zk_kiger
 * @Date 2019/10/5 12:05
 * @Version 1.0
 */

public interface CertService {
    Cert queryById(Integer id);

    Page pageQuery(Map<String, Object> paramMap);

    int deleteCert(Integer id);

    int deleteBatchCert(Data data);

    void saveCert(Cert cert);

    int updateCert(Cert cert);
}

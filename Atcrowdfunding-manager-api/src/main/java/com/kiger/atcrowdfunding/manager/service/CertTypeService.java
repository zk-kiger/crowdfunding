package com.kiger.atcrowdfunding.manager.service;

import java.util.List;
import java.util.Map;

/**
 * @ClassName CertTypeService
 * @Description TODO
 * @Author zk_kiger
 * @Date 2019/10/10 21:40
 * @Version 1.0
 */

public interface CertTypeService {
    List<Map<String, Object>> queryCertAccttype();

    int insertAcctTypeCert(Map<String, Object> paramMap);

    int deleteAcctTypeCert(Map<String, Object> paramMap);
}

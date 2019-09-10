package com.kiger.atcrowdfunding.manager.dao;

import com.kiger.atcrowdfunding.bean.AccountTypeCert;
import java.util.List;

public interface AccountTypeCertMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AccountTypeCert record);

    AccountTypeCert selectByPrimaryKey(Integer id);

    List<AccountTypeCert> selectAll();

    int updateByPrimaryKey(AccountTypeCert record);
}
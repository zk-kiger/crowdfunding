package com.kiger.atcrowdfunding.manager.dao;

import com.kiger.atcrowdfunding.bean.MemberAddress;
import java.util.List;

public interface MemberAddressMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MemberAddress record);

    MemberAddress selectByPrimaryKey(Integer id);

    List<MemberAddress> selectAll();

    int updateByPrimaryKey(MemberAddress record);
}
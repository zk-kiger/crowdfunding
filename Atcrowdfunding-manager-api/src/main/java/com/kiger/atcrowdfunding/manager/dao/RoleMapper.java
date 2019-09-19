package com.kiger.atcrowdfunding.manager.dao;

import com.kiger.atcrowdfunding.bean.Role;
import com.kiger.atcrowdfunding.vo.Data;

import java.util.List;
import java.util.Map;

public interface RoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    Role selectByPrimaryKey(Integer id);

    List<Role> selectAll();

    int updateByPrimaryKey(Role record);

    List queryList(Map paramMap);

    Integer queryCount(Map paramMap);

    int deleteBatchRole(List<Role> roles);
}
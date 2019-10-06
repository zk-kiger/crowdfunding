package com.kiger.atcrowdfunding.manager.dao;

import com.kiger.atcrowdfunding.bean.Advertisement;
import com.kiger.atcrowdfunding.bean.User;

import java.util.List;
import java.util.Map;

public interface AdvertisementMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Advertisement record);

    Advertisement selectByPrimaryKey(Integer id);

    List<Advertisement> selectAll();

    int updateByPrimaryKey(Advertisement record);

    List queryList(Map<String, Object> advertMap);

    Integer queryCount(Map<String, Object> advertMap);

    int deleteAdverts(List<User> datas);
}
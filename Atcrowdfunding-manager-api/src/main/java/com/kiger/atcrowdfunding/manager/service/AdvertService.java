package com.kiger.atcrowdfunding.manager.service;

import com.kiger.atcrowdfunding.bean.Advertisement;
import com.kiger.atcrowdfunding.util.Page;
import com.kiger.atcrowdfunding.vo.Data;

import java.util.Map;

/**
 * @ClassName AdvertService
 * @Description TODO
 * @Author zk_kiger
 * @Date 2019/10/5 13:25
 * @Version 1.0
 */

public interface AdvertService {
    Page pageQuery(Map<String, Object> advertMap);

    int insertAdvert(Advertisement advert);

    int deleteAdverts(Data ds);

    int deleteAdvert(Integer id);
}

package com.kiger.atcrowdfunding.manger.service.impl;

import com.kiger.atcrowdfunding.bean.Advertisement;
import com.kiger.atcrowdfunding.manager.dao.AdvertisementMapper;
import com.kiger.atcrowdfunding.manager.service.AdvertService;
import com.kiger.atcrowdfunding.util.Page;
import com.kiger.atcrowdfunding.vo.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @ClassName AdvertServiceImpl
 * @Description TODO
 * @Author zk_kiger
 * @Date 2019/10/5 13:25
 * @Version 1.0
 */

@Service
public class AdvertServiceImpl implements AdvertService {

    @Autowired
    private AdvertisementMapper advertisementMapper;

    public Page pageQuery(Map<String, Object> advertMap) {

        Page page = new Page((Integer)advertMap.get("pageno"), (Integer)advertMap.get("pagesize"));
        Integer startIndex = page.getStartIndex();
        advertMap.put("startIndex", startIndex);

        List datas = advertisementMapper.queryList(advertMap);
        page.setData(datas);

        Integer totalSize = advertisementMapper.queryCount(advertMap);
        page.setTotalSize(totalSize);

        return page;


    }

    public int insertAdvert(Advertisement advert) {
        return advertisementMapper.insert(advert);
    }

    public int deleteAdverts(Data ds) {
        return advertisementMapper.deleteAdverts(ds.getDatas());
    }

    public int deleteAdvert(Integer id) {
        return advertisementMapper.deleteByPrimaryKey(id);
    }
}

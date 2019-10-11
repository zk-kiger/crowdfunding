package com.kiger.atcrowdfunding.manger.service.impl;

import com.kiger.atcrowdfunding.bean.Cert;
import com.kiger.atcrowdfunding.bean.MemberCert;
import com.kiger.atcrowdfunding.manager.dao.CertMapper;
import com.kiger.atcrowdfunding.manager.service.CertService;
import com.kiger.atcrowdfunding.util.Page;
import com.kiger.atcrowdfunding.vo.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @ClassName CertServiceImpl
 * @Description TODO
 * @Author zk_kiger
 * @Date 2019/10/5 12:06
 * @Version 1.0
 */
@Service
public class CertServiceImpl implements CertService {

    @Autowired
    private CertMapper certMapper;

    public Cert queryById(Integer id) {
        return certMapper.selectByPrimaryKey(id);
    }

    public Page pageQuery(Map<String, Object> paramMap) {
        Page page = new Page((Integer)paramMap.get("pageno"), (Integer)paramMap.get("pagesize"));
        Integer startIndex = page.getStartIndex();
        paramMap.put("startIndex", startIndex);

        List datas = certMapper.queryList(paramMap);
        page.setData(datas);

        Integer totalSize = certMapper.queryCount(paramMap);
        page.setTotalSize(totalSize);

        return page;
    }

    public int deleteCert(Integer id) {
        return certMapper.deleteByPrimaryKey(id);
    }

    public int deleteBatchCert(Data data) {
        return certMapper.deleteBatchCert(data.getCerts());
    }

    public void saveCert(Cert cert) {
        certMapper.insert(cert);
    }

    public int updateCert(Cert cert) {
        return certMapper.updateByPrimaryKey(cert);
    }

    public List<Cert> queryAllCert() {
        return certMapper.selectAll();
    }

    public List<Cert> queryCertAccttype(String accttype) {
        return certMapper.queryCertAccttype(accttype);
    }

    public void saveMemberCert(List<MemberCert> certimgs) {
        for (MemberCert memberCert :
            certimgs) {
            certMapper.insertMemberCert(memberCert);
        }
    }
}

package com.kiger.atcrowdfunding.manger.service.impl;

import com.kiger.atcrowdfunding.bean.Role;
import com.kiger.atcrowdfunding.manager.dao.RoleMapper;
import com.kiger.atcrowdfunding.manager.service.RoleService;
import com.kiger.atcrowdfunding.util.Page;
import com.kiger.atcrowdfunding.vo.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @ClassName RoleServiceImpl
 * @Description TODO
 * @Author zk_kiger
 * @Date 2019/9/19 19:15
 * @Version 1.0
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    public void saveRole(Role role) {
        roleMapper.insert(role);
    }

    public Page queryPage(Map paramMap) {

        Page page = new Page((Integer)paramMap.get("pageno"), (Integer)paramMap.get("pagesize"));
        Integer startIndex = page.getStartIndex();
        paramMap.put("startIndex", startIndex);

        List datas = roleMapper.queryList(paramMap);
        page.setData(datas);

        Integer totalSize = roleMapper.queryCount(paramMap);
        page.setTotalSize(totalSize);

        return page;
    }

    public int deleteRole(Integer id) {
        return roleMapper.deleteByPrimaryKey(id);
    }

    public Role getRoleById(Integer id) {
        return roleMapper.selectByPrimaryKey(id);
    }

    public int updateRole(Role role) {
        return roleMapper.updateByPrimaryKey(role);
    }

    public int deleteBatchRole(Data data) {
        return roleMapper.deleteBatchRole(data.getRoles());
    }
}

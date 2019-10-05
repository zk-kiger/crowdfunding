package com.kiger.atcrowdfunding.manger.service.impl;

import com.kiger.atcrowdfunding.bean.Permission;
import com.kiger.atcrowdfunding.bean.Role;
import com.kiger.atcrowdfunding.bean.User;
import com.kiger.atcrowdfunding.exception.LoginFailException;
import com.kiger.atcrowdfunding.manager.dao.UserMapper;
import com.kiger.atcrowdfunding.manager.service.UserService;
import com.kiger.atcrowdfunding.util.Const;
import com.kiger.atcrowdfunding.util.MD5Util;
import com.kiger.atcrowdfunding.util.Page;
import com.kiger.atcrowdfunding.vo.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @ClassName UserServiceImpl
 * @Description TODO
 * @Author zk_kiger
 * @Date 2019/9/10 18:21
 * @Version 1.0
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;


    public User queryUserLogin(Map<String, Object> paramMap) {

        User user = userMapper.queryUserLogin(paramMap);

        if (user == null) {
            throw new LoginFailException("用户账号或密码不正确!");
        }

        return user;
    }

    /*public Page queryPage(Integer pageno, Integer pagesize) {

        Page page = new Page(pageno, pagesize);
        Integer startIndex = page.getStartIndex();

        List datas = userMapper.queryList(startIndex, pagesize);
        page.setData(datas);

        Integer totalSize = userMapper.queryCount();
        page.setTotalSize(totalSize);

        return page;
    }*/

    public int saveUser(User user) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        user.setCreatetime(sdf.format(new Date()));

        user.setUserpswd(MD5Util.digest(Const.PASSWORD));

        return userMapper.insert(user);
    }

    public Page queryPage(Map<String, Object> paramMap) {
        Page page = new Page((Integer)paramMap.get("pageno"), (Integer)paramMap.get("pagesize"));
        Integer startIndex = page.getStartIndex();
        paramMap.put("startIndex", startIndex);

        List datas = userMapper.queryList(paramMap);
        page.setData(datas);

        Integer totalSize = userMapper.queryCount(paramMap);
        page.setTotalSize(totalSize);

        return page;
    }

    public User getUserById(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    public int updateUser(User user) {
        return userMapper.updateByPrimaryKey(user);
    }

    public int deleteUser(Integer id) {
        return userMapper.deleteByPrimaryKey(id);
    }

    public int deleteBatchUser(Integer[] ids) {
        int count = 0;

        for (Integer id :
                ids) {
            count += userMapper.deleteByPrimaryKey(id);
        }

        return count;
    }
    /*public int deleteBatchUserByVO(Data data) {

        return userMapper.deleteBatchUserByVO(data);
    }*/

    public int deleteBatchUserByVO(Data data) {

        return userMapper.deleteBatchUserByVO(data.getDatas());
    }

    public List<Role> queryAllRole() {
        return userMapper.queryAllRole();
    }

    public List<Integer> queryRoleByUserId(Integer id) {
        return userMapper.queryRoleByUserId(id);
    }

    public int saveUserRoleRelationship(Integer userid, Data data) {
        return userMapper.saveUserRoleRelationship(userid, data);
    }

    public int deleteUserRoleRelationship(Integer userid, Data data) {
        return userMapper.deleteUserRoleRelationship(userid, data);
    }

    public List<Permission> queryPermissionByUserid(Integer id) {
        return userMapper.queryPermissionByUserid(id);
    }


}

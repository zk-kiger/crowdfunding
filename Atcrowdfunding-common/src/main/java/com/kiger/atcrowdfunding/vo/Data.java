package com.kiger.atcrowdfunding.vo;

import java.util.ArrayList;
import java.util.List;

import com.kiger.atcrowdfunding.bean.MemberCert;
import com.kiger.atcrowdfunding.bean.Role;
import com.kiger.atcrowdfunding.bean.User;

public class Data {

	private List<User> userList = new ArrayList<User>();
	private List<User> datas = new ArrayList<User>();
	private List<Role> roles = new ArrayList<Role>();

	private List<Integer> ids ;
	
	private List<MemberCert> certimgs = new ArrayList<MemberCert>();

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	public List<User> getDatas() {
		return datas;
	}

	public void setDatas(List<User> datas) {
		this.datas = datas;
	}

	public List<Integer> getIds() {
		return ids;
	}

	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}

	public List<MemberCert> getCertimgs() {
		return certimgs;
	}

	public void setCertimgs(List<MemberCert> certimgs) {
		this.certimgs = certimgs;
	}

	

}

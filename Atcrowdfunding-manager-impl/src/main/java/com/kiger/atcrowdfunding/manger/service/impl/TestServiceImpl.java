package com.kiger.atcrowdfunding.manger.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kiger.atcrowdfunding.manager.dao.TestDao;
import com.kiger.atcrowdfunding.manager.service.TestService;

@Service
public class TestServiceImpl implements TestService {

	@Autowired
	private TestDao testDao;

	public void insert() {
		System.out.printf("TestServiceImpl - insert");
		Map map = new HashMap();
		map.put("name", "zhang3");
		testDao.insert(map);
	}
}

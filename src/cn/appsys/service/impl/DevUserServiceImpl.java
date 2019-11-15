package cn.appsys.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.appsys.dao.devuser.DevUserMapper;
import cn.appsys.pojo.DevUser;
import cn.appsys.service.DevUserService;
@Service("devUserService")
public class DevUserServiceImpl implements DevUserService {
	
	@Resource(name="devUserMapper")
	private DevUserMapper devUserMapper;
	@Override
	public DevUser login(String devCode) {
		DevUser devUser=devUserMapper.login(devCode);
		return devUser;
	}

}

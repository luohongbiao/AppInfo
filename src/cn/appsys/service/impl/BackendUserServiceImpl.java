package cn.appsys.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.appsys.dao.backenduser.BackendUserMapper;
import cn.appsys.pojo.BackendUser;
import cn.appsys.pojo.DevUser;
import cn.appsys.service.BackendUserService;
@Service("backendUserService")
public class BackendUserServiceImpl implements BackendUserService {
	
	@Resource(name="backendUserMapper")
	private BackendUserMapper backendUserMapper;
	
	@Override
	public BackendUser login(String userCode) {
		return backendUserMapper.login(userCode);
	}

	
}

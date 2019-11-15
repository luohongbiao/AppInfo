package cn.appsys.service;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.BackendUser;
import cn.appsys.pojo.DevUser;

public interface BackendUserService {
	/**
	 * 使用用户名登录的方法
	 * @param userName
	 * @return
	 */
	public BackendUser login(@Param("userCode")String userCode);
}

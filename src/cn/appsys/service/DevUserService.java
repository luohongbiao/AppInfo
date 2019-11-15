package cn.appsys.service;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.DevUser;

public interface DevUserService {
	public DevUser login(@Param("devCode")String devCode);
}

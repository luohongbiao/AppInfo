package cn.appsys.dao.devuser;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import cn.appsys.pojo.DevUser;

@Repository("devUserMapper")
public interface DevUserMapper {
	/**
	 * 使用用户名登录的方法
	 * @param userName
	 * @return
	 */
	public DevUser login(@Param("devCode")String devCode);

}

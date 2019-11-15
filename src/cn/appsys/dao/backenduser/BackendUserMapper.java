package cn.appsys.dao.backenduser;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import cn.appsys.pojo.BackendUser;
import cn.appsys.pojo.DevUser;

@Repository("backendUserMapper")
public interface BackendUserMapper {
	/**
	 * 使用用户名登录的方法
	 * @param userName
	 * @return
	 */
	public BackendUser login(@Param("userCode")String userCode);
	/**
	 * 查询所有app信息
	 * 
	 */
	
}

package cn.appsys.dao.appversion;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import cn.appsys.pojo.AppVersion;

@Repository("appVersionMapper")
public interface AppVersionMapper {
	/**
	 * 按照appid查找这个app的历史版本
	 * @param id
	 * @return
	 */
	public List<AppVersion> getAppVersionById(@Param(value="id")Integer id);
	/**
	 * 新增版本信息
	 * @param appVersion
	 * @return
	 */
	public int addAppVersion(AppVersion appVersion);
	/**
	 * 按照appid查找最新版本id
	 * @param id
	 * @return
	 */
	public int newId(Integer id);
	/**
	 * 按照appid和版本信息id查询版本信息
	 * @param vid
	 * @param aid
	 * @return
	 */
	public AppVersion getAppVersion(@Param("vid")Integer vid,@Param("aid")Integer aid);
	/**
	 * 按照id删除版本的apk文件
	 * @param id
	 * @return
	 */
	public int delAppVersionById(@Param("id")Integer id);
	/**
	 * 按照版本id修改版本信息
	 * @param id
	 * @return
	 */
	public int modifyAppVersion(AppVersion appVersion);
	/**
	 * 修改版本状态
	 * @param appVersion
	 */
	public void  modify(AppVersion appVersion);
	/**
	 * 按照appid删除所有版本信息
	 * @param appId
	 * @return
	 */
	public int delVersion(Integer appId);
}

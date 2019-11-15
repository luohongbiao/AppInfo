package cn.appsys.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.AppVersion;

public interface AppVersionService {
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
	public boolean addAppVersion(AppVersion appVersion);
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
	public boolean delAppVersionById(@Param("id")Integer id);
	/**
	 * 按照版本id修改版本信息
	 * @param id
	 * @return
	 */
	public boolean modifyAppVersion(AppVersion appVersion);
}

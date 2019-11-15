package cn.appsys.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.AppInfo;

public interface AppInfoService {

	/**
	 * 获取所有app信息和按照条件查询app信息
	 * 
	 * @param softwareName
	 * @param status
	 * @param categoryLevel1
	 * @param categoryLevel2
	 * @param categoryLevel3
	 * @param flatformId
	 * @param devId
	 * @param currentPageNo
	 * @param pageSize
	 * @return
	 */
	public List<AppInfo> getAppInfoList(@Param("softwareName") String softwareName, @Param("status") Integer status,
			@Param("categoryLevel1") Integer categoryLevel1, @Param("categoryLevel2") Integer categoryLevel2,
			@Param("categoryLevel3") Integer categoryLevel3, @Param("flatformId") Integer flatformId,
			@Param("devId") Integer devId, @Param("currentPageNo") Integer currentPageNo,
			@Param("pageSize") Integer pageSize);

	/**
	 * 获取所有app信息数量
	 * 
	 * @return
	 */
	public int getAllAppInfo(@Param("softwareName") String softwareName, @Param("status") Integer status,
			@Param("categoryLevel1") Integer categoryLevel1, @Param("categoryLevel2") Integer categoryLevel2,
			@Param("categoryLevel3") Integer categoryLevel3, @Param("flatformId") Integer flatformId,
			@Param("devId") Integer devId, @Param("currentPageNo") Integer currentPageNo,
			@Param("pageSize") Integer pageSize);
	/**
	 * 按照软件id和最新版本id查询软件
	 * @return
	 */
	public AppInfo getAppinfoById(@Param("aid")Integer id,@Param("vid")Integer vid);
	/**
	 * 审核
	 * @param id
	 * @return
	 */
	public boolean checkApp(@Param("id")Integer id);
	/**
	 * 按照apkname查找该名称是否存在
	 * @param APKName
	 * @return
	 */
	public boolean queryApkName(@Param("apkName")String APKName);
	/**
	 * 新增app信息
	 * @param appInfo
	 * @return
	 */
	public boolean addAppinfo(@Param("appInfo")AppInfo appInfo);
	/**
	 * 更新appid的最新版本id
	 * @param id
	 * @return
	 */
	public boolean modifyVersId(Integer vId,Integer appId);
	/**
	 * 修改app信息
	 * @param appInfo
	 * @return
	 */
	public boolean modifyAppInfo(AppInfo appInfo);
	/**
	 * 按照id删除app
	 * @param id
	 * @return
	 */
	public boolean delApp(@Param("id")Integer id);
	/**
	 * 修改
	 * @param appInfo
	 * @return
	 */
	public boolean appsysUpdateSaleStatusById(AppInfo appInfo);
}

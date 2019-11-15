package cn.appsys.dao.appinfo;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import cn.appsys.pojo.AppInfo;

@Repository("appInfoMapper")
public interface AppInfoMapper {
	
	/**
	 * 查询所有数据和按条件查询
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
	public List<AppInfo> getAppInfoList(@Param("softwareName")String softwareName
			,@Param("status")Integer status
			,@Param("categoryLevel1")Integer categoryLevel1
			,@Param("categoryLevel2")Integer categoryLevel2
			,@Param("categoryLevel3")Integer categoryLevel3
			,@Param("flatformId")Integer flatformId
			,@Param("devId")Integer devId
			,@Param("currentPageNo")Integer currentPageNo
			,@Param("pageSize")Integer pageSize);
	/**
	 * 获取app数量
	 */
	public int getAllAppInfo(@Param("softwareName")String softwareName
			,@Param("status")Integer status
			,@Param("categoryLevel1")Integer categoryLevel1
			,@Param("categoryLevel2")Integer categoryLevel2
			,@Param("categoryLevel3")Integer categoryLevel3
			,@Param("flatformId")Integer flatformId
			,@Param("devId")Integer devId
			,@Param("currentPageNo")Integer currentPageNo
			,@Param("pageSize")Integer pageSize);
	/**
	 * 按照软件id和最新版本id查询软件
	 * @return
	 */
	public AppInfo getAppinfoById(@Param("aid")Integer aid,@Param("vid")Integer vid);
	/**
	 * 审核
	 * @param id
	 * @return
	 */
	public int checkApp(@Param("id")Integer id);
	/**
	 * 按照apkname查找该名称是否存在
	 * @param APKName
	 * @return
	 */
	public int queryApkName(@Param("apkName")String APKName);
	/**
	 * 新增app信息
	 * @param appInfo
	 * @return
	 */
	public int addAppinfo(AppInfo appInfo);
	/**
	 * 更新appid的最新版本id
	 * @param id
	 * @return
	 */
	public int modifyVersId(@Param("vId")Integer vId,@Param("appId")Integer appId);
	/**
	 * 更新app信息
	 * @return
	 */
	public int modifyAppInfo(AppInfo appInfo);
	/**
	 * 按照id删除app
	 * @param id
	 * @return
	 */
	public int delApp(@Param("id")Integer id);
	/**
	 *	     按照这个对象修改状态为上架
	 * @param appInfo
	 * @return
	 */
	public Object appsysUpdateSaleStatusById(AppInfo appInfo);
	
	public void modify(AppInfo appInfo);
}

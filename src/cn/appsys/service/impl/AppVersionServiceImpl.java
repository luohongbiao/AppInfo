package cn.appsys.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.appsys.dao.appversion.AppVersionMapper;
import cn.appsys.pojo.AppVersion;
import cn.appsys.service.AppVersionService;
@Service("appVersionService")
public class AppVersionServiceImpl implements AppVersionService {

	@Resource(name="appVersionMapper")
	private AppVersionMapper appVersionMapper;
	@Override
	public List<AppVersion> getAppVersionById(Integer id) {
		return appVersionMapper.getAppVersionById(id);
	}
	@Override
	public boolean addAppVersion(AppVersion appVersion) {
		if(appVersionMapper.addAppVersion(appVersion)==1) {
			return true;
		}
		return false;
	}
	@Override
	public int newId(Integer id) {
		return appVersionMapper.newId(id);
	}
	@Override
	public AppVersion getAppVersion(Integer vid, Integer aid) {
		return appVersionMapper.getAppVersion(vid, aid);
	}
	@Override
	public boolean delAppVersionById(Integer id) {
		if(appVersionMapper.delAppVersionById(id)==1) {
			return true;
		}
		return false;
	}
	@Override
	public boolean modifyAppVersion(AppVersion appVersion){
		if(appVersionMapper.modifyAppVersion(appVersion)==1){
			return true;
		}
		return false;
	}
}

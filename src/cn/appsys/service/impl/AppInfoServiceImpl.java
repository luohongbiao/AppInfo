package cn.appsys.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.sound.midi.MidiDevice.Info;

import org.springframework.stereotype.Service;

import com.sun.org.apache.bcel.internal.generic.RETURN;
import com.sun.xml.internal.bind.v2.schemagen.xmlschema.Appinfo;

import cn.appsys.dao.appinfo.AppInfoMapper;
import cn.appsys.dao.appversion.AppVersionMapper;
import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.AppVersion;
import cn.appsys.service.AppInfoService;
@Service("appInfoService")
public class AppInfoServiceImpl implements AppInfoService {
	
	@Resource(name="appInfoMapper")
	private AppInfoMapper appInfoMapper;
	
	@Resource(name="appVersionMapper")
	private AppVersionMapper appVersionMapper;

	@Override
	public List<AppInfo> getAppInfoList(String softwareName, Integer status, Integer categoryLevel1,
			Integer categoryLevel2, Integer categoryLevel3, Integer flatformId, Integer devId, Integer currentPageNo,
			Integer pageSize) {
		return appInfoMapper.getAppInfoList(softwareName, status, categoryLevel1, categoryLevel2, categoryLevel3, flatformId, devId, (currentPageNo-1)*pageSize, pageSize);
	}

	@Override
	public int getAllAppInfo(String softwareName, Integer status, Integer categoryLevel1, Integer categoryLevel2,
			Integer categoryLevel3, Integer flatformId, Integer devId, Integer currentPageNo, Integer pageSize) {
		return appInfoMapper.getAllAppInfo(softwareName, status, categoryLevel1, categoryLevel2, categoryLevel3, flatformId, devId, (currentPageNo-1)*pageSize, pageSize);
	}

	@Override
	public AppInfo getAppinfoById(Integer aid, Integer vid) {
		return appInfoMapper.getAppinfoById(aid, vid);
	}

	@Override
	public boolean checkApp(Integer id) {
		if(appInfoMapper.checkApp(id)>0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean queryApkName(String APKName) {
		if(appInfoMapper.queryApkName(APKName)==1) {
			return false;
		}
		return true;
	}

	@Override
	public boolean addAppinfo(AppInfo appInfo) {
		if(appInfoMapper.addAppinfo(appInfo)==1) {
			return true;
		}
		return false;
	}

	@Override
	public boolean modifyVersId(Integer vId,Integer appId) {
		if(appInfoMapper.modifyVersId(vId,appId)==1) {
			return true;
		}
		return false;
	}

	@Override
	public boolean modifyAppInfo(AppInfo appInfo) {
		if(appInfoMapper.modifyAppInfo(appInfo)==1) {
			return true;
		}
		return false;
	}

	@Override
	public boolean delApp(Integer id) {
		appVersionMapper.delVersion(id);//将版本删除
		if (appInfoMapper.delApp(id) == 1) {
			return true;
		}
		return false;
	}

	@Override
	public boolean appsysUpdateSaleStatusById(AppInfo appInfo) {
		AppInfo appinfo2=appInfoMapper.getAppinfoById(appInfo.getId(), null);
		Integer operator=appInfo.getModifyBy();//修改者
		if(operator<0||appInfo.getId()<0) {
			try {
				throw new Exception("修改者和appid不存在!");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(null==appinfo2) {
			return false;
		}else {
			switch (appinfo2.getStatus()) {
			case 2://状态为审核通过进行上架操作,4表示为已上架2表示为将版本状态改为已发布状态
				onSale(appinfo2,operator,4,2);
				break;
			case 5://当状态为已下架,可以进行上架操作	
				onSale(appinfo2,operator,4,2);
				break;
			case 4://当状态为已上架,可以进行下架操作	
				onSale(appinfo2,operator,5,null);
				break;
				default:
					return false;
			}
		}
		return true;
	}	
	
	private void onSale(AppInfo appInfo,Integer operator,Integer appStatus,Integer veisionStatus){
		offSale(appInfo,operator,appStatus);
		setSaleSwitchToAppVersion(appInfo,operator,veisionStatus);
	}
	
	
	private boolean offSale(AppInfo appInfo,Integer operator,Integer appStatus) {
		AppInfo appInfo2=new AppInfo();
		appInfo2.setId(appInfo.getId());
		appInfo2.setStatus(appStatus);
		appInfo2.setModifyBy(operator);
		appInfo2.setModifyDate(new Date());
		appInfoMapper.modify(appInfo2);
		return true;
	}
	
	private boolean setSaleSwitchToAppVersion(AppInfo appInfo,Integer operator,Integer saleStatus) {
		AppVersion appVersion=new AppVersion();
		appVersion.setId(appInfo.getVersionId());
		appVersion.setPublishStatus(saleStatus);
		appVersion.setModifyBy(operator);
		appVersion.setModifyDate(new Date(System.currentTimeMillis()));
		appVersionMapper.modify(appVersion);
		return true;
	}
	
}

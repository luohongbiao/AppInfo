package cn.appsys.controller;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.Null;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.tomcat.util.descriptor.web.LoginConfig;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.sun.java.swing.plaf.motif.resources.motif;
import com.sun.org.apache.bcel.internal.generic.NEW;

import cn.appsys.pojo.AppCategory;
import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.AppVersion;
import cn.appsys.pojo.DataDictionary;
import cn.appsys.pojo.DevUser;
import cn.appsys.service.AppCategoryService;
import cn.appsys.service.AppInfoService;
import cn.appsys.service.AppVersionService;
import cn.appsys.service.DataDictionaryService;
import cn.appsys.service.DevUserService;
import cn.appsys.tools.Constants;
import cn.appsys.tools.PageSupport;

@Controller
@RequestMapping("/dev")
public class DevUserControll {

	//注入app版本信息service
	@Resource(name="appVersionService")
	private AppVersionService appVersionService;
	
	// 注入前台service对象
	@Resource(name = "devUserService")
	private DevUserService devUserService;

	// 注入app信息service对象
	@Resource(name = "appInfoService")
	private AppInfoService appInfoService;

	// 注入分类service对象
	@Resource(name = "appCategoryService")
	private AppCategoryService appCategoryService;

	// 获取平台service对象
	@Resource(name = "dataDictionaryService")
	private DataDictionaryService dataDictionaryService;

	@RequestMapping(value = "/login")
	public String login() {
		return "devlogin";
	}

	// 开发者登录
	@RequestMapping(value = "/doLogin", method = RequestMethod.POST)
	public String doLogin(@RequestParam String devCode, @RequestParam String devPassword, 	HttpSession 	session,
			HttpServletRequest request) {
		DevUser devUser = devUserService.login(devCode);
		if (devUser != null) {
			if (devUser.getDevPassword().equals(devPassword)) {
				session.setAttribute(Constants.DEV_USER_SESSION, devUser);
				return "developer/main";
			} else {
				request.setAttribute("error", "账号或密码错误!");
				return "devlogin";
			}
		} else {
			request.setAttribute("error", "账号不存在!");
			return "devlogin";
		}
	}

	@RequestMapping("logout")
	public Object logout() {
		return "../../index";
	}
	
	// 去main主页面
	@RequestMapping(value = "/flatform/main")
	public String main() {
		return "developer/main";
	}

	// 所有的app
	@RequestMapping(value = "/flatform/getAppList.do")
	public Object appList(Model model,
			@RequestParam(value = "querySoftwareName", required = false) String querySoftwareName,
			@RequestParam(value = "queryStatus", required = false) String queryStatus,
			@RequestParam(value = "queryFlatformId", required = false) String queryFlatformId,
			@RequestParam(value = "queryCategoryLevel1", required = false) String 			queryCategoryLevel1,
			@RequestParam(value = "queryCategoryLevel2", required = false) String 			queryCategoryLevel2,
			@RequestParam(value = "queryCategoryLevel3", required = false) String 			queryCategoryLevel3,
			@RequestParam(value = "devId", required = false) Integer devId,
			@RequestParam(value = "pageIndex", required = false) Integer pageIndex) {
			List<AppInfo> appInfoList = null;// app信息集合
			List<AppCategory> categoryLevel1List = null;// 一级分类集合
			List<DataDictionary> flatFormList = null;// 平台集合
			List<DataDictionary> statusList = null;
			String _querySoftwareName = null;// 软件名称
			Integer _queryStatus = 0;// 状态
			Integer _queryFlatformId = 0;// 所属平台
			Integer _queryCategoryLevel1 = 0;// 一级分类
			Integer _queryCategoryLevel2 = 0;// 二级分类
		Integer _queryCategoryLevel3 = 0;// 三级分类
		Integer _devId = 0;// 作者id
		Integer _pageIndex = 1;// 当前页码
		int _pageSize = 5;// 设置页面大小
		if (querySoftwareName != null) {
			_querySoftwareName = querySoftwareName;
			model.addAttribute("querySoftwareName", querySoftwareName);
		}
		if (queryStatus != null&&queryStatus!="") {
			_queryStatus = Integer.valueOf(queryStatus);
			model.addAttribute("queryStatus", queryStatus);
		}
		if (queryFlatformId != null&&queryFlatformId!="") {
			_queryFlatformId = Integer.valueOf(queryFlatformId);
			model.addAttribute("queryFlatformId", queryFlatformId);
		}
		if (queryCategoryLevel1 != null&&queryCategoryLevel1!="") {
			_queryCategoryLevel1 = Integer.valueOf(queryCategoryLevel1);
			model.addAttribute("queryCategoryLevel1", queryCategoryLevel1);
		}
		if (queryCategoryLevel2 != null&&queryCategoryLevel2!="") {
			_queryCategoryLevel2 = Integer.valueOf(queryCategoryLevel2);
			model.addAttribute("queryCategoryLevel2", queryCategoryLevel2);
		}
		if (queryCategoryLevel3 != null&&queryCategoryLevel3!="") {
			_queryCategoryLevel3 = Integer.valueOf(queryCategoryLevel3);
			model.addAttribute("queryCategoryLevel3", queryCategoryLevel3);
		}
		if (devId != null) {
			_devId = Integer.valueOf(devId);
			model.addAttribute("devId", devId);
		}
		if (pageIndex != null) {
			_pageIndex = Integer.valueOf(pageIndex);
		}
		int total = appInfoService.getAllAppInfo(_querySoftwareName, _queryStatus, _queryCategoryLevel1,
				_queryCategoryLevel2, _queryCategoryLevel3, _queryFlatformId, _devId, _pageIndex, _pageSize);
		System.err.println("软件名称" + _querySoftwareName + "状态" + _queryStatus + "一级分类" + _queryCategoryLevel1 + "二级分类"
				+ _queryCategoryLevel2 + "三级分类" + _queryCategoryLevel3 + _queryFlatformId + "所属平台id" + _devId + "开发者id"
				+ _pageIndex + "当前页码" + _pageIndex + "显示几条" + _pageSize + "总数量" + total);
		PageSupport pages = new PageSupport();// 实例化page对象
		pages.setPageSize(_pageSize);// 设置页面大小
		pages.setTotalCount(total);// 设置总数量
		pages.setCurrentPageNo(_pageIndex);// 设置当前页码
		int pageCount = pages.getTotalPageCount();// 总页数
		if (_pageIndex < 1) {
			_pageIndex = 1;
		} else if (_pageIndex > pageCount) {
			_pageIndex = pageCount;
		}
		appInfoList = appInfoService.getAppInfoList(_querySoftwareName, _queryStatus, _queryCategoryLevel1,
				_queryCategoryLevel2, _queryCategoryLevel3, _queryFlatformId, _devId, _pageIndex, _pageSize);
		for(AppInfo info:appInfoList) {
			System.err.println("getVersionNo"+info.getVersionNo());
		}
		categoryLevel1List = appCategoryService.getAppCategoryByParentId(null);
		flatFormList = dataDictionaryService.getAllDataDictionary();
		statusList = dataDictionaryService.getAllStatus();
		model.addAttribute("statusList", statusList);
		model.addAttribute("flatFormList", flatFormList);
		model.addAttribute("categoryLevel1List", categoryLevel1List);
		model.addAttribute("appInfoList", appInfoList);
		model.addAttribute("pages", pages);
		return "developer/appinfolist";
	}

	// ajax加载平台
	@RequestMapping("/flatform/datadictionarylist.json")
	@ResponseBody
	public Object loadFlatform() {
		List<DataDictionary> flatFormList = null;// 平台集合
		flatFormList = dataDictionaryService.getAllDataDictionary();
		return flatFormList;
	}

	// ajax加载二三级分类
	@RequestMapping("/flatform/categorylevellist.json")
	@ResponseBody
	public Object loadLevel(@RequestParam(value = "pid") String pid) {
		List<AppCategory> appCategories = appCategoryService.getAppCategoryByParentId(pid);
		return appCategories;
	}

	//带着Appinfo对象去新增APP页面
	@RequestMapping(value = "/flatform/add",method=RequestMethod.GET)
	public Object addAccount(@ModelAttribute("appInfo") AppInfo appInfo) {
		return "developer/appinfoadd";
	}

	// ajax验证apkname是否重复
	@RequestMapping("/flatform/apkexist.json")
	@ResponseBody
	public Object checkApk(@RequestParam(value = "APKName") String APKName) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		if (APKName == null || APKName == "") {
			map.put("APKName", "empty");
			return map;
		} else if (appInfoService.queryApkName(APKName)) {
			map.put("APKName", "noexist");
			return map;
		} else {
			map.put("APKName", "exist");
			return map;
		}
	}

	// 单文件上传绑定数据执行新增操作
	@RequestMapping(value = "/flatform/appinfoaddsave", method = RequestMethod.POST)
	public Object appInfoAddSave(AppInfo appinfo,
			HttpSession session,
			HttpServletRequest request,
			@RequestParam(value = "a_logoPicPath", required = false) MultipartFile attach) {
		String logoPicPath = null;
		String path = request.getSession().getServletContext().getRealPath("statics" + 		File.separator + "uploadfiles");
		if (!attach.isEmpty()) {// 自带的验证是否为空的方法
			String oldFileName = attach.getOriginalFilename();// 原文件名
			String prefix = FilenameUtils.getExtension(oldFileName);// 源文件后缀
			int filesize = 500000;
			if (attach.getSize() > filesize) {
				// 上传大小不能超出500kb
				request.setAttribute("fileUploadError", "* 上传大小不得超过500KB");
				return "developer/appinfoadd";
			} else if (prefix.equalsIgnoreCase("png") || prefix.equalsIgnoreCase("jpg")
					|| prefix.equalsIgnoreCase("jpeg") || prefix.equalsIgnoreCase("pneg")) {
				String fileName = System.currentTimeMillis() + RandomUtils.nextInt(1000000) + "_Personal.jpg";
				File targeFile = new File(path, fileName);
				if (!targeFile.exists()) {
					targeFile.mkdirs();// 不存在就创建
				}
				// 保存
				try {
					attach.transferTo(targeFile);
					// 将这张图片写入磁盘
				} catch (Exception e) {
					e.printStackTrace();
					request.setAttribute("fileUploadError", "* 上传失败!");
					return "useradd";
				}
				logoPicPath = path + File.separator + fileName;
				logoPicPath=logoPicPath.substring(logoPicPath.length()-61);
			} else {
				request.setAttribute("fileUploadError", "* 上传图片格式不正确");
				return "useradd";
			}
		}
		appinfo.setCreatedBy(((DevUser)session.getAttribute(Constants.DEV_USER_SESSION)).getId());//用户id
		appinfo.setDevId(((DevUser)session.getAttribute(Constants.DEV_USER_SESSION)).getId());//开发者id
		appinfo.setDevId(((DevUser)session.getAttribute(Constants.DEV_USER_SESSION)).getId());
		appinfo.setCreationDate(new Date());//创建时间
		appinfo.setLogoLocPath(path);
		appinfo.setLogoPicPath(logoPicPath);
		if(appInfoService.addAppinfo(appinfo)) {
			return "redirect:/dev/flatform/getAppList.do";
		}
		return "developer/appinfoadd";
	}
	
	//去新增版本页面
	@RequestMapping("/flatform/appversionadd")
	public Object appVersionAdd(Model model
			,@RequestParam(value="id")String id){
		List<AppVersion> appVersionList=appVersionService.getAppVersionById(Integer.valueOf(id));
		model.addAttribute("appVersionList", appVersionList);
		model.addAttribute("appId", id);
		return "developer/appversionadd";
	}

	//新增版本操作
	@RequestMapping(value="/flatform/addversionsave")
	public Object addVersionSave(HttpServletRequest request,HttpSession session,
			@RequestParam(value="appId")String appId,
			@RequestParam(value="versionNo")String versionNo,
			@RequestParam(value="versionSize")String versionSize,
			@RequestParam(value="publishStatus")String publishStatus,
			@RequestParam(value="versionInfo")String versionInfo,
			@RequestParam(value="a_downloadLink")MultipartFile attach) {
		String logoPicPath = null;
		String fileName=null;
		String path = request.getSession().getServletContext().getRealPath("statics" + File.separator + "uploadfiles");
		if (!attach.isEmpty()) {// 自带的验证是否为空的方法
			String oldFileName = attach.getOriginalFilename();// 原文件名
			String prefix = FilenameUtils.getExtension(oldFileName);// 源文件后缀
			int filesize = 10000000;
			if (attach.getSize() > filesize) {
				// 上传大小不能超出500kb
				request.setAttribute("fileUploadError", "* 上传大小不得超出10000000KB");
				return "developer/appversionadd";
			} else if (prefix.equalsIgnoreCase("apk")) {
				fileName = System.currentTimeMillis() + RandomUtils.nextInt(1000000)+"_Personal.apk";
				File targeFile = new File(path, fileName);
				if (!targeFile.exists()) {
					targeFile.mkdirs();// 不存在就创建
				}
				// 保存
				try {
					attach.transferTo(targeFile);
					// 将这张图片写入磁盘
				} catch (Exception e) {
					e.printStackTrace();
					request.setAttribute("fileUploadError", "* 上传失败!");
					return "developer/appinfoadd";
				}
				logoPicPath = path + File.separator + fileName;
			} else {
				request.setAttribute("fileUploadError", "* 上传文件格式不正确");
				return "developer/appversionadd";
			}
		}
		AppVersion appVersion=new AppVersion();
		appVersion.setAppId(Integer.valueOf(appId));//appid
		appVersion.setVersionNo(versionNo);//版本号
		appVersion.setVersionInfo(versionInfo);//版本信息
		appVersion.setPublishStatus(Integer.valueOf(publishStatus));//版本状态
		appVersion.setDownloadLink(path);//apk下载地址
		BigDecimal size=new BigDecimal(versionSize);
		appVersion.setVersionSize(size);//版本大小
		appVersion.setCreatedBy(((DevUser)session.getAttribute(Constants.DEV_USER_SESSION)).getId());
		//创建者
		appVersion.setModifyBy(((DevUser)session.getAttribute(Constants.DEV_USER_SESSION)).getId());
		//更新者
		appVersion.setModifyDate(new Date());//更新时间
		appVersion.setCreationDate(new Date());//创建时间
		appVersion.setApkLocPath(path);//服务器存储路径
		appVersion.setApkFileName(fileName);//上传的apk文件名称
		if (appVersionService.addAppVersion(appVersion)) {
			Integer id=appVersionService.newId(Integer.valueOf(appId));//查询这个app的最新版本id
			if(id!=null) {
				if(appInfoService.modifyVersId(id,Integer.valueOf(appId))) {
					return "redirect:/dev/flatform/getAppList.do";
				}
			}
		}
		return "developer/appversionadd";
	}
	
	//接受用户的请求将此app的版本信息查询出来让用用户修改
	@RequestMapping("/flatform/appversionmodify")
	public Object appVersionmoDify(Model model,
			@RequestParam("vid")String vid,
			@RequestParam("aid")String aid) {
		AppVersion appVersion=appVersionService.getAppVersion(Integer.valueOf(vid), Integer.valueOf(aid));
		List<AppVersion>appVersionList=appVersionService.getAppVersionById(Integer.valueOf(aid));
		model.addAttribute("appVersionList", appVersionList);
		model.addAttribute("appVersion", appVersion);
		return "developer/appversionmodify";
	}
	
	//使用ajax删除该app的apk文件
	@RequestMapping("/flatform/delfile.json")
	@ResponseBody
	public Object delFile(@RequestParam("id")String id){
		HashMap<Object, String>map=new HashMap<Object, String>();
		if(appVersionService.delAppVersionById(Integer.valueOf(id))) {
			map.put("result", "success");
		}else {
			map.put("result", "failed");
		}
		return map;
	}
	
	//修改版本信息
	@RequestMapping(value="/flatform/appversionmodifysave",method=RequestMethod.POST)
	public Object appversionmodifysave(HttpServletRequest request,
			HttpSession session,
			@RequestParam("attach")MultipartFile attach,
			@RequestParam("id")String id,
			@RequestParam("appId")String appId,
			@RequestParam("versionNo")String versionNo,
			@RequestParam("versionSize")String versionSize,
			@RequestParam("publishStatus")String publishStatus,
			@RequestParam("versionInfo")String versionInfo) {
		String logoPicPath = null;
		String fileName=null;
		String path = request.getSession().getServletContext().getRealPath("statics" + File.separator 		+ "uploadfiles");
		if (!attach.isEmpty()) {// 自带的验证是否为空的方法
			String oldFileName = attach.getOriginalFilename();// 原文件名
			String prefix = FilenameUtils.getExtension(oldFileName);// 源文件后缀
			int filesize = 10000000;
			if (attach.getSize() > filesize) {
				// 上传大小不能超出500kb
				request.setAttribute("fileUploadError", "* 上传大小不得超出10000000KB");
				return "developer/appversionadd";
			} else if (prefix.equalsIgnoreCase("apk")) {
				fileName = System.currentTimeMillis() + RandomUtils.nextInt(1000000) + 				"_Personal.apk";
				File targeFile = new File(path, fileName);
				if (!targeFile.exists()) {
					targeFile.mkdirs();// 不存在就创建
				}
				// 保存
				try {
					attach.transferTo(targeFile);
					// 将这张图片写入磁盘
				} catch (Exception e) {
					e.printStackTrace();
					request.setAttribute("fileUploadError", "* 上传失败!");
					return "developer/appinfoadd";
				}
				logoPicPath = path + File.separator + fileName;
			}else{
				request.setAttribute("fileUploadError","* 上传文件格式不正确");
				return "developer/appversionadd";
			}
		}
		AppVersion appVersion=new AppVersion();
		appVersion.setId(Integer.valueOf(id));//版本id
		appVersion.setAppId(Integer.valueOf(appId));//appId
		appVersion.setVersionNo(versionNo);//版本号
		appVersion.setVersionInfo(versionInfo);//版本信息
		BigDecimal bigDecimal=new BigDecimal(versionSize);
		appVersion.setVersionSize(bigDecimal);//版本大小
		appVersion.setPublishStatus(Integer.valueOf(publishStatus));//版本状态
		appVersion.setDownloadLink(path);//下载地址
		appVersion.setModifyBy(((DevUser)session.getAttribute(Constants.DEV_USER_SESSION)).getId());
		appVersion.setModifyDate(new Date());//更新时间
		appVersion.setApkLocPath(path);////服务器存储地址
		appVersion.setApkFileName(fileName);//文件名字
		if(appVersionService.modifyAppVersion(appVersion)) {
			return "redirect:/dev/flatform/getAppList.do";
		}
		return "developer/appversionmodify";
	}
	
	//按照id吧信息都查出来去到修改页面
	@RequestMapping("/flatform/appinfomodify")
	public Object appinfoModify(Model model,@RequestParam("id")String id) {
		AppInfo appInfo=appInfoService.getAppinfoById(Integer.valueOf(id), null);
		System.err.println(appInfo.getAppInfo());
		model.addAttribute("appInfo", appInfo);
		return "developer/appinfomodify";
	}
	
	//修改app信息
	@RequestMapping(value="/flatform/appinfomodifysave",method=RequestMethod.POST)
	public Object modifyAppInfo(
			HttpServletRequest request,
			HttpSession session,
			@Param("id")String id,
			@Param("softwareName")String softwareName,
			@Param("APKName")String APKName,
			@Param("supportROM")String supportROM,
			@Param("interfaceLanguage")String interfaceLanguage,
			@Param("softwareSize")String softwareSize,
			@Param("downloads")String downloads,
			@Param("flatformId")String flatformId,
			@Param("categoryLevel1")String categoryLevel1,
			@Param("categoryLevel2")String categoryLevel2,
			@Param("categoryLevel3")String categoryLevel3,
			@Param("statusName")String statusName,
			@Param("appInfo")String appInfo,
			@Param("attach")MultipartFile attach) {
		String logoPicPath = null;
		String fileName=null;
		String path = request.getSession().getServletContext().getRealPath("statics" + File.separator 		+ "uploadfiles");
		if (!attach.isEmpty()) {// 自带的验证是否为空的方法
			String oldFileName = attach.getOriginalFilename();// 原文件名
			String prefix = FilenameUtils.getExtension(oldFileName);// 源文件后缀
			int filesize = 10000000;
			if (attach.getSize() > filesize) {
				// 上传大小不能超出500kb
				request.setAttribute("fileUploadError", "* 上传大小不得超出10000000KB");
				return "developer/appinfomodify";
			} else if (prefix.equalsIgnoreCase("png") || prefix.equalsIgnoreCase("jpg")
					|| prefix.equalsIgnoreCase("jpeg") || prefix.equalsIgnoreCase("pneg")){
				fileName = System.currentTimeMillis() + RandomUtils.nextInt(1000000) + 				"_Personal.jpg";
				File targeFile = new File(path, fileName);
				if (!targeFile.exists()) {
					targeFile.mkdirs();// 不存在就创建
				}
				// 保存
				try {
					attach.transferTo(targeFile);
					// 将这张图片写入磁盘
				} catch (Exception e) {
					e.printStackTrace();
					request.setAttribute("fileUploadError", "* 上传失败!");
					return "developer/appinfomodify";
				}
				logoPicPath=path + File.separator + fileName;
			} else {
				request.setAttribute("fileUploadError", "* 上传文件格式不正确");
				return "developer/appinfomodify";
			}
		}
		AppInfo apInfo=new AppInfo();
		apInfo.setId(Integer.valueOf(id));
		apInfo.setSoftwareName(softwareName);
		apInfo.setAPKName(APKName);
		apInfo.setSupportROM(supportROM);
		apInfo.setInterfaceLanguage(interfaceLanguage);
		BigDecimal bigDecimal=new BigDecimal(softwareSize);
		apInfo.setSoftwareSize(bigDecimal);
		apInfo.setDownloads(Integer.valueOf(downloads));
		apInfo.setFlatformId(Integer.valueOf(flatformId));
		apInfo.setCategoryLevel1(Integer.valueOf(categoryLevel1));
		apInfo.setCategoryLevel2(Integer.valueOf(categoryLevel2));
		apInfo.setCategoryLevel3(Integer.valueOf(categoryLevel3));
		apInfo.setStatusName(statusName);
		apInfo.setAppInfo(appInfo);
		apInfo.setModifyBy(((DevUser)session.getAttribute(Constants.DEV_USER_SESSION)).getId());
		apInfo.setModifyDate(new Date());
		apInfo.setLogoPicPath(path);
		apInfo.setLogoLocPath(logoPicPath);
		if(apInfo.getStatusName().equals("待审核")) {
			apInfo.setStatus(1);
		}else if(apInfo.getStatusName().equals("审核通过")) {
			apInfo.setStatus(2);
		}else if(apInfo.getStatusName().equals("审核未通过")) {
			apInfo.setStatus(3);
		}else if(apInfo.getStatusName().equals("已上架")) {
			apInfo.setStatus(4);
		}else if(apInfo.getStatusName().equals("已下架")) {
			apInfo.setStatus(5);
		}
		if(appInfoService.modifyAppInfo(apInfo)) {
			return "redirect:/dev/flatform/getAppList.do";
		}
		return "developer/appinfomodify";
	}
	
	//使用rest风格查看app信息
	@RequestMapping(value="/flatform/appview/{id}")
	public Object appView(Model model,@PathVariable("id")String id) {
	List<AppVersion>appVersionList=appVersionService.getAppVersionById(Integer.valueOf(id));
		AppInfo appInfo=appInfoService.getAppinfoById(Integer.valueOf(id), null);
		model.addAttribute("appInfo", appInfo);
		model.addAttribute("appVersionList", appVersionList);
		System.err.println(appInfo.getLogoPicPath());
		return "developer/appinfoview";
	}
	
	//删除app信息
	@RequestMapping("/flatform/delapp.json")
	@ResponseBody
	public Object delApp(@RequestParam("id")String id) {
		HashMap<String, String>map=new HashMap<String, String>();
		
		if(appInfoService.delApp(Integer.valueOf(id))) {
			map.put("delResult", "true");
		}else {
			map.put("delResult", "false");
		}
		return map;
	}
	
	//使用rest风格的ajax方式来操作
	@RequestMapping(value="/flatform/{appId}/sale.json",method=RequestMethod.PUT)
	@ResponseBody
	public Object sale(@PathVariable("appId")String id,HttpSession session) {
		HashMap<String, String>map=new HashMap<String, String>();
		Integer idInteger=0;
		try {
			idInteger=Integer.parseInt(id);
		} catch (Exception e) {
			idInteger=0;
		}
		map.put("errorCode", "0");
		if (idInteger>0) {
			try {
				DevUser devUser=(DevUser)session.getAttribute(Constants.DEV_USER_SESSION);
				AppInfo appInfo=new AppInfo();
				appInfo.setId(idInteger);
				appInfo.setModifyBy(idInteger);
				if(appInfoService.appsysUpdateSaleStatusById(appInfo)) {
					map.put("resultMsg", "success");
				}else {
					map.put("resultMsg", "failed");
				}
			} catch (Exception e) {
				map.put("errorCode", "exeception000001");
			}
		}else {
			map.put("errorCode", "exception000001");
		}
		System.err.println(map.toString());
		return map;
	}
}

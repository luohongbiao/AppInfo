package cn.appsys.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.chainsaw.Main;
import org.apache.tomcat.util.descriptor.web.LoginConfig;
import org.ietf.jgss.Oid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.appsys.pojo.AppCategory;
import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.BackendUser;
import cn.appsys.pojo.DataDictionary;
import cn.appsys.service.AppCategoryService;
import cn.appsys.service.AppInfoService;
import cn.appsys.service.BackendUserService;
import cn.appsys.service.DataDictionaryService;
import cn.appsys.tools.Constants;
import cn.appsys.tools.PageSupport;
@RequestMapping("/manager")
@Controller
public class BackendControll {

	@Resource(name = "backendUserService")
	private BackendUserService backendUserService;

	@Resource(name = "appInfoService")
	private AppInfoService appInfoService;
	
	@Resource(name="appCategoryService")
	private AppCategoryService appCategoryService;

	@Resource(name="dataDictionaryService")
	private DataDictionaryService dataDictionaryService;
	
	//去登录页面
	@RequestMapping(value = "/login")
	public String login() {
		return "backendlogin";
	}

	//去main主页面
	@RequestMapping(value="/backend/main")
	public String main() {
		System.err.println("main");
		return "backend/main";
	}
	
	// 处理登录请求
	@RequestMapping(value = "/doLogin", method = RequestMethod.POST)
	public String doLogin(@RequestParam String userCode, @RequestParam String userPassword, HttpSession session,
			HttpServletRequest request) {
		BackendUser backendUser = backendUserService.login(userCode);
		if (backendUser != null) {
			if (backendUser.getUserPassword().equals(userPassword)) {
				session.setAttribute(Constants.USER_SESSION, backendUser);
				return "/backend/main";
			} else {
				request.setAttribute("error", "账号或密码错误!");
				return "backendlogin";
			}
		} else {
			request.setAttribute("error", "账号不存在!");
			return "backendlogin";
		}
	}

	//将下拉框中的值从数据库中查找出来
	@RequestMapping(value = "/backend/app/list")
	public String getAppList(Model model,
			@RequestParam(value = "querySoftwareName", required = false) String querySoftwareName,
			@RequestParam(value = "queryFlatformId", required = false) Integer queryFlatformId,
			@RequestParam(value = "status", required = false) Integer status,
			@RequestParam(value = "queryCategoryLevel1", required = false) Integer queryCategoryLevel1,
			@RequestParam(value = "queryCategoryLevel2", required = false) Integer queryCategoryLevel2,
			@RequestParam(value = "queryCategoryLevel3", required = false) Integer queryCategoryLevel3,
			@RequestParam(value = "devId", required = false) Integer devId,
			@RequestParam(value = "pageIndex", required = false) Integer pageIndex) {
		List<AppInfo> appInfoList = null;//app信息集合
		List<DataDictionary>flatFormList=null;//加载所属平台
		List<AppCategory>categoryLevel1List=null;//取得一级分类
		int pageSize = Constants.pageSize;// 设置页面大小
		int currentPageNo = 1;// 设置当前页码
		String sofName = null;
		int statu = 0;
		int level1 = 0;
		int level2 = 0;
		int level3 = 0;
		int flatId = 0;
		int deId = 0;
		if (querySoftwareName != null) {
			sofName = querySoftwareName;
			model.addAttribute("querySoftwareName", querySoftwareName);
		}
		if (pageIndex != null) {
			try {
				currentPageNo = Integer.valueOf(pageIndex);
			} catch (Exception e) {
				return "";
			}
		}
		if (status != null) {
			statu = status;
			model.addAttribute("status",statu);
		}
		if (queryCategoryLevel1 != null) {
			level1 = queryCategoryLevel1;
			model.addAttribute("queryCategoryLevel1",queryCategoryLevel1);
		}
		if (queryCategoryLevel2 != null) {
			level2 = queryCategoryLevel2;
			model.addAttribute("queryCategoryLevel2",queryCategoryLevel2);
		}
		if (queryCategoryLevel3 != null) {
			level3 = queryCategoryLevel3;
			model.addAttribute("queryCategoryLevel3",queryCategoryLevel3);
		}
		if (queryFlatformId != null) {
			flatId = queryFlatformId;
			model.addAttribute("queryFlatformId",queryFlatformId);
		}
		if (devId != null) {
			deId = devId;
			model.addAttribute("devId",devId);
		}
		int total=appInfoService.getAllAppInfo(sofName, statu, level1, level2, level3, flatId, deId, currentPageNo, pageSize);// 获取app总数量
		PageSupport pages = new PageSupport();// 实例化页面对象
		pages.setCurrentPageNo(currentPageNo);// 设置当前页码
		pages.setPageSize(pageSize);// 设置页面大小
		pages.setTotalCount(total);// 设置总数量
		int pageCount = pages.getTotalPageCount();// 总页数
		if (currentPageNo < 1) {// 控制首尾页
			currentPageNo = 1;
		} else if (currentPageNo > pageCount) {
			currentPageNo = pageCount;
		}
		System.err.println("软件名称"+sofName+"状态"+statu+"一级分类"+ level1+"二级分类"+ level2+"三级分类"+level3+flatId+"所属平台id"+ deId+"开发者id"+ currentPageNo+"当前页码"+pageSize+"显示几条");
		appInfoList=appInfoService.getAppInfoList(sofName, statu, level1, level2, level3, flatId, deId, currentPageNo, pageSize);
		categoryLevel1List=appCategoryService.getAppCategoryByParentId(null);//取得一级分类
		flatFormList=dataDictionaryService.getAllDataDictionary();//加载所有平台
		model.addAttribute("flatFormList", flatFormList);
		model.addAttribute("categoryLevel1List", categoryLevel1List);
		model.addAttribute("appInfoList", appInfoList);
		model.addAttribute("pages", pages);
		return "backend/applist";
	}
	
	//加载二三级分类
	@RequestMapping(value="/backend/app/categorylevellist.json",method=RequestMethod.GET)
	@ResponseBody
	public Object level(@RequestParam String pid) {
		List<AppCategory>appCategories=appCategoryService.getAppCategoryByParentId(pid);
		return appCategories;
	}
	
	//注销
	@RequestMapping(value="/logout")
	public Object logout(HttpSession session) {
		session.removeAttribute(Constants.USER_SESSION);
		return "../../index";
	}

	//查询软件状态
	@RequestMapping(value="/backend/app/check",method=RequestMethod.GET)
	public Object check(Model model,@RequestParam String aid,@RequestParam String vid) {
		Integer _aid=null;
		Integer _vid=null;
		if(aid!=null) {
			_aid=Integer.valueOf(aid);
		}
		if(vid!=null) {
			_vid=Integer.valueOf(vid);
		}
		AppInfo appInfo=appInfoService.getAppinfoById(_aid,_vid);
		model.addAttribute("appInfo", appInfo);
		System.err.println(appInfo.getInterfaceLanguage());
		return "/backend/appcheck";
	}
	
	//审核
	@RequestMapping(value="/backend/app/checksave")
	public Object checkApp(@RequestParam("id")String id) {
		Integer _id=null;
		if(id!=null) {
			_id=Integer.valueOf(id);
		}
		boolean ion=appInfoService.checkApp(_id);
		return "redirect:/manager/backend/app/list";
	}
}

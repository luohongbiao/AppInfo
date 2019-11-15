package cn.appsys.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.appsys.dao.appcategory.AppCategoryMapper;
import cn.appsys.pojo.AppCategory;
import cn.appsys.service.AppCategoryService;
@Service("appCategoryService")
public class AppCategoryServiceImpl implements AppCategoryService {
	
	@Resource(name="appCategoryMapper")
	private AppCategoryMapper appCategoryMapper;
	
	@Override
	public List<AppCategory> getAppCategoryByParentId(String parentId) {
		return appCategoryMapper.getAppCategoryByParentId(parentId);
	}

}

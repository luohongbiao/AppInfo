package cn.appsys.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.AppCategory;

public interface AppCategoryService {
	/**
	 * 获取这个父级ID的所有下级app
	 * @param parentId
	 * @return
	 */
	public List<AppCategory> getAppCategoryByParentId(@Param("parentId")String parentId);
}

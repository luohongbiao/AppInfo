package cn.appsys.dao.appcategory;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import cn.appsys.pojo.AppCategory;

@Repository("appCategoryMapper")
public interface AppCategoryMapper {
	/**
	 * 获取这个父级ID的所有下级app
	 * @param parentId
	 * @return
	 */
	public List<AppCategory> getAppCategoryByParentId(@Param("parentId")String parentId);
	
}

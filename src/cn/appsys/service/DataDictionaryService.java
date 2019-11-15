package cn.appsys.service;

import java.util.List;

import cn.appsys.pojo.DataDictionary;

public interface DataDictionaryService {
	/**
	 * 获取所有的平台
	 * @return
	 */
	public List<DataDictionary> getAllDataDictionary();
	/**
	 * 获取所有状态
	 * @return
	 */
	public List<DataDictionary> getAllStatus();
}

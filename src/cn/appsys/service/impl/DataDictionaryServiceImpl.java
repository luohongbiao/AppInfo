package cn.appsys.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.appsys.dao.datadictionary.DataDictionaryMapper;
import cn.appsys.pojo.DataDictionary;
import cn.appsys.service.DataDictionaryService;
@Service("dataDictionaryService")
public class DataDictionaryServiceImpl implements DataDictionaryService {

	@Resource(name="dataDictionaryMapper")
	private DataDictionaryMapper dataDictionaryMapper;
	
	@Override
	public List<DataDictionary> getAllDataDictionary() {
		return dataDictionaryMapper.getAllDataDictionary();
	}

	@Override
	public List<DataDictionary> getAllStatus(){
		// TODO Auto-generated method stub
		return dataDictionaryMapper.getAllStatus();
	}

}

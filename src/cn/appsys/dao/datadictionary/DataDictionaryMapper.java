package cn.appsys.dao.datadictionary;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.appsys.pojo.DataDictionary;
import javafx.scene.chart.PieChart.Data;

@Repository("dataDictionaryMapper")
public interface DataDictionaryMapper {
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

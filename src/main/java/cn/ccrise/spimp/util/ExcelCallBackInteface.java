package cn.ccrise.spimp.util;

import java.util.HashMap;

import org.apache.poi.ss.usermodel.Workbook;

/**
 * excel导出的回调接口
 * 
 * @author zpf
 * 
 */
public interface ExcelCallBackInteface {

	public void process(Workbook book, HashMap<String, Object> root);
}

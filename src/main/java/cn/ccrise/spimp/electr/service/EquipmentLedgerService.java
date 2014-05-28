/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.electr.service;

import net.sf.jxls.transformer.XLSTransformer;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Criterion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import cn.ccrise.ikjp.core.access.HibernateDAO;
import cn.ccrise.ikjp.core.service.HibernateDataServiceImpl;
import cn.ccrise.ikjp.core.util.Page;
import cn.ccrise.spimp.electr.access.EquipmentLedgerDAO;
import cn.ccrise.spimp.electr.entity.EquipmentLedger;
import cn.ccrise.spimp.system.entity.Dictionary;
import cn.ccrise.spimp.system.service.DictionaryService;
import cn.ccrise.spimp.util.DateUtil;

/**
 * EquipmentLedger Service。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Service
public class EquipmentLedgerService extends HibernateDataServiceImpl<EquipmentLedger, Long> {
	@Autowired
	private DictionaryService dictionaryService;
	private void convertToMap(List<Dictionary> list, HashMap<String, Dictionary> aimMap) {
		Iterator<Dictionary> it = list.iterator();
		Dictionary temp = null;
		while (it.hasNext()) {
			temp = it.next();
			aimMap.put(temp.getItemName(), temp);
		}
	}
	public void importFormExcel(String filePath) throws Exception{
		InputStream ins = new FileInputStream(new File(filePath));
		XLSTransformer transformer = new XLSTransformer();
		HashMap<String, Object> root = new HashMap<String, Object>();
		Workbook book = transformer.transformXLS(ins, root);
		Sheet sheet = book.getSheetAt(0);
		Iterator<Row> rowIt = sheet.rowIterator();
		HashMap<String, Dictionary> deviceClass = new HashMap<String, Dictionary>();
		convertToMap(dictionaryService.find(Restrictions.eq("typeCode", "equipment_deviceCategory")), deviceClass);
		Row row = null;
		getDAO().getSession().createSQLQuery("DELETE FROM electr_equipment_ledgers ").executeUpdate();
		while (rowIt.hasNext()) {
			row = rowIt.next();
			saveData(row,deviceClass);
		}
	}
	/**
	 * 根据名称查找字典数据，如果找不到就首先创建对应的字典，然后再加载到缓存中，并且使用
	 * 
	 * @param groupCode
	 *            字典数据的分组key
	 * @param srcName
	 *            导入数据的值
	 * @param dataCache
	 *            当前缓存数据的集合
	 * @return
	 */
	private Dictionary getConvertDictionary(String groupCode, String srcName, HashMap<String, Dictionary> dataCache) {
		Dictionary result = null;
		srcName = srcName.replaceAll("　", "").trim();
		if (dataCache.containsKey(srcName)) {
			result = dataCache.get(srcName);
		} else {
			Dictionary dic = new Dictionary();
			dic.setTypeCode(groupCode);
			dic.setItemName(srcName);
			dictionaryService.save(dic);
			dataCache.put(srcName, dic);
			result = dic;
		}
		return result;
	}
	private void saveData(Row row,HashMap<String, Dictionary> deviceClass){
		if (row.getRowNum() >= 2) {
			Cell cell=null;
			int colIndex = 0;
			EquipmentLedger instance = new EquipmentLedger();
			
			//设备分类
			cell = row.getCell(colIndex++);
			instance.setDeviceClass(getConvertDictionary("equipment_deviceCategory", getStringValue(cell),
					deviceClass));
			//设备名称
			cell = row.getCell(colIndex++);
			instance.setDeviceName(getStringValue(cell));
			if(StringUtils.isBlank(instance.getDeviceName())){
				return;
			}
			//设备编号
			cell = row.getCell(colIndex++);
			instance.setEquipmentID(getStringValue(cell));
			//	规格型号
			cell = row.getCell(colIndex++);
			instance.setDeviceModel(getStringValue(cell));
			//生产厂家
			cell = row.getCell(colIndex++);
			instance.setProducer(getStringValue(cell));
			//技术特征
			cell = row.getCell(colIndex++);
			instance.setTechnology(getStringValue(cell));
			//单位
			cell = row.getCell(colIndex++);
			instance.setMeasureUnit(getStringValue(cell));
			//数量
			cell = row.getCell(colIndex++);
			instance.setAmount(getNumberValue(cell).intValue());
			//出厂日期
			cell = row.getCell(colIndex++);
			instance.setProductionDate(this.getDateValue(cell));
			//出厂编号
			cell = row.getCell(colIndex++);
			instance.setFactoryNumber(this.getStringValue(cell));
			//购买日期
			cell = row.getCell(colIndex++);
			instance.setBuyDate(this.getDateValue(cell));
			//使用日期
			cell = row.getCell(colIndex++);
			instance.setUseDate(this.getDateValue(cell));
			//使用年限
			cell = row.getCell(colIndex++);
			instance.setServiceLife(this.getNumberValue(cell).intValue());
			//在籍
			cell = row.getCell(colIndex++);
			instance.setInMembership(this.getStringValue(cell));
			//使用
			cell = row.getCell(colIndex++);
			instance.setInUse(getStringValue(cell));
			//备用
			cell = row.getCell(colIndex++);
			instance.setIsSpare(getStringValue(cell));
			//闲置
			cell = row.getCell(colIndex++);
			instance.setIsSpare(getStringValue(cell));
			//待修
			cell = row.getCell(colIndex++);
			instance.setNeedsRepair(getStringValue(cell));
			//待报废
			cell = row.getCell(colIndex++);
			instance.setPrepareScrapped(getStringValue(cell));
			//已报废
			cell = row.getCell(colIndex++);
			instance.setScrapped(getStringValue(cell));
			//借入
			cell = row.getCell(colIndex++);
			instance.setBorrowed(getStringValue(cell));
			//借出
			cell = row.getCell(colIndex++);
			instance.setIsLoan(getStringValue(cell));
			//主要附机
			cell = row.getCell(colIndex++);
			instance.setAttachedDevice(getStringValue(cell));
			//原值
			cell = row.getCell(colIndex++);
			instance.setOriginalValue(getStringValue(cell));
			//净值
			cell = row.getCell(colIndex++);
			instance.setNetWorth(this.getStringValue(cell));
			//使用地点
			cell = row.getCell(colIndex++);
			instance.setUsePlace(getStringValue(cell));
			//是否防爆
			cell = row.getCell(colIndex++);
			instance.setExplosionProof(getStringValue(cell));
			save(instance);
		}
	}
	@Autowired
	private EquipmentLedgerDAO equipmentLedgerDAO;

	@Override
	public HibernateDAO<EquipmentLedger, Long> getDAO() {
		return equipmentLedgerDAO;
	}
	
	public Page<EquipmentLedger> pageQuery(Page<EquipmentLedger> page,String search) {
		List<Criterion> criterions = new ArrayList<Criterion>();
		
		if (StringUtils.isNotBlank(search)) {
			criterions.add(Restrictions.or(Restrictions.ilike("deviceName", search, MatchMode.ANYWHERE),Restrictions.ilike("equipmentID", search, MatchMode.ANYWHERE),Restrictions.ilike("factoryNumber", search, MatchMode.ANYWHERE),Restrictions.ilike("inMembership", search, MatchMode.ANYWHERE)));
		}
		
		return getPage(page, criterions.toArray(new Criterion[0]));
	}
	public String getStringValue(Cell cell){
		if(cell==null){
			return "";
		}
		if(cell.getCellType()==Cell.CELL_TYPE_STRING){
			return cell.getStringCellValue();
		}
		if(cell.getCellType()==Cell.CELL_TYPE_NUMERIC){
			return String.valueOf(cell.getNumericCellValue());
		}
		if(cell.getCellType()==Cell.CELL_TYPE_BOOLEAN){
			return String.valueOf(cell.getBooleanCellValue());
		}
		return "";
	}
	public Double getNumberValue(Cell cell){
		if(cell==null){
			return 0d;
		}
		if(cell.getCellType()==Cell.CELL_TYPE_STRING){
			String temp = cell.getStringCellValue();
			temp=temp.trim();
			if(StringUtils.isNotBlank(temp)){
				logger.debug("尝试转化"+temp+" 到double ");
				return Double.valueOf(temp.trim());
			}
		}
		if(cell.getCellType()==Cell.CELL_TYPE_NUMERIC){
			return cell.getNumericCellValue();
		}
		if(cell.getCellType()==Cell.CELL_TYPE_BOOLEAN){
			return 0d;
		}
		return 0d;
	}
	public Date getDateValue(Cell cell){
		if(cell==null){
			return null;
		}
		if(cell.getCellType()==Cell.CELL_TYPE_STRING){
			String temp = cell.getStringCellValue();
			temp=temp.trim();
			if(StringUtils.isNotBlank(temp)){
				logger.debug("尝试转化"+temp+" 到date ");
				return DateUtil.String2date(temp.trim());
			}
		}
		return null;
	}
	public Page<EquipmentLedger> pageQuery(Page<EquipmentLedger> page,Long deviceClass,String search) {
		List<Criterion> criterions = new ArrayList<Criterion>();
		
		if (StringUtils.isNotBlank(search)) {
			criterions.add(Restrictions.or(Restrictions.ilike("deviceName", search, MatchMode.ANYWHERE),Restrictions.ilike("equipmentID", search, MatchMode.ANYWHERE),Restrictions.ilike("factoryNumber", search, MatchMode.ANYWHERE),Restrictions.ilike("inMembership", search, MatchMode.ANYWHERE)));
		}
		
		if (deviceClass != null){
			criterions.add(Restrictions.eq("deviceClass.id", deviceClass));
		}
		
		return getPage(page, criterions.toArray(new Criterion[0]));
	}
}
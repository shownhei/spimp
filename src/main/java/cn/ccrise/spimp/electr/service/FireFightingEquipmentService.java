/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.electr.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.transformer.XLSTransformer;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ccrise.ikjp.core.access.HibernateDAO;
import cn.ccrise.ikjp.core.service.HibernateDataServiceImpl;
import cn.ccrise.ikjp.core.util.Page;
import cn.ccrise.spimp.electr.access.FireFightingEquipmentDAO;
import cn.ccrise.spimp.electr.entity.FireFightingEquipment;
/**
 * FireFightingEquipment Service。
 * 
 */
@Service
public class FireFightingEquipmentService extends HibernateDataServiceImpl<FireFightingEquipment, Long> {
	@Autowired
	private FireFightingEquipmentDAO fireFightingEquipmentDAO;

	@Override
	public HibernateDAO<FireFightingEquipment, Long> getDAO() {
		return fireFightingEquipmentDAO;
	}
	public List<FireFightingEquipment> fetchByEquipmentIds(List<String> equipmentIds){
		if(equipmentIds.size()==0){
			return null;
		}
		return this.find(Restrictions.in("equipmentCode", equipmentIds.toArray(new String[0])));
	}
	public void importFormExcel(String fileName) throws ParsePropertyException, InvalidFormatException,
			FileNotFoundException, Exception {
		HashMap<String, Object> root = new HashMap<String, Object>();
		InputStream ins = new FileInputStream(new File(fileName));
		XLSTransformer transformer = new XLSTransformer();
		Workbook book = transformer.transformXLS(ins, root);
		Sheet sheet = book.getSheetAt(0);
		Iterator<Row> rowIt = sheet.rowIterator();
		Row row = null;
		int startRow = 3;// 数据的起始行
		int currentRowIndex = 0;
		Date recordDate = new Date(System.currentTimeMillis());
		while (rowIt.hasNext()) {
			int colIndex = 1;
			row = rowIt.next();
			currentRowIndex = row.getRowNum();
			if (currentRowIndex >= startRow) {
				FireFightingEquipment fireFighting = new FireFightingEquipment();
				fireFighting.setLocation(row.getCell(colIndex++).toString());// 存在地点
				fireFighting.setEquipmentCode(row.getCell(colIndex++).toString());// 编号
				fireFighting.setSandBoxCapacity(row.getCell(colIndex++).toString());// 沙箱容积
				fireFighting.setAmount1(row.getCell(colIndex++).toString());// 数量
				fireFighting.setMaintenanceDate1(row.getCell(colIndex++).toString());// 维修时间
				fireFighting.setAmount2(new Double(row.getCell(colIndex++).getNumericCellValue()).intValue());// 干粉-数量
				fireFighting.setMaintenance2(row.getCell(colIndex++).toString());// 维修时间
				fireFighting.setFireAxe(new Double(row.getCell(colIndex++).getNumericCellValue()).intValue());// 消防斧
				fireFighting.setFireHook(new Double(row.getCell(colIndex++).getNumericCellValue()).intValue());// 消防钩
				fireFighting.setFireBucket(new Double(row.getCell(colIndex++).getNumericCellValue()).intValue());// 消防桶
				fireFighting.setFireShovel(new Double(row.getCell(colIndex++).getNumericCellValue()).intValue());// 消防锹
				fireFighting.setOthers(row.getCell(colIndex++).toString());// 其他
				fireFighting.setRecordDate(recordDate);// 记录日期
			}
		}
	}

	public Page<FireFightingEquipment> pageQuery(Page<FireFightingEquipment> page, String search) {
		List<Criterion> criterions = new ArrayList<Criterion>();

		if (StringUtils.isNotBlank(search)) {
			criterions.add(Restrictions.or(Restrictions.ilike("location", search, MatchMode.ANYWHERE),
					Restrictions.ilike("equipmentCode", search, MatchMode.ANYWHERE)));
		}

		return getPage(page, criterions.toArray(new Criterion[0]));
	}
}
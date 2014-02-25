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
import cn.ccrise.spimp.electr.access.WindWaterEquipmentDAO;
import cn.ccrise.spimp.electr.entity.WindWaterEquipment;

/**
 * WindWaterEquipment Service。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Service
public class WindWaterEquipmentService extends HibernateDataServiceImpl<WindWaterEquipment, Long> {
	@Autowired
	private WindWaterEquipmentDAO windWaterEquipmentDAO;

	@Override
	public HibernateDAO<WindWaterEquipment, Long> getDAO() {
		return windWaterEquipmentDAO;
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
				WindWaterEquipment windWater = new WindWaterEquipment();
				windWater.setLocation(row.getCell(colIndex++).toString());// 安装位置
				windWater.setEquipmentCode(row.getCell(colIndex++).toString());// 编号
				windWater.setWindAmount(row.getCell(colIndex++).toString());// 安装套数
				windWater.setWindCycle(row.getCell(colIndex++).toString());// 维护周期
				windWater.setWaterAmount(row.getCell(colIndex++).toString());// 安装套数
				windWater.setWaterCycle(row.getCell(colIndex++).toString());// 维护周期
				windWater.setChargePerson(row.getCell(colIndex++).toString());// 负责人
				windWater.setPhoneNumber(row.getCell(colIndex++).toString());// 电话号码
				windWater.setRemark(row.getCell(colIndex++).toString());// 备注
				windWater.setRecordDate(recordDate);// 记录日期
			}
		}
	}

	public Page<WindWaterEquipment> pageQuery(Page<WindWaterEquipment> page, String search) {
		List<Criterion> criterions = new ArrayList<Criterion>();

		if (StringUtils.isNotBlank(search)) {
			criterions.add(Restrictions.or(Restrictions.ilike("location", search, MatchMode.ANYWHERE),
					Restrictions.ilike("equipmentCode", search, MatchMode.ANYWHERE)));
		}

		return getPage(page, criterions.toArray(new Criterion[0]));
	}
}
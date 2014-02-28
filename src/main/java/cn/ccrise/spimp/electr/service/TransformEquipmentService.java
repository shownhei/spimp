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
import org.apache.poi.ss.usermodel.Cell;
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
import cn.ccrise.spimp.electr.access.TransformEquipmentDAO;
import cn.ccrise.spimp.electr.entity.BrakeDevice;
import cn.ccrise.spimp.electr.entity.ElectromotorDevice;
import cn.ccrise.spimp.electr.entity.ReducerDevice;
import cn.ccrise.spimp.electr.entity.TensioningDevice;
import cn.ccrise.spimp.electr.entity.TransformEquipment;
import cn.ccrise.spimp.system.entity.Dictionary;
import cn.ccrise.spimp.system.service.DictionaryService;

@Service
public class TransformEquipmentService extends HibernateDataServiceImpl<TransformEquipment, Long> {

	@Autowired
	private TransformEquipmentDAO transformEquipmentDAO;
	@Autowired
	private DictionaryService dictionaryService;
	@Autowired
	private ReducerDeviceService reducerDeviceService;
	@Autowired
	private ElectromotorDeviceService electromotorDeviceService;
	@Autowired
	private BrakeDeviceService brakeDeviceService;
	@Autowired
	private TensioningDeviceService tensioningDeviceService;

	@Override
	public HibernateDAO<TransformEquipment, Long> getDAO() {
		return transformEquipmentDAO;
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
		HashMap<String, Dictionary> deviceClass = new HashMap<String, Dictionary>();
		/**
		 * 测试用的代码块 用来删除 之前的数据 避免重复
		 */
		getDAO().getSession().createQuery("DELETE FROM TransformEquipment").executeUpdate();
		getDAO().getSession().createQuery("DELETE FROM ReducerDevice").executeUpdate();
		getDAO().getSession().createQuery("DELETE FROM ElectromotorDevice").executeUpdate();
		getDAO().getSession().createQuery("DELETE FROM BrakeDevice").executeUpdate();
		getDAO().getSession().createQuery("DELETE FROM TensioningDevice").executeUpdate();
		convertTomap(dictionaryService.find(Restrictions.eq("typeCode", "equipment_device_class")), deviceClass);
		String temp = null;
		boolean ended = false;
		TransformEquipment equipment = null;
		int startRow = 3;// 数据的起始行
		int currentRowIndex = 0;
		while (rowIt.hasNext()) {
			row = rowIt.next();
			currentRowIndex = row.getRowNum();
			Cell cell = null;
			int endRow = 0;
			if (currentRowIndex >= 3) {
				int colIndex = 1;
				cell = row.getCell(colIndex++);
				temp = cell.toString();
				if (StringUtils.isBlank(temp.trim())) {
					continue;
				} else {
					if (row.getRowNum() > 3) {
						ended = true;
					}
					endRow = row.getRowNum() - 1;
					if (ended) {
						saveAccessories(startRow, endRow, sheet, equipment.getId());
						startRow = endRow + 1;
						ended = false;
					}
				}
				equipment = new TransformEquipment();
				endRow = row.getRowNum();
				equipment.setDeviceClass(getConvertDictionary("equipment_deviceClass", cell.toString(), deviceClass));// 设备分类
				cell = row.getCell(colIndex++);
				equipment.setDeviceName(cell.toString());// 设备名称
				cell = row.getCell(colIndex++);
				equipment.setDeviceModel(cell.toString());// 设备型号
				cell = row.getCell(colIndex++);
				equipment.setSpeed(cell.toString());// 速度(m/s)
				cell = row.getCell(colIndex++);
				equipment.setConveyingCapacity(cell.toString());// 输送量(T/h)
				cell = row.getCell(colIndex++);
				equipment.setFactoryNumber(cell.toString());// 出厂编号
				cell = row.getCell(colIndex++);
				equipment.setProductionDate(null);// 出厂日期
				cell = row.getCell(colIndex++);
				equipment.setEquipmentNumber(cell.toString());// 设备编号
				cell = row.getCell(colIndex++);
				equipment.setLocation(cell.toString());// 使用地点
				cell = row.getCell(colIndex++);
				equipment.setProducer(cell.toString());// 生产厂家
				cell = row.getCell(colIndex++);
				equipment.setLayoutLength(Integer.parseInt(cell.toString().toLowerCase().replace("m", "")));// 布置长度(m)
				cell = row.getCell(colIndex++);
				save(equipment);
				// equipment.setRecordGroup(recordGroup);//组织机构 当前登陆的组织机构
			}

		}
		saveAccessories(startRow, currentRowIndex, sheet, equipment.getId());
	}

	public Page<TransformEquipment> pageQuery(Page<TransformEquipment> page, Long deviceClass, String search) {
		List<Criterion> criterions = new ArrayList<Criterion>();

		if (StringUtils.isNotBlank(search)) {
			criterions.add(Restrictions.or(Restrictions.ilike("deviceName", search, MatchMode.ANYWHERE),
					Restrictions.ilike("deviceModel", search, MatchMode.ANYWHERE)));
		}

		if (deviceClass != null) {
			criterions.add(Restrictions.eq("deviceClass.id", deviceClass));
		}

		return getPage(page, criterions.toArray(new Criterion[0]));
	}

	private void convertTomap(List<Dictionary> list, HashMap<String, Dictionary> aimMap) {
		Iterator<Dictionary> it = list.iterator();
		Dictionary temp = null;
		while (it.hasNext()) {
			temp = it.next();
			aimMap.put(temp.getItemName(), temp);
		}
	}

	/***
	 * 根据excel文件中的数据 加载字典对象 如果不包含 就新建
	 * 
	 * @param groupCode
	 *            字典中对应的分组信息
	 * @param srcName
	 *            excel文件名中的原始数据值
	 * @param dataCache
	 *            缓存字典信息的map
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

	/**
	 * 保存配件信息
	 */
	private void saveAccessories(int startRowIndex, int endRowIndex, Sheet sheet, Long transformEquipmentId) {
		// 减速机
		Row row = null;
		for (int i = startRowIndex; i <= endRowIndex; i++) {
			int colIndex = 12;
			row = sheet.getRow(i);
			if (StringUtils.isBlank(row.getCell(colIndex).toString())) {
				continue;
			}
			ReducerDevice reducer = new ReducerDevice();
			reducer.setDeviceModel(row.getCell(colIndex++).toString());// 型号
			reducer.setRunningPower(row.getCell(colIndex++).toString());// 运行功率
			reducer.setTransmissionRatio(row.getCell(colIndex++).toString());// 传动比
			reducer.setFactoryNumber(row.getCell(colIndex++).toString());// 出厂编号
			reducer.setProducer(row.getCell(colIndex++).toString());// 生产厂家
			reducer.setTransformEquipmentId(transformEquipmentId);
			reducerDeviceService.save(reducer);
		}
		// 电动机
		for (int i = startRowIndex; i <= endRowIndex; i++) {
			int colIndex = 17;
			row = sheet.getRow(i);
			if (StringUtils.isBlank(row.getCell(colIndex).toString())) {
				continue;
			}
			ElectromotorDevice electromotor = new ElectromotorDevice();
			electromotor.setDeviceModel(row.getCell(colIndex++).toString());// 型号
			electromotor.setFactoryNumber(row.getCell(colIndex++).toString());// 编号
			electromotor.setProductionDate(null);// 出厂日期
			electromotor.setProducer(row.getCell(colIndex++).toString());// 生产厂家
			electromotor.setTransformEquipmentId(transformEquipmentId);
			electromotorDeviceService.save(electromotor);
		}
		// 制动器
		for (int i = startRowIndex; i <= endRowIndex; i++) {
			int colIndex = 21;
			row = sheet.getRow(i);
			if (StringUtils.isBlank(row.getCell(colIndex).toString())) {
				continue;
			}
			BrakeDevice brake = new BrakeDevice();
			brake.setDeviceModel(row.getCell(colIndex++).toString());// 型号
			brake.setFactoryNumber(row.getCell(colIndex++).toString());// 编号
			brake.setProductionDate(null);// 出厂日期
			brake.setProducer(row.getCell(colIndex++).toString());// 生产厂家
			brake.setTransformEquipmentId(transformEquipmentId);
			brakeDeviceService.save(brake);
		}
		// 拉紧装置
		Cell cell = null;
		for (int i = startRowIndex; i <= endRowIndex; i++) {
			int colIndex = 24;
			row = sheet.getRow(i);
			if (StringUtils.isBlank(row.getCell(colIndex).toString())) {
				continue;
			}
			TensioningDevice tensioning = new TensioningDevice();
			tensioning.setTakeUp(row.getCell(colIndex++).toString());// 拉紧方式
			tensioning.setDeviceName(row.getCell(colIndex++).toString());// 装置名称
			tensioning.setDeviceModel(row.getCell(colIndex++).toString());// 型号
			tensioning.setDeviceNumber(row.getCell(colIndex++).toString());// 编号
			logger.debug("{}",row.getCell(colIndex++).toString());
//			tensioning.setProductionDate(new Date(row.getCell(colIndex++).getNumericCellValue()));// 出厂日期
			cell = row.getCell(colIndex++);
			if(cell!=null){
				tensioning.setProducer(cell.toString());// 生产厂家
			}
			cell = row.getCell(colIndex++);
			if(cell!=null){
				tensioning.setTechParameters(cell.toString());// 技术参数
			}
			tensioning.setTransformEquipmentId(transformEquipmentId);
			tensioningDeviceService.save(tensioning);
		}
	}
}
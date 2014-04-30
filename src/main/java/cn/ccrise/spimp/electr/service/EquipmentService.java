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
import javax.servlet.http.HttpSession;

import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.transformer.XLSTransformer;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ccrise.ikjp.core.access.HibernateDAO;
import cn.ccrise.ikjp.core.service.HibernateDataServiceImpl;
import cn.ccrise.ikjp.core.util.Page;
import cn.ccrise.spimp.electr.access.EquipmentDAO;
import cn.ccrise.spimp.electr.entity.Accessory;
import cn.ccrise.spimp.electr.entity.Equipment;
import cn.ccrise.spimp.system.entity.Dictionary;
import cn.ccrise.spimp.system.service.DictionaryService;

/**
 * Equipment Service。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Service
public class EquipmentService extends HibernateDataServiceImpl<Equipment, Long> {
	@Autowired
	private EquipmentDAO equipmentDAO;
	@Autowired
	private DictionaryService dictionaryService;
	@Autowired
	private AccessoryService accessoryService;

	public boolean deleteEquipment(Long id, HttpSession httpSession) {
		Equipment tEquipment = findUniqueBy("id", id);
		List<Accessory> result = accessoryService.findBy("equipmentId", id);
		for (Accessory ac : result) {
			accessoryService.deleteAccessory(ac.getId(), httpSession);
		}
		return delete(tEquipment);
	}

	public List<Equipment> fetchByEquipmentIds(List<String> equipmentIds){
		return this.find(Restrictions.in("deviceNumber", equipmentIds.toArray(new String[0])));
	}
	@Override
	public HibernateDAO<Equipment, Long> getDAO() {
		return equipmentDAO;
	}

	public void importFormExcel(String fileName) throws ParsePropertyException, InvalidFormatException,
			FileNotFoundException, Exception {
		/**
		 * 字典数据
		 */
		HashMap<String, Dictionary> deviceClass = new HashMap<String, Dictionary>();
		HashMap<String, Dictionary> deviceCategory = new HashMap<String, Dictionary>();
		HashMap<String, Dictionary> deviceType = new HashMap<String, Dictionary>();

		HashMap<String, Dictionary> serviceEnvironment = new HashMap<String, Dictionary>();
		HashMap<String, Dictionary> deviceArea = new HashMap<String, Dictionary>();
		HashMap<String, Dictionary> stowedPosition = new HashMap<String, Dictionary>();
		// 缓存字典数据
		convertToMap(dictionaryService.find(Restrictions.eq("typeCode", "equipment_device_class")), deviceClass);
		convertToMap(dictionaryService.find(Restrictions.eq("typeCode", "equipment_deviceCategory")), deviceCategory);
		convertToMap(dictionaryService.find(Restrictions.eq("typeCode", "equipment_deviceType")), deviceType);

		convertToMap(dictionaryService.find(Restrictions.eq("typeCode", "equipment_serviceEnvironment")),
				serviceEnvironment);
		convertToMap(dictionaryService.find(Restrictions.eq("typeCode", "equipment_deviceArea")), deviceArea);
		convertToMap(dictionaryService.find(Restrictions.eq("typeCode", "equipment_stowedPosition")), stowedPosition);

		HashMap<String, Object> root = new HashMap<String, Object>();
		InputStream ins = new FileInputStream(new File(fileName));
		XLSTransformer transformer = new XLSTransformer();
		Workbook book = transformer.transformXLS(ins, root);
		Sheet sheet = book.getSheetAt(0);
		Iterator<Row> rowIt = sheet.rowIterator();
		Row row = null;
		getDAO().getSession().createSQLQuery("DELETE FROM electr_equipments").executeUpdate();
		while (rowIt.hasNext()) {
			row = rowIt.next();
			saveData(deviceClass, deviceCategory, deviceType, serviceEnvironment, deviceArea, stowedPosition, row);

		}

	}

	public Page<Equipment> pageQuery(Page<Equipment> page, Long deviceClass, Long deviceCategory, Long deviceType,
			Long serviceEnvironment, Long deviceArea, Long stowedPosition) {
		List<Criterion> criterions = new ArrayList<Criterion>();

		if (deviceClass != null) {
			criterions.add(Restrictions.eq("deviceClass.id", deviceClass));
		}
		if (deviceCategory != null) {
			criterions.add(Restrictions.eq("deviceCategory.id", deviceCategory));
		}
		if (deviceType != null) {
			criterions.add(Restrictions.eq("deviceType.id", deviceType));
		}
		if (serviceEnvironment != null) {
			criterions.add(Restrictions.eq("serviceEnvironment.id", serviceEnvironment));
		}
		if (deviceArea != null) {
			criterions.add(Restrictions.eq("deviceArea.id", deviceArea));
		}
		if (stowedPosition != null) {
			criterions.add(Restrictions.eq("stowedPosition.id", stowedPosition));
		}

		if (criterions.size() > 0) {
			return getPage(page, criterions.toArray(new Criterion[0]));
		} else {
			return getPage(page);
		}
	}

	private void convertToMap(List<Dictionary> list, HashMap<String, Dictionary> aimMap) {
		Iterator<Dictionary> it = list.iterator();
		Dictionary temp = null;
		while (it.hasNext()) {
			temp = it.next();
			aimMap.put(temp.getItemName(), temp);
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

	/**
	 * 保存一条导入数据
	 * 
	 * @param deviceClass
	 * @param deviceCategory
	 * @param deviceType
	 * @param serviceEnvironment
	 * @param deviceArea
	 * @param stowedPosition
	 * @param row
	 */
	private void saveData(HashMap<String, Dictionary> deviceClass, HashMap<String, Dictionary> deviceCategory,
			HashMap<String, Dictionary> deviceType, HashMap<String, Dictionary> serviceEnvironment,
			HashMap<String, Dictionary> deviceArea, HashMap<String, Dictionary> stowedPosition, Row row) {
		Cell cell;
		String tempSrc;
		if (row.getRowNum() >= 2) {
			int colIndex = 1;
			Equipment raw = new Equipment();
			cell = row.getCell(colIndex++);
			tempSrc = cell.getStringCellValue();
			tempSrc = tempSrc.replaceAll("　", "");
			if (StringUtils.isBlank(tempSrc)) {
				return;
			}
			raw.setDeviceClass(deviceClass.get(tempSrc.trim()));// 设备分类--
			cell = row.getCell(colIndex++);
			if (cell != null) {
				raw.setDeviceCategory(getConvertDictionary("equipment_deviceCategory", cell.getStringCellValue(),
						deviceCategory));// deviceCategory.get(cell.getStringCellValue().replaceAll("　",
											// "").trim()));//设备种类--
			}

			cell = row.getCell(colIndex++);
			if (cell != null) {
				raw.setDeviceType(getConvertDictionary("equipment_deviceType", cell.getStringCellValue(), deviceType));// deviceType.get(cell.getStringCellValue().replaceAll("　",
																														// "").trim()));//设备类型--
			}

			cell = row.getCell(colIndex++);
			if (cell != null) {
				raw.setDeviceName(cell.getStringCellValue().replaceAll("　", "").trim());// 设备名称
			}

			cell = row.getCell(colIndex++);
			if (cell != null) {
				raw.setDeviceModel(cell.getStringCellValue().replaceAll("　", "").trim());// 设备型号
			}

			cell = row.getCell(colIndex++);
			if (cell != null) {
				raw.setServiceEnvironment(getConvertDictionary("equipment_serviceEnvironment",
						cell.getStringCellValue(), serviceEnvironment));
			}

			cell = row.getCell(colIndex++);
			if (cell != null) {
				raw.setDeviceArea(getConvertDictionary("equipment_deviceArea", cell.getStringCellValue(), deviceArea));
			}

			cell = row.getCell(colIndex++);
			if (cell != null) {
				raw.setStowedPosition(getConvertDictionary("equipment_stowedPosition", cell.getStringCellValue(),
						stowedPosition));
			}
			cell = row.getCell(colIndex++);
			if (cell != null) {
				raw.setUsage(cell.getStringCellValue().replaceAll("　", "").trim());// 用途
			}

			cell = row.getCell(colIndex++);
			if (cell != null) {
				raw.setProducer(cell.getStringCellValue().replaceAll("　", "").trim());// 生产厂家
			}

			cell = row.getCell(colIndex++);
			if (cell != null) {
				int cellType = cell.getCellType();
				if (cellType == Cell.CELL_TYPE_NUMERIC) {
					raw.setDeviceNumber(String.valueOf(cell.getNumericCellValue()));// 设备编号
				} else if (cellType == Cell.CELL_TYPE_STRING) {
					raw.setDeviceNumber(cell.getStringCellValue().replaceAll("　", "").trim());// 设备编号
				}
			}
			cell = row.getCell(colIndex++);
			if (cell != null) {
				int cellType = cell.getCellType();
				if (cellType == Cell.CELL_TYPE_NUMERIC) {
					raw.setFactoryNumber(String.valueOf(cell.getNumericCellValue()));// 出厂编号
				} else if (cellType == Cell.CELL_TYPE_STRING) {
					raw.setFactoryNumber(cell.getStringCellValue().replaceAll("　", "").trim());// 出厂编号
				}
			}

			cell = row.getCell(colIndex++);
			cell = row.getCell(colIndex++);
			if (cell != null) {
				int cellType = cell.getCellType();
				if (cellType == Cell.CELL_TYPE_STRING) {
					// raw.setDeviceNumber(String.valueOf(cell.getNumericCellValue()));//
					// 出厂日期
				} else if (cellType == Cell.CELL_TYPE_NUMERIC) {
					raw.setProductionDate(new Date(cell.getDateCellValue().getTime()));// 出厂日期
				}
			}
			cell = row.getCell(colIndex++);
			if (cell != null) {
				int cellType = cell.getCellType();
				if (cellType == Cell.CELL_TYPE_NUMERIC) {
					raw.setChargePerson(String.valueOf(cell.getNumericCellValue()));// 包机人
				} else if (cellType == Cell.CELL_TYPE_STRING) {
					raw.setChargePerson(cell.getStringCellValue().replaceAll("　", "").trim());// 包机人
				}
			}

			cell = row.getCell(colIndex++);
			if (cell != null) {
				raw.setMonitor(cell.getStringCellValue().replaceAll("　", "").trim());// 班长/组长
			}

			cell = row.getCell(colIndex++);
			if (cell != null) {
				raw.setOpenLocker(cell.getStringCellValue().replaceAll("　", "").trim());// 三开一防锁
			}

			cell = row.getCell(colIndex++);
			if (cell != null) {
				int cellType = cell.getCellType();
				String value = null;
				if (cellType == Cell.CELL_TYPE_NUMERIC) {
					value = String.valueOf((int) cell.getNumericCellValue());
					raw.setLockerNumber(value);// 数量
				} else if (cellType == Cell.CELL_TYPE_STRING) {
					value = cell.getStringCellValue().replaceAll("　", "").trim();
				}
				raw.setLockerNumber(value);
			}
			save(raw);
		}
	}
}
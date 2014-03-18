/*
 * Copyright (C) 2012 CCRISE.
 */
package cn.ccrise.spimp.monitor.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ccrise.ikjp.core.access.HibernateDAO;
import cn.ccrise.ikjp.core.service.HibernateDataServiceImpl;
import cn.ccrise.spimp.monitor.access.MonitorSensorTypeDAO;
import cn.ccrise.spimp.monitor.entity.MonitorSensorType;

import com.google.common.collect.Lists;

/**
 * MonitorSensorType Service.
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Service
public class MonitorSensorTypeService extends HibernateDataServiceImpl<MonitorSensorType, Long> {
	@Autowired
	private MonitorSensorTypeDAO monitorSensorTypeDAO;

	private static Map<Integer, MonitorSensorType> INSTANCE_MAP;

	public synchronized Map<Integer, MonitorSensorType> getAllInstanceAsMap() {
		if (INSTANCE_MAP == null) {
			INSTANCE_MAP = new HashMap<Integer, MonitorSensorType>();

			List<MonitorSensorType> monitorSensorTypes = this.getAll();
			for (MonitorSensorType monitorSensorType : monitorSensorTypes) {
				INSTANCE_MAP.put(monitorSensorType.getSensorTypeId(), monitorSensorType);
			}
		}

		return INSTANCE_MAP;
	}

	@Override
	public HibernateDAO<MonitorSensorType, Long> getDAO() {
		return monitorSensorTypeDAO;
	}

	public MonitorSensorType getInstanceByTypeId(Integer sensorTypeId) {
		return findUniqueBy("sensorTypeId", sensorTypeId);
	}

	/**
	 * 根据类型获取指定范围的传感器类型数据
	 * 
	 * @param type
	 *            1:模拟量 2:数字量(开关量) 3:累积量 NULL:全部
	 * @return
	 */
	public List<MonitorSensorType> getSensorTypesAsType(Integer type) {
		if (type == null) {
			return this.getAll();
		}

		Integer[] protocolNumber = protocolAnalysis(type);

		ArrayList<Criterion> criterions = Lists.newArrayList();
		criterions.add(Restrictions.ge("sensorTypeId", protocolNumber[0]));
		criterions.add(Restrictions.le("sensorTypeId", protocolNumber[1]));

		return this.find(criterions.toArray(new Criterion[0]));
	}

	/**
	 * 根据类型获取协议起止范围 协议说明:0<=编号<=99的是模拟量，100<=编号<=199为数字量，200<=编号<=299为累计量
	 * 
	 * @param type
	 *            1:模拟量 2:数字量(开关量) 3:累积量 NULL:全部
	 * @return
	 */
	public Integer[] protocolAnalysis(Integer type) {
		int startNum = 0;
		int endNum = 0;

		switch (type) {
		case 1:
			endNum = 99;
			break;
		case 2:
			startNum = 100;
			endNum = 199;
			break;
		case 3:
			startNum = 200;
			endNum = 299;
			break;
		}

		Integer[] result = new Integer[2];
		result[0] = startNum;
		result[1] = endNum;

		return result;
	}

	public boolean save(String sensorTypeName, Integer sensorTypeId) {
		MonitorSensorType monitorSensorType = new MonitorSensorType();
		monitorSensorType.setSensorTypeName(sensorTypeName);
		monitorSensorType.setSensorTypeId(sensorTypeId);
		return save(monitorSensorType);
	}
}
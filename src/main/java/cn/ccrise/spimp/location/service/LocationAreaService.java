/*
 * Copyright (C) 2012 CCRISE.
 */
package cn.ccrise.spimp.location.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ccrise.ikjp.core.access.HibernateDAO;
import cn.ccrise.ikjp.core.service.HibernateDataServiceImpl;
import cn.ccrise.spimp.electr.service.EquipmentService;
import cn.ccrise.spimp.electr.service.FireFightingEquipmentService;
import cn.ccrise.spimp.electr.service.TransformEquipmentService;
import cn.ccrise.spimp.electr.service.WindWaterEquipmentService;
import cn.ccrise.spimp.location.access.LocationAreaDAO;
import cn.ccrise.spimp.location.entity.LocationArea;

/**
 * LocationArea Service。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Service
public class LocationAreaService extends HibernateDataServiceImpl<LocationArea, Long> {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private LocationAreaDAO locationAreaDAO;
	@Autowired
	private LocationStaffService locationStaffService;
	/**
	 * 电器设备
	 */
	@Autowired
	private EquipmentService equipmentService;
	/**
	 * 运输设备
	 */
	@Autowired
	private TransformEquipmentService transformEquipmentService;
	/**
	 * 压风自救设备
	 */
	@Autowired
	private FireFightingEquipmentService fireFightingEquipmentService;
	/**
	 * 供水施救
	 */
	@Autowired
	private WindWaterEquipmentService windWaterEquipmentService;
	private boolean isUndefined(Object instance) {
		return instance.getClass().getSimpleName().equals("Undefined");
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void deal(String jsonData,HashMap<String,Object> root){
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("javascript");
		StringBuilder buff = new StringBuilder();
		buff.append(" var json=");
		buff.append(jsonData);
		buff.append(";");
		buff.append("var PERSON= json.PERSON;"); 
		buff.append("var EQIPMENT= json.EQIPMENT;");
		buff.append("var ENRIROMENT= json.ENRIROMENT;");
		try {
			engine.eval(buff.toString());
		} catch (ScriptException e) {
			e.printStackTrace();
		}
		//人员
		Object PERSON = (Object) engine.get("PERSON");
		
		if (PERSON!=null && !isUndefined(PERSON)) {
			List<Map> PERSONs = (List<Map>) PERSON;
			
			dealPerson(PERSONs,root);
		}
		//设备
		Object EQIPMENT = (Object) engine.get("EQIPMENT");
		if (EQIPMENT!=null &&!isUndefined(EQIPMENT)) {
			List<Map> EQIPMENTs = (List<Map>) EQIPMENT;
			dealEquipment(EQIPMENTs,root);
		}
		//环境
		Object ENRIROMENT = (Object) engine.get("ENRIROMENT");
		if (ENRIROMENT!=null &&!isUndefined(ENRIROMENT)) {
			List<Map> ENRIROMENTs = (List<Map>) ENRIROMENT;
			dealEnriroment(ENRIROMENTs,root);
		}
	}
	/**
	 * 人员处理
	 * @param PERSONs
	 */
	@SuppressWarnings("rawtypes")
	private void dealPerson(List<Map> PERSONs,HashMap<String,Object> root){
		String DBID=null;
		ArrayList<String> curStationIds=new ArrayList<String>();
		for (Map raw : PERSONs) {
			this.logger.debug("{}",raw);
			DBID=(String)raw.get("DBID");
			System.out.println(DBID);
			
			curStationIds.add(DBID.split(";")[1].split(":")[1]);
		}
		root.put("PERSON",locationStaffService.find(Restrictions.in("curStationId", curStationIds.toArray(new String[0]))));
	}
	/**
	 * 设备 设计到三种表 electr_equipments
	 * electr_transform_equipments
	 * electr_wind_water_equipments
	 * electr_fire_fighting_equipments
	 * @param EQIPMENTs
	 */
	@SuppressWarnings("rawtypes")
	private void dealEquipment(List<Map> EQIPMENTs,HashMap<String,Object> root){
		String DBID=null;
		String TABLE=null;
		String equipmentId=null;
		HashMap<String,List<String>> reuslt= new HashMap<String,List<String>>();
		final String equipment="electr_equipments";
		final String transform="electr_transform_equipments";
		final String windwater="electr_wind_water_equipments";
		final String fire="electr_fire_fighting_equipments";
		reuslt.put(equipment, new LinkedList<String>());
		reuslt.put(transform, new LinkedList<String>());
		reuslt.put(windwater, new LinkedList<String>());
		reuslt.put(fire, new LinkedList<String>());
		for (Map raw : EQIPMENTs) {
			DBID=(String)raw.get("DBID");
			equipmentId=DBID.split(";")[1].split(":")[1];
			TABLE=(String)raw.get("TABLE");
			TABLE=TABLE.toLowerCase();
			if(!reuslt.containsKey(TABLE)){
				logger.warn("加载设备信息出现 不识别的表名"+TABLE);
			}else{
				reuslt.get(TABLE).add(equipmentId);
			}
		}
		HashMap<String,Object> equipments=new HashMap<String,Object>();
		equipments.put(equipment, equipmentService.fetchByEquipmentIds(reuslt.get(equipment)));
		equipments.put(transform, transformEquipmentService.fetchByEquipmentIds(reuslt.get(transform)));
		equipments.put(windwater, windWaterEquipmentService.fetchByEquipmentIds(reuslt.get(windwater)));
		equipments.put(fire, fireFightingEquipmentService.fetchByEquipmentIds(reuslt.get(fire)));
		root.put("EQIPMENT", equipments);
	}
	/**
	 * 环境
	 * @param ENRIROMENTs
	 */
	@SuppressWarnings("rawtypes")
	private void dealEnriroment(List<Map> ENRIROMENTs,HashMap<String,Object> root){
		String DBID=null;
		String nodeId=null;
		StringBuilder buff = new StringBuilder();
		buff.append(" select nodeid, nodeplace,stationid,sensorTypeId,sensorName,currentData");
		buff.append(" from K_Node where nodeid in( ");
		for (Map raw : ENRIROMENTs) {
			DBID=(String)raw.get("DBID");
			buff.append("'");
			nodeId=DBID.split(";")[1].split(":")[1];
			buff.append(nodeId);
			buff.append("',");
		}
		buff.delete(buff.length()-1, buff.length());
		buff.append(")");
		SQLQuery query=getDAO().getSession().createSQLQuery(buff.toString());
		root.put("ENRIROMENT",query.list());
	}
	@Override
	public HibernateDAO<LocationArea, Long> getDAO() {
		return locationAreaDAO;
	}
}
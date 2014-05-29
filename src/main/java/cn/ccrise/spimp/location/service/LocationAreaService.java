/*
 * Copyright (C) 2012 CCRISE.
 */
package cn.ccrise.spimp.location.service;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ccrise.ikjp.core.access.HibernateDAO;
import cn.ccrise.ikjp.core.service.HibernateDataServiceImpl;
import cn.ccrise.spimp.electr.entity.EquipmentLedger;
import cn.ccrise.spimp.electr.service.EquipmentLedgerService;
import cn.ccrise.spimp.electr.service.EquipmentService;
import cn.ccrise.spimp.electr.service.FireFightingEquipmentService;
import cn.ccrise.spimp.electr.service.TransformEquipmentService;
import cn.ccrise.spimp.electr.service.WindWaterEquipmentService;
import cn.ccrise.spimp.location.access.LocationAreaDAO;
import cn.ccrise.spimp.location.entity.LocationArea;
import cn.ccrise.spimp.spmi.document.service.DocumentService;

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
	 * 根据机电科的设备台账最新做的台账管理service
	 */
	@Autowired
	private EquipmentLedgerService equipmentLedgerService;
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
		buff.append("var CAMERA = json.CAMERA;");
		buff.append("var PERSON = json.PERSON;"); 
		buff.append("var EQIPMENT = json.EQIPMENT;");
		buff.append("var ENVIROMENT = json.ENVIROMENT;");
		try {
			engine.eval(buff.toString());
		} catch (ScriptException e) {
			e.printStackTrace();
		}
		//摄像头
		Object CAMERA = (Object) engine.get("CAMERA");
		logger.debug("CAMERA",CAMERA);
		if (CAMERA != null && !isUndefined(CAMERA)) {
			List<Map> CAMERAs = (List<Map>) CAMERA;
			dealCamera(CAMERAs,root);
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
		Object ENVIROMENT = (Object) engine.get("ENVIROMENT");
		if (ENVIROMENT!=null &&!isUndefined(ENVIROMENT)) {
			List<Map> ENVIROMENTs = (List<Map>) ENVIROMENT;
			dealEnviroment(ENVIROMENTs,root);
		}
		//先暂时加载所有的上传文档
		dealRelatedDocument(root);
	}
	@Autowired
	private DocumentService documentService;
	/**
	 * 加载相关文档
	 */
	private void dealRelatedDocument(HashMap<String,Object> root) {
		root.put("documents", this.documentService.find());
	}
	/**
	 * 摄像头处理
	 * @param CAMERAs
	 */
	@SuppressWarnings("rawtypes")
	private void dealCamera(List<Map> CAMERAs, HashMap<String, Object> root) {
		ArrayList<Map> cameras=new ArrayList<Map>();
		for (Map raw : CAMERAs) {
			cameras.add(raw);
		}
		this.logger.debug("{}",cameras);
		root.put("CAMERA", cameras);
	}
	
	/**
	 * 人员处理
	 * @param PERSONs
	 */
	@SuppressWarnings("rawtypes")
	private void dealPerson(List<Map> PERSONs,HashMap<String,Object> root){
		String DBID=null;
		ArrayList<String> curStationIds=new ArrayList<String>();
		int i=0;
		for (Map raw : PERSONs) {
			this.logger.debug("{}",raw);
			DBID=(String)raw.get("DBID");
			if(StringUtils.isBlank(DBID)){
				continue;
			}
			System.out.println(DBID);
			i+=1;
			curStationIds.add(DBID.split(";")[1].split(":")[1]);
		}
		if(i>0){
			root.put("PERSON",locationStaffService.find(Restrictions.in("curStationId", curStationIds.toArray(new String[0]))));
		}
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void dealEquipment(List<Map> EQIPMENTs,HashMap<String,Object> root){
		String DBID=null;
		String equipmentId=null;
		ArrayList<String> ids=new ArrayList<String>();
		String STATE=null;
		HashMap<String,String> stateMap = new HashMap<String,String> ();
		for (Map raw : EQIPMENTs) {
			DBID=(String)raw.get("DBID");
			if(StringUtils.isBlank(DBID)){
				continue;
			}
			STATE=(String)raw.get("STATE");
			equipmentId=DBID.split(";")[0].split(":")[1];
			stateMap.put(equipmentId, STATE);
			ids.add(equipmentId);
		}
		if(ids.size()>0){
			ArrayList<Map<String,Object>> equipments= new ArrayList<Map<String,Object>>();
			Map<String,Object> tempMap = null;
			for(EquipmentLedger raw :equipmentLedgerService.find(Restrictions.in("equipmentID", ids))){
				try {
					tempMap=convertBean(raw);
					tempMap.put("STATE", stateMap.get(raw.getEquipmentID()));
					equipments.add(tempMap);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			root.put("equipments",equipments );
		}
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private   Map convertBean(Object bean)
            throws Exception {
        Class type = bean.getClass();
        Map returnMap = new HashMap();
        BeanInfo beanInfo = Introspector.getBeanInfo(type);

        PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
        for (int i = 0; i< propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();
            if (!propertyName.equals("class")) {
                Method readMethod = descriptor.getReadMethod();
                Object result = readMethod.invoke(bean, new Object[0]);
                if (result != null) {
                    returnMap.put(propertyName, result);
                } else {
                    returnMap.put(propertyName, "");
                }
            }
        }
        return returnMap;
    }

	/**
	 * 设备 设计到三种表 electr_equipments
	 * electr_transform_equipments
	 * electr_wind_water_equipments
	 * electr_fire_fighting_equipments
	 * @param EQIPMENTs
	 */
	@SuppressWarnings("rawtypes")
	protected void dealEquipmentOld(List<Map> EQIPMENTs,HashMap<String,Object> root){
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
			if(StringUtils.isBlank(DBID)){
				continue;
			}
			equipmentId=DBID.split(";")[0].split(":")[1];
			TABLE=(String)raw.get("TABLE");
			
			TABLE=TABLE.toLowerCase();
			if(!reuslt.containsKey(TABLE)){
				logger.warn("加载设备信息出现 不识别的表名"+TABLE);
			}else{
				reuslt.get(TABLE).add(equipmentId);
			}
		}
		HashMap<String,Object> equipments=new HashMap<String,Object>();
		if(reuslt.get(equipment).size()>0){
			equipments.put(equipment, equipmentService.fetchByEquipmentIds(reuslt.get(equipment)));
		}
		if(reuslt.get(transform).size()>0){
		   equipments.put(transform, transformEquipmentService.fetchByEquipmentIds(reuslt.get(transform)));
		}
		if(reuslt.get(windwater).size()>0){
			equipments.put(windwater, windWaterEquipmentService.fetchByEquipmentIds(reuslt.get(windwater)));
		}
		if(reuslt.get(fire).size()>0){
			equipments.put(fire, fireFightingEquipmentService.fetchByEquipmentIds(reuslt.get(fire)));
		}
		root.put("EQIPMENT", equipments);
	}
	/**
	 * 环境
	 * @param ENVIROMENTs
	 */
	@SuppressWarnings("rawtypes")
	private void dealEnviroment(List<Map> ENVIROMENTs,HashMap<String,Object> root){
		String DBID=null;
		String nodeId=null;
		StringBuilder buff = new StringBuilder();
		buff.append(" select NodeID,NodePlace,StationID,SensorName");
		buff.append(" ,BoundUpperValue,BoundLowerValue,AlarmUpperValue,AlarmLowerValue,CurrentData,SensorUnit ");
		buff.append(" from K_Node where nodeid in( ");
		int i=0;
		for (Map raw : ENVIROMENTs) {
			DBID=(String)raw.get("DBID");
			if(StringUtils.isBlank(DBID)){
				continue;
			}
			i+=1;
			buff.append("'");
			nodeId=DBID.split(";")[1].split(":")[1];
			buff.append(nodeId);
			buff.append("',");
		}
		if(i>0){
			buff.delete(buff.length()-1, buff.length());
			buff.append(")");
			SQLQuery query=getDAO().getSession().createSQLQuery(buff.toString());
			root.put("ENVIROMENT",query.list());
		}
	}
	@Override
	public HibernateDAO<LocationArea, Long> getDAO() {
		return locationAreaDAO;
	}
}
/*
 * Copyright (C) 2012 CCRISE.
 */
package cn.ccrise.spimp.monitor.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ccrise.ikjp.core.access.HibernateDAO;
import cn.ccrise.ikjp.core.service.HibernateDataServiceImpl;
import cn.ccrise.spimp.monitor.access.MonitorStateDAO;
import cn.ccrise.spimp.monitor.entity.MonitorState;

/**
 * MonitorState Service.
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Service
public class MonitorStateService extends HibernateDataServiceImpl<MonitorState, Long> {
	@Autowired
	private MonitorStateDAO monitorStateDAO;

	private static Map<Integer, MonitorState> INSTANCE_MAP;

	public synchronized Map<Integer, MonitorState> getAllInstanceAsMap() {
		if (INSTANCE_MAP == null) {
			INSTANCE_MAP = new HashMap<Integer, MonitorState>();

			List<MonitorState> monitorStates = this.getAll();
			for (MonitorState monitorState : monitorStates) {
				INSTANCE_MAP.put(monitorState.getStateId(), monitorState);
			}
		}

		return INSTANCE_MAP;
	}

	@Override
	public HibernateDAO<MonitorState, Long> getDAO() {
		return monitorStateDAO;
	}

	public MonitorState getInstanceByStateId(Integer stateId) {
		return findUniqueBy("stateId", stateId);
	}

	public boolean save(String stateName, Integer stateId, String stateColor) {
		MonitorState monitorState = new MonitorState();
		monitorState.setStateName(stateName);
		monitorState.setStateId(stateId);
		monitorState.setStateColor(stateColor);
		return save(monitorState);
	}
}
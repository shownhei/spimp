/*
 * Copyright (C) CCRISE.
 */
package cn.ccrise.spimp.util;

import java.util.List;
import java.util.Map;

import cn.ccrise.ikjp.core.security.entity.GroupEntity;
import cn.ccrise.ikjp.core.security.entity.ResourceEntity;
import cn.ccrise.spimp.monitor.entity.MonitorAlarm;
import cn.ccrise.spimp.monitor.entity.MonitorFiveMinutesData;
import cn.ccrise.spimp.monitor.entity.MonitorNode;
import cn.ccrise.spimp.monitor.entity.MonitorRealData;
import cn.ccrise.spimp.monitor.entity.MonitorRealDatas;
import cn.ccrise.spimp.monitor.entity.MonitorSensorType;
import cn.ccrise.spimp.monitor.entity.MonitorState;
import cn.ccrise.spimp.monitor.entity.MonitorValueChange;
import cn.ccrise.spimp.system.entity.Account;

/**
 * 返回数据过滤。
 * <p>
 * 在以下几种情况下需要过滤返回数据：
 * <li>默认情况下，Spring MVC 3 会将POJO的所有属性都序列化，当POJO有外键关联到其他POJO的时候，返回的数据量会很大</li>
 * <li>涉及到系统安全的数据返回时需要过滤，例如用户密码</li>
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
public abstract class ResponseDataFilter {
	/**
	 * 过滤账户信息。
	 * 
	 * @param account
	 *            需要过滤的账户
	 * @return 过滤后的账户
	 */
	public static Account filter(Account account) {
		account.setCredential(null);
		account.getGroupEntity().setGroupEntities(null);
		account.getRoleEntity().setResourceEntities(null);
		return account;
	}

	/**
	 * 过滤机构。
	 * 
	 * @param groupEntity
	 * @return 过滤后的机构
	 */
	public static GroupEntity filter(GroupEntity groupEntity) {
		groupEntity.setOrganizationEntity(null);
		groupEntity.setGroupEntities(null);
		return groupEntity;
	}

	/**
	 * 过滤账户列表。
	 * 
	 * @param accounts
	 */
	public static void filter(List<Account> accounts) {
		for (int i = 0; i < accounts.size(); i++) {
			filter(accounts.get(i));
		}
	}

	/**
	 * 过滤MonitorAlarm中的关联数据.
	 * 
	 * @param monitorAlarm
	 *            需要过滤的数据
	 * @param monitorStateCache
	 *            传感器状态缓存
	 * @param monitorSensorTypeCache
	 *            传感器类型缓存
	 * @return 过滤后的数据
	 */
	public static MonitorAlarm filter(MonitorAlarm monitorAlarm, Map<Integer, MonitorState> monitorStateCache,
			Map<Integer, MonitorSensorType> monitorSensorTypeCache) {
		MonitorSensorType monitorSensorType = monitorSensorTypeCache.get(monitorAlarm.getSensorTypeId());
		monitorAlarm.setSensorName(monitorSensorType == null ? "" : monitorSensorType.getSensorTypeName());

		MonitorState monitorState = monitorStateCache.get(monitorAlarm.getStateId());
		monitorAlarm.setStateName(monitorState == null ? "" : monitorState.getStateName());

		return monitorAlarm;
	}

	/**
	 * 过滤MonitorFiveMinutesData中的关联数据.
	 * 
	 * @param monitorFiveMinutesData
	 *            需要过滤的数据
	 * @param monitorSensorTypeCache
	 *            传感器类型缓存
	 * @return 过滤后的数据
	 */
	public static MonitorFiveMinutesData filter(MonitorFiveMinutesData monitorFiveMinutesData,
			Map<Integer, MonitorSensorType> monitorSensorTypeCache) {
		MonitorSensorType monitorSensorType = monitorSensorTypeCache.get(monitorFiveMinutesData.getSensorTypeId());
		monitorFiveMinutesData.setSensorName(monitorSensorType == null ? "" : monitorSensorType.getSensorTypeName());

		return monitorFiveMinutesData;
	}

	/**
	 * 过滤MonitorNode中的关联数据.
	 * 
	 * @param monitorNode
	 *            需要过滤的数据
	 * @param monitorStateCache
	 *            传感器状态缓存
	 * @param monitorSensorTypeCache
	 *            传感器类型缓存
	 * @return 过滤后的数据
	 */
	public static MonitorNode filter(MonitorNode monitorNode, Map<Integer, MonitorState> monitorStateCache,
			Map<Integer, MonitorSensorType> monitorSensorTypeCache) {
		MonitorSensorType monitorSensorType = monitorSensorTypeCache.get(monitorNode.getSensorTypeId());
		monitorNode.setSensorName(monitorSensorType == null ? "" : monitorSensorType.getSensorTypeName());

		MonitorState monitorState = monitorStateCache.get(monitorNode.getStateId());
		monitorNode.setStateName(monitorState == null ? "" : monitorState.getStateName());

		return monitorNode;
	}

	/**
	 * 过滤MonitorRealData中的关联数据.
	 * 
	 * @param monitorRealData
	 *            需要过滤的数据
	 * @param monitorSensorTypeCache
	 *            传感器类型缓存
	 * @return 过滤后的数据
	 */
	public static MonitorRealData filter(MonitorRealData monitorRealData,
			Map<Integer, MonitorSensorType> monitorSensorTypeCache, Map<Integer, MonitorState> monitorStateCache) {
		MonitorSensorType monitorSensorType = monitorSensorTypeCache.get(monitorRealData.getSensorTypeId());
		monitorRealData.setSensorName(monitorSensorType == null ? "" : monitorSensorType.getSensorTypeName());

		MonitorState monitorState = monitorStateCache.get(monitorRealData.getStateId());
		monitorRealData.setStateName(monitorState == null ? "" : monitorState.getStateName());

		return monitorRealData;
	}

	public static MonitorRealDatas filter(MonitorRealDatas monitorRealDatas,
			Map<Integer, MonitorSensorType> monitorSensorTypeCache, Map<Integer, MonitorState> monitorStateCache) {
		MonitorSensorType monitorSensorType = monitorSensorTypeCache.get(monitorRealDatas.getSensorTypeId());
		monitorRealDatas.setSensorName(monitorSensorType == null ? "" : monitorSensorType.getSensorTypeName());

		MonitorState monitorState = monitorStateCache.get(monitorRealDatas.getStateId());
		monitorRealDatas.setStateName(monitorState == null ? "" : monitorState.getStateName());

		return monitorRealDatas;
	}

	/**
	 * 过滤MonitorValueChange中的关联数据.
	 * 
	 * @param monitorValueChange
	 *            需要过滤的数据
	 * @param monitorSensorTypeCache
	 *            传感器类型缓存
	 * @return 过滤后的数据
	 */
	public static MonitorValueChange filter(MonitorValueChange monitorValueChange,
			Map<Integer, MonitorSensorType> monitorSensorTypeCache) {
		MonitorSensorType monitorSensorType = monitorSensorTypeCache.get(monitorValueChange.getSensorTypeId());
		monitorValueChange.setSensorName(monitorSensorType == null ? "" : monitorSensorType.getSensorTypeName());

		return monitorValueChange;
	}

	/**
	 * 过滤资源。
	 * 
	 * @param resourceEntity
	 * @return
	 */
	public static ResourceEntity filter(ResourceEntity resourceEntity) {
		resourceEntity.setResourceEntities(null);
		return resourceEntity;
	}

	/**
	 * 过滤存入Session中的账户信息。
	 * 
	 * @param loginAccount
	 *            需要过滤的账户
	 * @return 过滤后的账户
	 */
	public static Account filterLoginAccount(Account loginAccount) {
		loginAccount.getGroupEntity().setGroupEntities(null);
		return loginAccount;
	}
}

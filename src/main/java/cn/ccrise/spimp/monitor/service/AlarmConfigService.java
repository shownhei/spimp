/*
 * Copyright (C) 2012 CCRISE.
 */
package cn.ccrise.spimp.monitor.service;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ccrise.ikjp.core.access.HibernateDAO;
import cn.ccrise.ikjp.core.service.HibernateDataServiceImpl;
import cn.ccrise.spimp.monitor.access.AlarmConfigDAO;
import cn.ccrise.spimp.monitor.entity.AlarmConfig;

/**
 * AlarmConfig Service.
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Service
public class AlarmConfigService extends HibernateDataServiceImpl<AlarmConfig, Long> {
	@Autowired
	private AlarmConfigDAO systemConfigDAO;

	public boolean deleteByAccountId(Long accountId) {
		String hql = "delete AlarmConfig where accountId=:accountId";
		createQuery(hql).setLong("accountId", accountId).executeUpdate();
		return true;
	}

	public List<AlarmConfig> getByAccountId(Long accountId) {
		return findBy("accountId", accountId);
	}

	@Override
	public HibernateDAO<AlarmConfig, Long> getDAO() {
		return systemConfigDAO;
	}

	public boolean init(Long accountId) {
		boolean result = true;
		result &= initBasic(accountId);
		result &= initParameter(accountId);
		result &= initPrompt(accountId);
		result &= initSensor(accountId);
		result &= initAlarm(accountId);

		return result;
	}

	public boolean save(String name, String value, Long accountId, String type) {
		AlarmConfig alarmConfig = new AlarmConfig();
		alarmConfig.setName(name);
		alarmConfig.setValue(value);
		alarmConfig.setAccountId(accountId);
		alarmConfig.setType(type);
		return save(alarmConfig);
	}

	public boolean updateByAccountId(Long accountId, Map<String, String> data) {
		boolean result = true;
		String hql = "update AlarmConfig set value=:newValue where accountId=:accountId";
		createQuery(hql).setString("newValue", "false").setLong("accountId", accountId).executeUpdate();

		for (String key : data.keySet()) {
			AlarmConfig alarmConfig = findUnique(Restrictions.eq("accountId", accountId), Restrictions.eq("name", key));
			if (data.get(key).equals("on")) {
				alarmConfig.setValue("true");
			} else {
				alarmConfig.setValue(data.get(key));
			}
			result &= update(alarmConfig);

		}
		return result;
	}

	private boolean initAlarm(Long accountId) {
		boolean result = true;
		result &= save("井下超员报警", "false", accountId, "alarm");
		result &= save("区域超员报警", "false", accountId, "alarm");
		result &= save("禁区报警", "false", accountId, "alarm");
		result &= save("求救报警", "true", accountId, "alarm");
		result &= save("超时报警", "false", accountId, "alarm");
		result &= save("无领导带班报警", "false", accountId, "alarm");
		return result;
	}

	private boolean initBasic(Long accountId) {
		boolean result = true;
		result &= save("自动弹出", "false", accountId, "basic");
		result &= save("自动播放", "false", accountId, "basic");
		return result;
	}

	private boolean initParameter(Long accountId) {
		boolean result = true;
		result &= save("提示报警", "0", accountId, "parameter");
		result &= save("报警时长", "0", accountId, "parameter");
		result &= save("瓦斯最值", "0", accountId, "parameter");
		result &= save("CO最值", "0", accountId, "parameter");
		return result;
	}

	private boolean initPrompt(Long accountId) {
		boolean result = true;
		result &= save("超限", "true", accountId, "prompt");
		result &= save("超断电", "true", accountId, "prompt");
		result &= save("异常", "false", accountId, "prompt");
		result &= save("故障", "false", accountId, "prompt");
		result &= save("负漂", "false", accountId, "prompt");
		result &= save("溢出", "false", accountId, "prompt");
		result &= save("断线", "false", accountId, "prompt");
		result &= save("调校", "false", accountId, "prompt");
		result &= save("挂起", "false", accountId, "prompt");
		result &= save("未知", "false", accountId, "prompt");
		result &= save("报警", "true", accountId, "prompt");
		return result;
	}

	private boolean initSensor(Long accountId) {
		boolean result = true;
		result &= save("甲烷", "true", accountId, "sensor");
		result &= save("风速", "false", accountId, "sensor");
		result &= save("风压", "false", accountId, "sensor");
		result &= save("CO", "true", accountId, "sensor");
		result &= save("温度", "false", accountId, "sensor");
		result &= save("水位", "false", accountId, "sensor");
		result &= save("煤位", "false", accountId, "sensor");
		result &= save("速度", "false", accountId, "sensor");
		result &= save("流量", "false", accountId, "sensor");
		result &= save("位移", "false", accountId, "sensor");
		result &= save("压力", "false", accountId, "sensor");
		result &= save("功率", "false", accountId, "sensor");
		result &= save("电流", "false", accountId, "sensor");
		result &= save("电压", "false", accountId, "sensor");
		result &= save("频率", "false", accountId, "sensor");
		result &= save("电量", "false", accountId, "sensor");
		result &= save("瓦斯流量", "false", accountId, "sensor");
		result &= save("标况流量", "false", accountId, "sensor");
		result &= save("CO2", "false", accountId, "sensor");
		result &= save("氧气", "false", accountId, "sensor");
		result &= save("压差", "false", accountId, "sensor");
		result &= save("位置", "false", accountId, "sensor");
		result &= save("湿度", "false", accountId, "sensor");
		result &= save("离层", "false", accountId, "sensor");
		result &= save("推进度", "false", accountId, "sensor");
		result &= save("氢气", "false", accountId, "sensor");
		result &= save("H2S", "false", accountId, "sensor");
		result &= save("烟雾", "false", accountId, "sensor");
		result &= save("主扇开停", "false", accountId, "sensor");
		result &= save("局扇开停", "false", accountId, "sensor");
		result &= save("皮带开停", "false", accountId, "sensor");
		result &= save("水泵开停", "false", accountId, "sensor");
		result &= save("设备开停", "false", accountId, "sensor");
		result &= save("风筒", "false", accountId, "sensor");
		result &= save("风门开关", "false", accountId, "sensor");
		result &= save("馈电器", "false", accountId, "sensor");
		result &= save("断电器", "false", accountId, "sensor");
		result &= save("报警器", "false", accountId, "sensor");
		result &= save("瓦斯累计量", "false", accountId, "sensor");
		result &= save("标况累计量", "false", accountId, "sensor");
		result &= save("提升钩数", "false", accountId, "sensor");
		result &= save("累计流量", "false", accountId, "sensor");
		result &= save("动态", "false", accountId, "sensor");
		return result;
	}
}
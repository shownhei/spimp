/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.ercs.service;

import java.util.Iterator;
import java.util.Queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ccrise.ikjp.core.access.HibernateDAO;
import cn.ccrise.ikjp.core.service.HibernateDataServiceImpl;
import cn.ccrise.spimp.ercs.access.EmergencyPlanInstanceDAO;
import cn.ccrise.spimp.ercs.entity.EmergencyPlanInstance;
import cn.ccrise.spimp.util.ErcsDeferredResult;

import com.google.common.collect.Queues;

/**
 * EmergencyPlanInstance Service。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Service
public class EmergencyPlanInstanceService extends HibernateDataServiceImpl<EmergencyPlanInstance, Long> {
	@Autowired
	private EmergencyPlanInstanceDAO emergencyPlanInstanceDAO;
	/**
	 * 等待通知刷新的请求队列
	 */
	public final Queue<ErcsDeferredResult<Object>> waitNotifyQueue = Queues.newConcurrentLinkedQueue();

	public void wait(ErcsDeferredResult<Object> ercsDeferredResult) {
		waitNotifyQueue.add(ercsDeferredResult);
	}

	public void notifyRefresh() {
		Iterator<ErcsDeferredResult<Object>> it = waitNotifyQueue.iterator();
		ErcsDeferredResult<Object> deferredResult = null;
		while (it.hasNext()) {
			deferredResult = it.next();
			try {
				deferredResult.setResult("refresh");
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				it.remove();
			}
		}
	}

	@Override
	public HibernateDAO<EmergencyPlanInstance, Long> getDAO() {
		return emergencyPlanInstanceDAO;
	}
}
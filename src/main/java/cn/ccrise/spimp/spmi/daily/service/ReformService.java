/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.daily.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.BooleanUtils;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import cn.ccrise.ikjp.core.access.HibernateDAO;
import cn.ccrise.ikjp.core.security.entity.GroupEntity;
import cn.ccrise.ikjp.core.service.HibernateDataServiceImpl;
import cn.ccrise.spimp.spmi.daily.access.ReformDAO;
import cn.ccrise.spimp.spmi.daily.entity.Plan;
import cn.ccrise.spimp.spmi.daily.entity.Plan.PlanStatus;
import cn.ccrise.spimp.spmi.daily.entity.Reform;
import cn.ccrise.spimp.spmi.daily.entity.Reform.ReformStatus;
import cn.ccrise.spimp.system.service.GroupService;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;

/**
 * Reform Service。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Service
public class ReformService extends HibernateDataServiceImpl<Reform, Long> {
	@Autowired
	private ReformDAO reformDAO;
	@Autowired
	private PlanService planService;
	@Autowired
	private GroupService groupService;

	/**
	 * 删除整改通知单，如果deletePlan为true，则同时删除对应的工作安排。
	 * 
	 * @param reformId
	 * @param deletePlan
	 * @return
	 */
	@Transactional(rollbackFor = { Exception.class }, isolation = Isolation.READ_COMMITTED)
	public boolean delete(Long reformId, boolean deletePlan) {
		boolean result = true;

		/* 关联删除工作安排 */
		Reform reformInDb = get(reformId);
		if (BooleanUtils.isTrue(deletePlan)) {
			result &= planService.delete(reformInDb.getPlanId());
		}

		/* 删除整改通知单 */
		result &= delete(reformInDb);

		return result;
	}

	@Override
	public HibernateDAO<Reform, Long> getDAO() {
		return reformDAO;
	}

	/**
	 * 保存整改通知单，如果createPlan为true，则同时生成工作安排并保存。
	 * 
	 * @param reform
	 * @param createPlan
	 * @return
	 */
	@Transactional(rollbackFor = { Exception.class }, isolation = Isolation.READ_COMMITTED)
	public boolean save(Reform reform, boolean createPlan) {
		boolean result = true;

		/* 生成整改通知单编号 */
		String numberString = generateNumber(reform.getSendGroupId(), reform.getCheckDate());

		/* 关联保存工作安排 */
		if (BooleanUtils.isTrue(createPlan)) {
			Plan plan = new Plan();
			plan.setTitle("整改通知单-" + numberString);
			plan.setContent(reform.getIssue());
			plan.setCategory("整改安排");
			plan.setCutoffDate(reform.getDeadlineDate());
			plan.setStatus(PlanStatus.未指派);
			plan.setCreaterId(reform.getPrincipalId());
			result &= planService.save(plan);

			reform.setPlanId(plan.getId());
		}

		/* 保存整改通知单 */
		reform.setNumber(numberString);
		reform.setStatus(ReformStatus.已下发);
		result &= save(reform);

		return result;
	}

	/**
	 * 编辑整改通知单，如果editPlan为true，则同时修改工作安排中的相关数据。
	 * 
	 * @param reform
	 * @param editPlan
	 * @return
	 */
	@Transactional(rollbackFor = { Exception.class }, isolation = Isolation.READ_COMMITTED)
	public boolean update(Reform reform, boolean editPlan) {
		boolean result = true;

		/* 重新生成整改通知单编号 */
		String numberString = generateNumber(reform.getSendGroupId(), reform.getCheckDate());

		/* 关联更新工作安排 */
		Reform reformInDb = get(reform.getId());
		if (BooleanUtils.isTrue(editPlan)) {
			Plan planInDb = planService.get(reformInDb.getPlanId());
			planInDb.setTitle("整改通知单-" + numberString);
			planInDb.setContent(reform.getIssue());
			planInDb.setCutoffDate(reform.getDeadlineDate());
			planInDb.setCreaterId(reform.getPrincipalId());
			result &= planService.update(planInDb);
		}

		/* 更新整改通知单 */
		reformInDb.setCheckDate(reform.getCheckDate());
		reformInDb.setChecker(reform.getChecker());
		reformInDb.setDeadlineDate(reform.getDeadlineDate());
		reformInDb.setIssue(reform.getIssue());
		reformInDb.setMeasure(reform.getMeasure());
		reformInDb.setNumber(numberString);
		reformInDb.setPrincipalId(reform.getPrincipalId());
		reformInDb.setSendGroupId(reform.getSendGroupId());
		reformInDb.setStandard(reform.getStandard());
		reformInDb.setTestGroupId(reform.getTestGroupId());
		result &= update(reformInDb);

		return result;
	}

	/**
	 * <p>
	 * 生成整改通知单编号。
	 * <p>
	 * 规则：机构编号+检查日期+序号，例如调度室2013-12-31号检查出的第5个整改，则通知单的编号为：DDS-20131231-005。
	 * 
	 * @param groupId
	 *            下发机构id
	 * @param checkDate
	 *            检查日期
	 * @return 整改通知单编号
	 */
	private String generateNumber(Long groupId, Date checkDate) {
		/* 获取机构编号 */
		String groupNumber = "UNKNOWN"; // 默认机构编号，基本不可能出现
		GroupEntity sendGroup = groupService.get(groupId);
		if (sendGroup != null) {
			groupNumber = sendGroup.getNumber();
		}

		/* 检查日期格式化 */
		String checkDateString = new SimpleDateFormat("yyyyMMdd").format(checkDate);

		/* 获取可用序号 */
		String sn = "001"; // 默认序号，从001开始
		List<Reform> reforms = find(Order.desc("id"), Restrictions.eq("sendGroupId", groupId),
				Restrictions.eq("checkDate", checkDate));
		if (reforms.size() > 0) {
			int currentSn = Integer.parseInt(reforms.get(0).getNumber().split("-")[2]);
			String newSn = (currentSn + 1) + "";
			sn = Strings.padStart(newSn, 3, '0');
		}

		return Joiner.on("-").join(groupNumber, checkDateString, sn);
	}
}
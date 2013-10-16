/*
 * Copyright (C) 2012 CCRISE.
 */
package cn.ccrise.spimp.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ccrise.ikjp.core.access.HibernateDAO;
import cn.ccrise.ikjp.core.service.HibernateDataServiceImpl;
import cn.ccrise.spimp.access.SMSDAO;
import cn.ccrise.spimp.entity.SMS;
import cn.ccrise.spimp.util.SMSHelper;

/**
 * SMS Service。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Service
public class SMSService extends HibernateDataServiceImpl<SMS, Long> {
	private static final String CONTENT_SUFFIX = " 税费办";
	private static final int SMS_MAX_LEN = 70;

	@Autowired
	private SMSDAO sMSDAO;

	@Override
	public HibernateDAO<SMS, Long> getDAO() {
		return sMSDAO;
	}

	/**
	 * 短信发送(通用)
	 * 
	 * @param mobileNumber
	 *            接收人手机号
	 * @param content
	 *            短信内容
	 * @return
	 */
	public boolean sendSimpleSMS(final String mobileNumber, String content) {
		if (StringUtils.isBlank(mobileNumber) || StringUtils.isBlank(content)) {
			return false;
		}

		boolean sendSuccess = false; // 短信发送状态

		SMSHelper msgCenter = SMSHelper.getInstance();
		if (msgCenter != null) {
			content = content + CONTENT_SUFFIX;

			// 2013-3-28 更新：如果短信长度超过71个字符，只发送前71个字符
			if (content.length() > SMS_MAX_LEN) {
				content = content.substring(0, SMS_MAX_LEN);
			}

			sendSuccess = msgCenter.sendMessage(mobileNumber, content);
		}

		return sendSuccess;
	}

	/**
	 * 短信发送接口(业务相关)
	 * 
	 * @param mobileNumber
	 *            接收人手机号
	 * @param receiveName
	 *            接收人姓名
	 * @param mineName
	 *            接收矿企名称
	 * @param sendName
	 *            发送人
	 * @param content
	 *            短信内容
	 * @return
	 */
	public boolean sendSMS(final String mobileNumber, final String receiveName, final String mineName,
			final String sendName, final String content) {
		if (StringUtils.isBlank(mobileNumber) || StringUtils.isBlank(content)) {
			return false;
		}

		boolean sendSuccess = false; // 短信发送状态

		// 历史信息处理
		List<SMS> smses = find(Restrictions.eq("mobileNumber", mobileNumber), Restrictions.eq("status", "未发送"),
				Restrictions.lt("count", 1)); // 如果该号码之前有发送失败的信息，重新发送
		for (SMS sms : smses) {
			sendSuccess = false; // reset

			// 可能在发送时出错，先更新发送次数
			sms.setCount(sms.getCount() + 1);
			update(sms);

			sendSuccess = sendSimpleSMS(mobileNumber, content);
			if (sendSuccess) {
				sms.setStatus("已发送");
				sms.setSendTime(new Timestamp(new Date().getTime()));
				sMSDAO.update(sms);
			}
		}

		// 当前信息处理
		sendSuccess = false; // reset
		SMS sms = new SMS();
		sms.setMobileNumber(mobileNumber);
		sms.setReceiveName(receiveName);
		sms.setMineName(mineName);
		sms.setSendName(sendName);
		sms.setContent(content);
		sms.setReadyTime(new Timestamp(new Date().getTime()));
		sms.setCount(1);
		sms.setStatus("未发送");
		save(sms);

		sendSuccess = sendSimpleSMS(mobileNumber, content);

		if (sendSuccess) {
			sms.setStatus("已发送");
			sms.setSendTime(new Timestamp(new Date().getTime()));
			update(sms);
		}

		return sendSuccess;
	}

	/**
	 * 短信发送接口(业务相关)
	 * 
	 * @param mobileNumber
	 *            接收人手机号
	 * @param receiveName
	 *            接收人姓名
	 * @param mineName
	 *            接收矿企名称
	 * @param sendName
	 *            发送人
	 * @param content
	 *            短信内容
	 * @return
	 */
	public void sendSMSByJob(final String mobileNumber, final String receiveName, final String mineName,
			final String sendName, final String content) {
		if (StringUtils.isBlank(mobileNumber) || StringUtils.isBlank(content)) {
			return;
		}

		// 直接将待发送的信息存入短信表，并将状态设置成未发送，最后由定时任务来发送短信
		SMS sms = new SMS();
		sms.setMobileNumber(mobileNumber);
		sms.setReceiveName(receiveName);
		sms.setMineName(mineName);
		sms.setSendName(sendName);
		sms.setContent(content);
		sms.setReadyTime(new Timestamp(new Date().getTime()));
		sms.setStatus("未发送");
		sms.setCount(0);
		save(sms);
	}
}
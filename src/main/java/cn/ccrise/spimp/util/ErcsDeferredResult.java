package cn.ccrise.spimp.util;

import java.sql.Timestamp;

import org.springframework.web.context.request.async.DeferredResult;

public class ErcsDeferredResult<T> extends DeferredResult<T> {

	private String sessionId;

	/**
	 * 记录时间，判断是否超时的时候使用
	 */
	private Timestamp recordTime;

	public Long getTimePassed() {
		Long temp = System.currentTimeMillis() - recordTime.getTime();
		System.out.println("等待时间:" + temp + "毫秒");
		return temp;
	}

	/**
	 * 记录时间，判断是否超时的时候使用
	 */
	public Timestamp getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(Timestamp recordTime) {
		this.recordTime = recordTime;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

}

package cn.ccrise.spimp.util;

import java.sql.Timestamp;

import org.springframework.web.context.request.async.DeferredResult;

public class ErcsDeferredResult<T> extends DeferredResult<T> {

	public static final Long TIME_LIMIT = 1000 * 60 * 4L;
	private String sessionId;

	/**
	 * 记录时间，判断是否超时的时候使用
	 */
	private Timestamp recordTime;

	/**
	 * 记录时间，判断是否超时的时候使用
	 */
	public Timestamp getRecordTime() {
		return recordTime;
	}

	public String getSessionId() {
		return sessionId;
	}

	/**
	 * 等待已耗时,毫秒
	 * 
	 * @return 耗时 毫秒数
	 */
	public Long getTimePassed() {
		Long temp = System.currentTimeMillis() - recordTime.getTime();
		System.out.println("等待时间:" + temp + "毫秒");
		return temp;
	}

	public void setRecordTime(Timestamp recordTime) {
		this.recordTime = recordTime;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	@Override
	public String toString() {
		return this.getSessionId() + "|" + this.getRecordTime() + "|" + this.getTimePassed();
	}

}

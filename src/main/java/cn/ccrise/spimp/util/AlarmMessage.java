package cn.ccrise.spimp.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 报警消息体
 * 
 * @author zpf
 * 
 */
public class AlarmMessage {

	private String sessionId;

	/**
	 * 新报警
	 */
	private List<Long> alarmList = new ArrayList<>();
	/**
	 * 附带消息
	 */
	private String messgage = null;

	public List<Long> getAlarmList() {
		return alarmList;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public void setAlarmList(List<Long> newAlarmList) {
		this.alarmList = newAlarmList;
	}

	public String getMessgage() {
		return messgage;
	}

	public void setMessgage(String messgage) {
		this.messgage = messgage;
	}

}

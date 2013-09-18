package cn.ccrise.spimp.util;

import java.util.HashMap;
import java.util.List;

public class CompletHandler implements Runnable {

	private String sessionId;

	private HashMap<String, List<Long>> record;

	public CompletHandler(String sessionId, HashMap<String, List<Long>> record) {
		super();
		this.sessionId = sessionId;
		this.record = record;
	}

	public HashMap<String, List<Long>> getRecord() {
		return record;
	}

	public String getSessionId() {
		return sessionId;
	}

	@Override
	public void run() {
		record.get(getSessionId());
	}

	public void setRecord(HashMap<String, List<Long>> record) {
		this.record = record;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

}

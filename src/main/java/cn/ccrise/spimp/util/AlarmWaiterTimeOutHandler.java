package cn.ccrise.spimp.util;

import java.util.Iterator;
import java.util.Vector;

/**
 * 
 * @author zpf
 * 
 */
public class AlarmWaiterTimeOutHandler implements Runnable {

	private String sessionId;
	private Vector<ErcsDeferredResult<AlarmMessage>> waitAlarmQueue;

	public AlarmWaiterTimeOutHandler(String sessionId, Vector<ErcsDeferredResult<AlarmMessage>> waitAlarmQueue) {
		super();
		this.sessionId = sessionId;
		this.waitAlarmQueue = waitAlarmQueue;
	}

	public String getSessionId() {
		return sessionId;
	}

	public Vector<ErcsDeferredResult<AlarmMessage>> getWaitAlarmQueue() {
		return waitAlarmQueue;
	}

	@Override
	public void run() {
		synchronized (waitAlarmQueue) {
			Iterator<ErcsDeferredResult<AlarmMessage>> it = waitAlarmQueue.iterator();
			ErcsDeferredResult<AlarmMessage> waiter = null;
			while (it.hasNext()) {
				waiter = it.next();
				if (waiter.getSessionId().equals(getSessionId())) {
					it.remove();
				}
			}
		}
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public void setWaitAlarmQueue(Vector<ErcsDeferredResult<AlarmMessage>> waitAlarmQueue) {
		this.waitAlarmQueue = waitAlarmQueue;
	}

}

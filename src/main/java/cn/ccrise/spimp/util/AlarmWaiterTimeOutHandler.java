package cn.ccrise.spimp.util;

import java.util.Iterator;
import java.util.Vector;

/**
 * 
 * @author zpf
 * 
 */
public class AlarmWaiterTimeOutHandler implements Runnable {

	private String requestKey;
	private Vector<ErcsDeferredResult<AlarmMessage>> waitAlarmQueue;

	public AlarmWaiterTimeOutHandler(String requestKey, Vector<ErcsDeferredResult<AlarmMessage>> waitAlarmQueue) {
		super();
		this.requestKey = requestKey;
		this.waitAlarmQueue = waitAlarmQueue;
	}

	public String getRequestKey() {
		return requestKey;
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
				if (waiter.getRequestKey().equals(getRequestKey())) {
					it.remove();
				}
			}
		}
	}

	public void setRequestKey(String requestKey) {
		this.requestKey = requestKey;
	}

	public void setWaitAlarmQueue(Vector<ErcsDeferredResult<AlarmMessage>> waitAlarmQueue) {
		this.waitAlarmQueue = waitAlarmQueue;
	}

}

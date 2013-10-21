/*
 * Copyright (C) 2012 CCRISE.
 */
package cn.ccrise.spimp.util;

import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.TooManyListenersException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 串口操作及消息发送工具类。
 */
public class SMSHelper implements SerialPortEventListener {
	protected static Logger log = LoggerFactory.getLogger(SMSHelper.class);
	private static SMSHelper _COMM_UTIL;
	private InputStream _INPUT_STREAM; // 从串口来的输入流
	private OutputStream _OUTPUT_STREAM; // 向串口输出的流
	private SerialPort _SERIAL_PORT; // 串口的引用
	private CommPortIdentifier _PORT_ID; // 端口ID

	private static StringBuilder _MESSAGE; // 设备产生的信息
	private static String _DEFAULT_PORT = Constant.SMS_COMM;

	private static final long _TIME_OUT = 5000; // 串口检测失效时长(ms)
	private static final long _TIME_DELAY = 50; // 串口检测相隔时长(ms)
	private static final long _COMM_DELAY = 300; // 串口数据监听等待时长(ms)

	private static boolean _INIT_SUCCESS = false; // 初始化状态

	/**
	 * 获取实例
	 * 
	 * @return
	 */
	public static synchronized SMSHelper getInstance() {
		return getInstance(_DEFAULT_PORT);
	}

	/**
	 * 获取实例(带串口号)
	 * 
	 * @param port
	 *            串口号
	 * @return
	 */
	public static synchronized SMSHelper getInstance(String port) {
		if (_COMM_UTIL == null) {
			_COMM_UTIL = new SMSHelper();

			// 获取主机串口列表
			Map<String, CommPortIdentifier> ports = _COMM_UTIL.getPortList();

			// 判断串口
			if (StringUtils.isBlank(port)) {
				port = _DEFAULT_PORT;
			}

			CommPortIdentifier commPortIdentifier = ports.get(port);
			if (commPortIdentifier == null) {
				log.error("短信猫:指定的串口不存在!");
				_INIT_SUCCESS = false;
				return null;
			}

			// 根据指定的串口初始化设备
			if (!_COMM_UTIL.initEquipment(commPortIdentifier, port)) {
				_COMM_UTIL = null;
			}
		}

		return _COMM_UTIL;
	}

	private boolean commResponsed = false; // 串口是否进行了数据响应

	private String smsCenterCode = ""; // 短信中心号码

	private SMSHelper() {

	}

	/**
	 * 关闭端口
	 */
	public void closePort() {
		if (_INPUT_STREAM != null) {
			try {
				_INPUT_STREAM.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (_OUTPUT_STREAM != null) {
			try {
				_OUTPUT_STREAM.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (_SERIAL_PORT != null) {
			_SERIAL_PORT.close();
			// log.info("短信猫:设备串行接口成功关闭.");
		}
	}

	/**
	 * 取得短信中心号码
	 * 
	 * @return
	 */
	public String gegMsgCenterCode() {
		return smsCenterCode;
	}

	/**
	 * 主机串口列表
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, CommPortIdentifier> getPortList() {
		Enumeration<CommPortIdentifier> portList = CommPortIdentifier.getPortIdentifiers();
		Map<String, CommPortIdentifier> ports = new HashMap<String, CommPortIdentifier>();
		while (portList.hasMoreElements()) {
			CommPortIdentifier cpi = portList.nextElement();
			if (cpi.getPortType() == CommPortIdentifier.PORT_SERIAL) {// 判断如果端口类型是串口
				ports.put(cpi.getName(), cpi);
			}
		}
		return ports;
	}

	/**
	 * 初始化设备
	 * 
	 * @param portList
	 * @param name
	 * @return
	 */
	public synchronized boolean initEquipment(CommPortIdentifier commPortIdentifier, String portName) {
		// log.info("短信猫:串行接口开始初始化(on port:" + portName + ")...");

		// 先将打开的串口关闭
		closePort();

		_PORT_ID = commPortIdentifier;

		try {
			_SERIAL_PORT = (SerialPort) _PORT_ID.open("My" + portName, 2000);

			_INPUT_STREAM = _SERIAL_PORT.getInputStream();
			_OUTPUT_STREAM = _SERIAL_PORT.getOutputStream();

			_SERIAL_PORT.addEventListener(this); // 给当前串口添加一个监听器
			_SERIAL_PORT.notifyOnDataAvailable(true); // 当有数据时通知

			_SERIAL_PORT.setSerialPortParams(2400, SerialPort.DATABITS_8, // 设置串口读写参数
					SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);

			String retv = sendATInstruction("AT+CMGF=0\r"); // 设为PDU方式
			if (!checkCommResponse(retv)) {
				initEquipmentFaild("设置PDU错误或无响应.AT码:AT+CMGF=0");
				return false;
			}
			retv = "";

			retv = sendATInstruction("AT+CNMI=1,1,2,0,1\r"); // 2,1,0,0,0\
																// 可以发送短息
			if (!checkCommResponse(retv)) {
				initEquipmentFaild("设置短信方式错误或无响应.AT码:AT+CNMI=1,1,2,0,1");
				return false;
			}
			retv = "";

			retv = sendATInstruction("AT+COPS?\r"); // 获取运营商
			if (!checkCommResponse(retv)) {
				initEquipmentFaild("获取运营商错误或无响应.AT码:AT+COPS?.错误原因：未SIM插卡");
				return false;
			} else {
				// log.info("短息猫:运营商-" + getCops(retv));
			}
			retv = "";

			retv = sendATInstruction("AT+CSCA?\r"); // 获取短信中心号码
			if (!checkCommResponse(retv)) {
				initEquipmentFaild("获取短信中心号码错误或无响应.AT码:AT+CSCA?");
				return false;
			}

			smsCenterCode = getCenterCode(retv);

			if (!smsCenterCode.contains("+")) {
				smsCenterCode = "+" + smsCenterCode;
			}

			// log.info("短信猫:短信中心号码" + smsCenterCode);
		} catch (PortInUseException e) {
			initEquipmentFaild("端口被占用.");
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			initEquipmentFaild(null);
			e.printStackTrace();
			return false;
		} catch (TooManyListenersException e) {
			initEquipmentFaild(null);
			e.printStackTrace();
			return false;
		} catch (UnsupportedCommOperationException e) {
			initEquipmentFaild(null);
			e.printStackTrace();
			return false;
		}

		// log.info("短信猫:串行接口初始化成功.端口：" + portName);
		_INIT_SUCCESS = true;
		return true;
	}

	/**
	 * 验证是否是正确的手机号码
	 * 
	 * @param phoneNum
	 * @return
	 */
	public boolean isMobileNum(String phoneNum) {
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Matcher m = p.matcher(phoneNum);
		return m.matches();
	}

	/**
	 * 消息发送核心方法
	 * 
	 * @param phoneNumber
	 *            发送对象
	 * @param message
	 *            发送的消息内容
	 * @return
	 */
	public synchronized boolean sendMessage(String phoneNumber, String message) {
		log.info("短信猫:开始向手机号(" + phoneNumber + ")发送信息.");
		if (!_INIT_SUCCESS) {
			log.info("短信猫:由于设备初始化失败,终止本次发送.");
			return false;
		}

		// 验证手机号码
		if (!isMobileNum(phoneNumber)) {
			log.info("短信猫:手机号(" + phoneNumber + ")为非法号码,短信处理中心禁止发送该信息.");
			return false;
		}

		phoneNumber = "+86" + phoneNumber; // 设置号码格式为世界标准

		boolean status = false; // 短信发送状态

		PDU pdu = null;
		String retv = "";

		try {
			pdu = PDUHelper.genPDUSms(smsCenterCode, phoneNumber, message); // 将短信转换成符合通讯规范的PDU格式
			retv = sendATInstruction("AT+CMGS=0" + pdu.getPduLength() + "\r"); // 发送短信

			if (StringUtils.isNotBlank(retv) && retv.contains(">")) {

				String rets = sendATInstruction(pdu.getPudContents() + (char) 26);

				if (!rets.contains("OK") || !rets.contains("ERROR")) {
					for (int i = 0; i < 5; i++) {
						_MESSAGE = new StringBuilder(); // 清空消息栈
						commResponsed = false; // 重置响应状态
						if (commHasResponsed()) {
							if (_MESSAGE.toString().contains("OK")) {
								status = true;
								break;
							} else if (_MESSAGE.toString().contains("ERROR")) {
								break;
							}
						}
					}
				} else if (_MESSAGE.toString().contains("OK")) {
					status = true;
				}
			} else {
				log.info("短信猫:向手机号(" + phoneNumber + ")发送信息超时.");
			}
		} catch (Exception exp) {
			log.error("短信猫:向手机号(" + phoneNumber + ")发送信息失败.\n 失败原因：" + exp.toString());
		}

		if (status) {
			log.info("短信猫:向手机号(" + phoneNumber + ")发送信息成功.");
		} else {
			log.info("短信猫:向手机号(" + phoneNumber + ")发送信息失败.");
		}

		return status;
	}

	/**
	 * 监听设备响应流
	 */
	@Override
	public void serialEvent(SerialPortEvent event) {
		switch (event.getEventType()) {
		case SerialPortEvent.BI:
		case SerialPortEvent.OE:
		case SerialPortEvent.FE:
		case SerialPortEvent.PE:
		case SerialPortEvent.CD:
		case SerialPortEvent.CTS:
		case SerialPortEvent.DSR:
		case SerialPortEvent.RI:
		case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
			break;
		case SerialPortEvent.DATA_AVAILABLE:// 当有可用数据时读取数据,并且给串口返回数据
			try {
				Thread.sleep(_COMM_DELAY);

				int c = _INPUT_STREAM.read();
				while (c != -1) {
					_MESSAGE.append((char) c);
					c = _INPUT_STREAM.read();
				}
			} catch (Exception e) {
			}

			commResponsed = true;

			break;
		}
	}

	/**
	 * 判断串口返回的数据是否正确
	 * 
	 * @param commResponse
	 * @return
	 */
	private boolean checkCommResponse(String commResponse) {
		if (StringUtils.isBlank(commResponse)) {
			return false;
		} else if (!StringUtils.contains(commResponse, "OK")) {
			return false;
		}

		return true;
	}

	/**
	 * 判断串口是否返回数据
	 * 
	 * @return
	 */
	private boolean commHasResponsed() {
		long tempTime = 0;
		while (tempTime < _TIME_OUT) {
			if (!commResponsed) {
				try {
					Thread.sleep(_TIME_DELAY);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				tempTime = tempTime + _TIME_DELAY;
			} else {
				return commResponsed;
			}
		}
		return commResponsed;
	}

	/**
	 * 将串口响应码转换为短信中心号码
	 * 
	 * @param commResponse
	 * @return
	 */
	private String getCenterCode(String commResponse) {
		commResponse = commResponse.replaceAll("\n", "").replaceAll("\r", "");
		if (commResponse.contains("+CSCA:")) {
			return commResponse.substring(commResponse.lastIndexOf("+"), commResponse.indexOf(",") - 1);
		} else {
			return "";
		}
	}

	/**
	 * 获取运营商
	 * 
	 * @param commResponse
	 * @return
	 */
	@SuppressWarnings("unused")
	private String getCops(String commResponse) {
		commResponse = commResponse.replaceAll("\n", "").replaceAll("\r", "");
		if (commResponse.contains("CUGSM")) {
			return "中国联通";
		} else if (commResponse.contains("CHINA MOBILE")) {
			return "中国移动";
		} else {
			return "中国电信";
		}
	}

	/**
	 * 初始化设备通用错误信息
	 * 
	 * @param errMsg
	 */
	private void initEquipmentFaild(String errMsg) {
		if (StringUtils.isNotBlank(errMsg)) {
			log.error("短信猫:" + errMsg);
		}
		log.error("短信猫:串行接口初始化失败.");
		_INIT_SUCCESS = false;
		closePort();
	}

	/**
	 * 向设备发送AT指令
	 * 
	 * @param content
	 *            指令内容
	 * @return
	 */
	private String sendATInstruction(String content) {
		_MESSAGE = new StringBuilder(); // 清空消息栈
		commResponsed = false; // 重置响应状态

		try {
			_OUTPUT_STREAM.write(content.getBytes());

			if (!commHasResponsed()) {
				_MESSAGE = new StringBuilder();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return _MESSAGE.toString();
	}
}

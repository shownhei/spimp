/*
 * Copyright (C) 2012 CCRISE.
 */
package cn.ccrise.spimp.util;

/**
 * PDU信息处理工具类。
 * 
 * @author David Niu(david.kosoon@gmail.com)
 */
public abstract class PDUHelper {
	/**
	 * 字符串转换(奇偶位互换)
	 * 
	 * @param numberString
	 * @return
	 */
	public static String encodeNumber(String numberString) {
		if (numberString.length() % 2 != 0) {
			numberString += "F"; // 补上F;
		}

		StringBuilder sb = new StringBuilder();

		char[] tempArray = numberString.toCharArray();

		for (int i = 0; i < tempArray.length; i += 2) {// 奇偶互换
			sb.append(tempArray[i + 1]);
			sb.append(tempArray[i]);
		}

		return sb.toString();
	}

	/**
	 * 生成短信PDU信息
	 * 
	 * @param centerNumber
	 * @param userNumber
	 * @param userMsg
	 * @param pduLength
	 * @return
	 */
	public static PDU genPDUSms(String centerNumber, String phoneNumber, String userMsg) {
		PDU pdu = new PDU();

		StringBuilder sb = new StringBuilder();
		if (phoneNumber.length() != 14 || userMsg.length() == 0) {
			return null; // +86XXXXXXXXXXX
		}

		// 短信中心号码处理
		String temp = centerNumber.replace("+", "19");
		if (temp.length() % 2 != 0) {
			temp += "F"; // 补上F为偶数
		}

		String codecn = encodeNumber(temp);

		int cnlength = (codecn.length() / 2);

		sb.append(Integer.toHexString(cnlength).length() % 2 == 0 ? Integer.toHexString(cnlength) : "0"
				+ Integer.toHexString(cnlength)); // 长度08

		sb.append(codecn); // 短信中心号码

		sb.append("1100");

		// 发送手机号(11位)处理
		sb.append("0D"); // 13

		temp = phoneNumber.replace("+", "19");

		if (temp.length() % 2 != 0) {
			temp += "F"; // 补上F为偶数
		}

		String codeun = encodeNumber(temp);

		sb.append(codeun); // 编码后发送手机号

		sb.append("0008A7");

		// 发送信息处理
		String codeum = toUnicode(userMsg);

		sb.append(Integer.toHexString(codeum.length() / 2).length() % 2 == 0 ? Integer.toHexString(codeum.length() / 2)
				: "0" + Integer.toHexString(codeum.length() / 2));

		sb.append(codeum);

		String contents = sb.toString();

		int pduLength = contents.length() / 2 - cnlength - 1;

		pdu.setPudContents(contents);
		pdu.setPduLength(pduLength);
		return pdu;
	}

	/**
	 * 将字符串转换为UNICODE编码
	 * 
	 * @param input
	 * @return
	 */
	public static String toUnicode(String input) {
		StringBuilder retv = new StringBuilder();
		for (char c : input.toCharArray()) {
			String hexB = Integer.toHexString(c);
			if (hexB.length() <= 2) {
				hexB = "00" + hexB;
			}
			retv.append(hexB);
		}
		return retv.toString().toUpperCase();
	}
}

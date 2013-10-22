/*
 * Copyright (C) 2012 CCRISE.
 */
package cn.ccrise.spimp.util;

import cn.ccrise.ikjp.core.util.PropertiesUtils;

/**
 * 系统常量。
 */
public abstract class Constant {
	// sms参数
	public static final String SMS_COMM = PropertiesUtils.getString("sms.comm");
	public static final int MOBILE_NUMBER_LENGTH = 11;
}

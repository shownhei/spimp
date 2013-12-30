/*
 * Copyright (C) CCRISE.
 */
package cn.ccrise.spimp.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
public class NumberUtils {
	@Test
	public void test() {
		Assert.assertEquals(0, Integer.parseInt("000"));
		Assert.assertEquals(5, Integer.parseInt("005"));
		Assert.assertEquals(50, Integer.parseInt("050"));
		Assert.assertEquals(500, Integer.parseInt("500"));
		Assert.assertEquals(505, Integer.parseInt("505"));
	}
}

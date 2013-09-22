/*
 * Copyright (C) CCRISE.
 */
package cn.ccrise.spimp.entity;

import org.junit.Test;

import cn.ccrise.ikjp.core.util.CodeGeneratorUtils;

/**
 * 代码自动生成测试。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
public class CodeGenerator {
	private final String packageName = "cn.ccrise.spimp";

	private final String uriPrefix = "scheme";
	private final String entityName = "Scheme";

	@Test
	public void generateController() {
		CodeGeneratorUtils.generateController(entityName, packageName, uriPrefix);
	}

	@Test
	public void generateDAO() {
		CodeGeneratorUtils.generateDAO(entityName, packageName, uriPrefix);
	}

	@Test
	public void generateEntity() {
		CodeGeneratorUtils.generateEntity(entityName, packageName, uriPrefix);
	}

	/**
	 * 生成后端代码，默认情况下使用此方法。
	 */
	@Test
	public void generateServer() {
		CodeGeneratorUtils.generateServer(entityName, packageName, uriPrefix);
	}

	@Test
	public void generateService() {
		CodeGeneratorUtils.generateService(entityName, packageName, uriPrefix);
	}
}

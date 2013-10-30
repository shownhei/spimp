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
	protected final String packageName = "cn.ccrise.spimp.spmi.instruction";

	protected final String uriPrefix = "spmi/instruction";
	protected final String entityName = "Focus";

	/**
	 * 页面名称（用于生成前端代码）
	 */
	protected static final String PAGE_TITLE = "领导指示 - 安全生产综合管理平台";

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

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
	// protected final String packageName = "cn.ccrise.spimp.spmi.schedule";
	// protected final String packageName = "cn.ccrise.spimp.spmi.quality";
	protected final String packageName = "cn.ccrise.spimp.spmi.daily";
	// protected final String packageName = "cn.ccrise.spimp.system";
	// protected final String packageName = "cn.ccrise.spimp.electr";

	// protected final String uriPrefix = "spmi/schedule";
	// protected final String uriPrefix = "spmi/quality";
	protected final String uriPrefix = "spmi/daily";
	// protected final String uriPrefix = "system";
	// protected final String uriPrefix = "electr/equipment";

	// protected final String entityName = "Output";
	// protected final String entityName = "TransportationGrade"; // 运输专业
	// protected final String entityName = "ElectroGrade"; // 机电专业
	// protected final String entityName = "TunnellingGrade"; // 掘进专业
	// protected final String entityName = "MiningGrade"; // 采煤专业
	// protected final String entityName = "Reform"; // 隐患整改
	protected final String entityName = "Plan"; // 工作安排

	// protected final String entityName = "Staff";
	// protected final String entityName = "Alteration";
	// protected final String entityName = "Equipment"; // 设备
	// protected final String entityName = "Accessory"; // 配件

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

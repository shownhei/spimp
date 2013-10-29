/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.instruction.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ccrise.ikjp.core.access.HibernateDAO;
import cn.ccrise.ikjp.core.service.HibernateDataServiceImpl;
import cn.ccrise.spimp.spmi.instruction.access.InstructionDAO;
import cn.ccrise.spimp.spmi.instruction.entity.Instruction;

/**
 * Instruction Service。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Service
public class InstructionService extends HibernateDataServiceImpl<Instruction, Long> {
	@Autowired
	private InstructionDAO instructionDAO;

	@Override
	public HibernateDAO<Instruction, Long> getDAO() {
		return instructionDAO;
	}
}
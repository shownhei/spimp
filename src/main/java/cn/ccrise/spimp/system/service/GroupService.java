/*
 * Copyright (C) CCRISE.
 */
package cn.ccrise.spimp.system.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cn.ccrise.ikjp.core.security.entity.GroupEntity;
import cn.ccrise.ikjp.core.security.service.impl.GroupEntityServiceImpl;

import com.google.common.collect.Lists;

/**
 * 扩展机构服务类。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Service
public class GroupService extends GroupEntityServiceImpl {
	public Object[] getChildrenMines(String number) {
		GroupEntity root = findUniqueBy("number", number);

		List<String> numbers = Lists.newArrayList();
		numbers.add(root.getNumber());

		for (GroupEntity groupEntity : getChildrenByQueryLabel(root, new String[] { "mine" })) {
			numbers.add(groupEntity.getNumber());
		}

		return numbers.toArray();
	}

	public boolean save(final String name, final String number, final String queryLabel, final String category,
			final GroupEntity parentGroupEntity) {
		GroupEntity groupEntity = new GroupEntity();
		groupEntity.setName(name);
		groupEntity.setNumber(number);
		groupEntity.setQueryLabel(queryLabel);
		groupEntity.setCategory(category);
		groupEntity.setTopLevel(false);
		groupEntity.setParentId(parentGroupEntity.getId());
		return save(groupEntity, parentGroupEntity);
	}

	private boolean save(final GroupEntity groupEntity, final GroupEntity parentGroupEntity) {
		boolean result1 = super.save(groupEntity);
		parentGroupEntity.getGroupEntities().add(groupEntity);
		boolean result2 = super.save(parentGroupEntity);
		return result1 && result2;
	}
}

/*
 * Copyright (C) CCRISE.
 */
package cn.ccrise.spimp.system.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import cn.ccrise.spimp.system.service.InitService;

/**
 * 基础数据监听器。
 * <p>
 * 当数据库中不存在基础数据时，自动执行初始化。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Service
public class InitListener implements ApplicationListener<ApplicationEvent> {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	private static final String CONTEXT_DISPLAY_NAME = "Root WebApplicationContext";

	@Autowired
	private InitService initService;

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		if (event instanceof ContextRefreshedEvent) {
			if (CONTEXT_DISPLAY_NAME.equals(((ContextRefreshedEvent) event).getApplicationContext().getDisplayName())) {
				logger.info("系统启动成功:)");

				if (initService.init()) {
					logger.debug("基础数据初始化成功:)");
				}
			}
		}
	}
}

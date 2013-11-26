package cn.ccrise.spimp.system.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import cn.ccrise.ikjp.core.util.PropertiesUtils;
import cn.ccrise.spimp.util.SMSHelper;

/**
 * 短信发送服务监听器。
 * <p>
 * 启动短信发送服务。
 */
@Service
public class SMSListener implements ApplicationListener<ApplicationEvent> {
	private static final String CONTEXT_DISPLAY_NAME = "Root WebApplicationContext";
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		if (event instanceof ContextRefreshedEvent) {
			if (CONTEXT_DISPLAY_NAME.equals(((ContextRefreshedEvent) event).getApplicationContext().getDisplayName())) {
				if (PropertiesUtils.getBoolean("sms.enable")) {
					logger.debug("启动短信模块");
					SMSHelper.getInstance();
				}
			}
		}
	}

}

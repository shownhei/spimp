/*
 * Copyright (C) CCRISE.
 */
package cn.ccrise.spimp.system.web.entity;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.web.context.request.async.DeferredResult;

/**
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
public class ReminderDeferredResult<T> extends DeferredResult<T> {
	private HttpSession httpSession;

	public ReminderDeferredResult(long timeout, HttpSession httpSession) {
		super(timeout);
		this.httpSession = httpSession;
	}

	public HttpSession getHttpSession() {
		return httpSession;
	}

	public void setHttpSession(HttpSession httpSession) {
		this.httpSession = httpSession;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}

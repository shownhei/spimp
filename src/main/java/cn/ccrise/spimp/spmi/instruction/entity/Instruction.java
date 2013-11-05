package cn.ccrise.spimp.spmi.instruction.entity;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import cn.ccrise.ikjp.core.entity.IDEntity;
import cn.ccrise.spimp.util.PageFields;

/**
 * 领导指示
 * 
 * <p>
 * 
 * @author David(david.kosoon@gmail.com)
 */
@Entity
@Table(name = "spmi_instruction_instructions")
public class Instruction extends IDEntity {

	/**
	 * 时间
	 */
	@PageFields(describtion = "时间", search = true)
	public Date time;

	/**
	 * 指示人
	 */
	@PageFields(describtion = "指示人", allowedNull = false, search = true)
	public String indicator;

	/**
	 * 承办人
	 */
	@PageFields(describtion = "承办人")
	public String undertaker;

	/**
	 * 接收人
	 */
	@PageFields(describtion = "接收人")
	public String receiver;

	/**
	 * 指示内容
	 */
	@PageFields(describtion = "指示内容", type = "textarea")
	public String content;

	public String getContent() {
		return content;
	}

	public String getIndicator() {
		return indicator;
	}

	public String getReceiver() {
		return receiver;
	}

	public Date getTime() {
		return time;
	}

	public String getUndertaker() {
		return undertaker;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setIndicator(String indicator) {
		this.indicator = indicator;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public void setUndertaker(String undertaker) {
		this.undertaker = undertaker;
	}
}

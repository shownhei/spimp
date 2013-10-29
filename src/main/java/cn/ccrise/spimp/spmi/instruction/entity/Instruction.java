package cn.ccrise.spimp.spmi.instruction.entity;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import cn.ccrise.ikjp.core.entity.IDEntity;

/**
 * 领导指示
 *
 * <p>
 *
 * @author David(david.kosoon@gmail.com)
 */
@Entity
@Table(name = "spmi_instruction_instructions")
public class Instruction extends IDEntity{
	
	/**
	 * 	时间
	 */
	public Date time;
	
	/**
	 * 	指示人
	 */
	public String indicator;
	
	/**
	 * 	承办人
	 */
	public String undertaker;
	
	/**
	 * 	接收人
	 */
	public String receiver;
	
	/**
	 * 	指示内容
	 */
	public String content;

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getIndicator() {
		return indicator;
	}

	public void setIndicator(String indicator) {
		this.indicator = indicator;
	}

	public String getUndertaker() {
		return undertaker;
	}

	public void setUndertaker(String undertaker) {
		this.undertaker = undertaker;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}

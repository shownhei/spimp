/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.car.entity;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.ccrise.ikjp.core.entity.IDEntity;
import cn.ccrise.ikjp.core.util.JsonTimeDeserializer;
import cn.ccrise.ikjp.core.util.JsonTimeSerializer;
import cn.ccrise.spimp.util.PageFields;

/**
 * RunLog。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Entity
@Table(name = "car_run_logs")
public class RunLog extends IDEntity {

	/**
	 * 车号
	 */
	@PageFields(describtion = "车号", allowedNull = true, search = true, type = "select", selectDataUri = "/spmi/car/carslist", selectShowField = "carNo")
	private Car car;
	/**
	 * 班次 零点、四点、八点
	 */
	@PageFields(describtion = "班次 ", allowedNull = false, search = true)
	private String classType;
	/**
	 * 车次
	 */
	@PageFields(describtion = "车次 ", allowedNull = false, search = false)
	private Integer trainNumber;
	/**
	 * 路程
	 */
	@PageFields(describtion = "路程 ", allowedNull = false, search = false)
	private Integer distance;
	/**
	 * 加油数
	 */
	@PageFields(describtion = "加油数 ", allowedNull = false, search = false)
	private Integer refuelNumber;
	/**
	 * 备注
	 */
	@PageFields(describtion = "备注 ", allowedNull = false, search = false)
	private String remark;
	/**
	 * 记录日期
	 */
	@PageFields(describtion = "记录日期 ", allowedNull = false, search = true)
	private Date addDate;
	/**
	 * 记录时间
	 */
	private Timestamp recordTime;

	@ManyToOne
	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public String getClassType() {
		return classType;
	}

	public void setClassType(String classType) {
		this.classType = classType;
	}

	public Integer getTrainNumber() {
		return trainNumber;
	}

	public void setTrainNumber(Integer trainNumber) {
		this.trainNumber = trainNumber;
	}

	public Integer getDistance() {
		return distance;
	}

	public void setDistance(Integer distance) {
		this.distance = distance;
	}

	public Integer getRefuelNumber() {
		return refuelNumber;
	}

	public void setRefuelNumber(Integer refuelNumber) {
		this.refuelNumber = refuelNumber;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getAddDate() {
		return addDate;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}

	@Column(updatable = false)
	@JsonSerialize(using = JsonTimeSerializer.class)
	@JsonDeserialize(using = JsonTimeDeserializer.class)
	public Timestamp getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(Timestamp recordTime) {
		this.recordTime = recordTime;
	}

}
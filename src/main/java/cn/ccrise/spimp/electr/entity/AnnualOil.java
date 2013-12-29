package cn.ccrise.spimp.electr.entity;

import java.text.DecimalFormat;

public class AnnualOil {
	private DecimalFormat df = new DecimalFormat(".##");
	private String carNo;
	/**
	 * 运行次数
	 */
	private Long trainNumber;

	/**
	 * 公里数
	 */
	private Long distance;
	/**
	 * 加油数
	 */
	private Long refuelNumber;
	/**
	 * 百公里油耗
	 */
	private Double oilDistance;
	/**
	 * 百公里油耗的前端显示
	 */
	private String oilDistanceDisplay;

	/**
	 * 平均油耗
	 */
	private String avgOilDistance;

	private String carCategory;
	/**
	 * 日平均运行次数
	 */
	private Double dayTrainNumber;
	/**
	 * 日平均运行次数
	 */
	private String dayTrainNumberDisplay;

	public void count() {
		if (distance > 0l) {
			oilDistance = (double) ((double) refuelNumber * 100 / (double) distance);
			df.applyPattern("0.00");
			oilDistanceDisplay = df.format(oilDistance);
		}
	}

	public String getAvgOilDistance() {
		return avgOilDistance;
	}

	public String getCarCategory() {
		return carCategory;
	}

	public String getCarNo() {
		return carNo;
	}

	public Double getDayTrainNumber() {
		return dayTrainNumber;
	}

	public String getDayTrainNumberDisplay() {
		return dayTrainNumberDisplay;
	}

	public DecimalFormat getDf() {
		return df;
	}

	public Long getDistance() {
		return distance;
	}

	public Double getOilDistance() {
		return oilDistance;
	}

	public String getOilDistanceDisplay() {
		return oilDistanceDisplay;
	}

	public Long getRefuelNumber() {
		return refuelNumber;
	}

	public Long getTrainNumber() {
		return trainNumber;
	}

	public void setAvgOilDistance(String avgOilDistance) {
		this.avgOilDistance = avgOilDistance;
	}

	public void setCarCategory(String carCategory) {
		this.carCategory = carCategory;
	}

	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}

	public void setDayTrainNumber(Double dayTrainNumber) {
		this.dayTrainNumber = dayTrainNumber;
	}

	public void setDayTrainNumberDisplay(String dayTrainNumberDisplay) {
		this.dayTrainNumberDisplay = dayTrainNumberDisplay;
	}

	public void setDf(DecimalFormat df) {
		this.df = df;
	}

	public void setDistance(Long distance) {
		this.distance = distance;
	}

	public void setOilDistance(Double oilDistance) {
		this.oilDistance = oilDistance;
	}

	public void setOilDistanceDisplay(String oilDistanceDisplay) {
		this.oilDistanceDisplay = oilDistanceDisplay;
	}

	public void setRefuelNumber(Long refuelNumber) {
		this.refuelNumber = refuelNumber;
	}

	public void setTrainNumber(Long trainNumber) {
		this.trainNumber = trainNumber;
	}

}

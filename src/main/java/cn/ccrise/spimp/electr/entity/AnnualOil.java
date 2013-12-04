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

	public String getAvgOilDistance() {
		return avgOilDistance;
	}

	public void setAvgOilDistance(String avgOilDistance) {
		this.avgOilDistance = avgOilDistance;
	}

	public String getOilDistanceDisplay() {
		return oilDistanceDisplay;
	}

	public void setOilDistanceDisplay(String oilDistanceDisplay) {
		this.oilDistanceDisplay = oilDistanceDisplay;
	}

	public Double getOilDistance() {
		return oilDistance;
	}

	public void count() {
		if (distance > 0l) {
			this.oilDistance = (double) ((double) refuelNumber * 100 / (double) distance);
			oilDistanceDisplay = df.format(oilDistance);
		}
	}

	public void setOilDistance(Double oilDistance) {
		this.oilDistance = oilDistance;
	}

	public String getCarCategory() {
		return carCategory;
	}

	public void setCarCategory(String carCategory) {
		this.carCategory = carCategory;
	}

	public String getCarNo() {
		return carNo;
	}

	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}

	public Long getTrainNumber() {
		return trainNumber;
	}

	public void setTrainNumber(Long trainNumber) {
		this.trainNumber = trainNumber;
	}

	public Long getDistance() {
		return distance;
	}

	public void setDistance(Long distance) {
		this.distance = distance;
	}

	public Long getRefuelNumber() {
		return refuelNumber;
	}

	public void setRefuelNumber(Long refuelNumber) {
		this.refuelNumber = refuelNumber;
	}

}

package cn.ccrise.spimp.electr.service;

import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Iterator;

import org.springframework.stereotype.Service;

import cn.ccrise.spimp.electr.entity.AnnualOil;

/**
 * 油耗统计服务：年度油耗统计、月度油耗统计
 * 
 * 
 */
@Service
public class OilStaticsService {
	/**
	 * 计算平均油耗（升） 同属一种类型的车的百公里油耗sum除以同种车型的条数
	 * 
	 * @param result
	 * @param df
	 */
	public void oilAVG(Collection<AnnualOil> result, DecimalFormat df) {
		Iterator<AnnualOil> it = result.iterator();
		AnnualOil temp = null;
		int size = result.size();
		double sum = 0;
		while (it.hasNext()) {
			temp = it.next();
			sum += temp.getOilDistance();
		}
		double avg = sum / size;
		it = result.iterator();
		while (it.hasNext()) {
			temp = it.next();
			temp.setAvgOilDistance(df.format(avg));
		}
	}
}

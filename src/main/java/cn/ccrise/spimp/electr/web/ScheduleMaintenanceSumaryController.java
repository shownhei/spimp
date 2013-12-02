package cn.ccrise.spimp.electr.web;

import java.util.HashMap;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * 胶轮车定期保养一览表
 * 
 * @author zpf
 * 
 */
public class ScheduleMaintenanceSumaryController {
	/**
	 * 定期保养一览表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/electr/maintenance/schedulesumary", method = RequestMethod.GET)
	public ModelAndView getScheduleMaintenance() {
		HashMap<String, Object> root = new HashMap<String, Object>();
		return new ModelAndView("electr/maintenance/schedulesumary/index", root);
	}
}

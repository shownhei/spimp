package cn.ccrise.spimp.monitor.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.ccrise.ikjp.core.util.Response;
import cn.ccrise.spimp.monitor.entity.MonitorState;
import cn.ccrise.spimp.monitor.service.MonitorStateService;

/**
 * MonitorState Controller
 * <p>
 * 
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Controller
public class MonitorStateController {
	@Autowired
	private MonitorStateService monitorStateService;

	@RequestMapping(value = "/monitor/monitor-states", method = RequestMethod.GET)
	@ResponseBody
	public Response getAllMonitorStates() {
		List<MonitorState> monitorStaste = monitorStateService.getAll();
		return new Response(monitorStaste);
	}
}

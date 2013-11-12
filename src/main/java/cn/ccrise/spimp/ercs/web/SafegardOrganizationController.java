package cn.ccrise.spimp.ercs.web;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.ccrise.ikjp.core.util.Page;
import cn.ccrise.ikjp.core.util.Response;
import cn.ccrise.spimp.ercs.entity.SafegardOrganization;
import cn.ccrise.spimp.ercs.service.SafegardOrganizationService;

/**
 * SafegardOrganization Controller。
 */
@Controller
public class SafegardOrganizationController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private SafegardOrganizationService safegardOrganizationService;

	@RequestMapping(value = "/ercs/safegard-organizations/{id}", method = RequestMethod.DELETE)
	public @ResponseBody
	boolean delete(@PathVariable long id) {
		return safegardOrganizationService.delete(id);
	}

	@RequestMapping(value = "/ercs/safegard-organizations/{id}", method = RequestMethod.GET)
	public @ResponseBody
	Response get(@PathVariable long id) {
		return new Response(safegardOrganizationService.get(id));
	}

	@RequestMapping(value = "/ercs/safegard-organizations", method = RequestMethod.GET)
	public @ResponseBody
	Response page(Page<SafegardOrganization> page, String organizationName) {
		if (StringUtils.isNotBlank(organizationName)) {
			return new Response(safegardOrganizationService.getPage(page,
					Restrictions.ilike("organizationName", organizationName, MatchMode.ANYWHERE)));
		} else {
			return new Response(safegardOrganizationService.getPage(page));
		}
	}

	@RequestMapping(value = "/ercs/safegard-organizations", method = RequestMethod.POST)
	public @ResponseBody
	Response save(@RequestBody SafegardOrganization safegardOrganization) {
		safegardOrganizationService.save(safegardOrganization);
		return new Response(safegardOrganization);
	}

	@RequestMapping(value = "/ercs/safegard-organizations/{id}", method = RequestMethod.PUT)
	public @ResponseBody
	Response update(@RequestBody SafegardOrganization safegardOrganization) {
		safegardOrganizationService.update(safegardOrganization);
		return new Response(safegardOrganization);
	}
}

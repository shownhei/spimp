package cn.ccrise.spimp.ercs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ccrise.ikjp.core.access.HibernateDAO;
import cn.ccrise.ikjp.core.service.HibernateDataServiceImpl;
import cn.ccrise.spimp.ercs.access.SafegardOrganizationDAO;
import cn.ccrise.spimp.ercs.entity.SafegardOrganization;

/**
 * 应急保障机构 SafegardOrganization Serviceã
 */
@Service
public class SafegardOrganizationService extends HibernateDataServiceImpl<SafegardOrganization, Long> {
	@Autowired
	private SafegardOrganizationDAO safegardOrganizationDAO;

	@Override
	public HibernateDAO<SafegardOrganization, Long> getDAO() {
		return safegardOrganizationDAO;
	}
}

package cn.ccrise.spimp.ercs.access;

import org.springframework.stereotype.Repository;

import cn.ccrise.ikjp.core.access.HibernateDAOImpl;
import cn.ccrise.spimp.ercs.entity.SafegardOrganization;

/**
 * SafegardOrganization DAO
 */
@Repository
public class SafegardOrganizationDAO extends HibernateDAOImpl<SafegardOrganization, Long> {
}

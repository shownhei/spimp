package cn.ccrise.spimp.ercs.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cn.ccrise.ikjp.core.entity.IDEntity;
import cn.ccrise.spimp.entity.Dictionary;

/**
 * 应急保障机构
 */
@Entity
@Table(name = "ciis_SafegardOrganization")
public class SafegardOrganization extends IDEntity {

	/**
	 * 资源名称
	 */
	private String organizationName;

	/**
	 * 资源类型
	 */
	private Dictionary organizationType;

	/**
	 * 行政区划分
	 */
	private String administrativeDivision;

	/**
	 * 救援资质
	 */
	private String qualification;

	/**
	 * 负责人
	 */
	private String personInCharge;

	/**
	 * 固话
	 */
	private String telephone;

	/**
	 * 手机
	 */
	private String mobilePhone;

	/**
	 * 备注
	 */
	private String remark;

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	@ManyToOne
	public Dictionary getOrganizationType() {
		return organizationType;
	}

	public void setOrganizationType(Dictionary organizationType) {
		this.organizationType = organizationType;
	}

	public String getAdministrativeDivision() {
		return administrativeDivision;
	}

	public void setAdministrativeDivision(String administrativeDivision) {
		this.administrativeDivision = administrativeDivision;
	}

	public String getQualification() {
		return qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	public String getPersonInCharge() {
		return personInCharge;
	}

	public void setPersonInCharge(String personInCharge) {
		this.personInCharge = personInCharge;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}

package cn.ccrise.spimp.ercs.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cn.ccrise.ikjp.core.entity.IDEntity;
import cn.ccrise.spimp.system.entity.Dictionary;

/**
 * 应急保障机构
 */
@Entity
@Table(name = "ercs_SafegardOrganization")
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

	public String getAdministrativeDivision() {
		return administrativeDivision;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	@ManyToOne
	public Dictionary getOrganizationType() {
		return organizationType;
	}

	public String getPersonInCharge() {
		return personInCharge;
	}

	public String getQualification() {
		return qualification;
	}

	public String getRemark() {
		return remark;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setAdministrativeDivision(String administrativeDivision) {
		this.administrativeDivision = administrativeDivision;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public void setOrganizationType(Dictionary organizationType) {
		this.organizationType = organizationType;
	}

	public void setPersonInCharge(String personInCharge) {
		this.personInCharge = personInCharge;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

}

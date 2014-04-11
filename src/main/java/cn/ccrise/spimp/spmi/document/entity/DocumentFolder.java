package cn.ccrise.spimp.spmi.document.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cn.ccrise.ikjp.core.entity.IDEntity;
import cn.ccrise.ikjp.core.security.entity.AccountEntity;
import cn.ccrise.spimp.util.PageFields;

@Entity
@Table(name = "spmi_document_folders")
public class DocumentFolder extends IDEntity {

	@PageFields(describtion = "文件夹名称", allowedNull = false, search = false)
	private String folderName;
	@PageFields(describtion = "上级id", allowedNull = false, search = false)
	private Long parentId;
	@PageFields(describtion = "创建人", allowedNull = false, search = false)
	private AccountEntity account;

	public String getFolderName() {
		return folderName;
	}

	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	@ManyToOne
	public AccountEntity getAccount() {
		return account;
	}

	public void setAccount(AccountEntity account) {
		this.account = account;
	}

}

/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.system.entity;

import cn.ccrise.ikjp.core.entity.IDEntity;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import java.sql.Date;

/**
 * Alteration。
 *
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Entity
@Table(name = "spimp_alterations")
public class Alteration extends IDEntity {
    private Date changeDate; // 变更日期
    private String content; // 变更内容

    @Lob
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(Date changeDate) {
        this.changeDate = changeDate;
    }
}
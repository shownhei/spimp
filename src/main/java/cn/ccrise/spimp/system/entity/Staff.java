/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.system.entity;

import cn.ccrise.ikjp.core.entity.IDEntity;
import cn.ccrise.ikjp.core.security.entity.GroupEntity;
import cn.ccrise.ikjp.core.util.JsonTimeDeserializer;
import cn.ccrise.ikjp.core.util.JsonTimeSerializer;
import com.google.common.collect.Lists;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

/**
 * Staff。
 * <p/>
 * 人员信息。
 *
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Entity
@Table(name = "spimp_staffs")
public class Staff extends IDEntity {
    /*
     * 基本信息
     */
    @NotBlank
    private String name; // 姓名
    private String gender; // 性别
    private String category; // 用工类别：正/协/临
    private String education; // 文化程度：高中/中专/大专/本科/硕士/博士
    private String duty; // 职务职称
    private String post; // 岗位
    private String partTime; // 兼职岗位
    private Date postDate; // 定岗日期
    private String identityCard; // 身份证
    private String qualification; // 资格证号
    private String remark; // 备注
    @NotNull
    private GroupEntity groupEntity; // 人员所属于的机构
    private List<Alteration> alterations = Lists.newArrayList(); // 变更记录
    /*
     * 其他信息
     */
    private Timestamp createTime = new Timestamp(System.currentTimeMillis()); // 创建时间
    private Timestamp updateTime = new Timestamp(System.currentTimeMillis()); // 最后更新时间

    public String getPartTime() {
        return partTime;
    }

    public void setPartTime(String partTime) {
        this.partTime = partTime;
    }

    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public String getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    @Lob
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL}, orphanRemoval = true)
    @Fetch(FetchMode.SUBSELECT)
    public List<Alteration> getAlterations() {
        return alterations;
    }

    public void setAlterations(List<Alteration> alterations) {
        this.alterations = alterations;
    }

    @JsonSerialize(using = JsonTimeSerializer.class)
    @JsonDeserialize(using = JsonTimeDeserializer.class)
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @ManyToOne(optional = false)
    public GroupEntity getGroupEntity() {
        return groupEntity;
    }

    public void setGroupEntity(GroupEntity groupEntity) {
        this.groupEntity = groupEntity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    @JsonSerialize(using = JsonTimeSerializer.class)
    @JsonDeserialize(using = JsonTimeDeserializer.class)
    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }
}
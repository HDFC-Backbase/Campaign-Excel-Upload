package com.backbase.campaignupload.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "corporate_offer_audit")
public class CorporateAuditEntity {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "title")
	private String title;
	

	@Column(name = "logo")
	private String logo;

	@Column(name = "offer_text")
	private String offertext;

	@Column(name = "corp_status")
	private String approvalstatus;
	
	@Column(name = "company_id")
	private String companyId;
	
	@Column(name = "created_by")
	private String createdBy;
	
	@Column(name = "updated_by")
	private String updatedBy;
	
	@Column(name = "checker_ip")
	private String checkerip;
	
	@Column(name = "maker_ip")
	private String makerip;
	
	@ManyToOne
	@JoinColumn(name = "crp_s_id", nullable = false)
	private CorporateStagingEntity corpstaginentity;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getOffertext() {
		return offertext;
	}

	public void setOffertext(String offertext) {
		this.offertext = offertext;
	}

	public String getApprovalstatus() {
		return approvalstatus;
	}

	public void setApprovalstatus(String approvalstatus) {
		this.approvalstatus = approvalstatus;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getCheckerip() {
		return checkerip;
	}

	public void setCheckerip(String checkerip) {
		this.checkerip = checkerip;
	}

	public String getMakerip() {
		return makerip;
	}

	public void setMakerip(String makerip) {
		this.makerip = makerip;
	}

	public CorporateStagingEntity getCorpstaginentity() {
		return corpstaginentity;
	}

	public void setCorpstaginentity(CorporateStagingEntity corpstaginentity) {
		this.corpstaginentity = corpstaginentity;
	}

	@Override
	public String toString() {
		return "CorporateAuditEntity [id=" + id + ", title=" + title + ", logo=" + logo + ", offertext=" + offertext
				+ ", approvalstatus=" + approvalstatus + ", companyId=" + companyId + ", createdBy=" + createdBy
				+ ", updatedBy=" + updatedBy + ", checkerip=" + checkerip + ", makerip=" + makerip + "]";
	}



}
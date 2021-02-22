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
@Table(name = "partner_offer_audit")
public class PartnerAuditEntity {
	

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

	@Column(name = "partn_status")
	private String approvalstatus;
	
	@Column(name = "created_by")
	private String createdBy;
	
	@Column(name = "updated_by")
	private String updatedBy;	
	
	@Column(name = "checker_ip")
	private String checkerip;
	
	@Column(name = "maker_ip")
	private String makerip;
	
	@ManyToOne
	@JoinColumn(name = "partn_stg_id", nullable = false)
	private PartnerOffersStagingEntity partnstaginentity;
	
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

	public PartnerOffersStagingEntity getPartnstaginentity() {
		return partnstaginentity;
	}

	public void setPartnstaginentity(PartnerOffersStagingEntity partnstaginentity) {
		this.partnstaginentity = partnstaginentity;
	}

	@Override
	public String toString() {
		return "PartnerAuditEntity [id=" + id + ", title=" + title + ", logo=" + logo + ", offertext=" + offertext
				+ ", approvalstatus=" + approvalstatus + ", createdBy=" + createdBy + ", updatedBy=" + updatedBy
				+ ", checkerip=" + checkerip + ", makerip=" + makerip + "]";
	}
	

}

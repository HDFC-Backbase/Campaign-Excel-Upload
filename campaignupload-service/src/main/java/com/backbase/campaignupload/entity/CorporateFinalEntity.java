package com.backbase.campaignupload.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name = "corporate_offer_final")
public class CorporateFinalEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "crp_title")
	private String title;

	@Column(name = "crp_logo")
	private String logo;

	@Column(name = "crp_offertext")
	private String offertext;

	@Column(name = "crp_status")
	private String approvalstatus;
	
	@Column(name = "created_by")
	private String createdBy;
	
	@Column(name = "updated_by")
	private String updatedBy;
	
	@Column(name = "checker_ip")
	private String checkerip;
	
	@Column(name = "maker_ip")
	private String makerip;
	
	@OneToOne
	@JoinColumn(name = "crp_s_id", nullable = false)
	private CorporateStagingEntity corporateStagingEntity;
	
	@ManyToOne
	@JoinColumn(name = "company_id", nullable = false)
	private CompanyFinalEntity companyfinalEntity;
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
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

	public CorporateStagingEntity getCorporateStagingEntity() {
		return corporateStagingEntity;
	}

	public void setCorporateStagingEntity(CorporateStagingEntity corporateStagingEntity) {
		this.corporateStagingEntity = corporateStagingEntity;
	}

	public CompanyFinalEntity getCompanyfinalEntity() {
		return companyfinalEntity;
	}

	public void setCompanyfinalEntity(CompanyFinalEntity companyfinalEntity) {
		this.companyfinalEntity = companyfinalEntity;
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

	@Override
	public String toString() {
		return "CorporateFinalEntity [id=" + id + ", title=" + title + ", logo=" + logo + ", offertext=" + offertext
				+ ", approvalstatus=" + approvalstatus + ", createdBy=" + createdBy + ", updatedBy=" + updatedBy
				+ ", checkerip=" + checkerip + ", makerip=" + makerip + "]";
	}

	

}

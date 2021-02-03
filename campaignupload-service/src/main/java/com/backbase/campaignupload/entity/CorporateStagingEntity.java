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
@Table(name = "corporate_offer_staging")
public class CorporateStagingEntity {

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

	@Column(name = "approval_status")
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
	@JoinColumn(name = "file_id", nullable = false)
	private FileApproveEntity corpfileApproveEntity;

	

	public CorporateStagingEntity() {

	}

	public CorporateStagingEntity(Integer id, String title, String logo, String offertext, String approvalstatus,
			String companyId, FileApproveEntity corpfileApproveEntity) {
		super();
		this.id = id;
		this.title = title;
		this.logo = logo;
		this.offertext = offertext;
		this.approvalstatus = approvalstatus;
		this.companyId = companyId;
		this.corpfileApproveEntity = corpfileApproveEntity;
	}



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public FileApproveEntity getCorpfileApproveEntity() {
		return corpfileApproveEntity;
	}

	public void setCorpfileApproveEntity(FileApproveEntity corpfileApproveEntity) {
		this.corpfileApproveEntity = corpfileApproveEntity;
	}
	
	public String getApprovalstatus() {
		return approvalstatus;
	}

	public void setApprovalstatus(String approvalstatus) {
		this.approvalstatus = approvalstatus;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	@Override
	public String toString() {
		return "CorporateStagingEntity [id=" + id + ", title=" + title + ", logo=" + logo + ", offertext=" + offertext
				+ ", approvalstatus=" + approvalstatus + ", companyId=" + companyId + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof CorporateFinalEntity) {
			CorporateFinalEntity cofinal = (CorporateFinalEntity) obj;
			return this.getOffertext().equals(cofinal.getOffertext()) && this.getCompanyId().equals(cofinal.getCompanyfinalEntity().getCompany_Id());
		}
		return false;
	}
}

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
@Table(name = "partner_offer_staging")
public class PartnerOffersStagingEntity {

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
	private FileApproveEntity fileApproveEntity;

	public PartnerOffersStagingEntity() {

	}

	public PartnerOffersStagingEntity(Integer id, String title, String logo, String offertext, String approvalstatus,
			FileApproveEntity fileApproveEntity) {
		super();
		this.id = id;
		this.title = title;
		this.logo = logo;
		this.offertext = offertext;
		this.approvalstatus = approvalstatus;
		this.fileApproveEntity = fileApproveEntity;
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

	public FileApproveEntity getFileApproveEntity() {
		return fileApproveEntity;
	}

	public void setFileApproveEntity(FileApproveEntity fileApproveEntity) {
		this.fileApproveEntity = fileApproveEntity;
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
		return "PartnerOffersStagingEntity [id=" + id + ", title=" + title + ", logo=" + logo + ", offertext="
				+ offertext + ", approvalstatus=" + approvalstatus + ", createdBy=" + createdBy + ", updatedBy="
				+ updatedBy + ", checkerip=" + checkerip + ", makerip=" + makerip + "]";
	}

	

	
}

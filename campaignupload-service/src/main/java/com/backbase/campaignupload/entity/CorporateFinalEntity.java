package com.backbase.campaignupload.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "corporate_offer_final")
public class CorporateFinalEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "title")
	private String title;

	@Column(name = "logo")
	private String logo;

	@Column(name = "offer_text")
	private String offertext;

	@Column(name = "approval_status")
	private String approvalstatus;
	
	@OneToOne
	@JoinColumn(name = "corp_id", nullable = false)
	private CorporateStagingEntity corporateStagingEntity;
	
	
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

	

}

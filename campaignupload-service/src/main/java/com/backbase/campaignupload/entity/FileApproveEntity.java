package com.backbase.campaignupload.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "file_master")
public class FileApproveEntity {

	@Id
	@Column(name = "f_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "f_name")
	private String filename;

	@Column(name = "f_display_name")
	private String displayfilename;

	@Column(name = "f_type")
	private String fileType;

	@Column(name = "f_status")
	private String filestatus;

	@Column(name = "created_by")
	private String createdby;

	@Column(name = "updated_by")
	private String updatedBy;

	@OneToMany(mappedBy = "fileApproveEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<PartnerOffersStagingEntity> campaignEntity;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getFilestatus() {
		return filestatus;
	}

	public void setFilestatus(String filestatus) {
		this.filestatus = filestatus;
	}

	public String getCreatedby() {
		return createdby;
	}

	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public List<PartnerOffersStagingEntity> getCampaignEntity() {
		return campaignEntity;
	}

	public void setCampaignEntity(List<PartnerOffersStagingEntity> campaignEntity) {
		this.campaignEntity = campaignEntity;
	}

	public String getDisplayfilename() {
		return displayfilename;
	}

	public void setDisplayfilename(String displayfilename) {
		this.displayfilename = displayfilename;
	}

	@Override
	public String toString() {
		return "FileApproveEntity [id=" + id + ", filename=" + filename + ", fileType=" + fileType + ", filestatus="
				+ filestatus + ", createdby=" + createdby + ", updatedBy=" + updatedBy + ", campaignEntity="
				+ campaignEntity + "]";
	}

}

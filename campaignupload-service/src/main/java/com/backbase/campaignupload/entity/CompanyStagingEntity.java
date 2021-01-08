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
@Table(name = "cmp_staging")
public class CompanyStagingEntity {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "c_id")
	private String company_Id;

	@Column(name = "c_name")
	private String company_name;

	@Column(name = "c_imageurl")
	private String image_url;
	
	@Column(name = "c_url")
	private String url;

	@ManyToOne
	@JoinColumn(name = "file_id", nullable = false)
	private FileApproveEntity fileApproveEntity;
	
	@Column(name = "c_status")
	private String approvalstatus;
	
	public CompanyStagingEntity() {

	}

	public CompanyStagingEntity(String company_Id, String company_name, String image_url) {
		this.company_Id = company_Id;
		this.company_name = company_name;
		this.image_url = image_url;
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	

	public String getCompany_Id() {
		return company_Id;
	}

	public void setCompany_Id(String company_Id) {
		this.company_Id = company_Id;
	}

	public String getCompany_name() {
		return company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}

	public String getImage_url() {
		return image_url;
	}

	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}


	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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

	@Override
	public String toString() {
		return "CompanyEntity [id=" + id + ", company_Id=" + company_Id + ", company_name=" + company_name
				+ ", image_url=" + image_url + "]";
	}



}

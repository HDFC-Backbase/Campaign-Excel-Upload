package com.backbase.campaignupload.service;

import java.io.IOException;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.backbase.campaignupload.entity.PartnerOffersStagingEntity;
import com.backbase.campaignupload.entity.FileApproveEntity;
import com.backbase.campaignupload.helper.ExcelHelper;
import com.backbase.campaignupload.repo.CampaignUploadRepo;
import com.backbase.campaignupload.repo.FileApproveRepository;

import liquibase.util.file.FilenameUtils;

@Service
@Transactional
public class CampaignUploadServiceImpl implements CampaignUploadService{
	private static final String PENDING = "Pending";

	@Autowired
	CampaignUploadRepo exeluploadrepo;

	@Autowired
	FileApproveRepository fileApproveRepository;

	@Override
	public void save(MultipartFile file, String sheetname, String uploadedBy, String filename) {
		try {
			List<PartnerOffersStagingEntity> companyfileuploads = ExcelHelper.excelToTutorials(file.getInputStream());
			FileApproveEntity fileApproveEntity = new FileApproveEntity();
			fileApproveEntity.setFilestatus(PENDING);
			fileApproveEntity.setCreatedby(uploadedBy);
			fileApproveEntity.setFilename(filename);
			fileApproveEntity.setFileType(FilenameUtils.getExtension(file.getOriginalFilename()));
			FileApproveEntity savedFile = fileApproveRepository.save(fileApproveEntity);

			companyfileuploads.stream().forEach(ce -> {
				ce.setFileApproveEntity(savedFile);
				ce.setApprovalstatus(PENDING);
			});

			exeluploadrepo.saveAll(companyfileuploads);
		} catch (IOException e) {
			throw new RuntimeException("fail to store excel data: " + e.getMessage());
		}
		
	}

	@Override
	public List<PartnerOffersStagingEntity> getPartnerOffers() {
		// TODO Auto-generated method stub
		return exeluploadrepo.findAll();
	}

}

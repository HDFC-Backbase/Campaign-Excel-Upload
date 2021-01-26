package com.backbase.campaignupload.service;

import java.io.IOException;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.backbase.campaignupload.entity.CorporateStagingEntity;
import com.backbase.campaignupload.entity.FileApproveEntity;
import com.backbase.campaignupload.entity.PartnerOffersStagingEntity;
import com.backbase.campaignupload.helper.ExcelHelper;
import com.backbase.campaignupload.repo.CompanyUploadRepo;
import com.backbase.campaignupload.repo.CorpFinalCustomRepo;
import com.backbase.campaignupload.repo.CorporateOfferRepo;
import com.backbase.campaignupload.repo.FileApproveRepository;
import com.backbase.campaignupload.repo.PTFinalCustomRepo;
import com.backbase.campaignupload.repo.PartnerOfferRepo;

import liquibase.util.file.FilenameUtils;

@Service
@Transactional
public class CampaignUploadServiceImpl implements CampaignUploadService {
	public static final String PENDING = "Pending";

	@Autowired
	PartnerOfferRepo partnerOfferRepo;

	@Autowired
	CorporateOfferRepo corporateOfferRepo;

	@Autowired
	FileApproveRepository fileApproveRepository;

	@Autowired
	CompanyUploadRepo companyupload;

	@Autowired
	PTFinalCustomRepo ptfinalCustomRepo;

	@Autowired
	CorpFinalCustomRepo corpFinalCustomRepo;

	@Override
	public void save(MultipartFile file, String sheetname, String uploadedBy, String filename) {
		try {
			List<PartnerOffersStagingEntity> companyfileuploads = ExcelHelper.excelToTutorials(file.getInputStream());
			FileApproveEntity fileApproveEntity = new FileApproveEntity();
			fileApproveEntity.setFilestatus(PENDING);
			fileApproveEntity.setCreatedby(uploadedBy);
			fileApproveEntity.setFilename(filename);
			fileApproveEntity.setDisplayfilename(file.getOriginalFilename());
			fileApproveEntity.setFileType(FilenameUtils.getExtension(file.getOriginalFilename()));
			FileApproveEntity savedFile = fileApproveRepository.save(fileApproveEntity);

			companyfileuploads.stream().forEach(ce -> {
				ce.setFileApproveEntity(savedFile);
				ce.setApprovalstatus(PENDING);
			});

			partnerOfferRepo.saveAll(companyfileuploads);
		} catch (IOException e) {
			throw new RuntimeException("fail to store excel data: " + e.getMessage());
		}

	}

	@Override
	public List<PartnerOffersStagingEntity> getPartnerOffers() {
		return partnerOfferRepo.findAll();
	}

	@Override
	public void savecarpoateoffer(MultipartFile file, String sheetname, String uploadedBy, String filename) {
		try {
			List<CorporateStagingEntity> corptaglist = ExcelHelper.excelToCorporateStaging(file.getInputStream(),
					companyupload);
			FileApproveEntity fileApproveEntity = new FileApproveEntity();
			fileApproveEntity.setCreatedby(uploadedBy);
			fileApproveEntity.setFilestatus(PENDING);
			fileApproveEntity.setFilename(filename);
			fileApproveEntity.setDisplayfilename(file.getOriginalFilename());
			fileApproveEntity.setFileType(FilenameUtils.getExtension(file.getOriginalFilename()));
			FileApproveEntity savedFile = fileApproveRepository.save(fileApproveEntity);
			corptaglist.stream().forEach(corp -> {
				corp.setCorpfileApproveEntity(savedFile);
				corp.setApprovalstatus(PENDING);

			});
			corporateOfferRepo.saveAll(corptaglist);
		} catch (IOException e) {
			throw new RuntimeException("Fail to store excel data: " + e.getMessage());
		}

	}

	@Override
	public List<CorporateStagingEntity> getCorporateOffers() {

		return corporateOfferRepo.findAll();
	}

	@Override
	public PartnerOffersStagingEntity getPartnerOffer(Integer id) {
		return partnerOfferRepo.findById(id).get();

	}

	@Override
	public void savePartnerOffer(PartnerOffersStagingEntity entity) {
		partnerOfferRepo.save(entity);

	}

	@Override
	public void deleteFinalPartnerOffer(PartnerOffersStagingEntity entity) {
		ptfinalCustomRepo.deletePTFinalByStagId(entity);

	}

	@Override
	public List<PartnerOffersStagingEntity> getLiveApprovedPartnerOffer() {
		return partnerOfferRepo.getLiveApprovedPT();

	}

	@Override
	public CorporateStagingEntity getCorpOffer(Integer id) {

		return corporateOfferRepo.findById(id).get();
	}

	@Override
	public void saveCorpOffer(CorporateStagingEntity entity) {
		corporateOfferRepo.save(entity);

	}

	@Override
	public void deleteFinalCorpOffer(CorporateStagingEntity entity) {

		corpFinalCustomRepo.deleteCorpFinalByStagId(entity);
	}

	@Override
	public List<CorporateStagingEntity> getLiveApprovedCorp() {
		return corporateOfferRepo.getLiveApprovedCorp();
	}

}

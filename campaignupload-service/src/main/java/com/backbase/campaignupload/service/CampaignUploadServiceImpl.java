package com.backbase.campaignupload.service;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.backbase.campaignupload.entity.CompanyFinalEntity;
import com.backbase.campaignupload.entity.CorporateFinalEntity;
import com.backbase.campaignupload.entity.CorporateStagingEntity;
import com.backbase.campaignupload.entity.FileApproveEntity;
import com.backbase.campaignupload.entity.PartnerOffersFinalEntity;
import com.backbase.campaignupload.entity.PartnerOffersStagingEntity;
import com.backbase.campaignupload.exception.CustomBadRequestException;
import com.backbase.campaignupload.reader.ExcelReader;
import com.backbase.campaignupload.repo.CompanyUploadRepo;
import com.backbase.campaignupload.repo.CorporateFinalUploadRepo;
import com.backbase.campaignupload.repo.CorporateOfferRepo;
import com.backbase.campaignupload.repo.FileApproveRepository;
import com.backbase.campaignupload.repo.PartnerFinalUploadRepo;
import com.backbase.campaignupload.repo.PartnerOfferRepo;

import liquibase.util.file.FilenameUtils;

@Service
@Transactional
public class CampaignUploadServiceImpl implements CampaignUploadService {
	public static final String PENDING = "Pending";
	
	private static final Logger logger = LoggerFactory.getLogger(CampaignUploadServiceImpl.class);

	@Autowired
	PartnerOfferRepo partnerOfferRepo;

	@Autowired
	CorporateOfferRepo corporateOfferRepo;

	@Autowired
	FileApproveRepository fileApproveRepository;

	@Autowired
	CompanyUploadRepo companyupload;

	@Autowired
	PartnerFinalUploadRepo partnerFinalUploadRepo;

	@Autowired
	CorporateFinalUploadRepo corporateFinalUploadRepo;

	@Autowired
	ExcelReader excelreader;

	@Override
	public void save(MultipartFile file, String sheetname, String uploadedBy, String dir, String makerip) {
		try {
			List<PartnerOffersStagingEntity> companyfileuploads = excelreader.excelToTutorials(file.getInputStream(),
					sheetname);
			FileApproveEntity fileApproveEntity = new FileApproveEntity();
			fileApproveEntity.setFilestatus(PENDING);
			fileApproveEntity.setCreatedby(uploadedBy);
			String filename = saveFiletoLocation(file, uploadedBy,dir);
			fileApproveEntity.setFilename(filename);
			fileApproveEntity.setDisplayfilename(file.getOriginalFilename());
			fileApproveEntity.setFileType(FilenameUtils.getExtension(file.getOriginalFilename()));
			FileApproveEntity savedFile = fileApproveRepository.save(fileApproveEntity);

			companyfileuploads.stream().forEach(ce -> {
				ce.setCreatedBy(uploadedBy);
				ce.setUpdatedBy("-");
				ce.setFileApproveEntity(savedFile);
				ce.setApprovalstatus(PENDING);
				ce.setMakerip(makerip);
				ce.setCheckerip("-");
			});

			partnerOfferRepo.saveAll(companyfileuploads);
		} catch (CustomBadRequestException e) {
			throw e;
		} catch (IOException e) {
			throw new RuntimeException("fail to store excel data: " + e.getMessage());
		}

	}

	@Override
	public List<PartnerOffersStagingEntity> getPartnerOffers() {
		return partnerOfferRepo.findAll();
	}

	@Override
	public void saveCorporateoffer(MultipartFile file, String sheetname, String uploadedBy, String dir,
			String makerip) {
		try {
			List<CorporateStagingEntity> corptaglist = excelreader.excelToCorporateStaging(file.getInputStream(),sheetname,
					companyupload);
			FileApproveEntity fileApproveEntity = new FileApproveEntity();
			fileApproveEntity.setCreatedby(uploadedBy);
			fileApproveEntity.setFilestatus(PENDING);
			String filename = saveFiletoLocation(file, uploadedBy,dir);
			fileApproveEntity.setFilename(filename);
			fileApproveEntity.setDisplayfilename(file.getOriginalFilename());
			fileApproveEntity.setFileType(FilenameUtils.getExtension(file.getOriginalFilename()));
			FileApproveEntity savedFile = fileApproveRepository.save(fileApproveEntity);
			corptaglist.stream().forEach(corp -> {
				corp.setCreatedBy(uploadedBy);
				corp.setUpdatedBy("-");
				corp.setCorpfileApproveEntity(savedFile);
				corp.setApprovalstatus(PENDING);
				corp.setMakerip(makerip);
				corp.setCheckerip("-");
			});
			corporateOfferRepo.saveAll(corptaglist);
		} catch (CustomBadRequestException e) {
			throw e;
		} catch (IOException e) {
			throw new RuntimeException("Fail to store excel data: " + e.getMessage());
		}

	}
	
	public String saveFiletoLocation(MultipartFile file, String uploadedBy, String dir) {

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String dateWithoutTime = dateFormat.format(new Date());

		String fileNameWithOutExt = FilenameUtils.removeExtension(file.getOriginalFilename());

		String filename = fileNameWithOutExt + "_" + dateWithoutTime + "_" + LocalDateTime.now().getHour() + "_"
				+ LocalDateTime.now().getMinute() + "_" + uploadedBy + ".xls";

		Path filepath = Paths.get(dir.toString(), filename.trim());
		try {
			file.transferTo(filepath);
		} catch (IllegalStateException e) {
			logger.info(e.getMessage());
		} catch (IOException e) {
			logger.info(e.getMessage());
		}

		return filename;
	}

	@Override
	public List<CorporateStagingEntity> getCorporateOffers() {

		return corporateOfferRepo.findAll();
	}

	@Override
	public PartnerOffersStagingEntity getPTWithOutFileId(Integer id) {
		return partnerOfferRepo.getPTWithOutFileId(id);
	}

	@Override
	public void savePartnerOffer(PartnerOffersStagingEntity entity) {
		partnerOfferRepo.save(entity);

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
	public void savePTFinal(PartnerOffersFinalEntity entity) {
		partnerFinalUploadRepo.save(entity);

	}

	@Override
	public PartnerOffersFinalEntity getFinalEntitybyStagId(PartnerOffersStagingEntity entity) {
		return partnerFinalUploadRepo.getFinalEntitybyStagId(entity);
	}

	@Override
	public void saveCorpFinal(CorporateFinalEntity entity) {
		corporateFinalUploadRepo.save(entity);
	}

	@Override
	public CorporateStagingEntity getCorporateWithOutFileId(Integer id) {

		return corporateOfferRepo.getCorporateWithOutFileId(id);
	}

	@Override
	public CorporateFinalEntity getcorpFinalEntitybyStagId(CorporateStagingEntity entity) {
		return corporateFinalUploadRepo.getFinalEntitybyStagId(entity);
	}

	@Override
	public CompanyFinalEntity getCompany(String name) {
		return companyupload.getCompany(name);
	}

	@Override
	public void deletePT(PartnerOffersFinalEntity ptfinal) {
		partnerFinalUploadRepo.delete(ptfinal);
	}

	@Override
	public void deleteCORP(CorporateFinalEntity corpfinal) {
		corporateFinalUploadRepo.delete(corpfinal);
	}

	@Override
	public List<PartnerOffersFinalEntity> findAllPT() {
		return partnerFinalUploadRepo.findAll();
	}

	@Override
	public List<CorporateFinalEntity> findAllCORP() {
		return corporateFinalUploadRepo.findAll();
	}

}

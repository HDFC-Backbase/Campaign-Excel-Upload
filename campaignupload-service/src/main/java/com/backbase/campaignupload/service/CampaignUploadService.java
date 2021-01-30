package com.backbase.campaignupload.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.backbase.campaignupload.entity.CompanyFinalEntity;
import com.backbase.campaignupload.entity.CorporateFinalEntity;
import com.backbase.campaignupload.entity.CorporateStagingEntity;
import com.backbase.campaignupload.entity.PartnerOffersFinalEntity;
import com.backbase.campaignupload.entity.PartnerOffersStagingEntity;


public interface CampaignUploadService {

	void save(MultipartFile file, String sheetname, String uploadedBy, String filename, String makerip);

	List<PartnerOffersStagingEntity> getPartnerOffers();

	void savecarpoateoffer(MultipartFile file, String sheetname, String uploadedBy, String filename, String makerip);

	List<CorporateStagingEntity> getCorporateOffers();


	void savePartnerOffer(PartnerOffersStagingEntity entity);


	List<PartnerOffersStagingEntity> getLiveApprovedPartnerOffer();
	
	//corporate
	
	CorporateStagingEntity getCorpOffer(Integer id);

	void saveCorpOffer(CorporateStagingEntity entity);

	//void deleteFinalCorpOffer(CorporateStagingEntity entity);

	List<CorporateStagingEntity> getLiveApprovedCorp();

	PartnerOffersStagingEntity getPTWithFileId(Integer id);
	
	void savePTFinal(PartnerOffersFinalEntity entity);
	
	PartnerOffersFinalEntity getFinalEntitybyStagId(PartnerOffersStagingEntity entity);
	

	void saveCorpFinal(CorporateFinalEntity entity);

	CorporateStagingEntity getCorporateWithFileId(Integer id);
	
	CorporateFinalEntity getcorpFinalEntitybyStagId(CorporateStagingEntity entity);
	CompanyFinalEntity getCompany(String name);


}

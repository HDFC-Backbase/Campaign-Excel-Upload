package com.backbase.campaignupload.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.backbase.campaignupload.entity.CorporateStagingEntity;
import com.backbase.campaignupload.entity.PartnerOffersStagingEntity;

public interface CampaignUploadService {

	void save(MultipartFile file, String sheetname, String uploadedBy, String filename);

	List<PartnerOffersStagingEntity> getPartnerOffers();

	void savecarpoateoffer(MultipartFile file, String sheetname, String uploadedBy, String filename);

	List<CorporateStagingEntity> getCorporateOffers();

	PartnerOffersStagingEntity getPartnerOffer(Integer id);

	void savePartnerOffer(PartnerOffersStagingEntity entity);

	void deleteFinalPartnerOffer(PartnerOffersStagingEntity entity);

	List<PartnerOffersStagingEntity> getLiveApprovedPartnerOffer();
	
	//corporate
	
	CorporateStagingEntity getCorpOffer(Integer id);

	void saveCorpOffer(CorporateStagingEntity entity);

	void deleteFinalCorpOffer(CorporateStagingEntity entity);

	List<CorporateStagingEntity> getLiveApprovedCorp();

}

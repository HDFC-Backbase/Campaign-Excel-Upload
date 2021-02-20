package com.backbase.campaignupload.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.backbase.campaignupload.entity.CompanyFinalEntity;
import com.backbase.campaignupload.entity.CorporateAuditEntity;
import com.backbase.campaignupload.entity.CorporateFinalEntity;
import com.backbase.campaignupload.entity.CorporateStagingEntity;
import com.backbase.campaignupload.entity.PartnerAuditEntity;
import com.backbase.campaignupload.entity.PartnerOffersFinalEntity;
import com.backbase.campaignupload.entity.PartnerOffersStagingEntity;


public interface CampaignUploadService {

	void save(MultipartFile file, String sheetname, String uploadedBy, String filename, String makerip);

	List<PartnerOffersStagingEntity> getPartnerOffers();

	void saveCorporateoffer(MultipartFile file, String sheetname, String uploadedBy, String filename, String makerip);

	List<CorporateStagingEntity> getCorporateOffers();
	
	//corporate
	
	CorporateStagingEntity getCorpOffer(Integer id);

	void saveCorpOffer(CorporateStagingEntity entity);
	
	void saveCorpFinal(CorporateFinalEntity entity);

	CorporateStagingEntity getCorporateWithOutFileId(Integer id);
	
	CorporateFinalEntity getcorpFinalEntitybyStagId(CorporateStagingEntity entity);

	void deleteCORP(CorporateFinalEntity corpfinal);
	
	List<CorporateFinalEntity> findAllCORP();
	
	//partner

	void savePartnerOffer(PartnerOffersStagingEntity entity);
	
	PartnerOffersStagingEntity getPTWithOutFileId(Integer id);
	
	void savePTFinal(PartnerOffersFinalEntity entity);
	
	PartnerOffersFinalEntity getFinalEntitybyStagId(PartnerOffersStagingEntity entity);
	
	void deletePT(PartnerOffersFinalEntity ptfinal);
	
	List<PartnerOffersFinalEntity> findAllPT();
	
	// company
	CompanyFinalEntity getCompany(String name);
	
	
	//audit
	public void saveCorpaudit(CorporateAuditEntity entity);
	public void savePartnaudit(PartnerAuditEntity entity);



}

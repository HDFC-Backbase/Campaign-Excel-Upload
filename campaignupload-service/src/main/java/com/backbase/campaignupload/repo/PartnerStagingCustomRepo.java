package com.backbase.campaignupload.repo;

import java.util.List;

import com.backbase.campaignupload.entity.PartnerOffersStagingEntity;


public interface PartnerStagingCustomRepo {

	List<PartnerOffersStagingEntity> getLiveApprovedPartner();
	
	PartnerOffersStagingEntity getPTWithFileId(Integer id);
}

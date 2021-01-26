package com.backbase.campaignupload.repo;

import java.util.List;

import com.backbase.campaignupload.entity.PartnerOffersStagingEntity;

public interface PTStagCustomRepo {
	
	List<PartnerOffersStagingEntity> getLiveApprovedPT();

}

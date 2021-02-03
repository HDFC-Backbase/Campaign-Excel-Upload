package com.backbase.campaignupload.repo;

import com.backbase.campaignupload.entity.PartnerOffersStagingEntity;

public interface PTStagCustomRepo {
	
	PartnerOffersStagingEntity getPTWithOutFileId(Integer id);

}

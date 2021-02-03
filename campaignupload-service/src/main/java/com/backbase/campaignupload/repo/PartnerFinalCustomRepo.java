package com.backbase.campaignupload.repo;

import com.backbase.campaignupload.entity.PartnerOffersFinalEntity;
import com.backbase.campaignupload.entity.PartnerOffersStagingEntity;

public interface PartnerFinalCustomRepo {

	PartnerOffersFinalEntity getFinalEntitybyStagId(PartnerOffersStagingEntity entity);
}

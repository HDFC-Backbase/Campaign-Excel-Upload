package com.backbase.campaignupload.repo;

import com.backbase.campaignupload.entity.CorporateFinalEntity;
import com.backbase.campaignupload.entity.CorporateStagingEntity;

public interface CorporateFinalCustomRepo {
	CorporateFinalEntity getFinalEntitybyStagId(CorporateStagingEntity entity);
}

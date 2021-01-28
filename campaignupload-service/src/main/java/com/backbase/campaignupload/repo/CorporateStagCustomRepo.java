package com.backbase.campaignupload.repo;

import java.util.List;

import com.backbase.campaignupload.entity.CorporateStagingEntity;


public interface CorporateStagCustomRepo {
	
	List<CorporateStagingEntity> getLiveApprovedCorporate();
	
	CorporateStagingEntity getCorporateWithFileId(Integer id);
}

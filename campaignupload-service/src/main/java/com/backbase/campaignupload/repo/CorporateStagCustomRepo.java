package com.backbase.campaignupload.repo;

import com.backbase.campaignupload.entity.CorporateStagingEntity;


public interface CorporateStagCustomRepo {
	
	CorporateStagingEntity getCorporateWithOutFileId(Integer id);
}

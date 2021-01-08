package com.backbase.campaignupload.repo;

import com.backbase.campaignupload.entity.CompanyStagingEntity;

public interface CompanyCustomRepository {
	
	CompanyStagingEntity getCompany(String name) throws Exception;

}

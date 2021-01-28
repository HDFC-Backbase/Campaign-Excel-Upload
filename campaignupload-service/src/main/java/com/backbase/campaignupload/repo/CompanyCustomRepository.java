package com.backbase.campaignupload.repo;

import com.backbase.campaignupload.entity.CompanyFinalEntity;

public interface CompanyCustomRepository {
	
	CompanyFinalEntity getCompany(String name);

}

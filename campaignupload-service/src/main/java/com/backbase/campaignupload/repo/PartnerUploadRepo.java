package com.backbase.campaignupload.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backbase.campaignupload.entity.PartnerOffersStagingEntity;


public interface PartnerUploadRepo extends JpaRepository<PartnerOffersStagingEntity,Integer>{
	
}

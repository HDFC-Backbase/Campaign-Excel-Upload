package com.backbase.campaignupload.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backbase.campaignupload.entity.PartnerOffersStagingEntity;

public interface CampaignUploadRepo extends JpaRepository<PartnerOffersStagingEntity,Integer>{

}

package com.backbase.campaignupload.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backbase.campaignupload.entity.CampaignStagingEntity;

public interface CampaignUploadRepo extends JpaRepository<CampaignStagingEntity,Integer>{

}

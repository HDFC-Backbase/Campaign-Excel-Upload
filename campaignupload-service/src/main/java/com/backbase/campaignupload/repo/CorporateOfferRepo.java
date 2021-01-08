package com.backbase.campaignupload.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backbase.campaignupload.entity.CorporateStagingEntity;

public interface CorporateOfferRepo extends JpaRepository<CorporateStagingEntity,Integer>{

}

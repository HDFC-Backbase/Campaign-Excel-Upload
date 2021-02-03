package com.backbase.campaignupload.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backbase.campaignupload.entity.PartnerOffersFinalEntity;


public interface PartnerFinalUploadRepo extends JpaRepository<PartnerOffersFinalEntity, Integer>,PartnerFinalCustomRepo {

}

package com.backbase.campaignupload.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backbase.campaignupload.entity.PartnerOffersStagingEntity;

public interface PartnerOfferRepo extends JpaRepository<PartnerOffersStagingEntity,Integer>,PTStagCustomRepo{

}

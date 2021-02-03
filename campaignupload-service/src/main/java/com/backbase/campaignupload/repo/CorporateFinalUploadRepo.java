package com.backbase.campaignupload.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backbase.campaignupload.entity.CorporateFinalEntity;


public interface CorporateFinalUploadRepo extends JpaRepository<CorporateFinalEntity, Integer>,CorporateFinalCustomRepo {

}

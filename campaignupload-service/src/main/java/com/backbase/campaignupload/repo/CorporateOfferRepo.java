package com.backbase.campaignupload.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.backbase.campaignupload.entity.CorporateStagingEntity;

public interface CorporateOfferRepo extends JpaRepository<CorporateStagingEntity,Integer>,CorporateStagCustomRepo{

	@Query("SELECT u FROM CorporateStagingEntity u ORDER BY u.updatedon desc")
	public List<CorporateStagingEntity> findAll();
}

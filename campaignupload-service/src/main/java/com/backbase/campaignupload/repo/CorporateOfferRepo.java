package com.backbase.campaignupload.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.backbase.campaignupload.entity.CorporateStagingEntity;

public interface CorporateOfferRepo extends JpaRepository<CorporateStagingEntity,Integer>,CorporateStagCustomRepo{

	@Query(value="SELECT * FROM corporate_offer_staging u ORDER BY u.updated_on desc",nativeQuery=true)
	public List<CorporateStagingEntity> findAll();
}

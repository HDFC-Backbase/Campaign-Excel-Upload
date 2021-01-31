package com.backbase.campaignupload.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.backbase.campaignupload.entity.PartnerOffersStagingEntity;

public interface PartnerOfferRepo extends JpaRepository<PartnerOffersStagingEntity,Integer>,PTStagCustomRepo{

	@Query("SELECT u FROM PartnerOffersStagingEntity u ORDER BY u.updatedon desc")
	public List<PartnerOffersStagingEntity> findAll();
}

package com.backbase.campaignupload.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.backbase.campaignupload.entity.PartnerOffersStagingEntity;

public interface PartnerOfferRepo extends JpaRepository<PartnerOffersStagingEntity,Integer>,PTStagCustomRepo{

	@Query(value="SELECT * FROM partner_offer_staging u ORDER BY u.updated_on desc",nativeQuery=true)
	public List<PartnerOffersStagingEntity> findAll();
}

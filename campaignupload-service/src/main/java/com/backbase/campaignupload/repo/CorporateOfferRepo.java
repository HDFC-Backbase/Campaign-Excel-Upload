package com.backbase.campaignupload.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.backbase.campaignupload.entity.CorporateStagingEntity;

public interface CorporateOfferRepo extends JpaRepository<CorporateStagingEntity,Integer>,CorporateStagCustomRepo{
	
	@Query(value="select * from corporate_offer_staging crp where crp.id not in(select costg.id from corporate_offer_staging costg where costg.approval_status='Pending' and costg.file_id is not null) order by crp.updated_on desc",nativeQuery=true)
	public List<CorporateStagingEntity> findAll();
}

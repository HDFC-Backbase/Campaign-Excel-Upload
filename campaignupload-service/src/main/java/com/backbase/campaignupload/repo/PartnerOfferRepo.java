package com.backbase.campaignupload.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.backbase.campaignupload.entity.PartnerOffersStagingEntity;

public interface PartnerOfferRepo extends JpaRepository<PartnerOffersStagingEntity,Integer>,PTStagCustomRepo{
	
	@Query(value="select * from partner_offer_staging p where p.id not in(select pstg.id from partner_offer_staging pstg where pstg.pt_status='Pending' and pstg.file_id is not null and pstg.id not in(select prtin.id from partner_offer_staging prtin inner join partner_offer_final prtf on prtin.id=prtf.p_s_id) ) order by p.updated_on desc",nativeQuery=true)
	public List<PartnerOffersStagingEntity> findAll();
}

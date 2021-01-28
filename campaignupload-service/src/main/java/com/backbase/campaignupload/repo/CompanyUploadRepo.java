package com.backbase.campaignupload.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import com.backbase.campaignupload.entity.CompanyStagingEntity;


public interface CompanyUploadRepo extends JpaRepository<CompanyStagingEntity,Integer>,CompanyCustomRepository
{
	
    @Query("SELECT COUNT(u) FROM CompanyStagingEntity u WHERE u.company_Id = :companyId")
    Long countByCompany_Id(@Param("companyId") String companyId);

}

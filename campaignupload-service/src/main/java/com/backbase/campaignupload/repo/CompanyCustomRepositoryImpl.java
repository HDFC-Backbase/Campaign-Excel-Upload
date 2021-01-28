package com.backbase.campaignupload.repo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.backbase.campaignupload.entity.CompanyFinalEntity;



@Repository
public class CompanyCustomRepositoryImpl implements CompanyCustomRepository {

	private static final Logger logger = LoggerFactory.getLogger(CompanyCustomRepositoryImpl.class);

	@PersistenceContext
	EntityManager entityManager;

	@Override
	public CompanyFinalEntity getCompany(String name) {

		CompanyFinalEntity comp = null;
		try {
			Query query = entityManager.createQuery("from CompanyFinalEntity ce  where ce.company_Id =: companyId ");
			query.setParameter("companyId", name);
			comp = (CompanyFinalEntity) query.getResultList().get(0);
		} catch (Exception e) {
			logger.info("Exception while querying " + e.getMessage());
		} finally {
			if (entityManager != null && entityManager.isOpen()) {
				entityManager.close();
			}
		}
		return comp;
	}

}

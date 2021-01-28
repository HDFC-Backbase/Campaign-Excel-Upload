package com.backbase.campaignupload.repo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.backbase.campaignupload.entity.CorporateFinalEntity;
import com.backbase.campaignupload.entity.CorporateStagingEntity;

@Repository
public class CorporateFinalCustomRepoImpl implements CorporateFinalCustomRepo {

	private static final Logger LOGGER = LoggerFactory.getLogger(CorporateFinalCustomRepoImpl.class);

	@PersistenceContext
	EntityManager entityManager;

	@Override
	public CorporateFinalEntity getFinalEntitybyStagId(CorporateStagingEntity entity) {
		CorporateFinalEntity corporateFinalEntity=null;

		try {
			Query query = entityManager.createQuery(
					"from CorporateFinalEntity ce  where ce.corporateStagingEntity =: corporateStagingEntity");
			query.setParameter("corporateStagingEntity", entity);
			corporateFinalEntity = (CorporateFinalEntity) query.getSingleResult();
			LOGGER.info("Entity returned from DB " + corporateFinalEntity);
		} catch (Exception e) {
			LOGGER.info(e.getMessage());
		} finally {
			if (entityManager != null && entityManager.isOpen()) {
				entityManager.close();
			}
		}
		return corporateFinalEntity;
	}

}

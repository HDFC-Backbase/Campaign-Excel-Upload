package com.backbase.campaignupload.repo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.backbase.campaignupload.entity.CorporateStagingEntity;

@Repository
public class CorpFinalCustomRepoImpl implements CorpFinalCustomRepo {

	private static final Logger LOGGER = LoggerFactory.getLogger(CorpFinalCustomRepoImpl.class);

	@PersistenceContext
	EntityManager entityManager;

	@Override
	public void deleteCorpFinalByStagId(CorporateStagingEntity entity) {
		try {
			Query query = entityManager.createQuery(
					"delete from CorporateFinalEntity ce  where ce.corporateStagingEntity =: corporateStagingEntity ");
			query.setParameter("corporateStagingEntity", entity);
			int i = query.executeUpdate();
			LOGGER.info("No of Entities deleted from DB " + i);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.info(e.getMessage());
		} finally {
			if (entityManager != null && entityManager.isOpen()) {
				entityManager.close();
			}
		}

	}

}

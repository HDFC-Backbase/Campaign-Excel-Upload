package com.backbase.campaignupload.repo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.backbase.campaignupload.entity.PartnerOffersStagingEntity;

@Repository
public class PTFinalCustomRepoImpl implements PTFinalCustomRepo {

	private static final Logger LOGGER = LoggerFactory.getLogger(PTFinalCustomRepoImpl.class);

	@PersistenceContext
	EntityManager entityManager;

	@Override
	public void deletePTFinalByStagId(PartnerOffersStagingEntity entity) {
		try {
			Query query = entityManager.createQuery(
					"delete from PartnerOffersFinalEntity ce  where ce.partoffstagentity =: partoffstagentity ");
			query.setParameter("partoffstagentity", entity);
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

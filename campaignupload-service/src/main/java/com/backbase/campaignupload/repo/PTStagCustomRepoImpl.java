package com.backbase.campaignupload.repo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.backbase.campaignupload.entity.PartnerOffersStagingEntity;

@Repository
public class PTStagCustomRepoImpl implements PTStagCustomRepo {

	private static final Logger LOGGER = LoggerFactory.getLogger(PTStagCustomRepoImpl.class);

	@PersistenceContext
	EntityManager entityManager;

	@Override
	public PartnerOffersStagingEntity getPTWithOutFileId(Integer id) {
		PartnerOffersStagingEntity pt = null;
		LOGGER.info("PartnerOffersStagingEntity repo"+id);

		try {
			Query query = entityManager.createQuery("from PartnerOffersStagingEntity ce  where ce.id =: id ");
			query.setParameter("id", id);
			pt = (PartnerOffersStagingEntity) query.getSingleResult();
		} catch (Exception e) {
			LOGGER.info("Exception while querying " + e.getMessage());

		} finally {
			if (entityManager != null && entityManager.isOpen()) {
				entityManager.close();
			}
		}
		return pt;

	}
}

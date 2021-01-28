package com.backbase.campaignupload.repo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.backbase.campaignupload.entity.PartnerOffersFinalEntity;
import com.backbase.campaignupload.entity.PartnerOffersStagingEntity;


@Repository
public class PartnerFinalCustomRepoImpl implements PartnerFinalCustomRepo {

	private static final Logger LOGGER = LoggerFactory.getLogger(PartnerFinalCustomRepoImpl.class);

	@PersistenceContext
	EntityManager entityManager;


	@Override
	public PartnerOffersFinalEntity getFinalEntitybyStagId(PartnerOffersStagingEntity entity) {
		PartnerOffersFinalEntity partnerOffersFinalEntity = null;
		try
		{
			Query query = entityManager
					.createQuery("from PartnerOffersFinalEntity ce  where ce.partoffstagentity =: partoffstagentity ");
			query.setParameter("partoffstagentity", entity);
			partnerOffersFinalEntity = (PartnerOffersFinalEntity) query.getSingleResult();
			LOGGER.info("Entity Returned from DB " +partnerOffersFinalEntity);
			
		}catch(Exception e)
		{
			e.printStackTrace();
			LOGGER.info(e.getMessage());
		}
		 finally {
				if (entityManager != null && entityManager.isOpen()) {
					entityManager.close();
				}

			}

		return partnerOffersFinalEntity;
	}

}

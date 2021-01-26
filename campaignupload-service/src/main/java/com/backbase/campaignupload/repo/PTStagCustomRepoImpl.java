package com.backbase.campaignupload.repo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

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
	public List<PartnerOffersStagingEntity> getLiveApprovedPT() {
		List<PartnerOffersStagingEntity> partnerOffersStagingEntity = null;
		try {
			TypedQuery<PartnerOffersStagingEntity> query = entityManager.createQuery(
					"SELECT d FROM PartnerOffersFinalEntity e JOIN e.partoffstagentity d where e.approvalstatus='Approved' ",
					PartnerOffersStagingEntity.class);
			partnerOffersStagingEntity = query.getResultList();
		} catch (Exception e) {
			LOGGER.info(e.getMessage());
		} finally {
			if (entityManager != null && entityManager.isOpen()) {
				entityManager.close();
			}
		}

		return partnerOffersStagingEntity;
	}

}

package com.backbase.campaignupload.repo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.backbase.campaignupload.entity.CorporateStagingEntity;

@Repository
public class CorpStagCustomRepoImpl implements CorpStagCustomRepo {

	private static final Logger LOGGER = LoggerFactory.getLogger(PTStagCustomRepoImpl.class);

	@PersistenceContext
	EntityManager entityManager;

	@Override
	public List<CorporateStagingEntity> getLiveApprovedCorp() {
		List<CorporateStagingEntity> corporateStagingEntity = null;
		try {
			TypedQuery<CorporateStagingEntity> query = entityManager.createQuery(
					"SELECT d FROM CorporateFinalEntity e JOIN e.corporateStagingEntity d where e.approvalstatus='Approved' ",
					CorporateStagingEntity.class);
			corporateStagingEntity = query.getResultList();
		} catch (Exception e) {
			LOGGER.info(e.getMessage());
		} finally {
			if (entityManager != null && entityManager.isOpen()) {
				entityManager.close();
			}
		}

		return corporateStagingEntity;
	}

}

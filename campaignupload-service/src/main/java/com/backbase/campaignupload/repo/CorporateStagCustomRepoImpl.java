package com.backbase.campaignupload.repo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.backbase.campaignupload.entity.CorporateStagingEntity;


@Repository
public class CorporateStagCustomRepoImpl implements CorporateStagCustomRepo {

	private static final Logger LOGGER = LoggerFactory.getLogger(CorporateStagCustomRepoImpl.class);

	@PersistenceContext
	EntityManager entityManager;
	

	@Override
	public CorporateStagingEntity getCorporateWithOutFileId(Integer id) {
		CorporateStagingEntity corp = null;
		try {
			Query query = entityManager.createQuery("from CorporateStagingEntity ce  where ce.id =: id ");
			query.setParameter("id", id);
			corp = (CorporateStagingEntity) query.getSingleResult();
		} catch (Exception e) {
			LOGGER.info("Exception while querying " + e.getMessage());
		} finally {
			if (entityManager != null && entityManager.isOpen()) {
				entityManager.close();
			}
		}
		return corp;
	}

}

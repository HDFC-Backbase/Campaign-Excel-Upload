package com.backbase.campaignupload.repo;

import java.util.List;

import com.backbase.campaignupload.entity.CorporateStagingEntity;

public interface CorpStagCustomRepo {
	List<CorporateStagingEntity> getLiveApprovedCorp();
}

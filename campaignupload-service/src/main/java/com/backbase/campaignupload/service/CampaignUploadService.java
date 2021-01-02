package com.backbase.campaignupload.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.backbase.campaignupload.entity.CampaignStagingEntity;

public interface CampaignUploadService {
	
void save(MultipartFile file, String sheetname,String uploadedBy,String filename);
	
	List<CampaignStagingEntity> getAllCampanian();

}

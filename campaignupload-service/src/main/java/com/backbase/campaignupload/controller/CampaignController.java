package com.backbase.campaignupload.controller;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.backbase.campaignupload.entity.CampaignStagingEntity;
import com.backbase.campaignupload.exception.CustomBadRequestException;
import com.backbase.campaignupload.exception.CustomInternalServerException;
import com.backbase.campaignupload.helper.ExcelHelper;
import com.backbase.campaignupload.rest.spec.v1.campaignupload.Campaign;
import com.backbase.campaignupload.rest.spec.v1.campaignupload.CampaignuploadApi;
import com.backbase.campaignupload.rest.spec.v1.campaignupload.CampaignuploadGetResponseBody;
import com.backbase.campaignupload.rest.spec.v1.campaignupload.CampaignuploadPostResponseBody;
import com.backbase.campaignupload.rest.spec.v1.campaignupload.Header;
import com.backbase.campaignupload.service.CampaignUploadServiceImpl;

import liquibase.util.file.FilenameUtils;

@RestController
public class CampaignController implements CampaignuploadApi {
	
	private static final Logger logger = LoggerFactory.getLogger(CampaignController.class);

	@Autowired
	CampaignUploadServiceImpl fileService;

	@Value("${file.location}")
	private String dir;

	@Override
	public CampaignuploadGetResponseBody getCampaignupload(HttpServletRequest arg0, HttpServletResponse arg1) {
		logger.info("Request received to get data");

		// TODO Auto-generated method stub
		CampaignuploadGetResponseBody campaignuploadGetResponseBody = new CampaignuploadGetResponseBody();

		List<CampaignStagingEntity> compent = fileService.getAllCampanian();

		List<Header> headerslist = new ArrayList<Header>();

		List<Object> dataList = new ArrayList<Object>();

		setHeaders(headerslist, "header");
		setHeaders(headerslist, "offertext");
		setHeaders(headerslist, "approvalstatus");
		
		Header hdcoffertext = new Header();
		hdcoffertext.setId("logo");
		hdcoffertext.setSortable(false);
		hdcoffertext.setSearchable(false);
		hdcoffertext.setCheckbox(false);
		hdcoffertext.setImg(true);
		hdcoffertext.setLink(false);
		headerslist.add(hdcoffertext);
		compent.stream().forEach(ce -> {
			Campaign campaignresponse = new Campaign();
			campaignresponse.setHeader(ce.getHeader());
			campaignresponse.setLogo(ce.getLogo());
			campaignresponse.setoffertext(ce.getOffertext());
			campaignresponse.setApprovalstatus(ce.getApprovalstatus());
						dataList.add(campaignresponse);
		});

		campaignuploadGetResponseBody.setHeaders(headerslist);
		campaignuploadGetResponseBody.setData(dataList);
		return campaignuploadGetResponseBody;	}

	@Override
	public CampaignuploadPostResponseBody postCampaignupload(MultipartFile file, String uploadedBy, HttpServletRequest arg2,
			HttpServletResponse arg3) {
		logger.info("Request received to Upload data");

		CampaignuploadPostResponseBody campaignuploadPostResponseBody =new CampaignuploadPostResponseBody();
		String message = "";
		if (ExcelHelper.hasExcelFormat(file)) {
			try {
				String filename = saveFiletoLocation(file, uploadedBy);
				fileService.save(file, "CampaignData", uploadedBy, filename);
				message = "Uploaded the file successfully: " + file.getOriginalFilename();
				campaignuploadPostResponseBody.setStatuscode("200");
				campaignuploadPostResponseBody.setMessage(message);
				return campaignuploadPostResponseBody;
			} catch (Exception e) {
				logger.info(e.getMessage());
				throw new CustomInternalServerException(
						"Could not upload the file: " + file.getOriginalFilename() + "!");
			}
		} else
			throw new CustomBadRequestException("Please upload an excel file!");
	}

	public void setHeaders(List<Header> headerslist, String headerId) {

		Header header = new Header();
		header.setId(headerId);
		header.setSortable(true);
		header.setSearchable(true);
		header.setCheckbox(false);
		header.setImg(false);
		header.setLink(false);
		headerslist.add(header);
	}

	public String saveFiletoLocation(MultipartFile file, String uploadedBy) {

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String dateWithoutTime = dateFormat.format(new Date());

		String fileNameWithOutExt = FilenameUtils.removeExtension(file.getOriginalFilename());

		String filename = fileNameWithOutExt + "_" + dateWithoutTime + "_" + LocalDateTime.now().getHour() + "_"
				+ LocalDateTime.now().getMinute() + "_" + uploadedBy + ".xls";

		Path filepath = Paths.get(dir.toString(), filename.trim());
		try {
			file.transferTo(filepath);
		} catch (IllegalStateException e) {
			logger.info(e.getMessage());
		} catch (IOException e) {
			logger.info(e.getMessage());
		}

		return filename;
	}

}

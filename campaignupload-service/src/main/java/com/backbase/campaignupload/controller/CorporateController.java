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

import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.backbase.campaignupload.entity.CompanyFinalEntity;
import com.backbase.campaignupload.entity.CorporateFinalEntity;
import com.backbase.campaignupload.entity.CorporateStagingEntity;
import com.backbase.campaignupload.exception.CustomBadRequestException;
import com.backbase.campaignupload.exception.CustomInternalServerException;
import com.backbase.campaignupload.helper.ExcelHelper;
import com.backbase.campaignupload.pojo.CampaignPutResponse;
import com.backbase.campaignupload.pojo.CorporateOfferPutRequest;
import com.backbase.campaignupload.rest.spec.v1.corporateoffers.CorporateOffer;
import com.backbase.campaignupload.rest.spec.v1.corporateoffers.CorporateoffersApi;
import com.backbase.campaignupload.rest.spec.v1.corporateoffers.CorporateoffersGetResponseBody;
import com.backbase.campaignupload.rest.spec.v1.corporateoffers.CorporateoffersPostResponseBody;
import com.backbase.campaignupload.rest.spec.v1.corporateoffers.Header;
import com.backbase.campaignupload.service.CampaignUploadService;
import com.backbase.campaignupload.service.CampaignUploadServiceImpl;
import com.backbase.validate.jwt.ValidateJwt;

import liquibase.util.file.FilenameUtils;

@RestController
public class CorporateController implements CorporateoffersApi {

	private static final Logger logger = LoggerFactory.getLogger(CorporateController.class);
	
	private static final String APPROVED = "Approved";

	private static final String REJECTED = "Rejected";

	@Autowired
	CampaignUploadService campaignUploadService;

	@Value("${file.location}")
	private String dir;

	@Override
	public CorporateoffersGetResponseBody getCorporateoffers(HttpServletRequest request, HttpServletResponse arg1) {
		logger.info("Request received to get Relation manager uploaded data");
		logger.info("Authorization header " + request.getHeader("authorization"));

		String authorization = request.getHeader("authorization").substring(7);

		String subject = ValidateJwt.validateJwt(authorization, "JWTSecretKeyDontUseInProduction!");

		logger.info("Subject in JWT " + subject);

		CorporateoffersGetResponseBody corporateoffersGetResponseBody = new CorporateoffersGetResponseBody();

		List<Header> headerslist = new ArrayList<Header>();

		List<Object> dataList = new ArrayList<Object>();

		setHeaders(headerslist, "title");
		setHeaders(headerslist, "companyId");

		Header hdclogo = new Header();
		hdclogo.setField("logo");
		hdclogo.setType("imageColumn");
		headerslist.add(hdclogo);

		Header hdcoffertext = new Header();
		hdcoffertext.setField("offerText");
		hdcoffertext.setType("largeTextColumn");
		headerslist.add(hdcoffertext);

		Header hdapproval = new Header();
		hdapproval.setField("approvalStatus");
		hdapproval.setEditable(false);
		headerslist.add(hdapproval);

		Header hdId = new Header();
		hdId.setField("id");
		hdId.setHide(true);
		headerslist.add(hdId);

		List<CorporateStagingEntity> corstg = campaignUploadService.getLiveApprovedCorp();

		corstg.stream().forEach(corp -> {
			CorporateOffer corpdata = new CorporateOffer();
			corpdata.setTitle(corp.getTitle());
			corpdata.setLogo(corp.getLogo());
			corpdata.setOffertext(corp.getOffertext());
			corpdata.setCompanyid(corp.getCompanyId());
			corpdata.setApprovalstatus(corp.getApprovalstatus());
			corpdata.setId(corp.getId());
			corpdata.setLive(true);
			dataList.add(corpdata);

		});

		List<CorporateStagingEntity> allcorp = campaignUploadService.getCorporateOffers();
		allcorp.removeAll(corstg);
		allcorp.stream().forEach(corp -> {
			CorporateOffer corpdata = new CorporateOffer();
			corpdata.setTitle(corp.getTitle());
			corpdata.setLogo(corp.getLogo());
			corpdata.setOffertext(corp.getOffertext());
			corpdata.setCompanyid(corp.getCompanyId());
			corpdata.setApprovalstatus(corp.getApprovalstatus());
			corpdata.setId(corp.getId());
			dataList.add(corpdata);

		});

		corporateoffersGetResponseBody.setHeaders(headerslist);
		corporateoffersGetResponseBody.setData(dataList);

		return corporateoffersGetResponseBody;

	}

	@Override
	public CorporateoffersPostResponseBody postCorporateoffers(MultipartFile file, HttpServletRequest request,
			HttpServletResponse arg3) {
		logger.info("Request Received to Upload Caorporate offer data");
		logger.info("Authorization header " + request.getHeader("authorization"));

		String authorization = request.getHeader("authorization").substring(7);

		String uploadedBy = ValidateJwt.validateJwt(authorization, "JWTSecretKeyDontUseInProduction!");

		logger.info("Subject in JWT " + uploadedBy);
		CorporateoffersPostResponseBody corporateoffersPostResponseBody = new CorporateoffersPostResponseBody();
		String message = "";
		if (ExcelHelper.hasExcelFormat(file)) {
			try {
				String filename = saveFiletoLocation(file, uploadedBy);
				campaignUploadService.savecarpoateoffer(file, "CorporateOffer", uploadedBy, filename);
				message = "Uploaded the file successfully: " + file.getOriginalFilename();
				corporateoffersPostResponseBody.setStatuscode("200");
				corporateoffersPostResponseBody.setMessage(message);
				return corporateoffersPostResponseBody;
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
		header.setField(headerId);
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

	
	@PutMapping
	public CampaignPutResponse putCompanies(@RequestBody CorporateOfferPutRequest requestBody, HttpServletRequest arg1,
			HttpServletResponse arg2) {

		logger.info("Request received to update data "+requestBody);

		CampaignPutResponse campaignPutResponse = new CampaignPutResponse();

		for (CorporateOffer corpoff : requestBody.getUpdates()) {
			if (corpoff.getId() > 0) {
				CorporateStagingEntity corpstg = campaignUploadService.getCorpOffer(corpoff.getId());
				corpstg.setCompanyId(corpoff.getCompanyid());
				corpstg.setTitle(corpoff.getTitle());
				corpstg.setLogo(corpoff.getLogo());
				corpstg.setOffertext(corpoff.getOffertext());
				corpstg.setApprovalstatus(CampaignUploadServiceImpl.PENDING);
				corpstg.setId(corpoff.getId());
				campaignUploadService.saveCorpOffer(corpstg);
			}else {
				CorporateStagingEntity corpstg = new CorporateStagingEntity();
				corpstg.setCompanyId(corpoff.getCompanyid());
				corpstg.setTitle(corpoff.getTitle());
				corpstg.setLogo(corpoff.getLogo());
				corpstg.setOffertext(corpoff.getOffertext());
				corpstg.setApprovalstatus(CampaignUploadServiceImpl.PENDING);
				campaignUploadService.saveCorpOffer(corpstg);
			}
		}

		campaignPutResponse.setMessage("Successfully update data in table");
		campaignPutResponse.setStatuscode(HttpStatus.SC_OK);

		return campaignPutResponse;
	}
	
	@PostMapping("/record/{id}")
	public CampaignPutResponse postRecord(@PathVariable String id, @RequestParam("action") String action,
			HttpServletRequest arg1, HttpServletResponse arg2) {

		logger.info("Request received to approve record data " + id);

		if (action.equalsIgnoreCase("A")) {
			CorporateStagingEntity corpstg = campaignUploadService.getCorporateWithFileId(Integer.parseInt(id));
			corpstg.setApprovalstatus(APPROVED);
			logger.info("CorporateStagingEntity entity going for save " + corpstg);
			campaignUploadService.saveCorpOffer(corpstg);
			
			CorporateFinalEntity corpfinal =campaignUploadService.getcorpFinalEntitybyStagId(corpstg);
			CompanyFinalEntity cmfinal=campaignUploadService.getCompany(corpstg.getCompanyId());

			if (corpstg.getCorpfileApproveEntity() == null && corpfinal==null) {
				CorporateFinalEntity corpfinals = new CorporateFinalEntity();
				
				corpfinals.setTitle(corpstg.getTitle());
				corpfinals.setLogo(corpstg.getLogo());
				corpfinals.setOffertext(corpstg.getOffertext());
				corpfinals.setApprovalstatus(APPROVED);
				corpfinals.setCorporateStagingEntity(corpstg);		
				if(cmfinal!=null)
					corpfinals.setCompanyfinalEntity(cmfinal);
				else
					throw new CustomBadRequestException("No Company Mapping Found");
				logger.info("CorporateFinalEntity entity going for save " + corpfinal);
				campaignUploadService.saveCorpFinal(corpfinals);
			}else {
				if(cmfinal!=null)
					corpfinal.setCompanyfinalEntity(cmfinal);
				else
					throw new CustomBadRequestException("No Company Mapping Found");
				corpfinal.setTitle(corpstg.getTitle());
				corpfinal.setLogo(corpstg.getLogo());
				corpfinal.setOffertext(corpstg.getOffertext());
				corpfinal.setApprovalstatus(APPROVED);
				
				logger.info("BAnnerFinalEntity entity going for save " + corpfinal);
				campaignUploadService.saveCorpFinal(corpfinal);
			}

		}

		else if (action.equalsIgnoreCase("R")) {
			CorporateStagingEntity corpstg = campaignUploadService.getCorporateWithFileId(Integer.parseInt(id));
			corpstg.setApprovalstatus(REJECTED);
			logger.info("YTStagingEntity entity going for save " + corpstg);
			campaignUploadService.saveCorpOffer(corpstg);
		}

		CampaignPutResponse campaignPutResponse = new CampaignPutResponse();

		campaignPutResponse.setMessage("Successfully update data in table");
		campaignPutResponse.setStatuscode(HttpStatus.SC_OK);

		return campaignPutResponse;
	}

}

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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

		logger.info("Request received to update data");

		logger.info("CompaniesPutRequestBody " + requestBody);

		CampaignPutResponse campaignPutResponse = new CampaignPutResponse();

		for (CorporateOffer prtoffer : requestBody.getUpdates()) {
			CorporateStagingEntity prtstag = campaignUploadService.getCorpOffer(prtoffer.getId());
			prtstag.setTitle(prtoffer.getTitle());
			prtstag.setLogo(prtoffer.getLogo());
			prtstag.setOffertext(prtoffer.getOffertext());
			prtstag.setApprovalstatus(CampaignUploadServiceImpl.PENDING);
			prtstag.setId(prtoffer.getId());
			campaignUploadService.saveCorpOffer(prtstag);
			//campaignUploadService.deleteFinalCorpOffer(prtstag);
		}

		campaignPutResponse.setMessage("Successfully update data in table");
		campaignPutResponse.setStatuscode(HttpStatus.SC_OK);

		return campaignPutResponse;
	}

}

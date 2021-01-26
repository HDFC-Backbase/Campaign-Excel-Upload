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

import com.backbase.campaignupload.entity.PartnerOffersStagingEntity;
import com.backbase.campaignupload.exception.CustomBadRequestException;
import com.backbase.campaignupload.exception.CustomInternalServerException;
import com.backbase.campaignupload.helper.ExcelHelper;
import com.backbase.campaignupload.pojo.CampaignPutResponse;
import com.backbase.campaignupload.pojo.PartnerOfferPutRequest;
import com.backbase.campaignupload.rest.spec.v1.partneroffers.Header;
import com.backbase.campaignupload.rest.spec.v1.partneroffers.PartnerOffer;
import com.backbase.campaignupload.rest.spec.v1.partneroffers.PartneroffersApi;
import com.backbase.campaignupload.rest.spec.v1.partneroffers.PartneroffersGetResponseBody;
import com.backbase.campaignupload.rest.spec.v1.partneroffers.PartneroffersPostResponseBody;
import com.backbase.campaignupload.service.CampaignUploadService;
import com.backbase.campaignupload.service.CampaignUploadServiceImpl;
import com.backbase.validate.jwt.ValidateJwt;

import liquibase.util.file.FilenameUtils;

@RestController
public class PartnerOffersController implements PartneroffersApi {

	private static final Logger logger = LoggerFactory.getLogger(PartnerOffersController.class);

	@Autowired
	CampaignUploadService campaignUploadService;

	@Value("${file.location}")
	private String dir;

	@Override
	public PartneroffersGetResponseBody getPartneroffers(HttpServletRequest request, HttpServletResponse arg1) {
		logger.info("Request received to get data");

		logger.info("Authorization header " + request.getHeader("authorization"));

		String authorization = request.getHeader("authorization").substring(7);

		String subject = ValidateJwt.validateJwt(authorization, "JWTSecretKeyDontUseInProduction!");

		logger.info("Subject in JWT " + subject);
		// TODO Auto-generated method stub
		PartneroffersGetResponseBody partneroffersGetResponseBody = new PartneroffersGetResponseBody();

		List<Header> headerslist = new ArrayList<Header>();

		List<Object> dataList = new ArrayList<Object>();

		setHeaders(headerslist, "title");

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

		List<PartnerOffersStagingEntity> ptstg = campaignUploadService.getLiveApprovedPartnerOffer();

		ptstg.stream().forEach(ce -> {
			PartnerOffer partnerofferresponse = new PartnerOffer();
			partnerofferresponse.setTitle(ce.getTitle());
			partnerofferresponse.setLogo(ce.getLogo());
			partnerofferresponse.setOffertext(ce.getOffertext());
			partnerofferresponse.setApprovalstatus(ce.getApprovalstatus());
			partnerofferresponse.setId(ce.getId());
			partnerofferresponse.setLive(true);
			dataList.add(partnerofferresponse);

		});

		List<PartnerOffersStagingEntity> compent = campaignUploadService.getPartnerOffers();
		compent.removeAll(ptstg);
		compent.stream().forEach(ce -> {
			PartnerOffer partnerofferresponse = new PartnerOffer();
			partnerofferresponse.setTitle(ce.getTitle());
			partnerofferresponse.setLogo(ce.getLogo());
			partnerofferresponse.setOffertext(ce.getOffertext());
			partnerofferresponse.setApprovalstatus(ce.getApprovalstatus());
			partnerofferresponse.setId(ce.getId());
			dataList.add(partnerofferresponse);

		});
		partneroffersGetResponseBody.setHeaders(headerslist);
		partneroffersGetResponseBody.setData(dataList);
		return partneroffersGetResponseBody;
	}

	@Override
	public PartneroffersPostResponseBody postPartneroffers(MultipartFile file, HttpServletRequest request,
			HttpServletResponse arg3) {

		// TODO Auto-generated method stub
		logger.info("Request received to Upload data");
		logger.info("Authorization header " + request.getHeader("authorization"));

		String authorization = request.getHeader("authorization").substring(7);

		String uploadedBy = ValidateJwt.validateJwt(authorization, "JWTSecretKeyDontUseInProduction!");

		logger.info("Subject in JWT " + uploadedBy);
		PartneroffersPostResponseBody partneroffersPostResponseBody = new PartneroffersPostResponseBody();
		String message = "";
		if (ExcelHelper.hasExcelFormat(file)) {
			try {
				String filename = saveFiletoLocation(file, uploadedBy);
				campaignUploadService.save(file, "PartnerOffer", uploadedBy, filename);
				message = "Uploaded the file successfully: " + file.getOriginalFilename();
				partneroffersPostResponseBody.setStatuscode("200");
				partneroffersPostResponseBody.setMessage(message);
				return partneroffersPostResponseBody;
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
	public CampaignPutResponse putCompanies(@RequestBody PartnerOfferPutRequest requestBody, HttpServletRequest arg1,
			HttpServletResponse arg2) {

		logger.info("Request received to update data");

		logger.info("CompaniesPutRequestBody " + requestBody);

		CampaignPutResponse campaignPutResponse = new CampaignPutResponse();

		for (PartnerOffer prtoffer : requestBody.getUpdates()) {
			PartnerOffersStagingEntity prtstag = campaignUploadService.getPartnerOffer(prtoffer.getId());
			prtstag.setTitle(prtoffer.getTitle());
			prtstag.setLogo(prtoffer.getLogo());
			prtstag.setOffertext(prtoffer.getOffertext());
			prtstag.setApprovalstatus(CampaignUploadServiceImpl.PENDING);
			prtstag.setId(prtoffer.getId());
			campaignUploadService.savePartnerOffer(prtstag);
			//campaignUploadService.deleteFinalPartnerOffer(prtstag);
		}

		campaignPutResponse.setMessage("Successfully update data in table");
		campaignPutResponse.setStatuscode(HttpStatus.SC_OK);

		return campaignPutResponse;
	}
}

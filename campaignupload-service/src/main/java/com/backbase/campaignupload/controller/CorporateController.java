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
import org.springframework.web.bind.annotation.DeleteMapping;
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

	private static final String DELETED = "Deleted";

	private static final String DELETE_PENDING = "Delete Pending";

	private static final String APPROVED = "Approved";

	private static final String REJECTED = "Rejected";

	@Autowired
	CampaignUploadService campaignUploadService;

	@Value("${file.location}")
	private String dir;

	@Value("${role.maker}")
	private String maker;

	@Value("${role.checker}")
	private String checker;

	@Override
	public CorporateoffersGetResponseBody getCorporateoffers(HttpServletRequest request, HttpServletResponse arg1) {

		logger.info("Request received to get Corporated Uploaded data");

		logger.info("Authorization header " + request.getHeader("authorization"));

		String authorization = request.getHeader("authorization").substring(7);

		String subject = ValidateJwt.validateJwt(authorization, "JWTSecretKeyDontUseInProduction!");

		List<String> role = ValidateJwt.getRole(authorization, "JWTSecretKeyDontUseInProduction!");

		logger.info("User in JWT " + subject);

		logger.info("Role in JWT " + role);

		CorporateoffersGetResponseBody corporateoffersGetResponseBody = new CorporateoffersGetResponseBody();

		List<Header> headerslist = new ArrayList<Header>();

		List<Object> dataList = new ArrayList<Object>();

		setHeaders(headerslist, "title", role, checker);
		setHeaders(headerslist, "companyId", role, checker);

		Header hdclogo = new Header();
		hdclogo.setField("logo");
		hdclogo.setType("imageColumn");
		if (role.contains(checker))
			hdclogo.setEditable(false);
		headerslist.add(hdclogo);

		Header hdcoffertext = new Header();
		hdcoffertext.setField("offerText");
		hdcoffertext.setType("largeTextColumn");
		if (role.contains(checker))
			hdcoffertext.setEditable(false);
		headerslist.add(hdcoffertext);

		Header hdapproval = new Header();
		hdapproval.setField("approvalStatus");
		hdapproval.setEditable(false);
		headerslist.add(hdapproval);

		Header hdcreatedBy = new Header();
		hdcreatedBy.setField("createdBy");
		hdcreatedBy.setEditable(false);
		headerslist.add(hdcreatedBy);

		Header hdupdatedBy = new Header();
		hdupdatedBy.setField("updatedBy");
		hdupdatedBy.setEditable(false);
		headerslist.add(hdupdatedBy);

		Header hdId = new Header();
		hdId.setField("id");
		hdId.setHide(true);
		if (role.contains(checker))
			hdId.setEditable(false);
		headerslist.add(hdId);

		Header hdlive = new Header();
		hdlive.setHeaderName("action");
		hdlive.setField("live");
		hdlive.setType("liveColumn");
		if (role.contains(maker))
			hdlive.setHide(true);
		headerslist.add(hdlive);

		List<CorporateStagingEntity> allcorp = campaignUploadService.getCorporateOffers();
		allcorp.stream().forEach(corp -> {
			CorporateOffer corpdata = new CorporateOffer();
			corpdata.setTitle(corp.getTitle());
			corpdata.setLogo(corp.getLogo());
			corpdata.setOffertext(corp.getOffertext());
			corpdata.setCompanyid(corp.getCompanyId());
			corpdata.setApprovalstatus(corp.getApprovalstatus());
			corpdata.setCreatedBy(corp.getCreatedBy());
			corpdata.setUpdatedBy(corp.getUpdatedBy());
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

		String makerip = request.getRemoteAddr();
		logger.info(" CorporateoffersPostResponseBody ip  " + makerip);

		logger.info("Authorization header " + request.getHeader("authorization"));

		String authorization = request.getHeader("authorization").substring(7);

		String uploadedBy = ValidateJwt.validateJwt(authorization, "JWTSecretKeyDontUseInProduction!");

		logger.info("User in JWT " + uploadedBy);

		List<String> role = ValidateJwt.getRole(authorization, "JWTSecretKeyDontUseInProduction!");

		logger.info("Role in JWT " + role);

		if (role.contains(maker)) {
			CorporateoffersPostResponseBody corporateoffersPostResponseBody = new CorporateoffersPostResponseBody();
			String message = "";
			if (ExcelHelper.hasExcelFormat(file)) {
				try {
					String filename = saveFiletoLocation(file, uploadedBy);
					campaignUploadService.saveCorporateoffer(file, "CorporateOffer", uploadedBy, filename, makerip);
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
		} else
			throw new CustomBadRequestException("Maker can only edit data");
	}

	public void setHeaders(List<Header> headerslist, String headerId, List<String> role, String checker) {

		Header header = new Header();
		header.setField(headerId);
		if (role.contains(checker))
			header.setEditable(false);
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
	public CampaignPutResponse putCompanies(@RequestBody CorporateOfferPutRequest requestBody,
			HttpServletRequest request, HttpServletResponse arg2) {

		logger.info("Request received to update data " + requestBody);

		logger.info("Authorization header " + request.getHeader("authorization"));

		String makerip = request.getRemoteAddr();
		logger.info("putCompanies ip  " + makerip);

		String authorization = request.getHeader("authorization").substring(7);

		String subject = ValidateJwt.validateJwt(authorization, "JWTSecretKeyDontUseInProduction!");

		logger.info("User in JWT " + subject);

		List<String> role = ValidateJwt.getRole(authorization, "JWTSecretKeyDontUseInProduction!");

		logger.info("Role in JWT " + role);

		if (role.contains(maker)) {

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
					corpstg.setCreatedBy(subject);
					corpstg.setUpdatedBy("-");
					corpstg.setMakerip(makerip);
					corpstg.setCheckerip("-");
					campaignUploadService.saveCorpOffer(corpstg);
				} else {
					CorporateStagingEntity corpstg = new CorporateStagingEntity();
					corpstg.setCompanyId(corpoff.getCompanyid());
					corpstg.setTitle(corpoff.getTitle());
					corpstg.setLogo(corpoff.getLogo());
					corpstg.setOffertext(corpoff.getOffertext());
					corpstg.setApprovalstatus(CampaignUploadServiceImpl.PENDING);
					corpstg.setCreatedBy(subject);
					corpstg.setUpdatedBy("-");
					corpstg.setMakerip(makerip);
					corpstg.setCheckerip("-");
					campaignUploadService.saveCorpOffer(corpstg);
				}
			}

			campaignPutResponse.setMessage("Successfully update data in table");
			campaignPutResponse.setStatuscode(HttpStatus.SC_OK);

			return campaignPutResponse;
		} else
			throw new CustomBadRequestException("Maker can only edit data");
	}

	@PostMapping("/record/{id}")
	public CampaignPutResponse postRecord(@PathVariable String id, @RequestParam("action") String action,
			HttpServletRequest request, HttpServletResponse arg2) {

		logger.info("Request received to approve record data " + id);

		logger.info("Authorization header " + request.getHeader("authorization"));

		String checkerip = request.getRemoteAddr();
		logger.info("postRecord ip  " + checkerip);

		String authorization = request.getHeader("authorization").substring(7);

		String subject = ValidateJwt.validateJwt(authorization, "JWTSecretKeyDontUseInProduction!");

		logger.info("User in JWT " + subject);

		List<String> role = ValidateJwt.getRole(authorization, "JWTSecretKeyDontUseInProduction!");

		logger.info("Role in JWT " + role);

		if (role.contains(checker)) {

			if (id != null && !id.equals(null)) {
				CorporateStagingEntity corpstg = campaignUploadService.getCorporateWithOutFileId(Integer.parseInt(id));
				if (corpstg != null) {
					CorporateFinalEntity corpfinal = campaignUploadService.getcorpFinalEntitybyStagId(corpstg);

					if (action.equalsIgnoreCase("A")) {
						corpstg.setApprovalstatus(APPROVED);
						corpstg.setUpdatedBy(subject);
						corpstg.setCheckerip(checkerip);
						logger.info("CorporateStagingEntity entity going for save " + corpstg);
						campaignUploadService.saveCorpOffer(corpstg);
						CompanyFinalEntity cmfinal = campaignUploadService.getCompany(corpstg.getCompanyId());

						if (corpstg.getCorpfileApproveEntity() == null && corpfinal == null) {
							CorporateFinalEntity corpfinals = new CorporateFinalEntity();
							corpfinals.setTitle(corpstg.getTitle());
							corpfinals.setLogo(corpstg.getLogo());
							corpfinals.setOffertext(corpstg.getOffertext());
							corpfinals.setApprovalstatus(APPROVED);
							corpfinals.setCreatedBy(corpstg.getCreatedBy());
							corpfinals.setUpdatedBy(subject);
							corpfinals.setCheckerip(checkerip);
							corpfinals.setMakerip(corpstg.getMakerip());
							corpfinals.setCorporateStagingEntity(corpstg);
							if (cmfinal != null)
								corpfinals.setCompanyfinalEntity(cmfinal);
							else
								throw new CustomBadRequestException("No Company Mapping Found");
							logger.info("CorporateFinalEntity entity going for save " + corpfinals);
							campaignUploadService.saveCorpFinal(corpfinals);
						} else {
							if (cmfinal != null)
								corpfinal.setCompanyfinalEntity(cmfinal);
							else
								throw new CustomBadRequestException("No Company Mapping Found");
							corpfinal.setTitle(corpstg.getTitle());
							corpfinal.setLogo(corpstg.getLogo());
							corpfinal.setOffertext(corpstg.getOffertext());
							corpfinal.setCreatedBy(corpstg.getCreatedBy());
							corpfinal.setUpdatedBy(subject);
							corpfinal.setApprovalstatus(APPROVED);
							corpfinal.setCheckerip(checkerip);
							corpfinal.setMakerip(corpstg.getMakerip());

							logger.info("CorporateFinalEntity entity going for save " + corpfinal);
							campaignUploadService.saveCorpFinal(corpfinal);
						}
					} else if (action.equalsIgnoreCase("R")) {
						if(corpstg.getApprovalstatus().equals(DELETE_PENDING))
							corpstg.setApprovalstatus(APPROVED);
						else
							corpstg.setApprovalstatus(REJECTED);
						corpstg.setUpdatedBy(subject);
						corpstg.setCheckerip(checkerip);
						logger.info("CorporateStagingEntity entity going for save " + corpstg);
						campaignUploadService.saveCorpOffer(corpstg);
					} else if (action.equals("D")) {
						corpstg.setApprovalstatus(DELETED);
						corpstg.setUpdatedBy(subject);
						corpstg.setCheckerip(checkerip);
						logger.info("CorporateStagingEntity entity going for delete " + corpstg);
						campaignUploadService.saveCorpOffer(corpstg);
						if (corpfinal != null)
							campaignUploadService.delete(corpfinal);
					}
				} else
					throw new CustomBadRequestException("No staging entity found with Id " + id);
			} else
				throw new CustomBadRequestException("Id cannot be null");

			CampaignPutResponse campaignPutResponse = new CampaignPutResponse();

			campaignPutResponse.setMessage("Successfully update data in table");
			campaignPutResponse.setStatuscode(HttpStatus.SC_OK);

			return campaignPutResponse;
		} else
			throw new CustomBadRequestException("Checker can only Approve data");
	}

	@DeleteMapping("/{id}")
	public CampaignPutResponse deleteCorp(@PathVariable String id, HttpServletRequest request,
			HttpServletResponse arg2) {

		logger.info("Request received to delete data");

		logger.info("Authorization header " + request.getHeader("authorization"));

		String makerip = request.getRemoteAddr();

		logger.info("Corporate Delete Record Maker ip  " + makerip);

		String authorization = request.getHeader("authorization").substring(7);

		String subject = ValidateJwt.validateJwt(authorization, "JWTSecretKeyDontUseInProduction!");

		List<String> role = ValidateJwt.getRole(authorization, "JWTSecretKeyDontUseInProduction!");

		logger.info("User in JWT " + subject);

		logger.info("Role in JWT " + role);

		if (role.contains(maker)) {

			if (id != null && !id.equals(null)) {
				CorporateStagingEntity corpstg = campaignUploadService.getCorporateWithOutFileId(Integer.parseInt(id));

				if (corpstg == null || corpstg.equals(null))
					throw new CustomBadRequestException("No entity found with id " + id);

				if (corpstg.getApprovalstatus().equals(DELETE_PENDING))
					throw new CustomBadRequestException("Record is already in pending state");

				if (corpstg != null) {
					corpstg.setApprovalstatus(DELETE_PENDING);
					corpstg.setUpdatedBy(subject);
					corpstg.setMakerip(makerip);
					corpstg.setCheckerip("-");
					campaignUploadService.saveCorpOffer(corpstg);
				}

				CampaignPutResponse youtubePutResponse = new CampaignPutResponse();
				youtubePutResponse.setMessage("Successfully updated data in table");
				youtubePutResponse.setStatuscode(HttpStatus.SC_OK);

				return youtubePutResponse;
			} else
				throw new CustomBadRequestException("Id cannot be null");
		} else
			throw new CustomBadRequestException("Maker can only edit data");

	}

}

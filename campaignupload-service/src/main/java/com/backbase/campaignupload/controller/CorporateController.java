package com.backbase.campaignupload.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.backbase.buildingblocks.presentation.errors.BadRequestException;
import com.backbase.buildingblocks.presentation.errors.InternalServerErrorException;
import com.backbase.campaignupload.entity.CompanyFinalEntity;
import com.backbase.campaignupload.entity.CorporateFinalEntity;
import com.backbase.campaignupload.entity.CorporateStagingEntity;
import com.backbase.campaignupload.exception.CustomBadRequestException;
import com.backbase.campaignupload.exception.CustomInternalServerException;
import com.backbase.campaignupload.reader.ExcelReader;
import com.backbase.campaignupload.rest.spec.v1.corporateoffers.Corporate;
import com.backbase.campaignupload.rest.spec.v1.corporateoffers.CorporateoffersApi;
import com.backbase.campaignupload.rest.spec.v1.corporateoffers.CorporateoffersGetResponseBody;
import com.backbase.campaignupload.rest.spec.v1.corporateoffers.CorporateoffersPostResponseBody;
import com.backbase.campaignupload.rest.spec.v1.corporateoffers.CorporateoffersPutRequestBody;
import com.backbase.campaignupload.rest.spec.v1.corporateoffers.CorporateoffersPutResponseBody;
import com.backbase.campaignupload.rest.spec.v1.corporateoffers.Header;
import com.backbase.campaignupload.rest.spec.v1.corporateoffers.IdDeleteResponseBody;
import com.backbase.campaignupload.rest.spec.v1.corporateoffers.IdPostResponseBody;
import com.backbase.campaignupload.rest.spec.v1.corporateoffers.Update;
import com.backbase.campaignupload.service.CampaignUploadService;
import com.backbase.validate.jwt.ValidateJwt;

import liquibase.util.file.FilenameUtils;

@RestController
public class CorporateController implements CorporateoffersApi {

	private static final String PENDING = "Pending";

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

	@Value("${excelfile.corporate.filename}")
	private String corpfilename;

	@Value("${excelfile.corporate.sheetname}")
	private String corpsheetname;

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

		// List<Header> headerslist = new ArrayList<Header>();

		List<Corporate> dataList = new ArrayList<Corporate>();

		/*
		 * setHeaders(headerslist, "title", role, checker); setHeaders(headerslist,
		 * "companyId", role, checker);
		 */

		/*
		 * Header hdclogo = new Header(); hdclogo.setField("logo");
		 * hdclogo.setType("imageColumn"); if (role.contains(checker))
		 * hdclogo.setEditable(false); headerslist.add(hdclogo);
		 * 
		 * Header hdcoffertext = new Header(); hdcoffertext.setField("offerText");
		 * hdcoffertext.setType("largeTextColumn"); if (role.contains(checker))
		 * hdcoffertext.setEditable(false); headerslist.add(hdcoffertext);
		 * 
		 * Header hdapproval = new Header(); hdapproval.setField("approvalStatus");
		 * hdapproval.setEditable(false); headerslist.add(hdapproval);
		 * 
		 * Header hdcreatedBy = new Header(); hdcreatedBy.setField("createdBy");
		 * hdcreatedBy.setEditable(false); headerslist.add(hdcreatedBy);
		 * 
		 * Header hdupdatedBy = new Header(); hdupdatedBy.setField("updatedBy");
		 * hdupdatedBy.setEditable(false); headerslist.add(hdupdatedBy);
		 * 
		 * Header hdId = new Header(); hdId.setField("id"); hdId.setHide(true); if
		 * (role.contains(checker)) hdId.setEditable(false); headerslist.add(hdId);
		 * 
		 * Header hdlive = new Header(); hdlive.setHeaderName("Action");
		 * hdlive.setField("live"); if (role.contains(maker)) {
		 * hdlive.setType("liveMakerColumn"); } else
		 * hdlive.setType("liveCheckerColumn"); headerslist.add(hdlive);
		 */

		List<CorporateStagingEntity> allcorp = campaignUploadService.getCorporateOffers();
		allcorp.stream().forEach(corp -> {
			Corporate corpdata = new Corporate();
			corpdata.setTitle(corp.getTitle());
			corpdata.setLogo(corp.getLogo());
			corpdata.setOfferText(corp.getOffertext());
			corpdata.setCompanyId(corp.getCompanyId());
			corpdata.setApprovalStatus(corp.getApprovalstatus());
			corpdata.setCreatedBy(corp.getCreatedBy());
			corpdata.setUpdatedBy(corp.getUpdatedBy());
			corpdata.setId(corp.getId());
			dataList.add(corpdata);

		});

		corporateoffersGetResponseBody.setCorporates(dataList);

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
			if (ExcelReader.hasExcelFormat(file)) {
				try {
					if (!FilenameUtils.removeExtension(file.getOriginalFilename()).equals(corpfilename))
						throw new CustomBadRequestException("Excel file name incorrect");
					campaignUploadService.saveCorporateoffer(file, corpsheetname, uploadedBy, dir, makerip);
					message = "Uploaded the file successfully: " + file.getOriginalFilename();
					corporateoffersPostResponseBody.setStatuscode(HttpStatus.SC_OK);
					corporateoffersPostResponseBody.setMessage(message);
					return corporateoffersPostResponseBody;
				} catch (CustomBadRequestException e) {
					throw e;
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

	@Override
	public IdDeleteResponseBody deleteId(String id, HttpServletRequest request, HttpServletResponse arg2) {
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
					if (corpstg.getApprovalstatus().equals(APPROVED)) {
						corpstg.setApprovalstatus(DELETE_PENDING);
						corpstg.setCreatedBy(subject);
						corpstg.setUpdatedBy("-");
						corpstg.setMakerip(makerip);
						corpstg.setCheckerip("-");
						campaignUploadService.saveCorpOffer(corpstg);
					} else
						throw new CustomBadRequestException("Only Approved records can be deleted");
				}

				IdDeleteResponseBody youtubePutResponse = new IdDeleteResponseBody();
				youtubePutResponse.setMessage("Record delete requested");
				youtubePutResponse.setStatuscode(HttpStatus.SC_OK);

				return youtubePutResponse;
			} else
				throw new CustomBadRequestException("Id cannot be null");
		} else
			throw new CustomBadRequestException("Maker can only edit data");
	}

	@SuppressWarnings("unlikely-arg-type")
	@Override
	public IdPostResponseBody postId(String id, String action, HttpServletRequest request, HttpServletResponse arg3)
			throws BadRequestException, InternalServerErrorException {
		logger.info("Request received to approve record data " + id);

		logger.info("Authorization header " + request.getHeader("authorization"));

		String checkerip = request.getRemoteAddr();

		logger.info("Corporate Approve checker ip  " + checkerip);

		String authorization = request.getHeader("authorization").substring(7);

		String subject = ValidateJwt.validateJwt(authorization, "JWTSecretKeyDontUseInProduction!");

		logger.info("User in JWT " + subject);

		List<String> role = ValidateJwt.getRole(authorization, "JWTSecretKeyDontUseInProduction!");
		
		IdPostResponseBody campaignPutResponse = new IdPostResponseBody();

		logger.info("Role in JWT " + role);

		if (role.contains(checker)) {

			if (id != null && !id.equals(null)) {
				CorporateStagingEntity corpstg = campaignUploadService.getCorporateWithOutFileId(Integer.parseInt(id));
				if (corpstg != null) {
					CorporateFinalEntity corpfinal = campaignUploadService.getcorpFinalEntitybyStagId(corpstg);

					if (action.equalsIgnoreCase("A")) {

						if (corpstg.getApprovalstatus().equals(DELETE_PENDING)) {
							corpstg.setApprovalstatus(DELETED);
							corpstg.setUpdatedBy(subject);
							corpstg.setCheckerip(checkerip);
							logger.info("CorporateStagingEntity entity going for delete " + corpstg);
							campaignPutResponse.setMessage("Records Approved Successfully");
							campaignUploadService.saveCorpOffer(corpstg);
							if (corpfinal != null)
								campaignUploadService.deleteCORP(corpfinal);
						} else {

							if (!corpstg.getApprovalstatus().equals(PENDING))
								throw new CustomBadRequestException("Only Pending records can be Approved");

							CompanyFinalEntity cmfinal = campaignUploadService.getCompany(corpstg.getCompanyId());
							List<CorporateFinalEntity> corpfinallist = campaignUploadService.findAllCORP();
							if (cmfinal != null) {
								if (/* corpstg.getCorpfileApproveEntity() == null && */corpfinal == null) {
									if (corpfinallist.contains(corpstg)) {

										overrideFinal(corpstg, corpfinallist, checkerip, subject);

									} else {
										logger.info("Creating new  CorporateFinalEntity");
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
										corpfinals.setCompanyfinalEntity(cmfinal);
										logger.info("CorporateFinalEntity entity going for save " + corpfinals);
										campaignUploadService.saveCorpFinal(corpfinals);
									}
								} else {
									logger.info("Updating existing CorporateFinalEntity");
									if (corpfinallist.contains(corpstg)) {

										overrideFinal(corpstg, corpfinallist, checkerip, subject);
										campaignUploadService.deleteCORP(corpfinal);

									} else {									
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
								}
								corpstg.setApprovalstatus(APPROVED);
								corpstg.setUpdatedBy(subject);
								corpstg.setCheckerip(checkerip);
								campaignPutResponse.setMessage("Records Approved Successfully");
								logger.info("CorporateStagingEntity entity going for save " + corpstg);
								campaignUploadService.saveCorpOffer(corpstg);
							} else
								throw new CustomBadRequestException("No Company Mapping Found");
						}
					} else if (action.equalsIgnoreCase("R")) {
						if (corpstg.getApprovalstatus().equals(DELETE_PENDING))
							corpstg.setApprovalstatus(APPROVED);
						else if (corpstg.getApprovalstatus().equals(PENDING))
							corpstg.setApprovalstatus(REJECTED);
						else
							throw new CustomBadRequestException("Only Pending records can be rejected");
						corpstg.setUpdatedBy(subject);
						corpstg.setCheckerip(checkerip);
						campaignPutResponse.setMessage("Records Rejected Successfully");
						logger.info("CorporateStagingEntity entity going for save " + corpstg);
						campaignUploadService.saveCorpOffer(corpstg);
					}
				} else
					throw new CustomBadRequestException("No staging entity found with Id " + id);
			} else
				throw new CustomBadRequestException("Id cannot be null");


			
			campaignPutResponse.setStatuscode(HttpStatus.SC_OK);

			return campaignPutResponse;
		} else
			throw new CustomBadRequestException("Checker can only Approve data");
	}

	@Override
	public CorporateoffersPutResponseBody putCorporateoffers(@Valid CorporateoffersPutRequestBody requestBody,
			HttpServletRequest request, HttpServletResponse arg2) {
		logger.info("Request received to update data " + requestBody);

		logger.info("Authorization header " + request.getHeader("authorization"));

		String makerip = request.getRemoteAddr();

		logger.info("Corporate Update maker ip " + makerip);

		String authorization = request.getHeader("authorization").substring(7);

		String subject = ValidateJwt.validateJwt(authorization, "JWTSecretKeyDontUseInProduction!");

		logger.info("User in JWT " + subject);

		List<String> role = ValidateJwt.getRole(authorization, "JWTSecretKeyDontUseInProduction!");

		logger.info("Role in JWT " + role);

		if (role.contains(maker)) {

			CorporateoffersPutResponseBody campaignPutResponse = new CorporateoffersPutResponseBody();

			for (Update corpoff : requestBody.getUpdates()) {
				if (corpoff.getCompanyId() != null && !corpoff.getCompanyId().equals(null)
						&& !corpoff.getCompanyId().matches("^[a-zA-Z0-9\\-]+$"))
					throw new CustomBadRequestException("Company Id should be alphanumerice");

				if (corpoff.getLogo() != null && !corpoff.getLogo().equals(null)
						&& !corpoff.getLogo().matches("([^\\s]+(\\.(?i)(jpe?g|png|gif|bmp))$)"))
					throw new CustomBadRequestException("Logo format is invalid");

				if (corpoff.getId() > 0) {
					CorporateStagingEntity corpstg = campaignUploadService.getCorpOffer(corpoff.getId());
					if (corpstg != null) {
						corpstg.setCompanyId(corpoff.getCompanyId());
						corpstg.setTitle(corpoff.getTitle());
						corpstg.setLogo(corpoff.getLogo());
						corpstg.setOffertext(corpoff.getOfferText());
						corpstg.setApprovalstatus(PENDING);
						corpstg.setId(corpoff.getId());
						corpstg.setCreatedBy(subject);
						corpstg.setUpdatedBy("-");
						corpstg.setMakerip(makerip);
						corpstg.setCheckerip("-");
						campaignUploadService.saveCorpOffer(corpstg);
					} else
						throw new CustomBadRequestException("No Entity found with Id " + corpoff.getId());
				} else {
					CorporateStagingEntity corpstg = new CorporateStagingEntity();
					corpstg.setCompanyId(corpoff.getCompanyId());
					corpstg.setTitle(corpoff.getTitle());
					corpstg.setLogo(corpoff.getLogo());
					corpstg.setOffertext(corpoff.getOfferText());
					corpstg.setApprovalstatus(PENDING);
					corpstg.setCreatedBy(subject);
					corpstg.setUpdatedBy("-");
					corpstg.setMakerip(makerip);
					corpstg.setCheckerip("-");
					campaignUploadService.saveCorpOffer(corpstg);
				}
			}

			campaignPutResponse.setMessage("Record Updated in table");
			campaignPutResponse.setStatuscode(HttpStatus.SC_OK);

			return campaignPutResponse;
		} else
			throw new CustomBadRequestException("Maker can only edit data");
	}

	@SuppressWarnings("unlikely-arg-type")
	public void overrideFinal(CorporateStagingEntity corpstg, List<CorporateFinalEntity> corpfinallist,
			String checkerip, String subject) {

		logger.info("Overriding existing CorporateFinalEntity");
		CorporateFinalEntity corpfinals = corpfinallist.get(corpfinallist.indexOf(corpstg));

		corpfinals.setTitle(corpstg.getTitle());
		corpfinals.setLogo(corpstg.getLogo());
		corpfinals.setOffertext(corpstg.getOffertext());
		corpfinals.setApprovalstatus(APPROVED);

		CorporateStagingEntity cs = corpfinals.getCorporateStagingEntity();
		cs.setApprovalstatus(DELETED);
		campaignUploadService.saveCorpOffer(cs);

		corpfinals.setCreatedBy(corpstg.getCreatedBy());
		corpfinals.setUpdatedBy(subject);
		corpfinals.setCheckerip(checkerip);
		corpfinals.setMakerip(corpstg.getMakerip());
		corpfinals.setCorporateStagingEntity(corpstg);		
		logger.info("CorporateFinalEntity entity going for save " + corpfinals);
		campaignUploadService.saveCorpFinal(corpfinals);
	}

}

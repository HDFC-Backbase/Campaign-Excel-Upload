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
import com.backbase.campaignupload.entity.CorporateAuditEntity;
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
import com.backbase.encryption.EncryptionAES;
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

	@Value("${secret}")
	private String secret;

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
			logger.info("Encryption started for getCorporateoffers");
			String getid;
			try {
				getid = EncryptionAES.encrypt(corp.getId(), secret);
			} catch (Exception e) {
				logger.info("Exception while encryption " + e.getMessage());
				throw new CustomInternalServerException(
						"Something went wrong while encrypting in CorporateExcelUpload");
			}
			logger.info("Encryption completed for getCorporateoffers");
			corpdata.setTitle(corp.getTitle());
			corpdata.setLogo(corp.getLogo());
			corpdata.setOfferText(corp.getOffertext());
			corpdata.setCompanyId(corp.getCompanyId());
			corpdata.setApprovalStatus(corp.getApprovalstatus());
			corpdata.setCreatedBy(corp.getCreatedBy());
			corpdata.setUpdatedBy(corp.getUpdatedBy());
			corpdata.setId(getid);
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

		String createdBy = ValidateJwt.validateJwt(authorization, "JWTSecretKeyDontUseInProduction!");

		List<String> role = ValidateJwt.getRole(authorization, "JWTSecretKeyDontUseInProduction!");

		logger.info("User in JWT " + createdBy);

		logger.info("Role in JWT " + role);
		try {
			if (role.contains(maker)) {

				if (id != null && !id.equals(null)) {
					logger.info("Decryption started for deleteId corporate upload");
					Integer deleteid = EncryptionAES.decrypt(id, secret);
					logger.info("Decryption completed for deleteId corporate upload");
					CorporateStagingEntity corpstg = campaignUploadService.getCorporateWithOutFileId(deleteid);

					if (corpstg == null || corpstg.equals(null))
						throw new CustomBadRequestException("No entity found with id " + deleteid);

					if (corpstg.getApprovalstatus().equals(DELETE_PENDING))
						throw new CustomBadRequestException("Record is already in pending state");

					if (corpstg != null) {
						if (corpstg.getApprovalstatus().equals(APPROVED)) {
							corpstg.setApprovalstatus(DELETE_PENDING);
							corpstg.setCreatedBy(createdBy);
							corpstg.setUpdatedBy("-");
							corpstg.setMakerip(makerip);
							corpstg.setCheckerip("-");
							campaignUploadService.saveCorpOffer(corpstg);
							corpauditsave(corpstg, DELETE_PENDING, createdBy,"-", makerip, "-");
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
		} catch (CustomBadRequestException e) {
			throw e;
		} catch (Exception e) {
			logger.info(e.getMessage());
			throw new CustomInternalServerException(
					"Something went wrong while delete request in CorporateExcelUpload");
		}
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

		String updatedBy = ValidateJwt.validateJwt(authorization, "JWTSecretKeyDontUseInProduction!");

		logger.info("User in JWT " + updatedBy);

		List<String> role = ValidateJwt.getRole(authorization, "JWTSecretKeyDontUseInProduction!");

		IdPostResponseBody campaignPutResponse = new IdPostResponseBody();

		logger.info("Role in JWT " + role);
		try {
			if (role.contains(checker)) {

				if (id != null && !id.equals(null)) {
					logger.info("Decryption started for postRecordId corporate upload ");
					Integer postrecordid = EncryptionAES.decrypt(id, secret);
					logger.info("Decryption completed for postRecordId corporate upload ");
					CorporateStagingEntity corpstg = campaignUploadService.getCorporateWithOutFileId(postrecordid);
					if (corpstg != null) {
						CorporateFinalEntity corpfinal = campaignUploadService.getcorpFinalEntitybyStagId(corpstg);

						if (action.equalsIgnoreCase("A")) {

							if (corpstg.getApprovalstatus().equals(DELETE_PENDING)) {
								corpstg.setApprovalstatus(DELETED);
								corpstg.setUpdatedBy(updatedBy);
								corpstg.setCheckerip(checkerip);
								logger.info("CorporateStagingEntity entity going for delete " + corpstg);
								campaignPutResponse.setMessage("Records Deleted Successfully");
								campaignUploadService.saveCorpOffer(corpstg);
								corpauditsave(corpstg, DELETED,  corpstg.getCreatedBy(),updatedBy,corpstg.getMakerip(), checkerip);
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

											overrideFinal(corpstg, corpfinallist, checkerip, updatedBy);

										} else {
											logger.info("Creating new  CorporateFinalEntity");
											CorporateFinalEntity corpfinals = new CorporateFinalEntity();
											corpfinals.setTitle(corpstg.getTitle());
											corpfinals.setLogo(corpstg.getLogo());
											corpfinals.setOffertext(corpstg.getOffertext());
											corpfinals.setApprovalstatus(APPROVED);
											corpfinals.setCreatedBy(corpstg.getCreatedBy());
											corpfinals.setUpdatedBy(updatedBy);
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

											overrideFinal(corpstg, corpfinallist, checkerip, updatedBy);
											campaignUploadService.deleteCORP(corpfinal);

										} else {
											corpfinal.setTitle(corpstg.getTitle());
											corpfinal.setLogo(corpstg.getLogo());
											corpfinal.setOffertext(corpstg.getOffertext());
											corpfinal.setCreatedBy(corpstg.getCreatedBy());
											corpfinal.setUpdatedBy(updatedBy);
											corpfinal.setApprovalstatus(APPROVED);
											corpfinal.setCheckerip(checkerip);
											corpfinal.setMakerip(corpstg.getMakerip());
											logger.info("CorporateFinalEntity entity going for save " + corpfinal);
											campaignUploadService.saveCorpFinal(corpfinal);
										}
									}
									corpstg.setApprovalstatus(APPROVED);
									corpstg.setUpdatedBy(updatedBy);
									corpstg.setCheckerip(checkerip);
									campaignPutResponse.setMessage("Records Approved Successfully");
									logger.info("CorporateStagingEntity entity going for save " + corpstg);
									campaignUploadService.saveCorpOffer(corpstg);
									corpauditsave(corpstg, APPROVED,corpstg.getCreatedBy(), updatedBy, corpstg.getMakerip(), checkerip);
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
							corpstg.setUpdatedBy(updatedBy);
							corpstg.setCheckerip(checkerip);
							campaignPutResponse.setMessage("Records Rejected Successfully");
							logger.info("CorporateStagingEntity entity going for save " + corpstg);
							campaignUploadService.saveCorpOffer(corpstg);
							corpauditsave(corpstg, corpstg.getApprovalstatus(),corpstg.getCreatedBy(), updatedBy, corpstg.getMakerip(), checkerip);
						}
					} else
						throw new CustomBadRequestException("No staging entity found with Id " + id);
				} else
					throw new CustomBadRequestException("Id cannot be null");

				campaignPutResponse.setStatuscode(HttpStatus.SC_OK);

				return campaignPutResponse;
			} else
				throw new CustomBadRequestException("Checker can only Approve data");
		} catch (CustomBadRequestException e) {
			throw e;
		} catch (Exception e) {
			logger.info(e.getMessage());
			throw new CustomInternalServerException(
					"Something went wrong while approve request in CorporateExcelUpload");
		}
	}

	@Override
	public CorporateoffersPutResponseBody putCorporateoffers(@Valid CorporateoffersPutRequestBody requestBody,
			HttpServletRequest request, HttpServletResponse arg2) {
		logger.info("Request received to update data " + requestBody);

		logger.info("Authorization header " + request.getHeader("authorization"));

		String makerip = request.getRemoteAddr();

		logger.info("Corporate Update maker ip " + makerip);

		String authorization = request.getHeader("authorization").substring(7);

		String createdBy = ValidateJwt.validateJwt(authorization, "JWTSecretKeyDontUseInProduction!");

		logger.info("User in JWT " + createdBy);

		List<String> role = ValidateJwt.getRole(authorization, "JWTSecretKeyDontUseInProduction!");

		logger.info("Role in JWT " + role);
		try {
			if (role.contains(maker)) {

				CorporateoffersPutResponseBody campaignPutResponse = new CorporateoffersPutResponseBody();

				for (Update corpoff : requestBody.getUpdates()) {
					if (corpoff.getCompanyId() != null && !corpoff.getCompanyId().equals(null)
							&& !corpoff.getCompanyId().matches("^[a-zA-Z0-9\\-]+$"))
						throw new CustomBadRequestException("Company Id should be alphanumerice");

					if (corpoff.getLogo() != null && !corpoff.getLogo().equals(null)
							&& !corpoff.getLogo().matches("([^\\s]+(\\.(?i)(jpe?g|png|gif|bmp))$)"))
						throw new CustomBadRequestException("Logo format is invalid");

					// if (corpoff.getId() > 0) {
					if (corpoff.getId() != null && !corpoff.getId().equals(null)) {
						logger.info("Decryption started for putCorporateoffers");
						Integer putid = EncryptionAES.decrypt(corpoff.getId(), secret);
						logger.info("Decryption completed for putCorporateoffers");
						CorporateStagingEntity corpstg = campaignUploadService.getCorpOffer(putid);
						if (corpstg != null) {
							corpstg.setCompanyId(corpoff.getCompanyId());
							corpstg.setTitle(corpoff.getTitle());
							corpstg.setLogo(corpoff.getLogo());
							corpstg.setOffertext(corpoff.getOfferText());
							corpstg.setApprovalstatus(PENDING);
							corpstg.setId(putid);
							corpstg.setCreatedBy(createdBy);
							corpstg.setUpdatedBy("-");
							corpstg.setMakerip(makerip);
							corpstg.setCheckerip("-");
							campaignUploadService.saveCorpOffer(corpstg);
							corpauditsave(corpstg, PENDING, createdBy,"-", makerip, "-");
						} else
							throw new CustomBadRequestException("No Entity found with Id " + corpoff.getId());
					} else {
						CorporateStagingEntity corpstg = new CorporateStagingEntity();
						corpstg.setCompanyId(corpoff.getCompanyId());
						corpstg.setTitle(corpoff.getTitle());
						corpstg.setLogo(corpoff.getLogo());
						corpstg.setOffertext(corpoff.getOfferText());
						corpstg.setApprovalstatus(PENDING);
						corpstg.setCreatedBy(createdBy);
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
		} catch (CustomBadRequestException e) {
			throw e;
		} catch (Exception e) {
			logger.info(e.getMessage());
			throw new CustomInternalServerException(
					"Something went wrong while update request in CorporateExcelUpload");
		}

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
	
	public void corpauditsave(CorporateStagingEntity corpStagingEntity, String approvalstatus,String createdBy, String updatedBy,String makerip,String checkerip)
	{
		logger.info("CorporateAuditEntity going for save");
		CorporateAuditEntity corpAud=new CorporateAuditEntity();
		corpAud.setCompanyId(corpStagingEntity.getCompanyId());
		corpAud.setTitle(corpStagingEntity.getTitle());
		corpAud.setLogo(corpStagingEntity.getLogo());
		corpAud.setOffertext(corpStagingEntity.getOffertext());
		corpAud.setApprovalstatus(approvalstatus);
		corpAud.setCreatedBy(createdBy);
		corpAud.setUpdatedBy(updatedBy);
		corpAud.setMakerip(makerip);
		corpAud.setCheckerip(checkerip);
		corpAud.setCorpstaginentity(corpStagingEntity);
		campaignUploadService.saveCorpaudit(corpAud);
		logger.info("CorporateAuditEntity save completed " + corpAud);


	}

}

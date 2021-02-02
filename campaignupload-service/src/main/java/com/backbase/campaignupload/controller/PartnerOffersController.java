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

import com.backbase.campaignupload.entity.PartnerOffersFinalEntity;
import com.backbase.campaignupload.entity.PartnerOffersStagingEntity;
import com.backbase.campaignupload.exception.CustomBadRequestException;
import com.backbase.campaignupload.exception.CustomInternalServerException;
import com.backbase.campaignupload.pojo.CampaignPutResponse;
import com.backbase.campaignupload.pojo.PartnerOfferPutRequest;
import com.backbase.campaignupload.reader.ExcelReader;
import com.backbase.campaignupload.rest.spec.v1.partneroffers.Header;
import com.backbase.campaignupload.rest.spec.v1.partneroffers.PartnerOffer;
import com.backbase.campaignupload.rest.spec.v1.partneroffers.PartneroffersApi;
import com.backbase.campaignupload.rest.spec.v1.partneroffers.PartneroffersGetResponseBody;
import com.backbase.campaignupload.rest.spec.v1.partneroffers.PartneroffersPostResponseBody;
import com.backbase.campaignupload.service.CampaignUploadService;
import com.backbase.validate.jwt.ValidateJwt;

import liquibase.util.file.FilenameUtils;

@RestController
public class PartnerOffersController implements PartneroffersApi {

	private static final String PENDING = "Pending";

	private static final Logger logger = LoggerFactory.getLogger(PartnerOffersController.class);

	private static final String DELETED = "Deleted";

	private static final String DELETE_PENDING = "Delete Pending";

	@Autowired
	CampaignUploadService campaignUploadService;

	@Value("${file.location}")
	private String dir;

	@Value("${role.maker}")
	private String maker;

	@Value("${role.checker}")
	private String checker;

	@Value("${excelfile.partner.filename}")
	private String partfilename;

	@Value("${excelfile.partner.sheetname}")
	private String partsheetname;

	private static final String APPROVED = "Approved";

	private static final String REJECTED = "Rejected";

	@Override
	public PartneroffersGetResponseBody getPartneroffers(HttpServletRequest request, HttpServletResponse arg1) {

		logger.info("Request received to get data");

		logger.info("Authorization header " + request.getHeader("authorization"));

		String authorization = request.getHeader("authorization").substring(7);

		String subject = ValidateJwt.validateJwt(authorization, "JWTSecretKeyDontUseInProduction!");

		List<String> role = ValidateJwt.getRole(authorization, "JWTSecretKeyDontUseInProduction!");

		logger.info("User in JWT " + subject);

		logger.info("Role in JWT " + role);

		PartneroffersGetResponseBody partneroffersGetResponseBody = new PartneroffersGetResponseBody();

		List<Header> headerslist = new ArrayList<Header>();

		List<Object> dataList = new ArrayList<Object>();

		Header hdctitle = new Header();
		hdctitle.setField("title");
		if (role.contains(checker))
			hdctitle.setEditable(false);
		headerslist.add(hdctitle);

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
		hdlive.setHeaderName("Action");
		hdlive.setField("live");
		if (role.contains(maker)) {
			hdlive.setType("liveMakerColumn");
		} else
			hdlive.setType("liveCheckerColumn");
		headerslist.add(hdlive);

		List<PartnerOffersStagingEntity> compent = campaignUploadService.getPartnerOffers();
		compent.stream().forEach(ce -> {
			PartnerOffer partnerofferresponse = new PartnerOffer();
			partnerofferresponse.setTitle(ce.getTitle());
			partnerofferresponse.setLogo(ce.getLogo());
			partnerofferresponse.setOffertext(ce.getOffertext());
			partnerofferresponse.setApprovalstatus(ce.getApprovalstatus());
			partnerofferresponse.setCreatedBy(ce.getCreatedBy());
			partnerofferresponse.setUpdatedBy(ce.getUpdatedBy());
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

		logger.info("Request received to Upload data");

		String makerip = request.getRemoteAddr();
		logger.info(" PartneroffersPostResponseBody ip  " + makerip);

		logger.info("Authorization header " + request.getHeader("authorization"));

		String authorization = request.getHeader("authorization").substring(7);

		String uploadedBy = ValidateJwt.validateJwt(authorization, "JWTSecretKeyDontUseInProduction!");

		logger.info("User in JWT " + uploadedBy);

		List<String> role = ValidateJwt.getRole(authorization, "JWTSecretKeyDontUseInProduction!");

		logger.info("Role in JWT " + role);

		if (role.contains(maker)) {
			PartneroffersPostResponseBody partneroffersPostResponseBody = new PartneroffersPostResponseBody();
			String message = "";
			if (ExcelReader.hasExcelFormat(file)) {
				try {
					if (!FilenameUtils.removeExtension(file.getOriginalFilename()).equals(partfilename))
						throw new CustomBadRequestException("Excel file name incorrect");
					String filename = saveFiletoLocation(file, uploadedBy);
					campaignUploadService.save(file, partsheetname, uploadedBy, filename, makerip);
					message = "Uploaded the file successfully: " + file.getOriginalFilename();
					partneroffersPostResponseBody.setStatuscode("200");
					partneroffersPostResponseBody.setMessage(message);
					return partneroffersPostResponseBody;
				} catch (CustomBadRequestException e) {
					throw e;
				} catch (Exception e) {
					logger.info(e.getMessage());
					throw new CustomInternalServerException(
							"Could not upload the file: " + file.getOriginalFilename() + "!");
				}
			} else
				throw new CustomBadRequestException("Please upload an excel .xls file!");
		} else
			throw new CustomBadRequestException("Maker can only edit data");
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
	public CampaignPutResponse putCompanies(@RequestBody PartnerOfferPutRequest requestBody, HttpServletRequest request,
			HttpServletResponse arg2) {

		logger.info("Request received to update data");

		logger.info("Authorization header " + request.getHeader("authorization"));

		String makerip = request.getRemoteAddr();

		logger.info("Partner Update Record maker ip  " + makerip);

		String authorization = request.getHeader("authorization").substring(7);

		String subject = ValidateJwt.validateJwt(authorization, "JWTSecretKeyDontUseInProduction!");

		List<String> role = ValidateJwt.getRole(authorization, "JWTSecretKeyDontUseInProduction!");

		logger.info("Role in JWT " + role);

		if (role.contains(maker)) {

			CampaignPutResponse campaignPutResponse = new CampaignPutResponse();

			for (PartnerOffer prtoffer : requestBody.getUpdates()) {
				if (prtoffer.getId() > 0) {
					PartnerOffersStagingEntity prtstag = campaignUploadService.getPTWithOutFileId(prtoffer.getId());
					if (prtstag!=null) {
						prtstag.setTitle(prtoffer.getTitle());
						prtstag.setLogo(prtoffer.getLogo());
						prtstag.setOffertext(prtoffer.getOffertext());
						prtstag.setApprovalstatus(PENDING);
						prtstag.setId(prtoffer.getId());
						prtstag.setCreatedBy(subject);
						prtstag.setUpdatedBy("-");
						prtstag.setMakerip(makerip);
						prtstag.setCheckerip("-");
						campaignUploadService.savePartnerOffer(prtstag);
					} else
						throw new CustomBadRequestException("No Entity found with Id "+prtoffer.getId());
				} else {
					PartnerOffersStagingEntity prtstag = new PartnerOffersStagingEntity();
					prtstag.setTitle(prtoffer.getTitle());
					prtstag.setLogo(prtoffer.getLogo());
					prtstag.setOffertext(prtoffer.getOffertext());
					prtstag.setCreatedBy(subject);
					prtstag.setUpdatedBy("-");
					prtstag.setMakerip(makerip);
					prtstag.setCheckerip("-");
					prtstag.setApprovalstatus(PENDING);
					campaignUploadService.savePartnerOffer(prtstag);
				}
			}

			campaignPutResponse.setMessage("Successfully update data in table");
			campaignPutResponse.setStatuscode(HttpStatus.SC_OK);

			return campaignPutResponse;
		} else
			throw new CustomBadRequestException("Maker can only edit data");
	}

	@SuppressWarnings("unlikely-arg-type")
	@PostMapping("/record/{id}")
	public CampaignPutResponse postRecord(@PathVariable String id, @RequestParam("action") String action,
			HttpServletRequest request, HttpServletResponse arg2) {

		logger.info("Request received to approve record data " + id);

		logger.info("Authorization header " + request.getHeader("authorization"));

		String checkerip = request.getRemoteAddr();

		logger.info("Partner Approve record checkerip " + checkerip);

		String authorization = request.getHeader("authorization").substring(7);

		String subject = ValidateJwt.validateJwt(authorization, "JWTSecretKeyDontUseInProduction!");

		List<String> role = ValidateJwt.getRole(authorization, "JWTSecretKeyDontUseInProduction!");

		logger.info("User in JWT " + subject);

		logger.info("Role in JWT " + role);

		if (role.contains(checker)) {

			if (id != null && !id.equals(null)) {

				PartnerOffersStagingEntity prtstag = campaignUploadService.getPTWithOutFileId(Integer.parseInt(id));

				if (prtstag != null) {

					PartnerOffersFinalEntity ptfinal = campaignUploadService.getFinalEntitybyStagId(prtstag);

					if (action.equalsIgnoreCase("A")) {

						if (prtstag.getApprovalstatus().equals(DELETE_PENDING)) {
							prtstag.setApprovalstatus(DELETED);
							prtstag.setUpdatedBy(subject);
							prtstag.setCheckerip(checkerip);
							logger.info("PartnerOffersStagingEntity entity going for delete " + prtstag);
							campaignUploadService.savePartnerOffer(prtstag);
							if (ptfinal != null)
								campaignUploadService.delete(ptfinal);

						} else {
							if (!prtstag.getApprovalstatus().equals(PENDING))
								throw new CustomBadRequestException("Only Pending records can be Approved");

							if (/* prtstag.getFileApproveEntity() == null && */ ptfinal == null) {

								List<PartnerOffersFinalEntity> pofinallist = campaignUploadService.findAllPT();

								if (pofinallist.contains(prtstag)) {

									PartnerOffersFinalEntity ptfinals = pofinallist.get(pofinallist.indexOf(prtstag));
									logger.info("Overriding existing PartnerOffersFinalEntity " + ptfinals);
									ptfinals.setTitle(prtstag.getTitle());
									ptfinals.setLogo(prtstag.getLogo());
									ptfinals.setOffertext(prtstag.getOffertext());
									ptfinals.setApprovalstatus(APPROVED);

									PartnerOffersStagingEntity pst = ptfinals.getPartoffstagentity();
									pst.setApprovalstatus(DELETED);
									logger.info("PartnerOffersStagingEntity entity going for delete " + prtstag);
									campaignUploadService.savePartnerOffer(pst);

									ptfinals.setPartoffstagentity(prtstag);
									ptfinals.setCreatedBy(prtstag.getCreatedBy());
									ptfinals.setUpdatedBy(subject);
									ptfinals.setCheckerip(checkerip);
									ptfinals.setMakerip(prtstag.getMakerip());
									logger.info("PartnerOffersFinalEntity entity going for save " + ptfinals);
									campaignUploadService.savePTFinal(ptfinals);
								} else {
									logger.info("Creating new PartnerOffersFinalEntity");
									PartnerOffersFinalEntity ptfinals = new PartnerOffersFinalEntity();
									ptfinals.setTitle(prtstag.getTitle());
									ptfinals.setLogo(prtstag.getLogo());
									ptfinals.setOffertext(prtstag.getOffertext());
									ptfinals.setApprovalstatus(APPROVED);
									ptfinals.setPartoffstagentity(prtstag);
									ptfinals.setCreatedBy(prtstag.getCreatedBy());
									ptfinals.setUpdatedBy(subject);
									ptfinals.setCheckerip(checkerip);
									ptfinals.setMakerip(prtstag.getMakerip());
									logger.info("PartnerOffersFinalEntity entity going for save " + ptfinals);
									campaignUploadService.savePTFinal(ptfinals);
								}
							} else {
								logger.info("Updating existing PartnerOffersFinalEntity");
								ptfinal.setTitle(prtstag.getTitle());
								ptfinal.setLogo(prtstag.getLogo());
								ptfinal.setOffertext(prtstag.getOffertext());
								ptfinal.setApprovalstatus(APPROVED);
								ptfinal.setCreatedBy(prtstag.getCreatedBy());
								ptfinal.setUpdatedBy(subject);
								ptfinal.setCheckerip(checkerip);
								ptfinal.setMakerip(prtstag.getMakerip());
								logger.info("PartnerOffersFinalEntity entity going for save " + ptfinal);
								campaignUploadService.savePTFinal(ptfinal);
							}
							prtstag.setApprovalstatus(APPROVED);
							prtstag.setUpdatedBy(subject);
							prtstag.setCheckerip(checkerip);
							logger.info("PartnerOffersStagingEntity entity going for save " + prtstag);
							campaignUploadService.savePartnerOffer(prtstag);
						}

					} else if (action.equalsIgnoreCase("R")) {
						if (prtstag.getApprovalstatus().equals(DELETE_PENDING))
							prtstag.setApprovalstatus(APPROVED);
						else if (prtstag.getApprovalstatus().equals(PENDING))
							prtstag.setApprovalstatus(REJECTED);
						else
							throw new CustomBadRequestException("Only Pending records can be rejected");
						prtstag.setUpdatedBy(subject);
						prtstag.setCheckerip(checkerip);
						logger.info("PartnerOffersStagingEntity entity going for save " + prtstag);
						campaignUploadService.savePartnerOffer(prtstag);
					}

					CampaignPutResponse campaignPutResponse = new CampaignPutResponse();

					campaignPutResponse.setMessage("Successfully update data in table");
					campaignPutResponse.setStatuscode(HttpStatus.SC_OK);

					return campaignPutResponse;
				} else
					throw new CustomBadRequestException("No Enity found for Id " + id);
			} else
				throw new CustomBadRequestException("Id cannot be null");
		} else
			throw new CustomBadRequestException("Checker can only Approve data");
	}

	@DeleteMapping("/{id}")
	public CampaignPutResponse deletePO(@PathVariable String id, HttpServletRequest request, HttpServletResponse arg2) {

		logger.info("Request received to delete data");

		logger.info("Authorization header " + request.getHeader("authorization"));

		String makerip = request.getRemoteAddr();

		logger.info("Partner Delete Record Maker ip  " + makerip);

		String authorization = request.getHeader("authorization").substring(7);

		String subject = ValidateJwt.validateJwt(authorization, "JWTSecretKeyDontUseInProduction!");

		List<String> role = ValidateJwt.getRole(authorization, "JWTSecretKeyDontUseInProduction!");

		logger.info("User in JWT " + subject);

		logger.info("Role in JWT " + role);

		if (role.contains(maker)) {

			if (id != null && !id.equals(null)) {
				PartnerOffersStagingEntity postg = campaignUploadService.getPTWithOutFileId(Integer.parseInt(id));

				if (postg == null || postg.equals(null))
					throw new CustomBadRequestException("No entity found with id " + id);

				if (postg.getApprovalstatus().equals(DELETE_PENDING))
					throw new CustomBadRequestException("Record is already in pending state");

				if (postg != null) {
					if (postg.getApprovalstatus().equals(APPROVED)) {
						postg.setApprovalstatus(DELETE_PENDING);
						postg.setCreatedBy(subject);
						postg.setUpdatedBy("-");
						postg.setMakerip(makerip);
						postg.setCheckerip("-");
						campaignUploadService.savePartnerOffer(postg);
					}else
						throw new CustomBadRequestException("Approved Records can be deleted");
					
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

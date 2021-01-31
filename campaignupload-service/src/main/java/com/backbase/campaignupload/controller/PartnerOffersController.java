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
		hdlive.setHeaderName("action");
		hdlive.setField("live");
		hdlive.setType("liveColumn");
		if (role.contains(maker))
			hdlive.setHide(true);
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
			if (ExcelHelper.hasExcelFormat(file)) {
				try {
					String filename = saveFiletoLocation(file, uploadedBy);
					campaignUploadService.save(file, "PartnerOffer", uploadedBy, filename, makerip);
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
					prtstag.setTitle(prtoffer.getTitle());
					prtstag.setLogo(prtoffer.getLogo());
					prtstag.setOffertext(prtoffer.getOffertext());
					prtstag.setApprovalstatus(CampaignUploadServiceImpl.PENDING);
					prtstag.setId(prtoffer.getId());
					prtstag.setCreatedBy(subject);
					prtstag.setUpdatedBy("-");
					prtstag.setMakerip(makerip);
					prtstag.setCheckerip("-");
					campaignUploadService.savePartnerOffer(prtstag);
				} else {
					PartnerOffersStagingEntity prtstag = new PartnerOffersStagingEntity();
					prtstag.setTitle(prtoffer.getTitle());
					prtstag.setLogo(prtoffer.getLogo());
					prtstag.setOffertext(prtoffer.getOffertext());
					prtstag.setCreatedBy(subject);
					prtstag.setUpdatedBy("-");
					prtstag.setMakerip(makerip);
					prtstag.setCheckerip("-");
					prtstag.setApprovalstatus(CampaignUploadServiceImpl.PENDING);
					campaignUploadService.savePartnerOffer(prtstag);
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
						prtstag.setApprovalstatus(APPROVED);
						prtstag.setUpdatedBy(subject);
						prtstag.setCheckerip(checkerip);
						logger.info("PartnerOffersStagingEntity entity going for save " + prtstag);
						campaignUploadService.savePartnerOffer(prtstag);

						if (prtstag.getFileApproveEntity() == null && ptfinal == null) {
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
						} else {
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

					} else if (action.equalsIgnoreCase("R")) {
						if (prtstag.getApprovalstatus().equals(DELETE_PENDING))
							prtstag.setApprovalstatus(APPROVED);
						else
							prtstag.setApprovalstatus(REJECTED);
						prtstag.setUpdatedBy(subject);
						prtstag.setCheckerip(checkerip);
						logger.info("PartnerOffersStagingEntity entity going for save " + prtstag);
						campaignUploadService.savePartnerOffer(prtstag);
					} else if (action.equals("D")) {
						prtstag.setApprovalstatus(DELETED);
						prtstag.setUpdatedBy(subject);
						prtstag.setCheckerip(checkerip);
						logger.info("PartnerOffersStagingEntity entity going for delete " + prtstag);
						campaignUploadService.savePartnerOffer(prtstag);
						if (ptfinal != null)
							campaignUploadService.delete(ptfinal);
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
					postg.setApprovalstatus(DELETE_PENDING);
					postg.setUpdatedBy(subject);
					postg.setMakerip(makerip);
					postg.setCheckerip("-");
					campaignUploadService.savePartnerOffer(postg);
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

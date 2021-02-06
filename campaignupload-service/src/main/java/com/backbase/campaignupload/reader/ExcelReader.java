package com.backbase.campaignupload.reader;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;

import com.backbase.campaignupload.entity.CorporateStagingEntity;
import com.backbase.campaignupload.entity.PartnerOffersStagingEntity;
import com.backbase.campaignupload.exception.CustomBadRequestException;
import com.backbase.campaignupload.repo.CompanyUploadRepo;

@Configuration
public class ExcelReader {

	@Value("${excelfile.partner.headers.title}")
	private String title;

	@Value("${excelfile.partner.headers.logo}")
	private String logo;

	@Value("${excelfile.partner.headers.offertext}")
	private String offertext;

	@Value("${excelfile.partner.noOfcolumns}")
	private String noOfcolumns;

	@Value("${excelfile.corporate.headers.companyId}")
	private String corpcompanyId;

	@Value("${excelfile.corporate.headers.title}")
	private String corptitle;

	@Value("${excelfile.corporate.headers.logo}")
	private String corplogo;

	@Value("${excelfile.corporate.headers.offertext}")
	private String corproffertext;

	@Value("${excelfile.corporate.noOfcolumns}")
	private String corpnoOfcolumns;

	private static final Logger logger = LoggerFactory.getLogger(ExcelReader.class);

	public static String TYPE = "application/vnd.ms-excel";
	static String[] HEADERs = { "Header", "Logo", "OfferText" };
	static String SHEET = "PartnerOffer";
	static String SHEETCORP = "CorporateOffer";

	public static boolean hasExcelFormat(MultipartFile file) {

		if (!TYPE.equals(file.getContentType())) {

			return false;
		}

		return true;
	}

	public List<PartnerOffersStagingEntity> excelToTutorials(InputStream is, String sheetname) {
		List<PartnerOffersStagingEntity> campaigndatalist = new ArrayList<PartnerOffersStagingEntity>();

		try {
			HSSFWorkbook workbook = new HSSFWorkbook(is);

			if (!workbook.getSheetAt(0).getSheetName().equals(sheetname)) {
				workbook.close();
				throw new CustomBadRequestException("Sheet name is not correct");
			}

			HSSFSheet sheet = workbook.getSheet(sheetname);

			if (sheet.getPhysicalNumberOfRows() < 2) {
				workbook.close();
				throw new CustomBadRequestException("Excel sheet has no data");
			}

			logger.info("No of Rows " + sheet.getLastRowNum());

			Row row0 = sheet.getRow(0);

			// checking number of rows
			if (row0.getPhysicalNumberOfCells() != Integer.parseInt(noOfcolumns)) {
				workbook.close();
				throw new CustomBadRequestException("Excel file has incorrect number of headers");
			}

			// checking header type and header names
			Iterator<Cell> celllist = row0.cellIterator();
			while (celllist.hasNext()) {
				Cell cell = celllist.next();
				if (!cell.getStringCellValue().equals(title) && !cell.getStringCellValue().equals(logo)
						&& !cell.getStringCellValue().equals(offertext)) {
					workbook.close();
					throw new CustomBadRequestException("Excel file has incorrect header names");
				}

			}

			logger.info("No of Rows " + sheet.getLastRowNum());

			/* row: */ for (int rowno = 1; rowno <= sheet.getLastRowNum(); rowno++) {

				logger.info("Current Row " + (rowno + 1));

				PartnerOffersStagingEntity campaigndata = new PartnerOffersStagingEntity();

				Row row = sheet.getRow(rowno);

				logger.info("No of cells " + row.getLastCellNum());

				for (int cell = 0; cell < row.getLastCellNum(); cell++) {

					Cell currentCell = row.getCell(cell);

					/*
					 * if (currentCell == null) continue row;
					 */

					logger.info("Current Cell " + currentCell);

					// getting header name
					String headername = sheet.getRow(0).getCell(cell).getStringCellValue();

					if (title.equals(headername)) {
						if (currentCell!=null && !currentCell.getStringCellValue().equals(null)
								&& !currentCell.getStringCellValue().equals(" ")) {
							campaigndata.setTitle(currentCell.getStringCellValue());
						} else {
							workbook.close();
							throw new CustomBadRequestException(title + " is incorrect at row no " + (rowno + 1));
						}
					} else if (logo.equals(headername)) {
						if (currentCell!=null && !currentCell.getStringCellValue().equals(null)
								&& !currentCell.getStringCellValue().equals(" ")
								&& currentCell.getStringCellValue().matches("([^\\s]+(\\.(?i)(jpe?g|png|gif|bmp))$)")) {
							campaigndata.setLogo(currentCell.getStringCellValue());
						} else {
							workbook.close();
							throw new CustomBadRequestException(logo + " is incorrect at row no " + (rowno + 1));
						}
					} else if (offertext.equals(headername)) {
						if (currentCell!=null && !currentCell.getStringCellValue().equals(null)
								&& !currentCell.getStringCellValue().equals(" ")) {
							campaigndata.setOffertext(currentCell.getStringCellValue());
						} else {
							workbook.close();
							throw new CustomBadRequestException(offertext + " is incorrect at row no " + (rowno + 1));
						}
					}

				}

				if (campaigndata.getTitle() != null && !campaigndata.getTitle().equals(null)
						&& campaigndata.getLogo() != null && !campaigndata.getLogo().equals(null)
						&& campaigndata.getOffertext() != null && !campaigndata.getOffertext().equals(null)) {
					campaigndatalist.add(campaigndata);
				}

			}

			workbook.close();

			return campaigndatalist;
		} catch (CustomBadRequestException e) {
			logger.info(e.getMessage());
			throw e;
		} catch (Exception e) {
			throw new RuntimeException("Fail to parse Excel file: " + e.getMessage());
		}
	}

	public List<CorporateStagingEntity> excelToCorporateStaging(InputStream is, String sheetname,
			CompanyUploadRepo companyupload) {

		List<CorporateStagingEntity> corpdatalist = new ArrayList<CorporateStagingEntity>();

		List<CorporateStagingEntity> corplistofnull = new ArrayList<CorporateStagingEntity>();

		try {
			HSSFWorkbook workbook = new HSSFWorkbook(is);

			if (!workbook.getSheetAt(0).getSheetName().equals(sheetname)) {
				workbook.close();
				throw new CustomBadRequestException("Sheet name is not correct");
			}

			HSSFSheet sheet = workbook.getSheet(sheetname);

			if (sheet.getPhysicalNumberOfRows() < 2) {
				workbook.close();
				throw new CustomBadRequestException("Excel sheet has no data");
			}

			logger.info("No of Rows " + sheet.getLastRowNum());

			Row row0 = sheet.getRow(0);

			// checking number of rows
			if (row0.getPhysicalNumberOfCells() != Integer.parseInt(corpnoOfcolumns)) {
				workbook.close();
				throw new CustomBadRequestException("Excel file has incorrect number of headers");
			}

			// checking header type and header names
			Iterator<Cell> celllist = row0.cellIterator();
			while (celllist.hasNext()) {
				Cell cell = celllist.next();
				if (!cell.getStringCellValue().equals(corpcompanyId) && !cell.getStringCellValue().equals(corptitle)
						&& !cell.getStringCellValue().equals(corplogo)
						&& !cell.getStringCellValue().equals(corproffertext)) {
					workbook.close();
					throw new CustomBadRequestException("Excel file has incorrect header names");
				}

			}

			logger.info("No of Rows " + sheet.getLastRowNum());

			/* row: */ for (int rowno = 1; rowno <= sheet.getLastRowNum(); rowno++) {

				logger.info("Current Row " + (rowno + 1));

				CorporateStagingEntity corpdata = new CorporateStagingEntity();

				Row row = sheet.getRow(rowno);

				logger.info("No of cells " + row.getLastCellNum());

				for (int cell = 0; cell < row.getLastCellNum(); cell++) {

					Cell currentCell = row.getCell(cell);

					/*
					 * if (currentCell == null) continue row;
					 */

					logger.info("Current Cell " + currentCell);

					// getting header name
					String headername = sheet.getRow(0).getCell(cell).getStringCellValue();

					if (corpcompanyId.equals(headername)) {
						if (currentCell!=null && !currentCell.getStringCellValue().equals(null)
								&& !currentCell.getStringCellValue().equals(" ")
								&& currentCell.getStringCellValue().matches("^[a-zA-Z0-9\\-]+$")) {
							Long countofentities = companyupload.countByCompany_Id(currentCell.getStringCellValue());
							if (countofentities == 0) {
								workbook.close();
								throw new CustomBadRequestException(
										corpcompanyId + " at row no." + (rowno + 1) + " not found in Company DB");
							} else
								corpdata.setCompanyId(currentCell.getStringCellValue());
						} else {
							workbook.close();
							throw new CustomBadRequestException(
									corpcompanyId + " should be alphanumeric row no." + (rowno + 1));
						}
					} else if (corptitle.equals(headername)) {
						if (currentCell!=null && !currentCell.getStringCellValue().equals(null)
								&& !currentCell.getStringCellValue().equals(" ")) {
							corpdata.setTitle(currentCell.getStringCellValue());
						} else {
							workbook.close();
							throw new CustomBadRequestException(corptitle + " is incorrect at row no." + (rowno + 1));
						}
					} else if (corplogo.equals(headername)) {
						if (currentCell!=null && !currentCell.getStringCellValue().equals(null)
								&& !currentCell.getStringCellValue().equals(" ")
								&& currentCell.getStringCellValue().matches("([^\\s]+(\\.(?i)(jpe?g|png|gif|bmp))$)")) {
							corpdata.setLogo(currentCell.getStringCellValue());
						} else {
							workbook.close();
							throw new CustomBadRequestException(corplogo + " should be valid logo image row no." + (rowno + 1));
						}
					} else if (corproffertext.equals(headername)) {
						if (currentCell!=null && !currentCell.getStringCellValue().equals(null)
								&& !currentCell.getStringCellValue().equals(" ")) {
							corpdata.setOffertext(currentCell.getStringCellValue());
						} else {
							workbook.close();
							throw new CustomBadRequestException(
									corproffertext + " is incorrect at row no." + (rowno + 1));
						}
					}

				}

				if (corpdata.getCompanyId() != null && !corpdata.getCompanyId().equals(null)
						&& corpdata.getTitle() != null && !corpdata.getTitle().equals(null)
						&& corpdata.getLogo() != null && !corpdata.getLogo().equals(null)
						&& corpdata.getOffertext() != null && !corpdata.getOffertext().equals(null)) {
					corpdatalist.add(corpdata);
				}

			}

			logger.info("Coporate List " + corpdatalist);

			corpdatalist.stream().forEach(rl -> {
				if (rl.getCompanyId().equals(null))
					corplistofnull.add(rl);
			});

			if (corpdatalist.size() == corplistofnull.size()) {
				workbook.close();
				throw new CustomBadRequestException("None of corporate entries matched with company Ids");
			}

			workbook.close();

			return corpdatalist;
		} catch (CustomBadRequestException e) {
			logger.info(e.getMessage());
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Fail to parse Excel file: " + e.getMessage());
		}

	}
}

package com.backbase.campaignupload.helper;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.backbase.campaignupload.entity.CorporateStagingEntity;
import com.backbase.campaignupload.entity.PartnerOffersStagingEntity;
import com.backbase.campaignupload.repo.CompanyUploadRepo;


public class ExcelHelper {
	
	private static final Logger logger = LoggerFactory.getLogger(ExcelHelper.class);

	public static String TYPE = "application/vnd.ms-excel";
	static String[] HEADERs = { "Header", "Logo", "OfferText"};
	static String SHEET = "PartnerOffer";
	static String SHEETCORP = "CorporateOffer";

	public static boolean hasExcelFormat(MultipartFile file) {

		if (!TYPE.equals(file.getContentType())) {

			return false;
		}

		return true;
	}

	public static List<PartnerOffersStagingEntity> excelToTutorials(InputStream is) {
		List<PartnerOffersStagingEntity> campaigndatalist = new ArrayList<PartnerOffersStagingEntity>();

		try {
			HSSFWorkbook workbook = new HSSFWorkbook(is);

			HSSFSheet sheet = workbook.getSheet(SHEET);
			
			logger.info("No of Rows " + sheet.getLastRowNum());

			row: for (int row = 1; row <= sheet.getLastRowNum(); row++) {

				logger.info("Current Row " + row);

				PartnerOffersStagingEntity campaigndata = new PartnerOffersStagingEntity();

				logger.info("No of cells " + sheet.getRow(row).getLastCellNum());

				for (int cell = 0; cell < sheet.getRow(row).getLastCellNum(); cell++) {

					HSSFCell currentCell = sheet.getRow(row).getCell(cell);
					
					if (sheet.getRow(row).getCell(0) == null)
						continue row;
					
					logger.info("Current Cell " + currentCell);

					switch (cell) {
					case 0:
						if (!currentCell.getStringCellValue().equalsIgnoreCase(null)
								&& !currentCell.getStringCellValue().equalsIgnoreCase(" ")) {
							campaigndata.setTitle(currentCell.getStringCellValue());
						}
						break;

					case 1:
						if (!currentCell.getStringCellValue().equalsIgnoreCase(null)
								&& !currentCell.getStringCellValue().equalsIgnoreCase(" ")) {
							campaigndata.setLogo(currentCell.getStringCellValue());
						}
						break;
					case 2:
						if (!currentCell.getStringCellValue().equalsIgnoreCase(null)
								&& !currentCell.getStringCellValue().equalsIgnoreCase(" ")) {
							campaigndata.setOffertext(currentCell.getStringCellValue());
						}
						break;
					
					default:
						break;
					}

				}

				campaigndatalist.add(campaigndata);
			}

			workbook.close();

			return campaigndatalist;
		} catch (Exception e) {
			throw new RuntimeException("Fail to parse Excel file: " + e.getMessage());
		}
	}
	public static List<CorporateStagingEntity> excelToCorporateStaging(InputStream is, CompanyUploadRepo companyupload) {


		List<CorporateStagingEntity> corpdatalist = new ArrayList<CorporateStagingEntity>();

		try {
			HSSFWorkbook workbook = new HSSFWorkbook(is);

			HSSFSheet sheet = workbook.getSheet(SHEETCORP);

			logger.info("No of Rows " + sheet.getLastRowNum());

			row: for (int row = 1; row <= sheet.getLastRowNum(); row++) {

				logger.info("Current Row " + row);

				CorporateStagingEntity corpdata = new CorporateStagingEntity();

				logger.info("No of cells " + sheet.getRow(row).getLastCellNum());

				for (int cell = 0; cell < sheet.getRow(row).getLastCellNum(); cell++) {

					HSSFCell currentCell = sheet.getRow(row).getCell(cell);
					
					if (sheet.getRow(row).getCell(0) == null)
						continue row;
					
					logger.info("Current Cell " + currentCell);

					switch (cell) {
					case 0:
						if (!currentCell.getStringCellValue().equalsIgnoreCase(null)
								&& !currentCell.getStringCellValue().equalsIgnoreCase(" ")) {
							Long countofentities = companyupload.countByCompany_Id(currentCell.getStringCellValue());
							if (countofentities == 0)
								continue row;
							else
								corpdata.setCompanyId(currentCell.getStringCellValue());
						}
			/*		case 0:
						if (!NumberToTextConverter.toText(currentCell.getNumericCellValue()).equalsIgnoreCase(null)
								&& !NumberToTextConverter.toText(currentCell.getNumericCellValue())
								.equalsIgnoreCase(" ")) {
							Long countofentities = companyupload.countByCompany_Id(NumberToTextConverter.toText(currentCell.getNumericCellValue()));
							if (countofentities == 0)
								continue row;
							else
								corpdata.setCompanyId(NumberToTextConverter.toText(currentCell.getNumericCellValue()));
						}*/
						break;
					case 1:
						if (!currentCell.getStringCellValue().equalsIgnoreCase(null)
								&& !currentCell.getStringCellValue().equalsIgnoreCase(" ")) {
							corpdata.setTitle(currentCell.getStringCellValue());
						}
						break;

					case 2:
						if (!currentCell.getStringCellValue().equalsIgnoreCase(null)
								&& !currentCell.getStringCellValue().equalsIgnoreCase(" ")) {
							corpdata.setLogo(currentCell.getStringCellValue());
						}
						break;
					case 3:
						if (!currentCell.getStringCellValue().equalsIgnoreCase(null)
								&& !currentCell.getStringCellValue().equalsIgnoreCase(" ")) {
							corpdata.setOffertext(currentCell.getStringCellValue());
						}
						break;
					
					default:
						break;
					}

				}
			

				corpdatalist.add(corpdata);
			}

			workbook.close();
			
			return corpdatalist;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Fail to parse Excel file: " + e.getMessage());
		}
	
	}
}

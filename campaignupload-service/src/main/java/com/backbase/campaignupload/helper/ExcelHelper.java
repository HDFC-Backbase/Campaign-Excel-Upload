package com.backbase.campaignupload.helper;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.backbase.campaignupload.entity.CampaignStagingEntity;


public class ExcelHelper {
	
	private static final Logger logger = LoggerFactory.getLogger(ExcelHelper.class);

	public static String TYPE = "application/vnd.ms-excel";
	static String[] HEADERs = { "Header", "Logo", "OfferText"};
	static String SHEET = "CampaignData";

	public static boolean hasExcelFormat(MultipartFile file) {

		if (!TYPE.equals(file.getContentType())) {

			return false;
		}

		return true;
	}

	public static List<CampaignStagingEntity> excelToTutorials(InputStream is) {
		List<CampaignStagingEntity> campaigndatalist = new ArrayList<CampaignStagingEntity>();

		try {
			HSSFWorkbook workbook = new HSSFWorkbook(is);

			HSSFSheet sheet = workbook.getSheet(SHEET);
			
			logger.info("No of Rows " + sheet.getLastRowNum());

			row: for (int row = 1; row <= sheet.getLastRowNum(); row++) {

				logger.info("Current Row " + row);

				CampaignStagingEntity campaigndata = new CampaignStagingEntity();

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
							campaigndata.setHeader(currentCell.getStringCellValue());
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

}

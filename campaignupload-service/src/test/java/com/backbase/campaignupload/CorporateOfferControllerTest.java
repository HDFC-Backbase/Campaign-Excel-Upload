package com.backbase.campaignupload;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.ByteArrayOutputStream;

import org.apache.catalina.core.ApplicationContext;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

/**
 * A wrapper annotation for use with integration tests.
 *
 * By default, assumes the integration test modifies the
 * {@link ApplicationContext} associated with the test/s and will therefore be
 * closed and removed from the context cache at the end of the class.
 */
@SpringBootTest(classes = Application.class)
@DirtiesContext(classMode = ClassMode.BEFORE_CLASS)
@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
@WebAppConfiguration
@ActiveProfiles("test")

/*
 * @Sql(scripts = {"classpath:drop_tables.sql","classpath:schema.sql",
 * "classpath:insert_file_master.sql",
 * "classpath:insert_corporateoffer_stag.sql" })
 */
@Sql(scripts = { "classpath:drop_tables.sql", "classpath:schema.sql", "classpath:insert_file_master.sql",
		"classpath:insert_cmp_stag.sql" })
public class CorporateOfferControllerTest {

	public static final String TEST_JWT = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJPbmxpbmUgSldUIEJ1aWxk"
			+ "ZXIiLCJpYXQiOjE0ODQ4MjAxOTYsImV4cCI6MTUxNjM1NjE5NiwiYXVkIjoid3d3LmV4YW1wbGUuY29tIiwic3ViIjoianJv"
			+ "Y2tldEBleGFtcGxlLmNvbSIsIkdpdmVuTmFtZSI6IkpvaG5ueSIsIlN1cm5hbWUiOiJSb2NrZXQiLCJFbWFpbCI6Impyb2Nr"
			+ "ZXRAZXhhbXBsZS5jb20iLCJSb2xlIjpbIk1hbmFnZXIiLCJQcm9qZWN0IEFkbWluaXN0cmF0b3IiXSwiaW51aWQiOiJKaW1te"
			+ "SJ9.O9TE28ygrHmDjItYK6wRis6wELD5Wtpi6ekeYfR1WqM";
	@Autowired
	private MockMvc mvc;

	@Test
	public void postCorporateOfferUpload() throws Exception {

		Workbook workbook = new HSSFWorkbook();
		Sheet sheet = workbook.createSheet("CorporateOffer");
		CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
		Font font = sheet.getWorkbook().createFont();
		font.setBold(true);
		cellStyle.setBorderTop(BorderStyle.THIN);
		cellStyle.setBorderBottom(BorderStyle.THIN);
		cellStyle.setBorderRight(BorderStyle.THIN);
		cellStyle.setFont(font);

		Row row = sheet.createRow(0);
		Cell cellcmpid = row.createCell(0);
		cellcmpid.setCellStyle(cellStyle);
		cellcmpid.setCellValue("CompanyId");

		Cell celltitle = row.createCell(1);
		celltitle.setCellStyle(cellStyle);
		celltitle.setCellValue("Title");

		Cell celllogo = row.createCell(1);
		celllogo.setCellStyle(cellStyle);
		celllogo.setCellValue("Logo");

		Cell celloffertext = row.createCell(2);
		celloffertext.setCellStyle(cellStyle);
		celloffertext.setCellValue("OfferText");

		Row row1 = sheet.createRow(1);
		Cell cell0 = row1.createCell(0);
		cell0.setCellValue("bostongroup");
		Cell cell1 = row1.createCell(1);
		cell1.setCellValue("Travel");
		Cell cell2 = row1.createCell(2);
		cell2.setCellValue(
				"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSgD514Sz3-HFz9n3C98n4KBlIwlEVjgXABzA&usqp=CAU");
		Cell cell3 = row1.createCell(3);
		cell3.setCellValue("Enjoy 50% off on purchases above 1,500");

		ByteArrayOutputStream bos = null;
		try {
			bos = new ByteArrayOutputStream();
			workbook.write(bos);
			workbook.close();
		} catch (Exception e) {
			System.out.println("Exception While writing excel file " + e.getMessage());
		}

		byte[] bytes = bos.toByteArray();

		MockMultipartFile file = new MockMultipartFile("file", "corporate-offer.xls", "application/vnd.ms-excel",
				bytes);

		this.mvc.perform(MockMvcRequestBuilders.multipart("/v1/corporate-offers").file(file)
				.param("uploadedBy", "Deepti Surve").header("Authorization", TEST_JWT)).andDo(print())
				.andExpect(status().isOk());
	}

	/*
	 * @Test public void getAllcorporateUpload() throws Exception {
	 * 
	 * RequestBuilder requestBuilderget =
	 * MockMvcRequestBuilders.get("/v1/corporate-offers").header("Authorization",
	 * TEST_JWT) .accept(MediaType.APPLICATION_JSON);
	 * 
	 * this.mvc.perform(requestBuilderget).andDo(print()).andExpect(status().isOk())
	 * .andExpect(jsonPath("$.headers").isArray()).andExpect(jsonPath("$.headers",
	 * hasSize(5))) .andExpect(jsonPath("$.headers[0].id",
	 * is("title"))).andExpect(jsonPath("$.headers[1].id", is("logo")))
	 * .andExpect(jsonPath("$.headers[2].id", is("offertext")))
	 * .andExpect(jsonPath("$.headers[3].id", is("companyid")))
	 * .andExpect(jsonPath("$.headers[4].id", is("approvalstatus")))
	 * .andExpect(jsonPath("$.data[0].title", is("Travel")))
	 * .andExpect(jsonPath("$.data[0].logo", is(
	 * "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSgD514Sz3-HFz9n3C98n4KBlIwlEVjgXABzA&usqp=CAU"
	 * ))) .andExpect(jsonPath("$.data[0].offertext",
	 * is("Enjoy 50% off on purchases above 1,500")))
	 * .andExpect(jsonPath("$.data[0].companyid", is("bostongroup")))
	 * .andExpect(jsonPath("$.data[0].approvalstatus", is("Approved"))); }
	 */
}

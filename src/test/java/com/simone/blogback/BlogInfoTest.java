package com.simone.blogback;

import java.io.IOException;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

public class BlogInfoTest {
	
	@Test
	public void test() throws IOException {
		try (Workbook wb = new XSSFWorkbook("blogjava.xlsx")) {
			Sheet st = wb.getSheet("blogs");
			System.out.printf("%s%n", st.getRow(169).getCell(2).getStringCellValue());
		}
	}
	
}

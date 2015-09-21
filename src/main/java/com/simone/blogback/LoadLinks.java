package com.simone.blogback;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.stream.IntStream;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class LoadLinks {
	
	public void loadLinks() throws IOException {
		ArrayList<String> links = new ArrayList<>();
		String url = "http://www.blogjava.net/wangxinsh55/default.html?page=%d";
		IntStream.range(1, 16).forEach(i -> {
			try {
				Document document = Jsoup.parse(new URL(String.format(url, i)), 10000);
				document.getElementsByAttributeValue("class", "postTitle").select("a").forEach(e -> {
					links.add(e.attr("href"));
				});
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		
		try (Workbook wb = new XSSFWorkbook()) {
			Sheet st = wb.createSheet("bloglinks");
			
			for (int i = 0; i < links.size(); i++) {
				st.createRow(i).createCell(0).setCellValue(links.get(i));
			}
			
			try (FileOutputStream fileOut = new FileOutputStream("blogjava.xlsx", false)) {
				wb.write(fileOut);
			}
		}
	}
	
}

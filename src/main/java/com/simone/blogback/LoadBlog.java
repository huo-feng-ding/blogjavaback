package com.simone.blogback;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.simone.blogback.entity.Blog;

public class LoadBlog {
	
	public Blog loadBlog(String url) throws MalformedURLException, IOException {
		Document document = Jsoup.parse(new URL(url), 10000);
		String title = document.select(".postTitle2").text();
		
		Elements content = document.select(".post");
		content.select(".postTitle").remove();
		
		Elements postDesc = content.select(".postDesc").remove();
		postDesc.select("a").remove();
		
		String postDate = postDesc.text().replaceAll(".+(\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}).+", "$1").trim();
		return new Blog(url, title, content.html(), postDate);
	}
	
	public void loadBlogs() throws IOException {
		FileInputStream fis = new FileInputStream("blogjava.xlsx");
		Workbook wb = new XSSFWorkbook(fis);
		Sheet st = wb.getSheet("bloglinks");
		int i = 0;
		ArrayList<String> bloglinks = new ArrayList<>();
		do {
			Row row = st.getRow(i++);
			if (row == null) {
				break;
			}
			Cell cell = row.getCell(0);
			if (cell == null) {
				break;
			}
			bloglinks.add(cell.getStringCellValue());
		} while (true);
		
		fis.close();
		
		Sheet blogsSt = wb.getSheet("blogs");
		if (blogsSt == null) {
			blogsSt = wb.createSheet("blogs");
		} else {
			wb.removeSheetAt(wb.getSheetIndex(blogsSt));
			blogsSt = wb.createSheet("blogs");
		}
		
		for (i = 0; i < bloglinks.size(); i++) {
			Blog blog = loadBlog(bloglinks.get(i));
			Row row = blogsSt.createRow(i);
			row.createCell(0).setCellValue(blog.getUrl());
			row.createCell(1).setCellValue(blog.getTitle());
			row.createCell(2).setCellValue(blog.getContent());
			row.createCell(3).setCellValue(blog.getPostTime());
			
			System.out.printf("finied %d%n", i);
		}
		
		try (FileOutputStream fileOut = new FileOutputStream("blogjava.xlsx")) {
			wb.write(fileOut);
		}
		
		wb.close();
	}
}

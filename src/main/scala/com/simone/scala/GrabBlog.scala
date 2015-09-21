package com.simone.scala

import java.io.FileOutputStream
import java.net.URL
import java.util.Date

import org.apache.poi.ss.usermodel.{Row, Workbook}
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import org.jsoup.Jsoup
import org.jsoup.nodes.{Element, Document}

import scala.collection.JavaConversions._

class GrabBlog {

  private def urlListStrs(cpage: Int): Iterator[String] = {
    val page: String = "http://www.blogjava.net/wangxinsh55/default.html?page=%d".format(cpage)
    for (
      e:Element <- Jsoup.parse(new URL(page), 30 * 1000).getElementsByClass("postTitle2").iterator();
      s = e.attr("href")
    ) yield s
  }

  private def pages: Int = {
    Jsoup.parse(new URL("http://www.blogjava.net/wangxinsh55/default.html?page=1"), 30 * 1000)
      .getElementsByClass("pager").select("a").last().attr("href").replaceAll(".*page=", "").toInt
  }

  case class Blog(url: String, title: String, content: String, date: Date)

  private def bakBlogs(seq: Seq[Blog]): Unit = {
    val wb: Workbook = new XSSFWorkbook()
    val sheet = Option(wb.getSheet("blogs")).map { f =>
      wb.removeSheetAt(wb.getSheetIndex(f))
      f
    }.getOrElse(wb.createSheet("blogs"))

    for (i <- 0 until seq.size;
         row: Row = sheet.createRow(i)
    ) {
      row.createCell(0).setCellValue(seq(i).url)
      row.createCell(1).setCellValue(seq(i).title)
      row.createCell(2).setCellValue(seq(i).content)
      row.createCell(3).setCellValue(seq(i).date)
    }

    try {
      wb.write(new FileOutputStream("blog.xlsx"))
    } finally {
      wb.close()
    }
  }

  def grabBlog(): Unit = {
    val blogs = for {
      i <- 1 to pages
      url: String <- urlListStrs(i)
      doc: Document = Jsoup.parse(new URL(url), 30 * 1000)
    } yield {
        val titleE = doc.select(".postTitle").remove()
        val dateE = doc.select(".postDesc").remove()
        val contentE = doc.select(".post")
        val date = DateTime.parse(dateE.text().replaceAll(".* (\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}) .*", "$1"),
          DateTimeFormat.forPattern("yyyy-MM-dd HH:mm")).toDate

        println("grab blog page %d, %s".format(i, url))

        Blog(url, titleE.text(), contentE.html(), date)
      }

    try {
      bakBlogs(blogs)
    } catch {
      case e: Exception => e.printStackTrace()
    }
  }
}

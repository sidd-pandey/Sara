package com.example.arpan.sara;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


public class TraiNewsCrawler {

	public static final String BASE_URL = "http://www.trai.gov.in";

	public static List<String> getNews() {
		// TODO Auto-generated method stub
		
		List<String> news = new ArrayList<String>();
		try {
			Document doc = Jsoup.connect("http://www.trai.gov.in/Content/Newsupdates.aspx").get();
			String title = doc.title();
			Elements test = (doc.getElementsByClass("upcoming_right").select("strong").select("a"));
			for(Element t : test){
				System.out.println(t.attr("title") + "##http://www.trai.gov.in"  + t.attr("href"));
				news.add(t.attr("title") + "@@@@"  + t.attr("href"));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return news;
	}
}

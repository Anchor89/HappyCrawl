package anchor89.test;

import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import anchor89.crawl.Fetcher;
import anchor89.util.U;

public class Try {
	String host = "http://www.ituring.com.cn";
	
	@Test
	public void testParse() {
		String html = "<a class=\"question_link\" href=\"http://chuansongme.com/n/113731\" id=\"__w2_o3iaxPJ_link\" target=\"_blank\"> 今日面试题：相差甚远；及Magic Index分析 </a>";
		Document doc = Jsoup.parse(html);
		U.println(doc);	
		U.println(doc.body().html());
	}
	
	@Test
	public void tryURL() throws MalformedURLException {
		URL url = new URL("http://chuansongme.com/n/113731");
		U.println(url.getProtocol());
		U.println(url.getHost());
	}

	/*@Test
	public void test() throws IOException {
		File file = new File("book.html");
		FileWriter fw = new FileWriter(file);
		Document doc = Fetcher.fetchHtml("http://www.ituring.com.cn/minibook/787#");
		Elements eles = doc.select("div#Catalog div.minibook-item a");
		List<Element> articles = new ArrayList<>();
		String title = null;
		Element h2 = null;
		int count = 0;
		for (Element e : eles) {
			String url = host + e.attr("href");
			Document newArticle = Fetcher.fetchHtml(url);

			Element content = newArticle.select("div.post-text").get(0);
			title = newArticle.select("title").html();
			if (count == 0) {
				Element head = newArticle.select("head").get(0);
				fw.append("<html>");
				Elements links = head.select("link");
				for (Element link:links) {
					if (link.hasAttr("href")) {
						link.attr("href", host+link.attr("href"));
					}
				}
				fw.append(head.toString());
				fw.append("<body>");
			}
			Element parent = null;
			while(!content.tagName().equalsIgnoreCase("body")) {
				parent = content.parent();
				parent.empty();
				parent.html(content.toString());
				content = parent;
			}
			setImgSrc(content);
			articles.add(content);
			if (count > 0) {
				fw.append(content.html());
			}
			++count;
			if (count == 10) {
				break;
			}
			U.println(count + ":" + title);
			U.println(h2);
		}
		fw.append("</body></html>");
		fw.flush();
		fw.close();
		U.println("Finished");
	} */
	
	private void setImgSrc(Element ele) {
		Elements eles = ele.select("img");
		for (Element e : eles) {
			if (e.hasAttr("src")) {
				String path = e.attr("src");
				e.attr("src", host+path);
			}
		}
	}
}

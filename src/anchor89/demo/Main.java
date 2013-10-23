package anchor89.demo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import anchor89.crawl.Fetcher;
import anchor89.crawl.Pager;
import anchor89.util.FileHelper;
import anchor89.util.U;

public class Main {
	private static String path = "F:/ForCrawler/chuansongmen_dzgz.htm";
	private static String outPath = "book.html";
	public static void main(String[] args) throws IOException {
		Pager indexPager = new DzgzIndex("0", path);
		Fetcher fetcher = new Fetcher();
		List<Pager> index = Arrays.asList(indexPager);
		fetcher.fetch(index);
		ContentCombiner cc = new ContentCombiner();
		String content = cc.combine(index); // Get content of <a>s
		Document doc = Jsoup.parse(content);
		Elements as = doc.select("a");
		List<Pager> articles = new ArrayList<Pager>();		
		int count = 0;
		for (Element a : as) {
			Pager pager = new ArticlePager(String.valueOf(count++), a.attr("href"));
			articles.add(pager);
		}
		fetcher.fetch(articles);
		ArticleCombiner ac = new ArticleCombiner();
		String finalHtml = ac.combine(articles);
		FileHelper.writeToFile(outPath, finalHtml);
		U.println("All down. File output at:" + outPath);
	}
}

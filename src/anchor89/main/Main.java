package anchor89.main;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import anchor89.crawl.Fetcher;
import anchor89.crawl.FilePager;
import anchor89.crawl.Pager;
import anchor89.util.U;

public class Main {
	private static String path = "F:/ForCrawler/chuansongmen_dzgz.htm";
	public static void main(String[] args) throws IOException {
		Pager index = new DzgzIndex("0", path);
		Fetcher fetcher = new Fetcher();
		List<Pager> pagers = Arrays.asList(index);
		fetcher.fetch(pagers);
		ContentCombiner cc = new ContentCombiner();
		String content = cc.combine(pagers);
		U.println(content);
	}
}

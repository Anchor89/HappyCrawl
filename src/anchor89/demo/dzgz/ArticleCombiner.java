package anchor89.demo.dzgz;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;

import anchor89.crawl.Combiner;
import anchor89.crawl.Pager;
import anchor89.util.U;

public class ArticleCombiner implements Combiner {

	@Override
	public String combine(List<Pager> pagers) {
		String result = "";
		Document frame = null;
		Elements articles = new Elements();

		for (Pager pager : pagers) {
			Map<String, String> cooked = pager.convert();
			if (cooked.containsKey("frame") && frame == null) {
				frame = Jsoup.parse(cooked.get("frame"));
				frame.title("´ý×Ö¹ëÖÐºÏ¼¯");
			}
			if (cooked.containsKey("article")) {
				try {
					articles.add(Jsoup.parse(cooked.get("article"))
							.select("div#essay-body").get(0));
				} catch (IndexOutOfBoundsException e) {
					U.println("Fail to combine article:" + pager.getUri());
					U.println(cooked.get("article"));
				}
			}
		}
		Element ele = frame.select("div#essay-body").get(0);
		Collections.reverse(articles);
		ele.insertChildren(0, articles);
		return frame.toString();
	}

}

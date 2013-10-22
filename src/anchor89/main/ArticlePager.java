package anchor89.main;

import java.util.HashMap;
import java.util.Map;





import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import anchor89.crawl.HttpPager;
import anchor89.util.U;

public class ArticlePager extends HttpPager {

	public ArticlePager(String id, String url) {
		super(id, url);
	}

	@Override
	public Map<String, String> convert() {
		Map<String, String> result = new HashMap<String, String>();
		Element title = doc.select("title").get(0);
		Element text = doc.select("div.text").get(0);
		result.put(id, title.html()+text.html());
		Element cur = text;
		Element parent = null;
		while(!cur.tag().getName().equalsIgnoreCase("body")) {
			parent = cur.parent();
			parent.empty();
			parent.html(cur.toString());
			cur = parent;
		}
		cur = cur.parent(); // got <html>
		cur.select("div.page-content").get(0).empty();
		result.put("frame", cur.toString());
		return result;
	}

}

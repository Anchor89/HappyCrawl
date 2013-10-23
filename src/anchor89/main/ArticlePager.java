package anchor89.main;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;









import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import anchor89.crawl.HttpPager;
import anchor89.util.U;

public class ArticlePager extends HttpPager {
	final private static Logger logger = LogManager.getLogger(ArticlePager.class);
	public ArticlePager(String id, String url) {
		super(id, url);
	}

	/**
	 * Return: 
	 * frame->outline html
	 * id->article
	 */
	@Override
	public Map<String, String> convert() {
		Map<String, String> result = new HashMap<String, String>();
		String host = "";
		try {
			host = new URL(uri).getHost();
		} catch (MalformedURLException e) {
			logger.error(e);
			e.printStackTrace();
		}
		Element title = doc.select("title").get(0);
		Element text = doc.select("div#essay-body").get(0);
		Elements imgs = text.select("img");
		for (Element img : imgs) {
			String src = img.attr("src");
			if (!src.startsWith("http")) {
				src = "http://" + host + src;
				img.attr("src", src);
			} else if (src.startsWith("http://essay.oss.ali")){
				src = src.substring(src.indexOf("689"));
				src = src.replace('/', '-');
				src = "pics/" + src;
				img.attr("src", src);
			}
		}
		result.put("article", title.toString()+text.toString());
		Element cur = text;
		Element parent = cur.parent();
		cur.empty();
		while(!parent.tag().getName().equalsIgnoreCase("html")) {
			parent.empty();
			parent.html(cur.toString());
			cur = parent;
			parent = cur.parent();
		}
		result.put("frame", parent.toString());
		return result;
	}

}

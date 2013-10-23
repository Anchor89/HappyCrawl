package anchor89.main;

import java.util.HashMap;
import java.util.Map;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import anchor89.crawl.FilePager;
import anchor89.crawl.Pager;
import anchor89.util.U;

public class DzgzIndex extends FilePager {

	public DzgzIndex(String id, String path) {
		super(id, path);
	}

	/**
	 * Return:
	 * id-><a...>
	 */
	@Override
	public Map<String, String> convert() {
		Map<String, String> result = new HashMap<String, String>();
		if (doc == null) {
			return result;
		}
		Elements items = doc.select("div.pagedlist_item a.question_link");
		int count = 0;
		for (Element item : items) {
			result.put(String.valueOf(count++), item.toString());
		}
		return result;
	}
}

package anchor89.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;

import anchor89.crawl.Combiner;
import anchor89.crawl.Pager;
import anchor89.util.U;

public class ContentCombiner implements Combiner {

	@Override
	public String combine(List<Pager> pagers) {
		String result = "";
		Tag tag = Tag.valueOf("div");
		if (pagers!=null && pagers.size()>0) {
			Pager index = pagers.get(0);
			Map<String, String> cooked = index.convert();
			Element ele = new Element(tag, "");
			Elements items = new Elements(cooked.size() + 1);
			for (int i=0; i<cooked.size(); i++) {
				items.add(null);
			}
			for (String k : cooked.keySet()) {
				String v = cooked.get(k);
				ele.html(v);
				items.set(Integer.valueOf(k), ele.select("a").get(0));
			}
			for (Element item : items) {
				result += item;
			}
		}		
		return result;
	}

}

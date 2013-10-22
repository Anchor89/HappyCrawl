package anchor89.main;

import java.util.List;
import java.util.Map;

import anchor89.crawl.Combiner;
import anchor89.crawl.Pager;

public class ArticleCombiner implements Combiner {

	@Override
	public String combine(List<Pager> pagers) {
		String result = "";
		for (Pager pager : pagers) {
			Map<String, String> cooked = pager.convert();
			String frame = null;
			if (cooked.containsKey("frame")) {
				frame = cooked.get("frame");
			}
		}			
		return result;
	}

}

package anchor89.demo.dianping;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import anchor89.crawl.HttpPager;

public class SearchPage extends HttpPager<String, Element> {
  final private static Logger logger = LogManager.getLogger(SearchPage.class);
  
  public SearchPage(String id) {
    super(id);
  }
  
  public SearchPage(String id, String url) {
    super(id, url);
  }
  
  @Override
  public Map<String, Element> convert() {
    Map<String, Element> result = new HashMap<String, Element>();
    if (doc != null) {
      Elements shopnames = doc.select("li.shopname a.BL");
      int count = 0;
      for (Element shopname:shopnames) {
        result.put(String.format("%s %d", id, count++), shopname);
      } 
    }
    return result;
  }
}

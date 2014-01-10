package anchor89.demo.dianping;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import anchor89.crawl.HttpPager;

public class ShopPage extends HttpPager<String, String> {
  final private static Logger logger = LogManager.getLogger(ShopPage.class);
  
  public ShopPage(String id) {
    super(id);
  }
  
  public ShopPage(String id, String url) {
    super(id, url);
  }

  @Override
  public Map<String, String> convert() {
    Map<String, String> result = new HashMap<String, String>();
    if (doc != null) {
      ShopInfo si = new ShopInfo();
      si.initFromDoc(doc);    
      result.put(id, si.toCsvLine());
    }

    return result;
  }
  

}

package anchor89.demo.dianping;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import anchor89.crawl.HttpPager;

public class CitySearchCategoryPager extends HttpPager<String, String> {
  final private static Logger logger = LogManager
      .getLogger(CitySearchCategoryPager.class);
  private static String host = "http://www.dianping.com";
  
  class Pair {
    public String key = "";
    public String value = "";
    
    public String toString() {
      return key+":"+value;
    }
  }
  
  public CitySearchCategoryPager(String id, String url) {
    super(id, url);
  }
  
  @Override
  public Map<String, String> convert() {
    Map<String, String> result = new HashMap<String, String>();
//    logger.info(doc);
    Elements distrinct1 = doc.select("li#append-cont dl");
    Element dl = distrinct1.first();
    Elements dtdd = dl.children();
    String distrinctName = "";
    Pair res = null;
    for (Element e : dtdd) {
      if (e.tag().getName().equalsIgnoreCase("dt")) {
        res = parseAnchor(e.child(0));
//        result.put(res.key, res.value);
        distrinctName = res.key;
      } else if (e.tag().getName().equalsIgnoreCase("dd")) {
        Elements lis = e.child(0).children();
        for (Element li : lis) {
          res = parseAnchor(li.child(0));
          result.put(distrinctName+" "+res.key, res.value);
        }
      }
    }
    return result;
  }
  
  private Pair parseAnchor(Element e) {
    Pair result = new Pair();
    if (e.tag().getName().equalsIgnoreCase("a")) {
      String name = e.text();
      String url = host + e.attr("href");
      result.key = name;
      result.value = url;
    }
    return result;
  }
}

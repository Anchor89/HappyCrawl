package anchor89.demo.dianping;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import anchor89.crawl.HttpPager;
import anchor89.demo.dianping.CitySearchCategoryPager.Pair;

public class DistrictSearchCategoryPager extends HttpPager<String, String>{
  final private static Logger logger = LogManager
      .getLogger(DistrictSearchCategoryPager.class);
  private static String host = "http://www.dianping.com";
  
  class Pair {
    public String key = "";
    public String value = "";
    
    public String toString() {
      return key+":"+value;
    }
  }
  
  public DistrictSearchCategoryPager(String id, String url) {
    super(id, url);
  }

  /**
   * Output districtName areaName:postfix of url
   */
  @Override
  public Map<String, String> convert() {
    Map<String, String> result = new HashMap<String, String>();
    Elements ul = doc.select("ul.navBlock ul.current");
    Element ditrinctNameLi = ul.first().child(0);
    String distrinct = parseAnchor(ditrinctNameLi.child(0)).key;
    distrinct = distrinct.substring(distrinct.lastIndexOf(';')+1);

    Elements areaLi = ul.first().child(1).child(0).children();
//    logger.info(areaLi);
    for (Element li : areaLi) {
      logger.info(li);
      Pair res = parseAnchor(li.child(0));
      logger.info(res);
      int end = res.key.indexOf('&');
      res.key = distrinct + " " + res.key.substring(0, end > 0? res.key.length():end);
      res.value = res.value.substring(res.value.lastIndexOf('/'));
      result.put(res.key, res.value);
    }
    return result;
  }
  
  private Pair parseAnchor(Element e) {
    Pair result = new Pair();
    if (e.tag().getName().equalsIgnoreCase("a")) {
      String name = e.html();
      String url = host + e.attr("href");
      result.key = name;
      result.value = url;
    }
    return result;
  }
}

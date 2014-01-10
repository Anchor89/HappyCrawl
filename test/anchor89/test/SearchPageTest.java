package anchor89.test;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Map;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.jsoup.nodes.Element;
import org.junit.Test;

import anchor89.crawl.Fetcher;
import anchor89.demo.dianping.SearchPage;
import anchor89.util.U;

public class SearchPageTest {
  final private static Logger logger = LogManager
      .getLogger(SearchPageTest.class);
  
  @Test
  public void test() {
    SearchPage sp = new SearchPage("test", "http://www.dianping.com/search/category/2/0/r1482");
    Fetcher fetcher = new Fetcher();
    if (fetcher.fetch(Arrays.asList(sp))) {
      Map<String, Element> cooked = sp.convert();
      for (String key : cooked.keySet()) {
        Element ele = cooked.get(key);
        U.println(key + ":" + ele);
      }
    }
  }
}

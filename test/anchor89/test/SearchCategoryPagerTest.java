package anchor89.test;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.junit.Test;

import anchor89.crawl.Fetcher;
import anchor89.demo.dianping.CitySearchCategoryPager;
import anchor89.util.FileHelper;

public class SearchCategoryPagerTest {
  final private static Logger logger = LogManager
      .getLogger(SearchCategoryPagerTest.class);
  
  @Test
  public void test() {
    CitySearchCategoryPager pager = new CitySearchCategoryPager("1", "http://www.dianping.com/search/category/2/0/r1488");
    Fetcher fetcher = new Fetcher();
    String text = "";
    if (fetcher.fetch(Arrays.asList(pager))) {
      Map<String, String> cooked = pager.convert();
      for (Entry<String, String> e : cooked.entrySet()) {
        text += e.getKey() + ":" + e.getValue() + "\n";
      }
      FileHelper.writeToFile("beijingSubDistrict", text);
    }    
  }
}

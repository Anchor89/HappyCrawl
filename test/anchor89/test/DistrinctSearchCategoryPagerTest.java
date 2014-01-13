package anchor89.test;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Map;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.junit.Test;

import anchor89.crawl.Fetcher;
import anchor89.demo.dianping.DistrictSearchCategoryPager;

public class DistrinctSearchCategoryPagerTest {
  final private static Logger logger = LogManager
      .getLogger(DistrinctSearchCategoryPagerTest.class);
  
  @Test
  public void test() {
    DistrictSearchCategoryPager pager = new DistrictSearchCategoryPager("test", "http://www.dianping.com/search/category/2/0/r17");
    Fetcher fetcher = new Fetcher();
    if (fetcher.fetch(Arrays.asList(pager))) {
      Map<String, String> cooked = pager.convert();
      logger.info(cooked);
    }
  }
}

package anchor89.test;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Map;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.jsoup.nodes.Element;
import org.junit.Test;

import anchor89.crawl.Fetcher;
import anchor89.demo.dianping.ShopInfo;
import anchor89.demo.dianping.ShopPage;
import anchor89.util.U;

public class ShopPageTest {
  final private static Logger logger = LogManager.getLogger(ShopPageTest.class);
  
  @Test
  public void test() {
    ShopPage sp = new ShopPage("test", "http://www.dianping.com/shop/571274");
    ShopInfo si = new ShopInfo();
    U.println(si.csvHeadLine());
  }
}

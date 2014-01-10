package anchor89.demo.dianping;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.jsoup.nodes.Element;

import anchor89.crawl.Fetcher;
import anchor89.crawl.HttpPager;

public class DianpingMain {
  final private static Logger logger = LogManager.getLogger(DianpingMain.class);
  
  public static void main(String[] args) throws IOException {
    List<String> searchUrls = new ArrayList<String>();
    String searchUrlBase = "http://www.dianping.com/search/category/2/0/r1482";
    searchUrls.add(searchUrlBase);
    for (int i=2; i<=50; i++) {
      searchUrls.add(searchUrlBase+"p"+i);
    }
    List<HttpPager> searchPages = new ArrayList<HttpPager>();
    int i = 1;
    for (String url : searchUrls) {
      searchPages.add((new SearchPage(String.valueOf(i++), url)).storeLocally(true, "dianpingSearchPage_"));
    }
    Fetcher fetcher = new Fetcher();
    Map<String, Element> shops = null;
    List<HttpPager> shopPages = new ArrayList<HttpPager>();
    List<String> csv = new ArrayList<String>();
    String host = "http://www.dianping.com";
    i = 0;
    
    String csvFileName = "fuxingmen.csv";
    File file = new File(csvFileName);
    if (!file.exists()) {
      file.createNewFile();
    }
    FileOutputStream fos = new FileOutputStream(csvFileName);
    OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
    
    int pageCount = 1;
    if (fetcher.fetch(searchPages)) {
      logger.info(String.format("Crawed %d search pages", searchPages.size()));
      for (HttpPager hp : searchPages) {
        hp.store();
        shops = hp.convert();
        shopPages.clear();
        for (Element ele : shops.values()) {
          String shopUrl = ele.attr("href");
          if (!shopUrl.startsWith("/shop")) continue;
          shopUrl = host+shopUrl;
          shopPages.add((new ShopPage(String.valueOf(i), shopUrl).storeLocally(true, "dianpingShopPage_")));
          i++;
        }
        try {
          Thread.sleep(50*1000);
        } catch (InterruptedException e) {
          logger.error(e);
        }
        if (fetcher.fetch(shopPages)) {
          logger.info(String.format("Crawled shop pages %d-%d", i-shopPages.size(),i-1));
          for (HttpPager shop : shopPages) {
            ShopPage s = (ShopPage) shop;
            csv.addAll(s.convert().values());
            for (String line : csv) {
              osw.write(line);
              osw.write("\n");
              osw.flush();
            }
            csv.clear();
          }
        } else {
          logger.warn(String.format("Failed to crawled shop pages %d-%d", i-shopPages.size(), i-1));
        }
        logger.info("Finish craw shops from search page:" + pageCount++);
      }
    }    
    osw.close();
    fos.close();
  }
}

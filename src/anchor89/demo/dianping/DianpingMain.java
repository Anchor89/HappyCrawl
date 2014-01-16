package anchor89.demo.dianping;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.jsoup.nodes.Element;

import anchor89.crawl.Fetcher;
import anchor89.crawl.HttpPager;

public class DianpingMain {
  final private static Logger logger = LogManager.getLogger(DianpingMain.class);
  private static List<UrlCandidate> urls = new ArrayList<UrlCandidate>();
  private static String host = "http://www.dianping.com";
  
  private static void initCrawlFuxingmenShops() {
//    urls.add(new UrlCandidate("FinanceStreet",
//        "http://www.dianping.com/search/category/2/0/r2189", 50)); // 金融街购物中心
    urls.add(new UrlCandidate("NanlishiluSubway",
        "http://www.dianping.com/search/category/2/0/r2109", 50)); // 南礼士路地铁站
    urls.add(new UrlCandidate("BaishengShoppingMall",
        "http://www.dianping.com/search/category/2/0/r2636", 50)); // 复兴门百盛
    urls.add(new UrlCandidate("FuxinmengSubway",
        "http://www.dianping.com/search/category/2/0/r2110", 50)); // 复兴门地铁站
    urls.add(new UrlCandidate("CentralMusicSchoool",
        "http://www.dianping.com/search/category/2/0/r2783", 33)); // 中央音乐学院
    urls.add(new UrlCandidate("MinzuHotel",
        "http://www.dianping.com/search/category/2/0/r2696", 31)); // 民族饭店
  }
  
  public static List<HttpPager> initSearchPages(UrlCandidate urlCan) {
    List<HttpPager> searchPages = new ArrayList<HttpPager>();
    int i = 0;
    for (String url : urlCan.getAll()) {
      searchPages.add(new SearchPage(String.valueOf(i++), url));
    }
    return searchPages;
  }
  
  public static boolean ensureFile(String filename) {
    boolean result = true;
    File file = new File(filename);
    if (!file.exists()) {
      try {
        file.createNewFile();
      } catch (IOException e) {
        result = false;
        logger.error(e);
      }
    }
    return result;
  }
  
  public static List<HttpPager> getShopPages(HttpPager searchPage) {
    List<HttpPager> result = new ArrayList<HttpPager>();
    Map<String, Element> shops = searchPage.convert();
    int i = 0;
    for (Element ele : shops.values()) {
      String shopUrl = ele.attr("href");
      if (!shopUrl.startsWith("/shop"))
        continue;
      shopUrl = host + shopUrl;
      result.add((new ShopPage(String.valueOf(i), shopUrl)));
      i++;
    }
    return result;
  }
  
  public static void pause(long millisecond) {
    try {
      Thread.sleep(50 * 1000);
    } catch (InterruptedException e) {
      logger.error(e);
    }
  }
  
  public static List<String> getShopsInfo(List<HttpPager> shops) {
    List<String> result = new ArrayList<String>();
    for (HttpPager shop : shops) {
      result.addAll(shop.convert().values());
    }
    return result;
  }
  
  private static void crawlShops() throws IOException {
    Fetcher fetcher = new Fetcher();
    List<HttpPager> searchPages = null;
    List<HttpPager> shopPages = new ArrayList<HttpPager>();
    String filename = "";
    
    initCrawlFuxingmenShops();
    
    for (UrlCandidate urlc : urls) {
      searchPages = initSearchPages(urlc);
      filename = urlc.getId() + ".csv";
      if (!ensureFile(filename)) continue;
      
      OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(filename), "UTF-8");
      osw.write(ShopInfo.csvHeadLine() + "\n");
      int pageCount = 1;
      if (fetcher.fetch(searchPages)) {
        logger
            .info(String.format("Crawed %d search pages for %s", searchPages.size(), urlc.getId()));
        for (HttpPager hp : searchPages) {
          pause(50*1000);
          shopPages = getShopPages(hp);
          if (fetcher.fetch(shopPages)) {
            logger.info("Finish craw shops from search page:" + pageCount++);
            for (String line : getShopsInfo(shopPages)) {
              osw.write(line + "\n");
            }
            osw.flush();
          } else {
            logger.info("Failed craw shops from search page:" + pageCount++);
          }          
        }
      }
      osw.close();
    }
  }
  
  private static void crawlDistrinctPage() {
    final String ajaxSearchUrl = "http://www.dianping.com/search/ajax/regionlist/category/2/0";
    // Init URLs of district to be crawled
    urls.add(new UrlCandidate("","http://www.dianping.com/search/category/2/0/r14",1));
    
    /*
    urls.add(new UrlCandidate("","http://www.dianping.com/search/category/2/0/r328",1));
    urls.add(new UrlCandidate("","http://www.dianping.com/search/category/2/0/r9157",1));
    urls.add(new UrlCandidate("","http://www.dianping.com/search/category/2/0/r15",1));
    urls.add(new UrlCandidate("","http://www.dianping.com/search/category/2/0/r20",1));
    urls.add(new UrlCandidate("","http://www.dianping.com/search/category/2/0/r9158",1));
    urls.add(new UrlCandidate("","http://www.dianping.com/search/category/2/0/r5951",1));
    urls.add(new UrlCandidate("","http://www.dianping.com/search/category/2/0/r16",1));
    urls.add(new UrlCandidate("","http://www.dianping.com/search/category/2/0/r17",1));
    urls.add(new UrlCandidate("","http://www.dianping.com/search/category/2/0/r5952",1));
    urls.add(new UrlCandidate("","http://www.dianping.com/search/category/2/0/r5950",1));
    urls.add(new UrlCandidate("","http://www.dianping.com/search/category/2/0/r21",1));
  */
    // Get subarea urls
    Fetcher fetcher = new Fetcher().useProxy(false);
    List<HttpPager> pagers = new ArrayList<HttpPager>();
    for (UrlCandidate url : urls) {
      DistrictSearchCategoryPager pager = new DistrictSearchCategoryPager("", url.getBase());
      pagers.add(pager);
    }
    
    List<HttpPager> ajaxes = new ArrayList<HttpPager>();
    if (fetcher.fetch(pagers)) {
      for (HttpPager pager : pagers) {
        Map<String, String> cooked = pager.convert();
        for (String key : cooked.keySet()) {
          logger.info(key + ":" + cooked.get(key));
          AjaxAreaPager ajax = new AjaxAreaPager(key, ajaxSearchUrl+cooked.get(key));
//          ajaxes.add(ajax);
        }        
      }
    }
    
    if (fetcher.fetch(ajaxes)) {
      
    }
    
  }
  
  public static void main(String[] args) throws IOException {
    crawlDistrinctPage();
  }

}

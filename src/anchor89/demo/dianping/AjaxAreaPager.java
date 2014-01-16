package anchor89.demo.dianping;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import anchor89.crawl.HttpPager;

public class AjaxAreaPager {
  final private static Logger logger = LogManager
      .getLogger(AjaxAreaPager.class);

  private String id = "";
  private String url = "";
  public AjaxAreaPager(String id, String url) {
    this.id = id;
    this.url = url;
  }
}

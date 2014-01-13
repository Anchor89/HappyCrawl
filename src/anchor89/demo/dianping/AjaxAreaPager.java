package anchor89.demo.dianping;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import anchor89.crawl.HttpPager;

public class AjaxAreaPager extends HttpPager<String, String> {
  final private static Logger logger = LogManager
      .getLogger(AjaxAreaPager.class);

  public AjaxAreaPager(String id, String url) {
    super(id, url);
  }

  @Override
  public Map<String, String> convert() {
    Map<String, String> result = new HashMap<String, String>();
    logger.info(doc);
    return result;
  }
}

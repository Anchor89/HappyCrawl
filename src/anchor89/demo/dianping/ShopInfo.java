package anchor89.demo.dianping;

import java.util.Date;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import anchor89.util.U;

public class ShopInfo {
  final private static Logger logger = LogManager.getLogger(ShopInfo.class);
  private String shopTitle = "";
  private String location = "";
  private String call = "";
  private String costAver = "";
  private String rateTaste = "";
  private String rateEnv = "";
  private String rateService = "";
  private String groupProm = "";
  private String comment = "";
  private long time;
  public ShopInfo initFromDoc(Document doc) {
    Elements shopTitleEle = doc.select("h1.shop-title");
    Elements localtionEle= doc.select("div.shop-location li:eq(1)");
    Elements callEle = doc.select("div.shop-location li:eq(2)");
    Elements costAverEle = doc.select("div.rst-taste strong.stress");
    Elements rateEle = doc.select("div.rst-taste span.rst");
    Elements groupPromEle = doc.select("div.promo-list li");
    Elements commentEle = doc.select("div.comment-tab li[data-name='all'] em");
    
    shopTitle = U.firstTextOrEmpty(shopTitleEle);
    location = U.firstTextOrEmpty(localtionEle);
    call = U.firstTextOrEmpty(callEle);
    costAver = U.firstTextOrEmpty(costAverEle);
    rateTaste = U.nthTextOrEmpty(rateEle, 0);
    rateEnv = U.nthTextOrEmpty(rateEle, 1);
    rateService = U.nthTextOrEmpty(rateEle, 2);
    groupProm = "";
    for (Element ele : groupPromEle) {
      String[] buyCount = ele.select("span.col-exp").text().split("\\D", 1);
      groupProm += String.format("%s:%s ", ele.select("strong.price").text().trim(),buyCount.length > 0? buyCount[0]:"null"); 
    }
    comment = U.firstTextOrEmpty(commentEle);
    time= (new Date()).getTime();
    return this;
  }
  
  public String toCsvLine() {
    return String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%d", shopTitle, location, call, costAver, rateTaste, rateEnv, rateService, groupProm, comment, time);
  }
  
  public String getShopTitle() {
    return shopTitle;
  }

  public void setShopTitle(String shopTitle) {
    this.shopTitle = shopTitle;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String getCall() {
    return call;
  }

  public void setCall(String call) {
    this.call = call;
  }

  public String getCostAver() {
    return costAver;
  }

  public void setCostAver(String costAver) {
    this.costAver = costAver;
  }

  public String getRateTaste() {
    return rateTaste;
  }

  public void setRateTaste(String rateTaste) {
    this.rateTaste = rateTaste;
  }

  public String getRateEnv() {
    return rateEnv;
  }

  public void setRateEnv(String rateEnv) {
    this.rateEnv = rateEnv;
  }

  public String getRateService() {
    return rateService;
  }

  public void setRateService(String rateService) {
    this.rateService = rateService;
  }

  public String getGroupProm() {
    return groupProm;
  }

  public void setGroupProm(String groupProm) {
    this.groupProm = groupProm;
  }

  public ShopInfo() {

  }
}

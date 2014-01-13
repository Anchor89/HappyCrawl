package anchor89.demo.dianping;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

class UrlCandidate {
  private String id = "";
  private String base = "";
  private int count = 0;
  public UrlCandidate(String id, String base, int count) {
    this.id = id;
    this.base = base;
    this.count = count;
  }
  
  public String getId() {
    return id;
  }
  
  public String getBase() {
    return base;
  }

  public int getCount() {
    return count;
  }

  public String get(int i) {
    if (i < 1 || i > count) {
      return "";
    } else {
      if (i == 1) {
        return base;
      } else {
        return base+"p"+i;
      }
    }
  }
  
  public List<String> getAll() {
    List<String> result = new ArrayList<String>();
    for (int i=1; i<=count; i++) {
      result.add(get(i));
    }
    return result;
  }
}
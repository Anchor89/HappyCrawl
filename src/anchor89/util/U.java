package anchor89.util;

import org.jsoup.select.Elements;

public class U {
	public static void println(Object line) {
		System.out.println(line);
	}
	
  public static String firstTextOrEmpty(Elements ele) {
    return nthTextOrEmpty(ele, 0);
  }
  
  public static String nthTextOrEmpty(Elements ele, int index) {
    return ele.size() <= index? "":ele.get(index).text().trim();
  }
}

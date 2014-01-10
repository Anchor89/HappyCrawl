package anchor89.crawl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class HttpPager<K, V> extends Pager<K, V> {
  private static Logger logger = LogManager.getLogger(HttpPager.class);
  private boolean storeLocally = false;
  private String fileNamePrefix = "";
    
	public HttpPager(String id) {
		super(id);
	}
	public HttpPager(String id, String url) {
		super(id, url);
	}

  public HttpPager<K, V> storeLocally(boolean switcher, String fileNamePrefix) {
    storeLocally = switcher;
    this.fileNamePrefix = fileNamePrefix != null? fileNamePrefix:"";
    return this;
  }
  
  public HttpPager store() {
    if (storeLocally && doc != null) {
      try {
        File file = new File(fileNamePrefix + id);
        if (!file.exists()) {
          file.createNewFile();
        }
        FileOutputStream fos = new FileOutputStream(fileNamePrefix + id);
        OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
        osw.write(doc.html());
        osw.flush();
        osw.close();
        fos.close();
      } catch (FileNotFoundException e1) {
        logger.error(e1);
      } catch (UnsupportedEncodingException e) {
        logger.error(e);
      } catch (IOException e) {
        logger.error(e);
      }
    }
    return this;
  }
}

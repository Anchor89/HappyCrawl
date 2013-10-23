package anchor89.crawl;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import anchor89.util.U;

public class Fetcher {
	final private static Logger logger = LogManager.getLogger(Fetcher.class);
	protected class LonelyPager extends Thread {
		final private Logger logger = LogManager.getLogger(LonelyPager.class);
		private Pager pager = null;

		protected LonelyPager(Pager pager){
			this.pager = pager;
		}
		
		@Override 
		public void run() {
			if (pager == null) {
				logger.error("Pager is null. Thread:" + this);
			} else {
				String url = pager.getUri();
				if (url != null) {
					int tryTime = 0;
					while (tryTime++ < 3) {
						try {
							pager.setDoc(fetch(this.pager));
							tryTime = 3;
							logger.info("Success crawled:" + pager.getId());
						} catch (IOException e) {
							logger.error("Fail(" + tryTime + ") to crawl for URL:" + url + "\n"
									+ e);
						}
					}
				} else {
					logger.error("Pager with id:" + pager.getId() + " has NULL url.");
				}				
			}
		}
		
		protected Document fetch(Pager pager) throws IOException {
			Document result = null;
			if (pager instanceof HttpPager) {
				result = Jsoup.connect(pager.getUri()).timeout(20000).get();				
			} else if (pager instanceof FilePager) {
				File file = new File(pager.getUri());
				result = Jsoup.parse(file, "UTF-8");				
			}

			return result;
		}
	}
	
	public Fetcher() {

	}
	
	public boolean fetch(List<? extends Pager> pagers) {
		boolean result = true;
		List<LonelyPager> waiting = new ArrayList<LonelyPager>();
		ExecutorService threadPool = Executors.newFixedThreadPool(10);
		for (Pager p : pagers) {
			threadPool.execute(new LonelyPager(p));
		}
		threadPool.shutdown();
		try {
			threadPool.awaitTermination(pagers.size(), TimeUnit.HOURS);
		} catch (InterruptedException e) {
			logger.error(e);
			result = false;
		}
		return result;
	}
}

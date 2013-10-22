package anchor89.crawl;

import java.util.Map;
import org.jsoup.nodes.Document;

public abstract class Pager {
	protected String uri;
	protected String id;
	protected Document doc = null;
	public Pager(String id) {
		this.id = id;
	}
	public Pager(String id, String uri) {
		this.id = id;
		this.uri = uri;
	}

	public abstract Map<String, String> convert();

	public String getUri() {
		return uri;
	}
	
	public void setUri(String uri) {
		this.uri = uri;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Document getDoc() {
		return doc;
	}
	public void setDoc(Document doc) {
		this.doc = doc;
	}
}

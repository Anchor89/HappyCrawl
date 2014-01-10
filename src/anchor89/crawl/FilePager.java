package anchor89.crawl;

public abstract class FilePager<K, V> extends Pager<K, V> {
	public FilePager(String id) {
		super(id);
	}
	public FilePager(String id, String path){
		super(id, path);
	}
}

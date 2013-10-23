package anchor89.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FileHelper {
	final private static Logger logger = LogManager.getLogger(FileHelper.class);
	public static boolean writeToFile(String path, String content) {
		boolean result = true;
		try {
			FileOutputStream fos = new FileOutputStream(path); 
			OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
			osw.write(content);
			osw.flush();
			osw.close();
		} catch (IOException e) {
			logger.error(e);
		}
		return result;
	}
}

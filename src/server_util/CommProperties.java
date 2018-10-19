package server_util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 * @author lisu
 *
 */
public class CommProperties {
	private CommProperties() {

	}

	public static String getValue(String key,File file) {
		String value = null;
		Properties properties = null;
		InputStream inputStream = null;
		try {
			properties = new Properties();
			inputStream = new FileInputStream(file);
			properties.load(inputStream);
			value = properties.getProperty(key);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return value;
	}
/*
	public static void setValue(String key, String value) {
		Properties properties = null;
		InputStream inputStream = null;
		OutputStream outputStream = null;
		try {
			properties = new Properties();
			inputStream = new FileInputStream(file);
			properties.load(inputStream);
			properties.setProperty(key, value);

			// 将properties中的内容更新到文件中
			outputStream = new FileOutputStream(file);
			properties.store(outputStream, "");
			outputStream.flush();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}*/

}

package util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import server_util.CommDateandTime;

public class LogFile {
	private LogFile(){}
	public static void logInfo(String message) {
		String strDate = CommDateandTime.getDate();
		File file = new File("./log/" + strDate + ".log");
		Writer writer = null;
		try {
			if (file.exists() == false) {
				file.createNewFile();
			}
			writer = new FileWriter(file, true);
			writer.write("[" + CommDateandTime.getDateAndTime() + "]:" + message);
			writer.write("\n");
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}

package server_util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * 日期时间工具包
 * @author lisu
 *
 */
public class CommDateandTime {
	private CommDateandTime(){}
	/**
	 * 获取当前日期"yyyy-MM-dd"
	 * @return strDate 
	 */ 
	public static String getDate() {
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String strDate = dateFormat.format(date);
		return strDate;
	}
	/**
	 * 获取当前时间"yyyy-MM-dd HH:mm:ss"
	 * @return strDate 
	 */ 
	public static String getDateAndTime() {
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strDate = dateFormat.format(date);
		return strDate;
	}
}

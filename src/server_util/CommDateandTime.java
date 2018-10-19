package server_util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * ����ʱ�乤�߰�
 * @author lisu
 *
 */
public class CommDateandTime {
	private CommDateandTime(){}
	/**
	 * ��ȡ��ǰ����"yyyy-MM-dd"
	 * @return strDate 
	 */ 
	public static String getDate() {
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String strDate = dateFormat.format(date);
		return strDate;
	}
	/**
	 * ��ȡ��ǰʱ��"yyyy-MM-dd HH:mm:ss"
	 * @return strDate 
	 */ 
	public static String getDateAndTime() {
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strDate = dateFormat.format(date);
		return strDate;
	}
}

package server_util;

import java.io.File;


/**��ȡ�ļ�������
 * @author Administrator
 *
 */
public class CommFileClass {
	private CommFileClass(){
		
	}
	
	 /**
	 *��ȡ�ļ���  ·��"./Userinfo.txt"
	 */
	public static File file_name = new File("./Userinfo.txt");
	
	 /**
	 *��ȡ�����ļ�  ·��"./file/config.ini"
	 */
	public static File post_file_name=new File("./file/config.ini");
}

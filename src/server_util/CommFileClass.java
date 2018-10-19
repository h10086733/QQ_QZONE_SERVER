package server_util;

import java.io.File;


/**读取文件工具类
 * @author Administrator
 *
 */
public class CommFileClass {
	private CommFileClass(){
		
	}
	
	 /**
	 *读取文件名  路径"./Userinfo.txt"
	 */
	public static File file_name = new File("./Userinfo.txt");
	
	 /**
	 *读取配置文件  路径"./file/config.ini"
	 */
	public static File post_file_name=new File("./file/config.ini");
}

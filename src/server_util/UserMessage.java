package server_util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JTable;

/**
 * 判断用户信息工具类
 * 
 * @author lisu
 * 
 */
public class UserMessage {
	private UserMessage() {

	}
	/**
	 * 判断用户姓名是否是中文或字母
	 * 
	 * @param username
	 *            传人要判断的用户姓名
	 * @return false表示不合法，true表示合法
	 */
	public static boolean userName(String userName) {
		boolean name = userName.matches("^[\u4e00-\u9fa5]+$");
		return name;
	}
	/**
	 * 添加用户自动生成用户ID
	 * 
	 * @return 返回生成的用户ID
	 */
	public static String userId() {
		String userId = null;
		BufferedReader bufferedReader = null;
		int newId = 0;
		try {
			bufferedReader = new BufferedReader(new FileReader(
					CommFileClass.file_name));
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				String[] str_Array = line.split(",");
				String struserId = str_Array[0];
				int usetid = Integer.parseInt(struserId);
				if (newId < usetid) {
					newId = usetid;
				}
			}
			String string = String.valueOf(newId + 1);
			if (newId < 10) {
				userId = "0000" + string;
			}
			if (newId >= 10 && newId < 100) {
				userId = "000" + string;
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return userId;
	}
	/**
	 * 判断用户密码长度是否在3~16 之间且(只允许是数字，字母 , _ )
	 * 
	 * @param userPassword
	 *            输入要判断的密码
	 * @return false表示不合法，true表示合法
	 */
	public static boolean userPassword(String userPassword) {
		boolean password = true;
		if ((userPassword.length() >= 3) && (userPassword.length() <= 16)) {
			for (int i = 0; i < userPassword.length(); i++) {
				char chj = userPassword.charAt(i);
				String strj = String.valueOf(chj);
				// 确定指定字符是否为数字。          //确定指定字符是否为字母。
				if (Character.isDigit(chj) || Character.isLetter(chj)
						|| strj.equals("_")) {
					continue;
				} else {
					password = false;
					break;
				}
			}

		} else {
			password = false;
		}
		return password;
	}

	/**
	 * 判断用户年龄是否是数字，是数字必须在20-150;
	 * 
	 * @param userage
	 *            输入要判断的年龄
	 * @return false表示不合法，true表示合法
	 */
	public static boolean checkUserAge(String userAge) {
		boolean age = true;
		if (userAge.equals("")) {
			age = false;
			return age;
		} else {
			for (int i = 0; i < userAge.length(); i++) {
				char j = userAge.charAt(i);
				if (Character.isDigit(j)) {
					continue;
				} else {
					age = false;
					return age;
				}
			}
			int intUserage = Integer.parseInt(userAge);
			if ((intUserage >= 20) && (intUserage < 150)) {

			} else {
				age = false;
				return age;
			}
		}
		return age;
	}
	/**
	 * 判断用户地址在不为空时，长度不大于 100;
	 * 
	 * @param userAddress
	 *            输入要判断的地址
	 * @return false表示不合法，true表示合法
	 */
	public static boolean checkUserAddress(String userAddress) {
		boolean address = true;
		if (userAddress != null) {
			if (userAddress.length() <= 100) {
			} else {
				address = false;
			}
		}
		return address;
	}
}

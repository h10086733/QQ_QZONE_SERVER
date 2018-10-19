package server_util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JTable;

/**
 * �ж��û���Ϣ������
 * 
 * @author lisu
 * 
 */
public class UserMessage {
	private UserMessage() {

	}
	/**
	 * �ж��û������Ƿ������Ļ���ĸ
	 * 
	 * @param username
	 *            ����Ҫ�жϵ��û�����
	 * @return false��ʾ���Ϸ���true��ʾ�Ϸ�
	 */
	public static boolean userName(String userName) {
		boolean name = userName.matches("^[\u4e00-\u9fa5]+$");
		return name;
	}
	/**
	 * ����û��Զ������û�ID
	 * 
	 * @return �������ɵ��û�ID
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
	 * �ж��û����볤���Ƿ���3~16 ֮����(ֻ���������֣���ĸ , _ )
	 * 
	 * @param userPassword
	 *            ����Ҫ�жϵ�����
	 * @return false��ʾ���Ϸ���true��ʾ�Ϸ�
	 */
	public static boolean userPassword(String userPassword) {
		boolean password = true;
		if ((userPassword.length() >= 3) && (userPassword.length() <= 16)) {
			for (int i = 0; i < userPassword.length(); i++) {
				char chj = userPassword.charAt(i);
				String strj = String.valueOf(chj);
				// ȷ��ָ���ַ��Ƿ�Ϊ���֡�          //ȷ��ָ���ַ��Ƿ�Ϊ��ĸ��
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
	 * �ж��û������Ƿ������֣������ֱ�����20-150;
	 * 
	 * @param userage
	 *            ����Ҫ�жϵ�����
	 * @return false��ʾ���Ϸ���true��ʾ�Ϸ�
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
	 * �ж��û���ַ�ڲ�Ϊ��ʱ�����Ȳ����� 100;
	 * 
	 * @param userAddress
	 *            ����Ҫ�жϵĵ�ַ
	 * @return false��ʾ���Ϸ���true��ʾ�Ϸ�
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

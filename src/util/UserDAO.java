package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import server_util.CommFileClass;


public class UserDAO {
	private UserDAO(){
		
	}
	public static int isUser(String userId, String passWord) {
		int result = 0;
		BufferedReader bufferedReader=null;
		try {
			bufferedReader = new BufferedReader(new FileReader(CommFileClass.file_name));
			String line=null;
			while((line=bufferedReader.readLine())!=null){
				String []strArray=line.split(",");
				String user_Id=strArray[0];
				String pass_Word=strArray[2];
				if(user_Id.equals(userId)){
					if(pass_Word.equals(passWord)){
						 result=1;
					}else  result=0;
					break;
				}else  result=-1;				
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	public static int ispassword(String userId, String oldPassword) {
		int result = 0;
		BufferedReader bufferedReader=null;
		try {
			bufferedReader = new BufferedReader(new FileReader(CommFileClass.file_name));
			String line=null;
			while((line=bufferedReader.readLine())!=null){
				String []strArray=line.split(",");
				String user_Id=strArray[0];
				String pass_Word=strArray[2];
				if(user_Id.equals(userId)){
					if(pass_Word.equals(oldPassword)){
						 result=1;
					}else  result=0;break;
				}			
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
		return result;
	}
	public static UserBean getUserBean(String username) {
		UserBean user = new UserBean();
		File file = new File("./Userinfo.txt");
		BufferedReader bufferedReader = null;
		try {
			bufferedReader = new BufferedReader(new FileReader(file));
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				String[] userArray = line.split(",");
				String db_Id = userArray[0];
				if (db_Id.equals(username)) {
					user.setUserId((userArray[0]));
					user.setUserName(userArray[1]);
					user.setUserPassword(userArray[2]);
					user.setUserSex(userArray[3]);
					user.setUserAge(userArray[4]);
					user.setUserAddress(userArray[5]);
					user.setUserOnlineStatus("ÔÚÏß");
					user.setUserRegTime(userArray[7]);
					
					break;
				}
			}
		} catch (Exception e) {
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
		return user;
	}
}

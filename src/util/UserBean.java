package util;

import java.io.Serializable;

/**
 * �û����û�������Ϣ
 * 
 * @author lisu
 * 
 */
public class UserBean implements Serializable  {

	/** �û�ID(QQ��) */
	private   String userId;
	
	/** �û����� */
	private String userName;
	
	/** �û����� */
	private String userPassword;
	
	/** �û��Ա�
	 * 
	 */
	private String userSex;
	
	/**
	 *  �û������� 
	 */
	private String userAge;
	
	/** 
	 * �û��ĵ�ַ 
	*/
	private String userAddress;
	
	/** �û�����״̬
	 *  
	 */
	private String userOnlineStatus;
	
	/** 
	 * �û�ע������ 
	 */
	private String userRegTime;
	

	public String getUserAddress() {
		return userAddress;
	}

	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}

	public String getUserAge() {
		return userAge;
	}

	public void setUserAge(String userAge) {
		this.userAge = userAge;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserOnlineStatus() {
		return userOnlineStatus;
	}

	public void setUserOnlineStatus(String userOnlineStatus) {
		this.userOnlineStatus = userOnlineStatus;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserRegTime() {
		return userRegTime;
	}

	public void setUserRegTime(String userRegTime) {
		this.userRegTime = userRegTime;
	}

	public String getUserSex() {
		return userSex;
	}

	public void setUserSex(String userSex) {
		this.userSex = userSex;
	}
}

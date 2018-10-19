package util;

import java.io.Serializable;

/**
 * 用户类用户具体信息
 * 
 * @author lisu
 * 
 */
public class UserBean implements Serializable  {

	/** 用户ID(QQ号) */
	private   String userId;
	
	/** 用户姓名 */
	private String userName;
	
	/** 用户密码 */
	private String userPassword;
	
	/** 用户性别
	 * 
	 */
	private String userSex;
	
	/**
	 *  用户的年龄 
	 */
	private String userAge;
	
	/** 
	 * 用户的地址 
	*/
	private String userAddress;
	
	/** 用户在线状态
	 *  
	 */
	private String userOnlineStatus;
	
	/** 
	 * 用户注册日期 
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

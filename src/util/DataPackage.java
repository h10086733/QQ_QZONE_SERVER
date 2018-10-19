package util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class DataPackage implements Serializable {
	/**
	 * 登录包类型
	 */
	public static final int Login_Pack = 1;
	/**
	 * 用户名不存在的包类型
	 */
	public static final int Error_User = 2;
	/**
	 * 密码错误的包类型
	 */
	public static final int Error_Pwd = 3;
	/**
	 * 用户名和密码都正确的包类型
	 */
	public static final int Succ_User = 4;
	/**
	 * 发送在线用户包类型
	 */
	public static final int OnLineUser = 5;
	/**
	 * 用户下线包类型
	 */
	public static final int OffLine_Pack = 6;
	/**
	 * 公告
	 */
	public static final int Bulletin=7;
	
	/** 群聊 */
	public static final int Public_Chat=8;
	
	/** 私聊 */
	public static final int intPrivate_Chat=9;
	
	/** 修改密码 */
	public static final int Alter_Password=10;
	
	/** 修改成功 */
	public static final int intAlter_Password_Success=11;
	
	/** 原始密码错误 */
	public static final int intInit_Password_Error=13;
	
	/** 停止服务 */
	public static final int stopserver=14;
	
	/**  强制下线 */
	public static final int forceOffLine=15;
	
	/**  用户已经在线 */
	public static final int UserOnLine=16;
	
	public static final int OnLineUser1=17;
	
	private int dataType;
	private Map dataMap = new HashMap();
	
	private UserBean userBean;

	public UserBean getUserBean() {
		return userBean;
	}

	public void setUserBean(UserBean userBean) {
		this.userBean = userBean;
	}

	public int getDataType() {
		return dataType;
	}

	public void setDataType(int dataType) {
		this.dataType = dataType;
	}

	public Map getDataMap() {
		return dataMap;
	}

	public void setDataMap(Map dataMap) {
		this.dataMap = dataMap;
	}
}

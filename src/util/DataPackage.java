package util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class DataPackage implements Serializable {
	/**
	 * ��¼������
	 */
	public static final int Login_Pack = 1;
	/**
	 * �û��������ڵİ�����
	 */
	public static final int Error_User = 2;
	/**
	 * �������İ�����
	 */
	public static final int Error_Pwd = 3;
	/**
	 * �û��������붼��ȷ�İ�����
	 */
	public static final int Succ_User = 4;
	/**
	 * ���������û�������
	 */
	public static final int OnLineUser = 5;
	/**
	 * �û����߰�����
	 */
	public static final int OffLine_Pack = 6;
	/**
	 * ����
	 */
	public static final int Bulletin=7;
	
	/** Ⱥ�� */
	public static final int Public_Chat=8;
	
	/** ˽�� */
	public static final int intPrivate_Chat=9;
	
	/** �޸����� */
	public static final int Alter_Password=10;
	
	/** �޸ĳɹ� */
	public static final int intAlter_Password_Success=11;
	
	/** ԭʼ������� */
	public static final int intInit_Password_Error=13;
	
	/** ֹͣ���� */
	public static final int stopserver=14;
	
	/**  ǿ������ */
	public static final int forceOffLine=15;
	
	/**  �û��Ѿ����� */
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

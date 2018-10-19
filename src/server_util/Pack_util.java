package server_util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import serverframe_thread.Server_WaitIo_Thread;
import util.DataPackage;
import util.Global_OnLine;
import util.UserBean;

/**
 * ���Ͱ�������
 * 
 * @author lisu
 * 
 */
public class Pack_util {
	private Pack_util() {
	}

	/**
	 * Ⱥ��������
	 * 
	 * @param dataPackage
	 */
	public static void sendAll_pack(DataPackage dataPackage) {
		for (int i = 0; i < Global_OnLine.onLineUserList.size(); i++) {
			Server_WaitIo_Thread clientThread = Global_OnLine.onLineUserList
					.get(i);
			try {
				clientThread.getObjectOutputStream().writeObject(dataPackage);
				clientThread.getObjectOutputStream().flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 *����������ӵ������ķ���
	 */
	public static void add() {
		DataPackage dataPackage = new DataPackage();
		Map map = new HashMap();
		Vector vector = new Vector();
		vector.add("������");
		for (int i = 0; i < Global_OnLine.onLineUserList.size(); i++) {
			Server_WaitIo_Thread serverWaitIoThread = Global_OnLine.onLineUserList
					.get(i);
			UserBean userBean = serverWaitIoThread.getUserBean();
			vector.add(userBean);
		}
		map.put("onLineUserList", vector);
		dataPackage.setDataType(DataPackage.OnLineUser);
		dataPackage.setDataMap(map);
	}

	/**
	 * ���͵��İ�����
	 * 
	 * @param message
	 *            ���͵���Ϣ
	 * @param tostrID
	 *            ����Ϣ�˵�ID
	 * @param userId
	 *            ����Ϣ�˵�����
	 * @param userBean
	 *            ����Ϣ�˵�����
	 */
	public static void send_One(String message, String tostrID, String userId,
			UserBean userBean) {
		DataPackage dataPackage = new DataPackage();
		dataPackage.setDataType(DataPackage.Public_Chat);
		Map dataMap = new HashMap();
		dataPackage.setUserBean(userBean);
		dataMap.put("message", message);
		dataPackage.setDataMap(dataMap);
		for (int i = 0; i < Global_OnLine.onLineUserList.size(); i++) {
			Server_WaitIo_Thread clientThread = Global_OnLine.onLineUserList
					.get(i);
			if (clientThread.getUserBean().getUserId().equals(tostrID)
			// || clientThread.userBean.getUserId().equals(userId)
			) {
				try {
					clientThread.getObjectOutputStream().writeObject(
							dataPackage);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				try {
					clientThread.getObjectOutputStream().flush();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	/**
	 * ����Ⱥ�İ��ķ���
	 * 
	 * @param message
	 *            Ⱥ�ĵ���Ϣ
	 * @param userBean
	 *            �����˵�����
	 */
	public static void send_All(String message, UserBean userBean) {
		DataPackage dataPackage = new DataPackage();
		dataPackage.setDataType(DataPackage.Public_Chat);
		Map dataMap = new HashMap();
		dataPackage.setUserBean(userBean);
		dataMap.put("message", message);
		dataPackage.setDataMap(dataMap);
		for (int i = 0; i < Global_OnLine.onLineUserList.size(); i++) {
			Server_WaitIo_Thread clientThread = Global_OnLine.onLineUserList
					.get(i);
			try {
				clientThread.getObjectOutputStream().writeObject(dataPackage);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			try {
				clientThread.getObjectOutputStream().flush();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
}

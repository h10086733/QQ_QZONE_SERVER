package serverframe_thread;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Reader;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import server_util.CommDateandTime;
import server_util.CommFileClass;
import server_util.Pack_util;
import server_util.Table_util;
import serverframe_servermanage.ServerManagePanel;
import util.DataPackage;
import util.Global_OnLine;
import util.LogFile;
import util.UserBean;
import util.UserDAO;

/**���������˽��յ��ͻ�����Ϣ���߳�
 * @author lisu
 *
 */
public class Server_WaitIo_Thread extends Thread {
	/**
	 * ����������
	 */
	private ObjectInputStream objectInputStream = null;

	/**
	 * ���������
	 */
	private ObjectOutputStream objectOutputStream = null;

	/**
	 * 
	 * Ҫ���ӵ��׽���
	 *
	 */
	private Socket socket;

	/**
	 *  �����������������˽��յ��ͻ�����Ϣ���̵߳ı���
	 */
	private boolean isStop = false;

	/**
	 * �û���Ϣ
	 */
	private UserBean userBean = null;

	/**
	 * ѡ�����_����������
	 */
	private ServerManagePanel serverManagePanel;
	/**
	 * ���ݰ�Э��
	 */
	private DataPackage dataPackage;

	public Server_WaitIo_Thread(Socket socket,
			ServerManagePanel serverManagePanel) {
		this.socket = socket;
		this.serverManagePanel = serverManagePanel;
		try {
			objectInputStream = new ObjectInputStream(socket.getInputStream());
			objectOutputStream = new ObjectOutputStream(socket
					.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void run() {
		while (isStop == false) {
			try {
				dataPackage = (DataPackage) this.objectInputStream
						.readObject();
				int dataType = dataPackage.getDataType();
				// ��¼��
				if (dataType == DataPackage.Login_Pack) {
					Map dataMap = dataPackage.getDataMap();
					String username = String.valueOf(dataMap.get("username"));
					String password = String.valueOf(dataMap.get("password"));
					// ��ʼ�����û����ݣ���������Ϣ��������
					int result = UserDAO.isUser(username, password);
					dataPackage = new DataPackage();
					for (int i = 0; i < Global_OnLine.onLineUserList.size(); i++) {
						Server_WaitIo_Thread serverWaitIoThread = Global_OnLine.onLineUserList
								.get(i);
						UserBean userBean = serverWaitIoThread.userBean;
						if (userBean.getUserId().equals(username)) {
							result = 2;
						}
					}
					if (result == -1) {
						dataPackage.setDataType(DataPackage.Error_User);
					} else if (result == 0) {
						dataPackage.setDataType(DataPackage.Error_Pwd);
						isStop=true;
					} else if (result == 1) {
						dataPackage.setDataType(DataPackage.Succ_User);
					} else if (result == 2) {
						dataPackage.setDataType(DataPackage.UserOnLine);
					}
					userBean = UserDAO.getUserBean(username);
					Map dataMap_1 = dataPackage.getDataMap();
					dataMap_1.put("UserBean", userBean);
					this.objectOutputStream.writeObject(dataPackage);
					this.objectOutputStream.flush();
					// �����û�
					if (result == 1) {
						Global_OnLine.onLineUserList.add(this);
						// Global_OnLine.hashMap.put(this.getId(), this);
						userBean = UserDAO.getUserBean(username);
						serverManagePanel.getTaPromptInfo().append(
								CommDateandTime.getDateAndTime() + ": " + "�û�"
										+ userBean.getUserName() + "("
										+ userBean.getUserId() + ")" + "������"
										+ "\n"); // ͨѶ��Ϣ��ʾ
						LogFile.logInfo("�û�" + userBean.getUserName() + "("
								+ userBean.getUserId() + ")" + "������" + "\n");
						// ���û����ݷ��͵��ͻ���
						DataPackage dataPackage = new DataPackage();
						Map map = new HashMap();
						Vector vector = new Vector();
						vector.add("������");
						for (int i = 0; i < Global_OnLine.onLineUserList.size(); i++) {
							Server_WaitIo_Thread serverWaitIoThread = Global_OnLine.onLineUserList
									.get(i);
							UserBean userBean = serverWaitIoThread.userBean;
							vector.add(userBean.getUserName()+"_"+userBean.getUserId());
						}
						map.put("onLineUserList",vector );
						dataPackage.setDataType(DataPackage.OnLineUser);
						dataPackage.setDataMap(map);
						// ѭ���������е�Socket(�û�)
						Pack_util.sendAll_pack(dataPackage);
						// ���·���˵������û��б�ı��
						Table_util.renew_OnlineTable(serverManagePanel);

					}
				} else if (dataType == DataPackage.OffLine_Pack) {
					// 1��
					Global_OnLine.onLineUserList.remove(this);
					serverManagePanel.getTaPromptInfo()
							.append(
									CommDateandTime.getDateAndTime() + ": "
											+ "�û�" + userBean.getUserName()
											+ "(" + userBean.getUserId() + ")"
											+ "������" + "\n"); // ͨѶ��Ϣ��ʾ
					LogFile.logInfo("�û�" + userBean.getUserName() + "("
							+ userBean.getUserId() + ")" + "������" + "\n");
					//System.out.println(Global_OnLine.onLineUserList.size());
					// ���û����ݷ��͵��ͻ���
					dataPackage = new DataPackage();
					Map map = new HashMap();
					Vector vector = new Vector();
					vector.add("������");
					for (int i = 0; i < Global_OnLine.onLineUserList.size(); i++) {
						Server_WaitIo_Thread serverWaitIoThread = Global_OnLine.onLineUserList
								.get(i);
						UserBean userBean = serverWaitIoThread.userBean;
						vector.add(userBean.getUserName()+"_"+userBean.getUserId());
					}
					map.put("onLineUserList", vector);
					dataPackage.setDataType(DataPackage.OnLineUser);
					dataPackage.setDataMap(map);
					// ѭ���������е�Socket
					Pack_util.sendAll_pack(dataPackage);
					// ���·���˵������û��б�ı��
					Table_util.renew_OnlineTable(serverManagePanel);
					if (objectOutputStream != null) {
						objectOutputStream.close();
					}
					if (objectInputStream != null) {
						objectInputStream.close();
					}
					if (socket != null) {
						socket.close();
					}
					// ���߳�
					this.isStop = true;
					// Ⱥ�İ�
				} else if (dataType == DataPackage.Public_Chat) {
					Map dataMap = dataPackage.getDataMap();
					String message = String.valueOf(dataMap.get("message"));
					String thisusername = String.valueOf(dataMap
							.get("thisusername"));
					// String message_1=dataPackage.getUserBean().getUserId();
					Pack_util.send_All(CommDateandTime.getDateAndTime() + "\n"
							+ thisusername + "��������˵:  " + message,userBean);
					// ˽�İ�
				} else if (dataType == DataPackage.intPrivate_Chat) {
					Map dataMap = dataPackage.getDataMap();
					String message = String.valueOf(dataMap.get("message"));
					String message_1 = dataPackage.getUserBean().getUserId();
					String tostrID = String.valueOf(dataMap.get("tostrID"));
					String userId = String.valueOf(dataMap.get("userId"));
					String tousername = String.valueOf(dataMap
							.get("tousername"));
					String thisusername = String.valueOf(dataMap
							.get("thisusername"));
					Pack_util.send_One(CommDateandTime.getDateAndTime() + "\n"
							+ thisusername + "����˵:  "
							+ message, tostrID, userId,userBean);
					// �޸������
				} else if (dataType == DataPackage.Alter_Password) {
					UserBean userBean = dataPackage.getUserBean();
					Map dataMap = dataPackage.getDataMap();
					String userId = userBean.getUserId();
					String oldPassword = String.valueOf(dataMap
							.get("oldPassword"));
					String newPassword = String.valueOf(dataMap
							.get("newPassword"));
					String affirmPassword = String.valueOf(dataMap
							.get("affirmPassword"));
					int result = UserDAO.ispassword(userId, oldPassword);
					if (result == 0) {
						dataPackage
								.setDataType(DataPackage.intInit_Password_Error);
					} else if (result == 1) {
						dataPackage
								.setDataType(DataPackage.intAlter_Password_Success);
					}
					this.objectOutputStream.writeObject(dataPackage);
					this.objectOutputStream.flush();
					if (result == 1) {
						// ���·����������Table��ԭ��������ĳ��µ�
						Table_util.renew_OnlineTable_newpassword(
								serverManagePanel, userId, newPassword);
						// ���ļ����޸����룬����Ҫ�޸��ϵ�ID����������
						Table_util.modifypassword(newPassword, userId);
					}
					// �����û������Table
					Table_util
							.renew_Table(
									serverManagePanel.getServerFrame().getUserManagePanel().getTable(),
									serverManagePanel.getServerFrame().getUserManagePanel().getColumnVector());
				}
			} catch (SocketException e) {
				Global_OnLine.onLineUserList.remove(this);
				serverManagePanel.getTaPromptInfo().append(
						CommDateandTime.getDateAndTime() + ": " + "�û�"
								+ userBean.getUserName() + "("
								+ userBean.getUserId() + ")" + "������" + "\n"); // ͨѶ��Ϣ��ʾ
				//System.out.println(Global_OnLine.onLineUserList.size());
				// ���û����ݷ��͵��ͻ���
				DataPackage dataPackage_1 = new DataPackage();
				Map map = new HashMap();
				Vector vector = new Vector();
				vector.add("������");
				for (int i = 0; i < Global_OnLine.onLineUserList.size(); i++) {
					Server_WaitIo_Thread serverWaitIoThread = Global_OnLine.onLineUserList
							.get(i);
					UserBean userBean = serverWaitIoThread.userBean;
					vector.add(userBean.getUserName()+"_"+userBean.getUserId());
				}
				map.put("onLineUserList", vector);
				dataPackage_1.setDataType(DataPackage.OnLineUser);
				dataPackage_1.setDataMap(map);
				// ѭ���������е�Socket
				Pack_util.sendAll_pack(dataPackage_1);
				// ���·���˵������û��б�ı��
				Table_util.renew_OnlineTable(serverManagePanel);
				try {
					if (objectOutputStream != null)
						objectOutputStream.close();
					if (objectInputStream != null)
						objectInputStream.close();
					if (socket != null)
						socket.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				// ���߳�
				this.isStop = true;
			} catch (IOException e1) {
				e1.printStackTrace();
			}catch (Exception e) {
				//e.printStackTrace();
			}
		}
	}
	public ObjectInputStream getObjectInputStream() {
		return objectInputStream;
	}

	public void setObjectInputStream(ObjectInputStream objectInputStream) {
		this.objectInputStream = objectInputStream;
	}

	public ObjectOutputStream getObjectOutputStream() {
		return objectOutputStream;
	}

	public void setObjectOutputStream(ObjectOutputStream objectOutputStream) {
		this.objectOutputStream = objectOutputStream;
	}
	public UserBean getUserBean() {
		return userBean;
	}

	public void setUserBean(UserBean userBean) {
		this.userBean = userBean;
	}
	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public boolean isStop() {
		return isStop;
	}

	public void setStop(boolean isStop) {
		this.isStop = isStop;
	}

	public ServerManagePanel getServerManagePanel() {
		return serverManagePanel;
	}

	public void setServerManagePanel(ServerManagePanel serverManagePanel) {
		this.serverManagePanel = serverManagePanel;
	}

	public DataPackage getDataPackage() {
		return dataPackage;
	}

	public void setDataPackage(DataPackage dataPackage) {
		this.dataPackage = dataPackage;
	}
}

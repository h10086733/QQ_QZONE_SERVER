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

/**服务器不端接收到客户端信息的线程
 * @author lisu
 *
 */
public class Server_WaitIo_Thread extends Thread {
	/**
	 * 对象输入流
	 */
	private ObjectInputStream objectInputStream = null;

	/**
	 * 对象输出流
	 */
	private ObjectOutputStream objectOutputStream = null;

	/**
	 * 
	 * 要连接的套接字
	 *
	 */
	private Socket socket;

	/**
	 *  用来开启服务器不端接收到客户端信息的线程的变量
	 */
	private boolean isStop = false;

	/**
	 * 用户信息
	 */
	private UserBean userBean = null;

	/**
	 * 选项卡属性_服务管理面板
	 */
	private ServerManagePanel serverManagePanel;
	/**
	 * 数据包协议
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
				// 登录包
				if (dataType == DataPackage.Login_Pack) {
					Map dataMap = dataPackage.getDataMap();
					String username = String.valueOf(dataMap.get("username"));
					String password = String.valueOf(dataMap.get("password"));
					// 开始解析用户数据，并发送消息反馈包。
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
					// 在线用户
					if (result == 1) {
						Global_OnLine.onLineUserList.add(this);
						// Global_OnLine.hashMap.put(this.getId(), this);
						userBean = UserDAO.getUserBean(username);
						serverManagePanel.getTaPromptInfo().append(
								CommDateandTime.getDateAndTime() + ": " + "用户"
										+ userBean.getUserName() + "("
										+ userBean.getUserId() + ")" + "上线了"
										+ "\n"); // 通讯信息提示
						LogFile.logInfo("用户" + userBean.getUserName() + "("
								+ userBean.getUserId() + ")" + "上线了" + "\n");
						// 将用户数据发送到客户端
						DataPackage dataPackage = new DataPackage();
						Map map = new HashMap();
						Vector vector = new Vector();
						vector.add("所有人");
						for (int i = 0; i < Global_OnLine.onLineUserList.size(); i++) {
							Server_WaitIo_Thread serverWaitIoThread = Global_OnLine.onLineUserList
									.get(i);
							UserBean userBean = serverWaitIoThread.userBean;
							vector.add(userBean.getUserName()+"_"+userBean.getUserId());
						}
						map.put("onLineUserList",vector );
						dataPackage.setDataType(DataPackage.OnLineUser);
						dataPackage.setDataMap(map);
						// 循环发给所有的Socket(用户)
						Pack_util.sendAll_pack(dataPackage);
						// 更新服务端的在线用户列表的表格
						Table_util.renew_OnlineTable(serverManagePanel);

					}
				} else if (dataType == DataPackage.OffLine_Pack) {
					// 1：
					Global_OnLine.onLineUserList.remove(this);
					serverManagePanel.getTaPromptInfo()
							.append(
									CommDateandTime.getDateAndTime() + ": "
											+ "用户" + userBean.getUserName()
											+ "(" + userBean.getUserId() + ")"
											+ "下线了" + "\n"); // 通讯信息提示
					LogFile.logInfo("用户" + userBean.getUserName() + "("
							+ userBean.getUserId() + ")" + "下线了" + "\n");
					//System.out.println(Global_OnLine.onLineUserList.size());
					// 将用户数据发送到客户端
					dataPackage = new DataPackage();
					Map map = new HashMap();
					Vector vector = new Vector();
					vector.add("所有人");
					for (int i = 0; i < Global_OnLine.onLineUserList.size(); i++) {
						Server_WaitIo_Thread serverWaitIoThread = Global_OnLine.onLineUserList
								.get(i);
						UserBean userBean = serverWaitIoThread.userBean;
						vector.add(userBean.getUserName()+"_"+userBean.getUserId());
					}
					map.put("onLineUserList", vector);
					dataPackage.setDataType(DataPackage.OnLineUser);
					dataPackage.setDataMap(map);
					// 循环发给所有的Socket
					Pack_util.sendAll_pack(dataPackage);
					// 更新服务端的在线用户列表的表格
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
					// 关线程
					this.isStop = true;
					// 群聊包
				} else if (dataType == DataPackage.Public_Chat) {
					Map dataMap = dataPackage.getDataMap();
					String message = String.valueOf(dataMap.get("message"));
					String thisusername = String.valueOf(dataMap
							.get("thisusername"));
					// String message_1=dataPackage.getUserBean().getUserId();
					Pack_util.send_All(CommDateandTime.getDateAndTime() + "\n"
							+ thisusername + "对所有人说:  " + message,userBean);
					// 私聊包
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
							+ thisusername + "对你说:  "
							+ message, tostrID, userId,userBean);
					// 修改密码包
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
						// 更新服务器管理的Table把原来的密码改成新的
						Table_util.renew_OnlineTable_newpassword(
								serverManagePanel, userId, newPassword);
						// 在文件里修改密码，传入要修改认的ID，和新密码
						Table_util.modifypassword(newPassword, userId);
					}
					// 更新用户管理的Table
					Table_util
							.renew_Table(
									serverManagePanel.getServerFrame().getUserManagePanel().getTable(),
									serverManagePanel.getServerFrame().getUserManagePanel().getColumnVector());
				}
			} catch (SocketException e) {
				Global_OnLine.onLineUserList.remove(this);
				serverManagePanel.getTaPromptInfo().append(
						CommDateandTime.getDateAndTime() + ": " + "用户"
								+ userBean.getUserName() + "("
								+ userBean.getUserId() + ")" + "下线了" + "\n"); // 通讯信息提示
				//System.out.println(Global_OnLine.onLineUserList.size());
				// 将用户数据发送到客户端
				DataPackage dataPackage_1 = new DataPackage();
				Map map = new HashMap();
				Vector vector = new Vector();
				vector.add("所有人");
				for (int i = 0; i < Global_OnLine.onLineUserList.size(); i++) {
					Server_WaitIo_Thread serverWaitIoThread = Global_OnLine.onLineUserList
							.get(i);
					UserBean userBean = serverWaitIoThread.userBean;
					vector.add(userBean.getUserName()+"_"+userBean.getUserId());
				}
				map.put("onLineUserList", vector);
				dataPackage_1.setDataType(DataPackage.OnLineUser);
				dataPackage_1.setDataMap(map);
				// 循环发给所有的Socket
				Pack_util.sendAll_pack(dataPackage_1);
				// 更新服务端的在线用户列表的表格
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
				// 关线程
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

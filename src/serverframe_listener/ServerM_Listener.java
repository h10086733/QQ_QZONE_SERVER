package serverframe_listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

import server_util.CommDateandTime;
import server_util.CommFileClass;
import serverframe_servermanage.ServerManagePanel;
import serverframe_thread.Server_WaitClient_Thread;
import serverframe_thread.Server_WaitIo_Thread;
import serverframe_usermanage.User_ModifyUserDialog;
import util.DataPackage;
import util.LogFile;
import util.Global_OnLine;

/**
 * 服务器面板监听
 * 
 * @author lisu
 * 
 */
public class ServerM_Listener implements ActionListener {

	/**
	 * 服务器面板
	 */
	private ServerManagePanel serverManagePanel;
	/**
	 * 服务器套接字
	 */
	private ServerSocket serverSocket = null;
	/**
	 * 服务器等待客户端线程
	 */
	private Server_WaitClient_Thread serverWaitClientThread;
	/**
	 * 
	 * 开关服务器等待客户端线程的变量 false表示关 true表示开
	 */
	private boolean isStart = false;

	public ServerM_Listener(ServerManagePanel serverManagePanel) {
		this.serverManagePanel = serverManagePanel;
	}

	public void actionPerformed(ActionEvent e) {
		// 开启服务
		if (e.getSource().equals(serverManagePanel.getBtnStarServer())) {
			starServer();
		}
		// 停止服务
		else if (e.getSource().equals(serverManagePanel.getBtnStopServer())) {
			try {
				String str = ("服务器已经关闭");
				LogFile.logInfo(str + "\n");
				// 把信息发送到日志文件里
				serverManagePanel.getTaPromptInfo().append(
						CommDateandTime.getDateAndTime() + ": " + str + "\n"); // 通讯信息提示
				serverManagePanel.getLabImg().setIcon((new ImageIcon("./images/serverstop.gif")));
				serverManagePanel.getBtnStarServer().setEnabled(true);
				serverManagePanel.getBtnStopServer().setEnabled(false);
				serverManagePanel.getBtnOffLine().setEnabled(false);
				serverManagePanel.getBtnSend().setEnabled(false);
				serverManagePanel.getTaSendBulletin().setEditable(false);
				DataPackage dataPackage = new DataPackage();
				dataPackage.setDataType(DataPackage.stopserver);
				for (int i = 0; i < Global_OnLine.onLineUserList.size(); i++) {
					Server_WaitIo_Thread clientThread = Global_OnLine.onLineUserList
							.get(i);
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
				/*
				 * serverWaitClientThread.serverWaitIoThread.objectInputStream.close
				 * ();
				 * serverWaitClientThread.serverWaitIoThread.objectOutputStream
				 * .close();
				 */
				// serverWaitClientThread.getServerWaitIoThread().setStop(true);
				serverWaitClientThread.setStatwait(false);
				serverSocket.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			// 公告包
		} else if (e.getSource().equals(serverManagePanel.getBtnSend())) {
			String Bulletinmessage = CommDateandTime.getDateAndTime() + "\n"
					+ (serverManagePanel.getTaSendBulletin().getText() + "\n");
			if (serverManagePanel.getTaSendBulletin().getText().length() >= 200) {
				JOptionPane.showMessageDialog(null, "公告信息一次最多只能发送200个字符 ");

			} else {
				sendbulletinmessage(Bulletinmessage);
				JOptionPane.showMessageDialog(null, "发送成功");
				serverManagePanel.getTaSendBulletin().setText("");
			}

			// 强制下线包
		} else if (e.getSource().equals(serverManagePanel.getBtnOffLine())) {
			int index = this.serverManagePanel.getTabOnlineUser()
					.getSelectedRow();
			if (index < 0) {
				JOptionPane.showMessageDialog(null, "请选择一行后再做强制下线 ");
				return;
			} else {
				DefaultTableModel tableModel = (DefaultTableModel) serverManagePanel
						.getTabOnlineUser().getModel();
				String userId = "";
				userId = String.valueOf(tableModel.getValueAt(index, 0));
				System.out.println(userId);
				DataPackage dataPackage = new DataPackage();
				dataPackage.setDataType(DataPackage.forceOffLine);
				for (int i = 0; i < Global_OnLine.onLineUserList.size(); i++) {
					Server_WaitIo_Thread clientThread = Global_OnLine.onLineUserList
							.get(i);
					try {
						if (clientThread.getUserBean().getUserId().equals(
								userId)) {
							clientThread.getObjectOutputStream().writeObject(
									dataPackage);
						}

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
	}

	/**
	 * 服务器发送公告
	 * 
	 * @param bulletinmessage
	 *            公告信息
	 */
	public void sendbulletinmessage(String bulletinmessage) {
		DataPackage dataPackage = new DataPackage();
		dataPackage.setDataType(DataPackage.Bulletin);
		Map dataMap = new HashMap();
		dataMap.put("bulletinmessage", bulletinmessage);
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

	/**
	 *开启服务方法
	 */
	public void starServer() {
		String value = null;
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(CommFileClass.post_file_name);
			Properties properties = new Properties();
			properties.load(inputStream);
			value = properties.getProperty("port");
			int value_1 = Integer.parseInt(value);
			serverSocket = new ServerSocket(value_1);
			// 把信息发送到日志文件里
			String str = ("服务器已经启动服务器的端口:" + value_1 + "\n");
			LogFile.logInfo(str);
			isStart = true;
			serverWaitClientThread = new Server_WaitClient_Thread(serverSocket,
					this.serverManagePanel);
			Thread waitClientThread = new Thread(serverWaitClientThread);
			waitClientThread.start();
			serverManagePanel.getLabImg().setIcon((new ImageIcon("./images/serverstart.gif")));
			serverManagePanel.getBtnStarServer().setEnabled(false);
			serverManagePanel.getBtnStopServer().setEnabled(true);
			serverManagePanel.getBtnOffLine().setEnabled(true);
			serverManagePanel.getBtnSend().setEnabled(true);
			serverManagePanel.getTaSendBulletin().setEditable(true);
			JTextArea textArea = this.serverManagePanel.getTaPromptInfo();
			textArea.append(CommDateandTime.getDateAndTime() + ":服务端启动" + "\n");
			// 光标定位在最后
			textArea.setCaretPosition(textArea.getDocument().getLength());
		} catch (IOException e1) {
			System.out.println("服务启动失败");
			isStart = false;
			e1.printStackTrace();
		}
	}

}

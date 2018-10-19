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
 * ������������
 * 
 * @author lisu
 * 
 */
public class ServerM_Listener implements ActionListener {

	/**
	 * ���������
	 */
	private ServerManagePanel serverManagePanel;
	/**
	 * �������׽���
	 */
	private ServerSocket serverSocket = null;
	/**
	 * �������ȴ��ͻ����߳�
	 */
	private Server_WaitClient_Thread serverWaitClientThread;
	/**
	 * 
	 * ���ط������ȴ��ͻ����̵߳ı��� false��ʾ�� true��ʾ��
	 */
	private boolean isStart = false;

	public ServerM_Listener(ServerManagePanel serverManagePanel) {
		this.serverManagePanel = serverManagePanel;
	}

	public void actionPerformed(ActionEvent e) {
		// ��������
		if (e.getSource().equals(serverManagePanel.getBtnStarServer())) {
			starServer();
		}
		// ֹͣ����
		else if (e.getSource().equals(serverManagePanel.getBtnStopServer())) {
			try {
				String str = ("�������Ѿ��ر�");
				LogFile.logInfo(str + "\n");
				// ����Ϣ���͵���־�ļ���
				serverManagePanel.getTaPromptInfo().append(
						CommDateandTime.getDateAndTime() + ": " + str + "\n"); // ͨѶ��Ϣ��ʾ
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
			// �����
		} else if (e.getSource().equals(serverManagePanel.getBtnSend())) {
			String Bulletinmessage = CommDateandTime.getDateAndTime() + "\n"
					+ (serverManagePanel.getTaSendBulletin().getText() + "\n");
			if (serverManagePanel.getTaSendBulletin().getText().length() >= 200) {
				JOptionPane.showMessageDialog(null, "������Ϣһ�����ֻ�ܷ���200���ַ� ");

			} else {
				sendbulletinmessage(Bulletinmessage);
				JOptionPane.showMessageDialog(null, "���ͳɹ�");
				serverManagePanel.getTaSendBulletin().setText("");
			}

			// ǿ�����߰�
		} else if (e.getSource().equals(serverManagePanel.getBtnOffLine())) {
			int index = this.serverManagePanel.getTabOnlineUser()
					.getSelectedRow();
			if (index < 0) {
				JOptionPane.showMessageDialog(null, "��ѡ��һ�к�����ǿ������ ");
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
	 * ���������͹���
	 * 
	 * @param bulletinmessage
	 *            ������Ϣ
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
	 *�������񷽷�
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
			// ����Ϣ���͵���־�ļ���
			String str = ("�������Ѿ������������Ķ˿�:" + value_1 + "\n");
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
			textArea.append(CommDateandTime.getDateAndTime() + ":���������" + "\n");
			// ��궨λ�����
			textArea.setCaretPosition(textArea.getDocument().getLength());
		} catch (IOException e1) {
			System.out.println("��������ʧ��");
			isStart = false;
			e1.printStackTrace();
		}
	}

}

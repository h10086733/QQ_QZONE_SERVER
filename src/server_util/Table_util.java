package server_util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import serverframe_servermanage.ServerManagePanel;
import serverframe_thread.Server_WaitIo_Thread;
import serverframe_usermanage.UserManagePanel;
import serverframe_usermanage.User_AddUserDialog;
import serverframe_usermanage.User_ModifyUserDialog;
import util.Global_OnLine;
import util.UserBean;

/**
 * �����ɾ�Ĳ飬ˢ�� ������
 * 
 * @author lisu
 * 
 */
public class Table_util {
	public static boolean flag=false;
	private Table_util() {

	}

	/**
	 * �û������� ɾ������
	 * 
	 * @param userid
	 *            Ҫɾ���û���I��
	 */
	public static void delete(String userid) {
		BufferedReader bufferedReader = null;
		BufferedWriter bufferedWriter = null;
		List<String> list = new ArrayList<String>();

		try {
			bufferedReader = new BufferedReader(new FileReader(
					".\\Userinfo.txt"));
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				String[] userArray = line.split(",");
				String id = userArray[0];
				if (id.equals(userid)) {
					// �ҵ������¼��������һ�ε�ѭ��
					continue;
				} else {
					list.add(line);
				}
			}
			// ����Ҫ��true,��Ϊ�Ǹ���
			bufferedWriter = new BufferedWriter(new FileWriter(
					".\\Userinfo.txt"));
			for (int i = 0; i < list.size(); i++) {
				bufferedWriter.write(list.get(i));
				bufferedWriter.write("\n");
				bufferedWriter.flush();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e2) {
					e2.printStackTrace();
				}
			}
			if (bufferedWriter != null) {
				try {
					bufferedWriter.close();
				} catch (IOException e3) {
					e3.printStackTrace();
				}
			}
		}
	}

	/**�û��޸ķ���
	 * @param user_Modify
	 * @param userid
	 * @param bufferedReader
	 */
	public static void modify(User_ModifyUserDialog user_Modify, String userid,
			BufferedReader bufferedReader) {
		BufferedWriter bufferedWriter = null;
		List<String> list = new ArrayList<String>();
		try {

			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				// System.out.println("aaaaaaa" + line);
				String[] userArray = line.split(",");
				String id = userArray[0];
				String date = userArray[7];
				if (id.equals(userid)) {
					bufferedWriter = new BufferedWriter(new FileWriter(
							".\\Userinfo.txt"));
					// �ҵ������¼��������һ�ε�ѭ��
					// System.out.println(id);
					
						flag=true;
						line = id
								+ ","
								+ user_Modify.getTfName().getText()
								+ ","
								+ String.valueOf(user_Modify.getTfPassword().getPassword())
								+ ","
								+ (user_Modify.getComSex().getSelectedIndex() == 0 ? "��"
										: "Ů") + ","
								+ user_Modify.getTfAge().getText() + ","
								+ user_Modify.getTfAddress().getText() + ","
								+ "������" + "," + date;
						list.add(line);
						user_Modify.setVisible(false);
						user_Modify.dispose();
					

				} else {
					list.add(line);
				}
			}
			// ����Ҫ��true,��Ϊ�Ǹ���
			System.out.println(list.size());
			for (int i = 0; i < list.size(); i++) {
				bufferedWriter.write(list.get(i));
				bufferedWriter.write("\n");
				bufferedWriter.flush();
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		} 
	}

	/**�û��޸����뷽��
	 * @param password
	 * @param userid
	 */
	public static void modifypassword(String password, String userid) {
		BufferedReader bufferedReader = null;
		BufferedWriter bufferedWriter = null;
		List<String> list = new ArrayList<String>();
		try {
			bufferedReader = new BufferedReader(new FileReader(
					".\\Userinfo.txt"));
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {

				String[] userArray = line.split(",");
				String id = userArray[0];
				String username = userArray[1];
				String sex = userArray[3];
				String age = userArray[4];
				String address = userArray[5];
				String isno = userArray[6];
				String date = userArray[7];
				if (id.equals(userid)) {
					String line_1 = id + "," + username + "," + password + ","
							+ sex + "," + age + "," + address + "," + isno
							+ "," + date;
					list.add(line_1);
				} else {
					list.add(line);
				}
			}
			// ����Ҫ��true,��Ϊ�Ǹ���
			bufferedWriter = new BufferedWriter(new FileWriter(
					".\\Userinfo.txt"));
			for (int i = 0; i < list.size(); i++) {
				bufferedWriter.write(list.get(i));
				bufferedWriter.write("\n");
				bufferedWriter.flush();
			}
			list.clear();
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e2) {
					e2.printStackTrace();
				}
			}
			if (bufferedWriter != null) {
				try {
					bufferedWriter.close();
				} catch (IOException e3) {
					e3.printStackTrace();
				}
			}
		}
	}

	/**�û���ӷ���
	 * @param user_Add
	 * @param str
	 */
	public static void add(User_AddUserDialog user_Add, String str) {
		Writer writer = null;
		BufferedWriter bufferedWriter = null;
		try {
			writer = new FileWriter(CommFileClass.file_name, true);
			bufferedWriter = new BufferedWriter(writer);
			String userinfo = user_Add.getTfUserID().getText()
					+ ","
					+ user_Add.getTfName().getText()
					+ ","
					+ String.valueOf(user_Add.getTfPassword().getPassword())
					+ ","
					+ (user_Add.getComSex().getSelectedIndex() == 0 ? "��" : "Ů")
					+ "," + user_Add.getTfAge().getText() + ","
					+ user_Add.getTfAddress().getText() + "," + "������" + ","
					+ str;
			if (UserMessage.userName(user_Add.getTfName().getText()) == false) {
				JOptionPane.showMessageDialog(null, "��ʵ������������ 2~10 ֮�� (����������)");
			} else if (UserMessage.userPassword(String.valueOf(user_Add.getTfPassword()
					.getPassword())) == false) {
				JOptionPane.showMessageDialog(null,
						"���� ������ 3~16 ֮�� (ֻ���������֣���ĸ , _ )");
			} else if (UserMessage.checkUserAge(user_Add.getTfAge().getText()) == false) {
				JOptionPane.showMessageDialog(null, "���� ������ 20~150֮��");
			} else if (UserMessage.checkUserAddress(user_Add.getTfAddress()
					.getText()) == false) {
				JOptionPane
						.showMessageDialog(null, "��ַ ����Ϊ�գ����粻Ϊ��ʱ�����Ȳ����� 100;");
			} else {
				bufferedWriter.write(userinfo + "\n");
				bufferedWriter.flush();

				user_Add.dispose();
			}
		} catch (Exception e2) {
			e2.printStackTrace();
		} finally {
			// �ر���Դ
			try {
				if (bufferedWriter != null)
					bufferedWriter.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	/**���±��
	 * @param table
	 * @param columnVector
	 */
	public static void renew_Table(JTable table, Vector columnVector) {
		columnVector = new Vector();
		columnVector.add("�˺�");
		columnVector.add("�û�����");
		columnVector.add("�û�����");
		columnVector.add("�Ա�");
		columnVector.add("����");
		columnVector.add("��ַ");
		columnVector.add("�Ƿ�����");
		columnVector.add("ע��ʱ��");

		Vector rowVector = new Vector();
		Reader reader;
		try {
			reader = new FileReader(".\\Userinfo.txt");
			BufferedReader bufferedReader = new BufferedReader(reader);
			String str = null;
			try {
				while ((str = bufferedReader.readLine()) != null) {
					Vector tempVector_2 = new Vector();
					String[] string = str.split(",");
					tempVector_2.add(string[0]);
					tempVector_2.add(string[1]);
					tempVector_2.add(string[2]);
					tempVector_2.add(string[3]);
					tempVector_2.add(string[4]);
					tempVector_2.add(string[5]);
					tempVector_2.add(string[6]);
					tempVector_2.add(string[7]);
					rowVector.add(tempVector_2);
				}

			} catch (IOException e) {
				e.printStackTrace();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		TableModel dataModel = new DefaultTableModel(rowVector, columnVector);
		table.setModel(dataModel);
	}

	public static void renew_OnlineTable(ServerManagePanel serverManagePanel) {
		JTable table = serverManagePanel.getTabOnlineUser();
		DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
		Vector columnVector = serverManagePanel.getColumnVector();
		Vector rowVector = new Vector();
		for (int i = 0; i < Global_OnLine.onLineUserList.size(); i++) {
			Server_WaitIo_Thread clientThread = Global_OnLine.onLineUserList
					.get(i);
			UserBean userBean = clientThread.getUserBean();
			Vector tempVector = new Vector();
			tempVector.add(userBean.getUserId());
			tempVector.add(userBean.getUserName());
			tempVector.add(userBean.getUserPassword());
			tempVector.add(userBean.getUserSex());
			tempVector.add(userBean.getUserAge());
			tempVector.add(userBean.getUserAddress());
			tempVector.add(userBean.getUserOnlineStatus());
			tempVector.add(userBean.getUserRegTime());
			rowVector.add(tempVector);

		}
		tableModel.setDataVector(rowVector, columnVector);
		// ģ�͵����ݸ��ģ�֪ͨ��ͼ���и���
		tableModel.fireTableDataChanged();
	}

	public static void renew_OnlineTable_newpassword(
			ServerManagePanel serverManagePanel, String userId,
			String newPassword) {
		JTable table = serverManagePanel.getTabOnlineUser();
		DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
		Vector columnVector = serverManagePanel.getColumnVector();
		Vector rowVector = new Vector();
		for (int i = 0; i < Global_OnLine.onLineUserList.size(); i++) {
			Server_WaitIo_Thread clientThread = Global_OnLine.onLineUserList
					.get(i);
			UserBean userBean_1 = clientThread.getUserBean();
			if (userBean_1.getUserId().equals(userId)) {
				userBean_1.setUserPassword(newPassword);
			}
			Vector tempVector = new Vector();
			tempVector.add(userBean_1.getUserId());
			tempVector.add(userBean_1.getUserName());
			tempVector.add(userBean_1.getUserPassword());
			tempVector.add(userBean_1.getUserSex());
			tempVector.add(userBean_1.getUserAge());
			tempVector.add(userBean_1.getUserAddress());
			tempVector.add(userBean_1.getUserOnlineStatus());
			tempVector.add(userBean_1.getUserRegTime());
			rowVector.add(tempVector);
		}
		tableModel.setDataVector(rowVector, columnVector);
		// ģ�͵����ݸ��ģ�֪ͨ��ͼ���и���
		tableModel.fireTableDataChanged();
	}
}

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
 * 表格增删改查，刷新 工具类
 * 
 * @author lisu
 * 
 */
public class Table_util {
	public static boolean flag=false;
	private Table_util() {

	}

	/**
	 * 用户管理表格 删除方法
	 * 
	 * @param userid
	 *            要删除用户的I号
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
					// 找到这个记录后，跳出这一次的循环
					continue;
				} else {
					list.add(line);
				}
			}
			// 不需要加true,因为是覆盖
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

	/**用户修改方法
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
					// 找到这个记录后，跳出这一次的循环
					// System.out.println(id);
					
						flag=true;
						line = id
								+ ","
								+ user_Modify.getTfName().getText()
								+ ","
								+ String.valueOf(user_Modify.getTfPassword().getPassword())
								+ ","
								+ (user_Modify.getComSex().getSelectedIndex() == 0 ? "男"
										: "女") + ","
								+ user_Modify.getTfAge().getText() + ","
								+ user_Modify.getTfAddress().getText() + ","
								+ "不在线" + "," + date;
						list.add(line);
						user_Modify.setVisible(false);
						user_Modify.dispose();
					

				} else {
					list.add(line);
				}
			}
			// 不需要加true,因为是覆盖
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

	/**用户修改密码方法
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
			// 不需要加true,因为是覆盖
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

	/**用户添加方法
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
					+ (user_Add.getComSex().getSelectedIndex() == 0 ? "男" : "女")
					+ "," + user_Add.getTfAge().getText() + ","
					+ user_Add.getTfAddress().getText() + "," + "不在线" + ","
					+ str;
			if (UserMessage.userName(user_Add.getTfName().getText()) == false) {
				JOptionPane.showMessageDialog(null, "真实姓名长度需在 2~10 之间 (必需是中文)");
			} else if (UserMessage.userPassword(String.valueOf(user_Add.getTfPassword()
					.getPassword())) == false) {
				JOptionPane.showMessageDialog(null,
						"密码 长度在 3~16 之间 (只允许是数字，字母 , _ )");
			} else if (UserMessage.checkUserAge(user_Add.getTfAge().getText()) == false) {
				JOptionPane.showMessageDialog(null, "年龄 是数字 20~150之间");
			} else if (UserMessage.checkUserAddress(user_Add.getTfAddress()
					.getText()) == false) {
				JOptionPane
						.showMessageDialog(null, "地址 可以为空，但如不为空时，长度不大于 100;");
			} else {
				bufferedWriter.write(userinfo + "\n");
				bufferedWriter.flush();

				user_Add.dispose();
			}
		} catch (Exception e2) {
			e2.printStackTrace();
		} finally {
			// 关闭资源
			try {
				if (bufferedWriter != null)
					bufferedWriter.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	/**更新表格
	 * @param table
	 * @param columnVector
	 */
	public static void renew_Table(JTable table, Vector columnVector) {
		columnVector = new Vector();
		columnVector.add("账号");
		columnVector.add("用户姓名");
		columnVector.add("用户密码");
		columnVector.add("性别");
		columnVector.add("年龄");
		columnVector.add("地址");
		columnVector.add("是否在线");
		columnVector.add("注册时间");

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
		// 模型的数据更改，通知视图进行更新
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
		// 模型的数据更改，通知视图进行更新
		tableModel.fireTableDataChanged();
	}
}

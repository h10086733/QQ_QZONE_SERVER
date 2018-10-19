package serverframe_listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import server_util.Pack_util;
import server_util.Table_util;
import server_util.UserMessage;
import serverframe_thread.Server_WaitIo_Thread;
import serverframe_usermanage.User_ModifyUserDialog;
import util.DataPackage;
import util.Global_OnLine;
import util.UserBean;
import util.UserDAO;

/**
 * 用户管理面板点修改后窗口保存按钮监听
 * 
 * @author Administrator
 * 
 */
public class UserM_Modify_SaveListener implements ActionListener {

	/**
	 * 用户管理面板，修改窗口
	 */
	private User_ModifyUserDialog user_Modify;

	/**
	 * 读取文件流
	 */
	private BufferedReader bufferedReader = null;
	
	/**
	 * 写入文件流
	 */
	private BufferedWriter bufferedWriter = null;
	
	/**
	 *将用户信息装入LIST容器 
	 */
	private List<String> list = new ArrayList<String>();
	
	private String name;
	private String password;
	private String sex;
	private String age;
	private String addpess;
	private String online;
	private String date;

	public UserM_Modify_SaveListener(User_ModifyUserDialog userModifyUserDialog) {
		this.user_Modify = userModifyUserDialog;
		try {
			bufferedReader = new BufferedReader(new FileReader(
					".\\Userinfo.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(user_Modify.getBtnSave())) {
			JTable table = this.user_Modify.getServerFrame()
					.getUserManagePanel().getTable();
			int index_1 = table.getSelectedRow();
			String userid = String.valueOf(table.getValueAt(index_1, 0));
			// 修改文件里的内容
			if (UserMessage.userName(user_Modify.getTfName()
					.getText()) == false) {
				JOptionPane.showMessageDialog(null,
						"真实姓名长度需在 2~10 之间 (必需是中文)");
			} else if (UserMessage.userPassword(String.valueOf(user_Modify
					.getTfPassword().getPassword())) == false) {
				JOptionPane.showMessageDialog(null,
						"密码 长度在 3~16 之间 (只允许是数字，字母 , _ )");
			} else if (UserMessage.checkUserAge(user_Modify.getTfAge()
					.getText()) == false) {
				JOptionPane.showMessageDialog(null, "年龄 是数字 20~150之间");
			} else if (UserMessage.checkUserAddress(user_Modify
					.getTfAddress().getText()) == false) {
				JOptionPane.showMessageDialog(null,
						"地址 可以为空，但如不为空时，长度不大于 100;");
				
			}else{
			Table_util.modify(user_Modify, userid, bufferedReader);
			System.out.println("aaaaa");
			
			// 更新用户表格
			if(Table_util.flag){
				Table_util.flag=false;
			Table_util.renew_Table(table, this.user_Modify.getServerFrame()
					.getUserManagePanel().getColumnVector());
			
			// 更新服务表格
			// Table_util.renew_OnlineTable(user_Modify.serverFrame.serverManagePanel);
			JTable table_1 = user_Modify.getServerFrame()
					.getServerManagePanel().getTabOnlineUser();
			DefaultTableModel tableModel = (DefaultTableModel) table_1
					.getModel();
			Vector columnVector = user_Modify.getServerFrame()
					.getServerManagePanel().getColumnVector();
			Vector rowVector = new Vector();
			for (int i = 0; i < Global_OnLine.onLineUserList.size(); i++) {
				Server_WaitIo_Thread clientThread = Global_OnLine.onLineUserList
						.get(i);
				UserBean userBean_1 = clientThread.getUserBean();
				for (int j = 0; j < 8; j++) {
					String id = String.valueOf(table.getValueAt(j, 0));
					if (id.equals(userid)) {
						name = String.valueOf(table.getValueAt(j, 1));
						password = String.valueOf(table.getValueAt(j, 2));
						sex = String.valueOf(table.getValueAt(j, 3));
						age = String.valueOf(table.getValueAt(j, 4));
						addpess = String.valueOf(table.getValueAt(j, 5));
						online = "在线";
						date = String.valueOf(table.getValueAt(j, 7));
					}
				}
				if (userBean_1.getUserId().equals(userid)) {
					userBean_1.setUserName(name);
					userBean_1.setUserPassword(password);
					userBean_1.setUserSex(sex);
					userBean_1.setUserAge(age);
					userBean_1.setUserAddress(addpess);
					userBean_1.setUserOnlineStatus(online);
					userBean_1.setUserRegTime(date);
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

			DataPackage dataPackage = new DataPackage();
			Map map = new HashMap();
			Vector vector = new Vector();
			vector.add("所有人");
			for (int i = 0; i < Global_OnLine.onLineUserList.size(); i++) {
				Server_WaitIo_Thread serverWaitIoThread = Global_OnLine.onLineUserList
						.get(i);
				UserBean userBean = serverWaitIoThread.getUserBean();
				vector.add(userBean.getUserName()+"_"+userBean.getUserId());
			}
			map.put("onLineUserList", vector);
			dataPackage.setDataType(DataPackage.OnLineUser);
			dataPackage.setDataMap(map);
			// 循环发给所有的Socket(用户)
			Pack_util.sendAll_pack(dataPackage);
			user_Modify.getManagePanel().getButton_Modify().setEnabled(false);
			user_Modify.getManagePanel().getButton_Delete().setEnabled(false);
			user_Modify.getManagePanel().getButton_Modifypassword().setEnabled(
					false);
		}
		}
		}
		if (e.getSource().equals(user_Modify.getBtnCancle())) { /* 取消按钮 监听 */
			user_Modify.dispose();

		}
	}
}
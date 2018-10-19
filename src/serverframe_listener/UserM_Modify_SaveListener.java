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
 * �û����������޸ĺ󴰿ڱ��水ť����
 * 
 * @author Administrator
 * 
 */
public class UserM_Modify_SaveListener implements ActionListener {

	/**
	 * �û�������壬�޸Ĵ���
	 */
	private User_ModifyUserDialog user_Modify;

	/**
	 * ��ȡ�ļ���
	 */
	private BufferedReader bufferedReader = null;
	
	/**
	 * д���ļ���
	 */
	private BufferedWriter bufferedWriter = null;
	
	/**
	 *���û���Ϣװ��LIST���� 
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
			// �޸��ļ��������
			if (UserMessage.userName(user_Modify.getTfName()
					.getText()) == false) {
				JOptionPane.showMessageDialog(null,
						"��ʵ������������ 2~10 ֮�� (����������)");
			} else if (UserMessage.userPassword(String.valueOf(user_Modify
					.getTfPassword().getPassword())) == false) {
				JOptionPane.showMessageDialog(null,
						"���� ������ 3~16 ֮�� (ֻ���������֣���ĸ , _ )");
			} else if (UserMessage.checkUserAge(user_Modify.getTfAge()
					.getText()) == false) {
				JOptionPane.showMessageDialog(null, "���� ������ 20~150֮��");
			} else if (UserMessage.checkUserAddress(user_Modify
					.getTfAddress().getText()) == false) {
				JOptionPane.showMessageDialog(null,
						"��ַ ����Ϊ�գ����粻Ϊ��ʱ�����Ȳ����� 100;");
				
			}else{
			Table_util.modify(user_Modify, userid, bufferedReader);
			System.out.println("aaaaa");
			
			// �����û����
			if(Table_util.flag){
				Table_util.flag=false;
			Table_util.renew_Table(table, this.user_Modify.getServerFrame()
					.getUserManagePanel().getColumnVector());
			
			// ���·�����
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
						online = "����";
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
			// ģ�͵����ݸ��ģ�֪ͨ��ͼ���и���
			tableModel.fireTableDataChanged();

			DataPackage dataPackage = new DataPackage();
			Map map = new HashMap();
			Vector vector = new Vector();
			vector.add("������");
			for (int i = 0; i < Global_OnLine.onLineUserList.size(); i++) {
				Server_WaitIo_Thread serverWaitIoThread = Global_OnLine.onLineUserList
						.get(i);
				UserBean userBean = serverWaitIoThread.getUserBean();
				vector.add(userBean.getUserName()+"_"+userBean.getUserId());
			}
			map.put("onLineUserList", vector);
			dataPackage.setDataType(DataPackage.OnLineUser);
			dataPackage.setDataMap(map);
			// ѭ���������е�Socket(�û�)
			Pack_util.sendAll_pack(dataPackage);
			user_Modify.getManagePanel().getButton_Modify().setEnabled(false);
			user_Modify.getManagePanel().getButton_Delete().setEnabled(false);
			user_Modify.getManagePanel().getButton_Modifypassword().setEnabled(
					false);
		}
		}
		}
		if (e.getSource().equals(user_Modify.getBtnCancle())) { /* ȡ����ť ���� */
			user_Modify.dispose();

		}
	}
}
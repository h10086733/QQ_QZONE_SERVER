package serverframe_listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import server_util.Table_util;
import serverframe_thread.Server_WaitIo_Thread;
import serverframe_usermanage.UserManagePanel;
import util.DataPackage;
import util.Global_OnLine;
import util.UserBean;

/**
 * 用户管理面板删除按钮监听
 * 
 * @author lisu
 * 
 */
public class UserM_DeleteListener implements ActionListener {
	/**
	 *用户管理面板
	 */
	private UserManagePanel managePanel;

	/**
	 * 获取要删除用户的ID
	 */
	private String userid;

	/**
	 *获取要删除表格的中行数
	 */
	private int index;

	public UserM_DeleteListener(UserManagePanel managePanel) {
		this.managePanel = managePanel;
	}

	public void actionPerformed(ActionEvent e) {
		index = managePanel.getTable().getSelectedRow();
		userid = String.valueOf(managePanel.getTable().getValueAt(index, 0));
		boolean isOnline = false;
		for (int i = 0; i < Global_OnLine.onLineUserList.size(); i++) {
			Server_WaitIo_Thread serverWaitIoThread = Global_OnLine.onLineUserList
					.get(i);
			UserBean userBean = serverWaitIoThread.getUserBean();
			if (userid.equals(userBean.getUserId())) {
				isOnline = true;
			}
		}
		if (index < 0) {
			JOptionPane.showMessageDialog(null, "请选择一行后再做删除");
			return;
		} else if (!isOnline) {
			Table_util.delete(userid);
			// 更新表格数据
			managePanel.getButton_Modify().setEnabled(false);
			managePanel.getButton_Delete().setEnabled(false);
			managePanel.getButton_ModifyAllpassword().setEnabled(false);
			JTable table = this.managePanel.getTable();
			DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
			tableModel.removeRow(index);
			tableModel.fireTableDataChanged();
		} else {
			JOptionPane.showMessageDialog(null, "删除成功");
			DataPackage dataPackage = new DataPackage();
			dataPackage.setDataType(DataPackage.forceOffLine);
			for (int i = 0; i < Global_OnLine.onLineUserList.size(); i++) {
				Server_WaitIo_Thread clientThread = Global_OnLine.onLineUserList
						.get(i);
				try {
					if (clientThread.getUserBean().getUserId().equals(userid)) {
						clientThread.getObjectOutputStream().writeObject(
								dataPackage);
					}

				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					clientThread.getObjectOutputStream().flush();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			Table_util.delete(userid);
			// 更新表格数据
			JTable table = this.managePanel.getTable();
			DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
			tableModel.removeRow(index);
			tableModel.fireTableDataChanged();

		}
	}
}

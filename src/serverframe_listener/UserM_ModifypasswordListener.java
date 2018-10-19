package serverframe_listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import server_util.Table_util;
import serverframe_usermanage.UserManagePanel;

/**用户管理密码重置监听
 * @author lisu
 *
 */
public class UserM_ModifypasswordListener implements ActionListener {
	
	/**
	 *用户管理面板 
	 */
	private UserManagePanel managePanel;

	public UserM_ModifypasswordListener(UserManagePanel managePanel) {
		this.managePanel = managePanel;
	}

	public void actionPerformed(ActionEvent e) {
		int index = managePanel.getTable().getSelectedRow();
		if (index < 0) {
			JOptionPane.showMessageDialog(null, "请选择一行后再做密码重置");
			return;
		} else {
			//在文件里修改密码
			String userid = String.valueOf(managePanel.getTable().getValueAt(index, 0));
			String password="123456";
			Table_util.modifypassword(password,userid);	
		}
		//更新表格
		Table_util.renew_Table(this.managePanel.getTable(), this.managePanel.getColumnVector());
		managePanel.getButton_Modify().setEnabled(false);
		managePanel.getButton_Delete().setEnabled(false);
		managePanel.getButton_Modifypassword().setEnabled(false);
			
	}
}

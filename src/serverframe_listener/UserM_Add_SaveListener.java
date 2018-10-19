package serverframe_listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Vector;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import server_util.CommDateandTime;
import server_util.CommFileClass;
import server_util.Table_util;
import serverframe_usermanage.UserManagePanel;
import serverframe_usermanage.User_AddUserDialog;

/**用户管理添加窗口保存按钮监听
 * @author lisu
 *
 */
public class UserM_Add_SaveListener implements ActionListener {
	
	/**
	 * 用户管理添加窗口
	 */
	private User_AddUserDialog user_Add;

	public UserM_Add_SaveListener(User_AddUserDialog userAddUserDialog) {
		this.user_Add = userAddUserDialog;
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(user_Add.getBtnSave())) {
			String str = CommDateandTime.getDateAndTime();
			// 在文件添加用户
			Table_util.add(user_Add, str);
			// 更新表格
			Table_util.renew_Table(this.user_Add.getManagePanel().getTable(),
					this.user_Add.getManagePanel().getColumnVector());
			user_Add.getManagePanel().getButton_Modify().setEnabled(false);
			user_Add.getManagePanel().getButton_Delete().setEnabled(false);
			user_Add.getManagePanel().getButton_ModifyAllpassword().setEnabled(false);
		} else if (e.getSource().equals(user_Add.getBtnCancle())) { /* 取消按钮 监听 */
			user_Add.dispose();

		}

	}

}
package serverframe_usermanage;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import qq_serverframe.ServerFrame;
import server_util.Table_util;
import serverframe_listener.UserM_DeleteListener;
import serverframe_listener.UserM_Inquire_Listener;
import serverframe_listener.UserM_ModifypasswordListener;

/**选项卡属性_用户管理面板
 * @author lisu
 *
 */
public class UserManagePanel extends JPanel {
	/**
	 * 用戶管理表格
	 */
	private JTable table = null;
	/**
	 * 用戶管理表格rowVector容器
	 */
	private Vector rowVector;
	/**
	 * 用戶管理表格columnVector容器
	 */
	private Vector columnVector;
	/**
	 * 輸入要查詢的ID
	 */
	private JTextField field_Id;
	/**
	 * 輸入要查詢的姓名
	 */
	private JTextField field_Name;
	/**
	 * 查詢按鈕
	 */
	private JButton button_Inquire;
	/**
	 * 添加按鈕
	 */
	private JButton button_Add;
	/**
	 * 修改按鈕
	 */
	private JButton button_Modify;
	/**
	 * 刪除按鈕
	 */
	private JButton button_Delete;
	/**
	 * 重置密碼
	 */
	private JButton button_Modifypassword;
	/**
	 *  重置所有密碼
	 */
	private JButton button_ModifyAllpassword;
	/**
	 * 服务器主界面
	 */
	private ServerFrame serverFrame;
	/**
	 * 选项卡属性_用户管理面板
	 */
	private UserManagePanel managePanel;

	public UserManagePanel(ServerFrame serverFrame) {
		this.serverFrame = serverFrame;
		this.managePanel = this;
		this.setLayout(new BorderLayout());
		JPanel jPanel_up = get_JPanel_up();
		JTable jTable_center = get_JTable_center();
		JPanel jPanel_down = get_JPanel_down();
		this.add(jPanel_up, BorderLayout.NORTH);
		this.add(new JScrollPane(jTable_center), BorderLayout.CENTER);
		this.add(jPanel_down, BorderLayout.SOUTH);
	}

	/**
	 *  
	 * @return 返回容器添加的ID,姓名,查询
	 */
	public JPanel get_JPanel_up() {
		JPanel jPanel = new JPanel();

		JLabel jLabel_1 = new JLabel("ID");
		field_Id = new JTextField(10);
		JLabel jLabel_2 = new JLabel("姓名");
		field_Name = new JTextField(10);
		button_Inquire = new JButton("查询");

		jPanel.add(jLabel_1);
		jPanel.add(field_Id);
		jPanel.add(jLabel_2);
		jPanel.add(field_Name);
		jPanel.add(button_Inquire);

		return jPanel;
	}

	/**
	 * 
	 * @return 返回容器添加的表格
	 */
	public JTable get_JTable_center() {
		table = new JTable();
		// 更新表格
		Table_util.renew_Table(table, columnVector);
		initComponent();
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				button_Modify.setEnabled(true);
				button_Delete.setEnabled(true);
				button_Modifypassword.setEnabled(true);

			}
		});
		return table;
	}

	/**
	 * 设置表格得长和宽
	 */
	private void initComponent() {
		this.table.setRowHeight(24);
		// 设置列高
		this.table.getTableHeader().setPreferredSize(new Dimension(5, 32));
		// 设定表格的列之间不可移动
		this.table.getTableHeader().setResizingAllowed(false);
		// 设定表格的列之间不可拖动
		this.table.getTableHeader().setReorderingAllowed(false);
		// 设定表格的选择模式(单行)
		this.table.getSelectionModel().setSelectionMode(
				ListSelectionModel.SINGLE_SELECTION);
	}

	public JPanel get_JPanel_down() {
		JPanel jPanel = new JPanel();

		button_Add = new JButton("添加");
		button_Modify = new JButton("修改");
		button_Delete = new JButton("删除");
		button_Modifypassword = new JButton("重置密码");
		button_ModifyAllpassword = new JButton("重置所有密码");
		button_Modify.setEnabled(false);
		button_Delete.setEnabled(false);
		button_Modifypassword.setEnabled(false);
		jPanel.add(button_Add);
		jPanel.add(button_Modify);
		jPanel.add(button_Delete);
		jPanel.add(button_Modifypassword);
		jPanel.add(button_ModifyAllpassword);

		// 添加按钮监听
		button_Add.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				User_AddUserDialog addUserDialog = new User_AddUserDialog(
						managePanel);
			}
		});
		// 修改按钮监听
		button_Modify.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				int index = table.getSelectedRow();
				if (index < 0) {
					JOptionPane.showMessageDialog(null, "请选择一行后再做修改");
					return;
				} else {
					User_ModifyUserDialog modifyUserDialog = new User_ModifyUserDialog(
							serverFrame, managePanel);
				}
			}
		});
		// 删除按钮监听
		UserM_DeleteListener deleteListener = new UserM_DeleteListener(
				managePanel);
		button_Delete.addActionListener(deleteListener);
		// 重置密码按钮监听
		UserM_ModifypasswordListener userMModifypasswordListener = new UserM_ModifypasswordListener(
				managePanel);
		button_Modifypassword.addActionListener(userMModifypasswordListener);
		UserM_Inquire_Listener inquireListener = new UserM_Inquire_Listener(
				managePanel);
		button_Inquire.addActionListener(inquireListener);
		return jPanel;
	}
	public JTable getTable() {
		return table;
	}

	public void setTable(JTable table) {
		this.table = table;
	}

	public Vector getRowVector() {
		return rowVector;
	}

	public void setRowVector(Vector rowVector) {
		this.rowVector = rowVector;
	}

	public Vector getColumnVector() {
		return columnVector;
	}

	public void setColumnVector(Vector columnVector) {
		this.columnVector = columnVector;
	}

	public JTextField getField_Id() {
		return field_Id;
	}

	public void setField_Id(JTextField fieldId) {
		field_Id = fieldId;
	}

	public JTextField getField_Name() {
		return field_Name;
	}

	public void setField_Name(JTextField fieldName) {
		field_Name = fieldName;
	}

	public JButton getButton_Inquire() {
		return button_Inquire;
	}

	public void setButton_Inquire(JButton buttonInquire) {
		button_Inquire = buttonInquire;
	}

	public JButton getButton_Add() {
		return button_Add;
	}

	public void setButton_Add(JButton buttonAdd) {
		button_Add = buttonAdd;
	}

	public JButton getButton_Modify() {
		return button_Modify;
	}

	public void setButton_Modify(JButton buttonModify) {
		button_Modify = buttonModify;
	}

	public JButton getButton_Delete() {
		return button_Delete;
	}

	public void setButton_Delete(JButton buttonDelete) {
		button_Delete = buttonDelete;
	}

	public JButton getButton_Modifypassword() {
		return button_Modifypassword;
	}

	public void setButton_Modifypassword(JButton buttonModifypassword) {
		button_Modifypassword = buttonModifypassword;
	}

	public JButton getButton_ModifyAllpassword() {
		return button_ModifyAllpassword;
	}

	public void setButton_ModifyAllpassword(JButton buttonModifyAllpassword) {
		button_ModifyAllpassword = buttonModifyAllpassword;
	}

	public ServerFrame getServerFrame() {
		return serverFrame;
	}

	public void setServerFrame(ServerFrame serverFrame) {
		this.serverFrame = serverFrame;
	}

	public UserManagePanel getManagePanel() {
		return managePanel;
	}

	public void setManagePanel(UserManagePanel managePanel) {
		this.managePanel = managePanel;
	}

}

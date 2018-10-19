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

/**ѡ�����_�û��������
 * @author lisu
 *
 */
public class UserManagePanel extends JPanel {
	/**
	 * �Ñ�������
	 */
	private JTable table = null;
	/**
	 * �Ñ�������rowVector����
	 */
	private Vector rowVector;
	/**
	 * �Ñ�������columnVector����
	 */
	private Vector columnVector;
	/**
	 * ݔ��Ҫ��ԃ��ID
	 */
	private JTextField field_Id;
	/**
	 * ݔ��Ҫ��ԃ������
	 */
	private JTextField field_Name;
	/**
	 * ��ԃ���o
	 */
	private JButton button_Inquire;
	/**
	 * ��Ӱ��o
	 */
	private JButton button_Add;
	/**
	 * �޸İ��o
	 */
	private JButton button_Modify;
	/**
	 * �h�����o
	 */
	private JButton button_Delete;
	/**
	 * �����ܴa
	 */
	private JButton button_Modifypassword;
	/**
	 *  ���������ܴa
	 */
	private JButton button_ModifyAllpassword;
	/**
	 * ������������
	 */
	private ServerFrame serverFrame;
	/**
	 * ѡ�����_�û��������
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
	 * @return ����������ӵ�ID,����,��ѯ
	 */
	public JPanel get_JPanel_up() {
		JPanel jPanel = new JPanel();

		JLabel jLabel_1 = new JLabel("ID");
		field_Id = new JTextField(10);
		JLabel jLabel_2 = new JLabel("����");
		field_Name = new JTextField(10);
		button_Inquire = new JButton("��ѯ");

		jPanel.add(jLabel_1);
		jPanel.add(field_Id);
		jPanel.add(jLabel_2);
		jPanel.add(field_Name);
		jPanel.add(button_Inquire);

		return jPanel;
	}

	/**
	 * 
	 * @return ����������ӵı��
	 */
	public JTable get_JTable_center() {
		table = new JTable();
		// ���±��
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
	 * ���ñ��ó��Ϳ�
	 */
	private void initComponent() {
		this.table.setRowHeight(24);
		// �����и�
		this.table.getTableHeader().setPreferredSize(new Dimension(5, 32));
		// �趨������֮�䲻���ƶ�
		this.table.getTableHeader().setResizingAllowed(false);
		// �趨������֮�䲻���϶�
		this.table.getTableHeader().setReorderingAllowed(false);
		// �趨����ѡ��ģʽ(����)
		this.table.getSelectionModel().setSelectionMode(
				ListSelectionModel.SINGLE_SELECTION);
	}

	public JPanel get_JPanel_down() {
		JPanel jPanel = new JPanel();

		button_Add = new JButton("���");
		button_Modify = new JButton("�޸�");
		button_Delete = new JButton("ɾ��");
		button_Modifypassword = new JButton("��������");
		button_ModifyAllpassword = new JButton("������������");
		button_Modify.setEnabled(false);
		button_Delete.setEnabled(false);
		button_Modifypassword.setEnabled(false);
		jPanel.add(button_Add);
		jPanel.add(button_Modify);
		jPanel.add(button_Delete);
		jPanel.add(button_Modifypassword);
		jPanel.add(button_ModifyAllpassword);

		// ��Ӱ�ť����
		button_Add.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				User_AddUserDialog addUserDialog = new User_AddUserDialog(
						managePanel);
			}
		});
		// �޸İ�ť����
		button_Modify.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				int index = table.getSelectedRow();
				if (index < 0) {
					JOptionPane.showMessageDialog(null, "��ѡ��һ�к������޸�");
					return;
				} else {
					User_ModifyUserDialog modifyUserDialog = new User_ModifyUserDialog(
							serverFrame, managePanel);
				}
			}
		});
		// ɾ����ť����
		UserM_DeleteListener deleteListener = new UserM_DeleteListener(
				managePanel);
		button_Delete.addActionListener(deleteListener);
		// �������밴ť����
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

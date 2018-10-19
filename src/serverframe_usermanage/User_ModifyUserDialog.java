package serverframe_usermanage;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.JTextField;

import qq_serverframe.ServerFrame;
import serverframe_listener.UserM_Add_SaveListener;
import serverframe_listener.UserM_Modify_SaveListener;


/***�û������޸Ĵ���
 * @author Administrator
 *
 */
public class User_ModifyUserDialog extends JDialog {
	
	/**
	 * �û�ID
	 */
	private JTextField tfUserID;

	/**
	 * �û�����
	 */
	private JTextField tfName;
	
	/**
	 * �û�����
	 */
	private  JPasswordField tfPassword;
	/**
	 * �û��Ա�
	 */
	private JComboBox comSex;
	/**
	 * �û�״̬
	 */
	private JComboBox onLine;
	/**
	 * �û�����
	 */
	private JTextField tfAge;
	/**
	 * �û���ַ
	 */
	private JTextField tfAddress;
	/**
	 * �û�ע������
	 */
	private JTextField tfDate;
	/**
	 * �û������޸Ĵ��ڱ��水ť
	 */
	private JButton btnSave;
	/**
	 * �û������޸Ĵ���ȡ����ť
	 */
	private JButton btnCancle;
	
	/**
	 * ѡ�����_�û��������
	 */
	private UserManagePanel managePanel;
	
	/**
	 * ������������
	 */
	private ServerFrame serverFrame;
		
	
	public User_ModifyUserDialog(ServerFrame serverFrame, UserManagePanel managePanel){
		super(serverFrame,true);
		this.serverFrame = serverFrame;
		this.managePanel=managePanel;
		this.setTitle("�޸��û�");
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(this);
		this.setSize(290, 300);
		
		initComponent();
	}
	private void initComponent() {
		JPanel panel=new JPanel(new GridLayout(8,1));
		JPanel panel_1=new JPanel();
		JPanel panel_2=new JPanel();
		JPanel panel_3=new JPanel();
		JPanel panel_4=new JPanel();
		JPanel panel_5=new JPanel();
		JPanel panel_6=new JPanel();
		JPanel panel_7=new JPanel();
		JPanel panel_8=new JPanel();
		
		panel.add(panel_1);
		panel.add(panel_2);
		panel.add(panel_3);
		panel.add(panel_4);
		panel.add(panel_5);
		panel.add(panel_6);
		panel.add(panel_7);
		panel.add(panel_8);
		
		JLabel labUserID=new JLabel("��      ��:");
		panel_1.add(labUserID);	
		tfUserID=new JTextField(15);
		tfUserID.setEditable(false);
		panel_1.add(tfUserID);
		
		JLabel labName=new JLabel("��      ��:");
		panel_2.add(labName);
		tfName=new JTextField(15);
		panel_2.add(tfName);
		
		JLabel labPass=new JLabel("��      ��:");
		panel_3.add(labPass);
		tfPassword=new JPasswordField(15);
		panel_3.add(tfPassword);
		
		JLabel labSex=new JLabel("��      ��:");
		panel_4.add(labSex);
		String []sex=new String[]{"��","Ů"};
		comSex=new JComboBox(sex);
		panel_4.add(comSex);
		JLabel labnull=new JLabel("�Ƿ����ߣ�  ");
		String []onLlne=new String[]{"������","����"};
		onLine=new JComboBox(onLlne);
		panel_4.add(labnull);
		panel_4.add(onLine);
		
		JLabel labAge=new JLabel("��      ��:");	
		panel_5.add(labAge);
		tfAge=new JTextField(15);
		panel_5.add(tfAge);
		
		JLabel labAddress=new JLabel("��      ַ:");
		panel_6.add(labAddress);
		tfAddress=new JTextField(15);
		panel_6.add(tfAddress);
		
		JLabel labDate=new JLabel("ע��ʱ��:");
		panel_7.add(labDate);
		tfDate=new JTextField(15);
		tfDate.setEditable(false);
		panel_7.add(tfDate);
		
		btnSave=new JButton("����");
		panel_8.add(btnSave);
		btnCancle=new JButton("ȡ��");
		panel_8.add(btnCancle);
		
		
		// ��ʼ��DiaLog�пؼ���ֵ
		JTable table = this.serverFrame.getUserManagePanel().getTable();
		int index = table.getSelectedRow();
		if (index > -1) {
			this.tfUserID.setText(String.valueOf(table.getValueAt(index, 0)));
			this.tfName.setText(String.valueOf(table.getValueAt(index, 1)));
			this.tfPassword.setText(String.valueOf(table.getValueAt(index, 2)));
			String userSex = String.valueOf(table.getValueAt(index, 3));
			if (userSex.equals("��")) {
				this.comSex.setSelectedIndex(0);
			} else if (userSex.equals("Ů")) {
				this.comSex.setSelectedIndex(1);
			}
			this.tfAge.setText(String.valueOf(table.getValueAt(index, 4)));
			this.tfAddress.setText(String.valueOf(table.getValueAt(index, 5)));
			this.tfDate.setText(String.valueOf(table.getValueAt(index, 7)));
		}

		

		UserM_Modify_SaveListener listener = new UserM_Modify_SaveListener(this);
		btnSave.addActionListener(listener);
		btnCancle.addActionListener(listener);
		this.add(panel);
		this.setVisible(true);
		
	}
	public JTextField getTfUserID() {
		return tfUserID;
	}
	public void setTfUserID(JTextField tfUserID) {
		this.tfUserID = tfUserID;
	}
	public JTextField getTfName() {
		return tfName;
	}
	public void setTfName(JTextField tfName) {
		this.tfName = tfName;
	}
	public JPasswordField getTfPassword() {
		return tfPassword;
	}
	public void setTfPassword(JPasswordField tfPassword) {
		this.tfPassword = tfPassword;
	}
	public JComboBox getComSex() {
		return comSex;
	}
	public void setComSex(JComboBox comSex) {
		this.comSex = comSex;
	}
	public JComboBox getOnLine() {
		return onLine;
	}
	public void setOnLine(JComboBox onLine) {
		this.onLine = onLine;
	}
	public JTextField getTfAge() {
		return tfAge;
	}
	public void setTfAge(JTextField tfAge) {
		this.tfAge = tfAge;
	}
	public JTextField getTfAddress() {
		return tfAddress;
	}
	public void setTfAddress(JTextField tfAddress) {
		this.tfAddress = tfAddress;
	}
	public JTextField getTfDate() {
		return tfDate;
	}
	public void setTfDate(JTextField tfDate) {
		this.tfDate = tfDate;
	}
	public JButton getBtnSave() {
		return btnSave;
	}
	public void setBtnSave(JButton btnSave) {
		this.btnSave = btnSave;
	}
	public JButton getBtnCancle() {
		return btnCancle;
	}
	public void setBtnCancle(JButton btnCancle) {
		this.btnCancle = btnCancle;
	}
	public UserManagePanel getManagePanel() {
		return managePanel;
	}
	public void setManagePanel(UserManagePanel managePanel) {
		this.managePanel = managePanel;
	}
	public ServerFrame getServerFrame() {
		return serverFrame;
	}
	public void setServerFrame(ServerFrame serverFrame) {
		this.serverFrame = serverFrame;
	}
}
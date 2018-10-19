package serverframe_usermanage;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


import qq_serverframe.ServerFrame;
import server_util.UserMessage;
import server_util.CommDateandTime;
import server_util.CommFileClass;
import serverframe_listener.UserM_Add_SaveListener;



/**用户管理添加窗口
 * @author Administrator
 *
 */
public class User_AddUserDialog extends JDialog {

	/**
	 * 用户ID
	 */
	private JTextField tfUserID;

	/**
	 * 用户姓名
	 */
	private JTextField tfName;
	
	/**
	 * 用户密码
	 */
	private  JPasswordField tfPassword;
	/**
	 * 用户性别
	 */
	private JComboBox comSex;
	/**
	 * 用户状态
	 */
	private JComboBox onLine;
	/**
	 * 用户年龄
	 */
	private JTextField tfAge;
	/**
	 * 用户地址
	 */
	private JTextField tfAddress;
	/**
	 * 用户注册日期
	 */
	private JTextField tfDate;

	/**
	 * 用户管理添加窗口保存按钮
	 */
	private  JButton btnSave;
	/**
	 * 用户管理添加窗口取消按钮
	 */
	private JButton btnCancle;
	
	/**
	 * 选项卡属性_用户管理面板
	 */
	private UserManagePanel managePanel;

	public User_AddUserDialog(UserManagePanel managePanel){
	//super(serverFrame,true);
	this.managePanel = managePanel;
	this.setTitle("添加用户");
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
		
		JLabel labUserID=new JLabel("编      号:");
		panel_1.add(labUserID);	
		tfUserID=new JTextField(15);
		tfUserID.setText(UserMessage.userId());
		tfUserID.setEditable(false);
		panel_1.add(tfUserID);
		
		JLabel labName=new JLabel("姓      名:");
		panel_2.add(labName);
		tfName=new JTextField(15);
		
		panel_2.add(tfName);
		
		JLabel labPass=new JLabel("密      码:");
		panel_3.add(labPass);
		tfPassword=new JPasswordField(15);
		tfPassword.setText("123456");
		panel_3.add(tfPassword);
		
		JLabel labSex=new JLabel("性      别:");
		panel_4.add(labSex);
		String []sex=new String[]{"男","女"};
		comSex=new JComboBox(sex);
		panel_4.add(comSex);
		JLabel labnull=new JLabel("是否在线：  ");
		String []onLlne=new String[]{"不在线","在线"};
		onLine=new JComboBox(onLlne);
		panel_4.add(labnull);
		panel_4.add(onLine);
		
		JLabel labAge=new JLabel("年      龄:");	
		panel_5.add(labAge);
		tfAge=new JTextField(15);
		panel_5.add(tfAge);
		
		JLabel labAddress=new JLabel("地      址:");
		panel_6.add(labAddress);
		tfAddress=new JTextField(15);
		panel_6.add(tfAddress);
		
		JLabel labDate=new JLabel("注册时间:");
		panel_7.add(labDate);
		tfDate=new JTextField(CommDateandTime.getDateAndTime());
		tfDate.setEditable(false);
		panel_7.add(tfDate);
		
		btnSave=new JButton("保存");
		panel_8.add(btnSave);
		btnCancle=new JButton("取消");
		panel_8.add(btnCancle);
		
		

		UserM_Add_SaveListener listener = new UserM_Add_SaveListener(this);
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
}

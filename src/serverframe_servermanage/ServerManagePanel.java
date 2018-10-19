package serverframe_servermanage;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.event.AncestorListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import qq_serverframe.ServerFrame;
import serverframe_listener.ServerM_Listener;
import util.LogFile;

/**
 * 选项卡属性_服务管理面板
 * 
 * @author lisu
 * 
 */
public class ServerManagePanel extends JPanel {
	/**
	 * 通讯信息提示
	 */
	private JTextArea taPromptInfo;

	/**
	 * 发送公告
	 */
	private JTextArea taSendBulletin;

	/**
	 * 发送 按钮
	 */
	private JButton btnSend;

	/**
	 * 图片 Label
	 */
	private JLabel labImg;

	/**
	 * 启动服务按钮
	 */
	private JButton btnStarServer;

	/**
	 * 停止服务按钮
	 */
	private JButton btnStopServer;

	/**
	 * 强制下线按钮
	 */
	private JButton btnOffLine;

	/**
	 * 在线用户表格
	 */
	private JTable tabOnlineUser;

	/**
	 * 表rowVector容器
	 */
	private Vector rowVector;
	/**
	 * 表columnVector容器
	 */
	private Vector columnVector;

	/**
	 * 服务器主界面
	 */
	private ServerFrame serverFrame;

	/**
	 * 服务器面板监听
	 */
	private ServerM_Listener serverMListener;

	public ServerManagePanel(ServerFrame serverFrame) {
		this.setLayout(new BorderLayout());
		inittable_up();
		this.serverFrame = serverFrame;
		JPanel jPanel_down = new JPanel(new BorderLayout());
		JPanel jPanel_down_Lef = inittable_down_Left();
		JPanel jPanel_down_Rigth = inittable_down_Rigth();
		jPanel_down.add(jPanel_down_Lef, BorderLayout.CENTER);
		jPanel_down.add(jPanel_down_Rigth, BorderLayout.EAST);
		this.add(jPanel_down, BorderLayout.SOUTH);
		registerListener();
	}

	/**
	 *构建 服务管理面板上部分(在线用户表格)
	 */
	public void inittable_up() {
		JPanel jPanel = new JPanel(new BorderLayout());
		JPanel jPanel_1 = new JPanel(new BorderLayout());
		Border outBorder = BorderFactory.createEmptyBorder(10, 5, 10, 5);
		Border inBorder = BorderFactory.createTitledBorder("在线用户列表");
		Border border = BorderFactory.createCompoundBorder(outBorder, inBorder);

		columnVector = new Vector();
		columnVector.add("帐号");
		columnVector.add("用户姓名");
		columnVector.add("用户密码");
		columnVector.add("性别");
		columnVector.add("年龄");
		columnVector.add("地址");
		columnVector.add("是否在线");
		columnVector.add("注册时间");
		rowVector = new Vector();
		TableModel tableModel = new DefaultTableModel(null, columnVector);
		tabOnlineUser = new JTable();
		tabOnlineUser.setModel(tableModel);
		jPanel.setBorder(border);
		jPanel.add(new JScrollPane(tabOnlineUser), BorderLayout.CENTER);
		jPanel_1.add(jPanel);
		// 凹入斜面边缘的边框
		jPanel_1.setBorder(BorderFactory.createLoweredBevelBorder());
		this.add(jPanel_1, BorderLayout.CENTER);

	}
	/**
	 * @return  返回服务管理面板下部分的左部分(通讯信息提示，公告发送)的容器
	 */
	private JPanel inittable_down_Left() {
		JPanel jPanel = new JPanel(new BorderLayout());
		JPanel jPanel_up = new JPanel(new BorderLayout());
		JPanel jPanel_down = new JPanel(new BorderLayout());
		JPanel jPanel_up_1 = new JPanel(new BorderLayout());
		JPanel jPanel_down_1 = new JPanel(new BorderLayout());

		Border outBorde_upr = BorderFactory.createEmptyBorder(1, 1, 1, 1);
		Border inBorder_up = BorderFactory.createTitledBorder("通讯信息提示");
		Border border_up = BorderFactory.createCompoundBorder(outBorde_upr,
				inBorder_up);
		jPanel_up.setBorder(border_up);
		taPromptInfo = new JTextArea(4, 13);
		JScrollPane jspProInfo_up = new JScrollPane(taPromptInfo);
		Border outBorder_down = BorderFactory.createEmptyBorder(1, 1, 1, 1);
		Border inBorder_down = BorderFactory.createTitledBorder("公告发送");
		Border border_down = BorderFactory.createCompoundBorder(outBorder_down,
				inBorder_down);
		jPanel_down.setBorder(border_down);

		taSendBulletin = new JTextArea(5, 13);
		JScrollPane jspProInfo_down = new JScrollPane(taSendBulletin);
		taSendBulletin.setEditable(false);
		btnSend = new JButton("发送");
		btnSend.setEnabled(false);
		Box box = Box.createHorizontalBox();
		box.add(Box.createHorizontalGlue());
		box.add(btnSend);
		box.add(Box.createHorizontalStrut(30));
		jPanel_down.add(box, BorderLayout.SOUTH);
		jPanel_down.add(jspProInfo_down);
		jPanel_up.add(jspProInfo_up);
		jPanel_up_1.add(jPanel_up);
		jPanel_down_1.add(jPanel_down);
		jPanel.add(jPanel_up_1, BorderLayout.CENTER);
		jPanel.add(jPanel_down_1, BorderLayout.SOUTH);

		jPanel_up_1.setBorder(BorderFactory.createLoweredBevelBorder());
		jPanel_down_1.setBorder(BorderFactory.createLoweredBevelBorder());
		jPanel.setBorder(BorderFactory.createLoweredBevelBorder());

		return jPanel;
	}

	/**
	 * @return 返回服务管理面板下部分的右部分(服务器管理)的容器
	 */
	private JPanel inittable_down_Rigth() {
		JPanel jPanel = new JPanel(new BorderLayout());
		JPanel jPanel_1 = new JPanel(new BorderLayout());

		Border outBorder_down = BorderFactory.createEmptyBorder(1, 1, 1, 1);
		Border inBorder_down = BorderFactory.createTitledBorder("服务器管理");
		Border border_down = BorderFactory.createCompoundBorder(outBorder_down,
				inBorder_down);
		jPanel.setBorder(border_down);// 添加

		labImg = new JLabel(new ImageIcon("./images/serverstop.gif"));
		// Box boxAll = Box.createHorizontalBox();

		// boxAll.add(labImg);

		btnStarServer = new JButton("启动服务");
		btnStopServer = new JButton("停止服务");
		btnStopServer.setEnabled(false);
		btnOffLine = new JButton("强制下线");
		btnOffLine.setEnabled(false);
		Box boxChild = Box.createVerticalBox();
		boxChild.add(Box.createVerticalGlue());
		boxChild.add(btnStarServer);
		boxChild.add(Box.createVerticalGlue());
		boxChild.add(btnStopServer);
		boxChild.add(Box.createVerticalGlue());
		boxChild.add(btnOffLine);
		boxChild.add(Box.createVerticalGlue());

		Box boxAll = Box.createHorizontalBox();
		boxAll.add(Box.createHorizontalStrut(30));
		boxAll.add(labImg);
		boxAll.add(Box.createHorizontalStrut(20));
		boxAll.add(boxChild);
		boxAll.add(Box.createHorizontalStrut(10));

		jPanel.add(boxAll);// 添加
		jPanel_1.add(jPanel);
		jPanel_1.setBorder(BorderFactory.createLoweredBevelBorder());
		return jPanel_1;
	}

	/**
	 * 注册选项卡属性_服务管理面板中的按钮监听
	 */
	public void registerListener() {
		serverMListener = new ServerM_Listener(this);
		btnStarServer.addActionListener(serverMListener);
		btnStopServer.addActionListener(serverMListener);
		btnOffLine.addActionListener(serverMListener);
		btnSend.addActionListener(serverMListener);
	}

	public JTextArea getTaPromptInfo() {
		return taPromptInfo;
	}

	public void setTaPromptInfo(JTextArea taPromptInfo) {
		this.taPromptInfo = taPromptInfo;
	}

	public JTextArea getTaSendBulletin() {
		return taSendBulletin;
	}

	public void setTaSendBulletin(JTextArea taSendBulletin) {
		this.taSendBulletin = taSendBulletin;
	}

	public JButton getBtnSend() {
		return btnSend;
	}

	public void setBtnSend(JButton btnSend) {
		this.btnSend = btnSend;
	}

	public JLabel getLabImg() {
		return labImg;
	}
	public JButton getBtnStarServer() {
		return btnStarServer;
	}

	public void setBtnStarServer(JButton btnStarServer) {
		this.btnStarServer = btnStarServer;
	}

	public JButton getBtnStopServer() {
		return btnStopServer;
	}

	public void setBtnStopServer(JButton btnStopServer) {
		this.btnStopServer = btnStopServer;
	}

	public JButton getBtnOffLine() {
		return btnOffLine;
	}

	public void setBtnOffLine(JButton btnOffLine) {
		this.btnOffLine = btnOffLine;
	}

	public JTable getTabOnlineUser() {
		return tabOnlineUser;
	}

	public void setTabOnlineUser(JTable tabOnlineUser) {
		this.tabOnlineUser = tabOnlineUser;
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

	public ServerFrame getServerFrame() {
		return serverFrame;
	}

	public void setServerFrame(ServerFrame serverFrame) {
		this.serverFrame = serverFrame;
	}

	public ServerM_Listener getServerMListener() {
		return serverMListener;
	}

	public void setServerMListener(ServerM_Listener serverMListener) {
		this.serverMListener = serverMListener;
	}

}

package qq_serverframe;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;

import serverframe_logmanage.LogManagePanel;
import serverframe_servermanage.ServerManagePanel;
import serverframe_servermanage.ServerManageQQPanel;
import serverframe_usermanage.UserManagePanel;
/**
 * ������������
 * @author lisu
 *
 */
public class ServerFrame extends JFrame {
	/**
	 * ѡ�����_����������
	 */
	private ServerManagePanel serverManagePanel;
	
	/**
	 * ѡ�����_QQ����
	 */
	private ServerManageQQPanel serverManageQQPanel;
	
	/**
	 * ѡ�����_�û��������
	 */
	private UserManagePanel userManagePanel;
	/**
	 * ѡ�����_��־�������
	 */
	private LogManagePanel logManagePanel;

	ServerFrame() {
		this.setSize(550, 520);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//this.addWindowListener(new TestWindowListener());
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setTitle("��ҵQQ��������");

		JTabbedPane jTabbedPane = new JTabbedPane();
		serverManagePanel = new ServerManagePanel(this);
		serverManageQQPanel = new ServerManageQQPanel(this);
		userManagePanel = new UserManagePanel(this);
		logManagePanel = new LogManagePanel();

		jTabbedPane.addTab("QQ����", serverManageQQPanel);
//		jTabbedPane.addTab("����������", serverManagePanel);
//		jTabbedPane.addTab("�û�����", userManagePanel);
//		jTabbedPane.addTab("��־����", logManagePanel);
		this.add(jTabbedPane);
		this.addWindowListener(new WindowAdapter() {	
			public void windowClosing(WindowEvent e) {
				//serverManagePanel.serverMListener.serverWaitClientThread.
				
				int result = JOptionPane.showConfirmDialog(null,
						"����ֹͣ�������ٹ�", "������ʾ", JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.YES_OPTION) {
					
					ServerFrame.this.setVisible(false);
					ServerFrame.this.dispose();
					System.exit(0);
				} else {

					return;
				}
				
			}
			
		});
		this.setVisible(true);
	}
	public ServerManagePanel getServerManagePanel() {
		return serverManagePanel;
	}
	public void setServerManagePanel(ServerManagePanel serverManagePanel) {
		this.serverManagePanel = serverManagePanel;
	}
	public UserManagePanel getUserManagePanel() {
		return userManagePanel;
	}
	public void setUserManagePanel(UserManagePanel userManagePanel) {
		this.userManagePanel = userManagePanel;
	}
	public LogManagePanel getLogManagePanel() {
		return logManagePanel;
	}
	public void setLogManagePanel(LogManagePanel logManagePanel) {
		this.logManagePanel = logManagePanel;
	}
}
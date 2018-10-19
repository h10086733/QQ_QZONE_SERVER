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
 * 服务器主界面
 * @author lisu
 *
 */
public class ServerFrame extends JFrame {
	/**
	 * 选项卡属性_服务管理面板
	 */
	private ServerManagePanel serverManagePanel;
	
	/**
	 * 选项卡属性_QQ工具
	 */
	private ServerManageQQPanel serverManageQQPanel;
	
	/**
	 * 选项卡属性_用户管理面板
	 */
	private UserManagePanel userManagePanel;
	/**
	 * 选项卡属性_日志管理面板
	 */
	private LogManagePanel logManagePanel;

	ServerFrame() {
		this.setSize(550, 520);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//this.addWindowListener(new TestWindowListener());
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setTitle("企业QQ服务器端");

		JTabbedPane jTabbedPane = new JTabbedPane();
		serverManagePanel = new ServerManagePanel(this);
		serverManageQQPanel = new ServerManageQQPanel(this);
		userManagePanel = new UserManagePanel(this);
		logManagePanel = new LogManagePanel();

		jTabbedPane.addTab("QQ工具", serverManageQQPanel);
//		jTabbedPane.addTab("服务器管理", serverManagePanel);
//		jTabbedPane.addTab("用户管理", userManagePanel);
//		jTabbedPane.addTab("日志管理", logManagePanel);
		this.add(jTabbedPane);
		this.addWindowListener(new WindowAdapter() {	
			public void windowClosing(WindowEvent e) {
				//serverManagePanel.serverMListener.serverWaitClientThread.
				
				int result = JOptionPane.showConfirmDialog(null,
						"请先停止服务器再关", "友情提示", JOptionPane.YES_NO_OPTION);
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
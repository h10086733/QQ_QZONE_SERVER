package qq_serverframe;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;

import server_util.CommFileClass;
import server_util.CommProperties;

/**
 * 服务器启动配置窗口
 * @author lisu
 *
 */
public class ServerLogin extends JDialog {
	/**
	 * 文件标签
	 */
	private JLabel fileLabel = null;
	/**
	 * 端口标签
	 */
	private JLabel  portLabel = null;
	/**
	 *文件文本框
	 */
	private JTextField fileField = null;
	/**
	 *端口文本框
	 */
	private JTextField  portField = null;
	/**
	 * 测试连接按钮
	 */
	private JButton testConnectButton = null;
	/**
	 * 测试端口按钮
	 */
	private JButton testPortButton = null;
	/**
	 * 保存配置
	 */
	private JButton saveButton = null;
	/**
	 * 进入服务器按钮
	 */
	private JButton loginButton = null;
	/**
	 * 浏览文件按钮
	 */
	private JButton broswerButton = null;

	/**
	 * 构建窗口方法
	 */
	public ServerLogin() {
		this.setTitle("服务器启动配置");
		this.setSize(400, 250);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setLayout(new FlowLayout());
		this.initCompoment();
		this.readConfig();
		this.setVisible(true);

		// 添加按纽的监听
		ButtonListener buttonListener = new ButtonListener();
		this.testConnectButton.addActionListener(buttonListener);
		this.testPortButton.addActionListener(buttonListener);
		this.loginButton.addActionListener(buttonListener);
	}

	/**
	 *构建按钮文本容器方法
	 */
	private void initCompoment() {

		this.fileLabel = new JLabel("文  件  ");
		this.fileField = new JTextField();
		this.broswerButton = new JButton("浏览...");

		this.portLabel = new JLabel("端口号  ");
		this.portField = new JTextField(20);
		this.testConnectButton = new JButton("测试连接");
		this.testPortButton = new JButton("测试端口");
		//this.saveButton = new JButton("保存配置");
		this.loginButton = new JButton("进入服务器");

		Box box = Box.createVerticalBox();

		Box fileBox = Box.createHorizontalBox();
		fileBox.add(fileLabel);
		fileBox.add(Box.createHorizontalStrut(10));
		fileBox.add(fileField);
		fileBox.add(Box.createHorizontalStrut(10));
		fileBox.add(broswerButton);

		Box portBox = Box.createHorizontalBox();
		portBox.add(portLabel);
		portBox.add(Box.createHorizontalStrut(10));
		portBox.add(portField);

		Box buttonBox = Box.createHorizontalBox();
		buttonBox.add(testPortButton);
		buttonBox.add(Box.createHorizontalStrut(10));
		//buttonBox.add(saveButton);
		buttonBox.add(Box.createHorizontalStrut(10));
		buttonBox.add(loginButton);

		// 添加到总的Box中
		box.add(Box.createVerticalStrut(20));
		box.add(fileBox);

		box.add(Box.createVerticalStrut(20));
		box.add(portBox);
		box.add(Box.createVerticalStrut(20));
		box.add(buttonBox);
		this.add(box);
	}

	/**
	 * 读取配置文件和端口方法
	 */
	private void readConfig() {
		// 读取配置文件的值
		String socketPort = CommProperties.getValue("port",CommFileClass.post_file_name);
		String UserFile = String.valueOf(CommFileClass.file_name);
		this.portField.setText(socketPort);
		this.fileField.setText( UserFile);

	}

	/**服务器启动配置监听
	 * @author lisu
	 *
	 */
	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(testPortButton)) {
				String port = ServerLogin.this.portField.getText();
				ServerSocket serverSocket = null;
				try {
					serverSocket = new ServerSocket(Integer.parseInt(port));
					JOptionPane.showMessageDialog(null, "端口测试成功，端口可用");
				} catch (NumberFormatException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "端口测试失败", "错误消息",
							JOptionPane.ERROR_MESSAGE);
					return;
				} finally {
					if (serverSocket != null) {
						try {
							serverSocket.close();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
			else if (e.getSource().equals(loginButton)){
				ServerFrame serverFrame = new ServerFrame();
				ServerLogin.this.setVisible(false);
				ServerLogin.this.dispose();
			} 
		}
	}
	public final static String NILO = "com.nilo.plaf.nimrod.NimRODLookAndFeel";

	public static void main(String[] args) {
		// 设置皮肤
		try {
			UIManager.setLookAndFeel(ServerLogin.NILO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ServerLogin serverLogin = new ServerLogin();
	}
}

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
 * �������������ô���
 * @author lisu
 *
 */
public class ServerLogin extends JDialog {
	/**
	 * �ļ���ǩ
	 */
	private JLabel fileLabel = null;
	/**
	 * �˿ڱ�ǩ
	 */
	private JLabel  portLabel = null;
	/**
	 *�ļ��ı���
	 */
	private JTextField fileField = null;
	/**
	 *�˿��ı���
	 */
	private JTextField  portField = null;
	/**
	 * �������Ӱ�ť
	 */
	private JButton testConnectButton = null;
	/**
	 * ���Զ˿ڰ�ť
	 */
	private JButton testPortButton = null;
	/**
	 * ��������
	 */
	private JButton saveButton = null;
	/**
	 * �����������ť
	 */
	private JButton loginButton = null;
	/**
	 * ����ļ���ť
	 */
	private JButton broswerButton = null;

	/**
	 * �������ڷ���
	 */
	public ServerLogin() {
		this.setTitle("��������������");
		this.setSize(400, 250);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setLayout(new FlowLayout());
		this.initCompoment();
		this.readConfig();
		this.setVisible(true);

		// ��Ӱ�Ŧ�ļ���
		ButtonListener buttonListener = new ButtonListener();
		this.testConnectButton.addActionListener(buttonListener);
		this.testPortButton.addActionListener(buttonListener);
		this.loginButton.addActionListener(buttonListener);
	}

	/**
	 *������ť�ı���������
	 */
	private void initCompoment() {

		this.fileLabel = new JLabel("��  ��  ");
		this.fileField = new JTextField();
		this.broswerButton = new JButton("���...");

		this.portLabel = new JLabel("�˿ں�  ");
		this.portField = new JTextField(20);
		this.testConnectButton = new JButton("��������");
		this.testPortButton = new JButton("���Զ˿�");
		//this.saveButton = new JButton("��������");
		this.loginButton = new JButton("���������");

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

		// ��ӵ��ܵ�Box��
		box.add(Box.createVerticalStrut(20));
		box.add(fileBox);

		box.add(Box.createVerticalStrut(20));
		box.add(portBox);
		box.add(Box.createVerticalStrut(20));
		box.add(buttonBox);
		this.add(box);
	}

	/**
	 * ��ȡ�����ļ��Ͷ˿ڷ���
	 */
	private void readConfig() {
		// ��ȡ�����ļ���ֵ
		String socketPort = CommProperties.getValue("port",CommFileClass.post_file_name);
		String UserFile = String.valueOf(CommFileClass.file_name);
		this.portField.setText(socketPort);
		this.fileField.setText( UserFile);

	}

	/**�������������ü���
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
					JOptionPane.showMessageDialog(null, "�˿ڲ��Գɹ����˿ڿ���");
				} catch (NumberFormatException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "�˿ڲ���ʧ��", "������Ϣ",
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
		// ����Ƥ��
		try {
			UIManager.setLookAndFeel(ServerLogin.NILO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ServerLogin serverLogin = new ServerLogin();
	}
}

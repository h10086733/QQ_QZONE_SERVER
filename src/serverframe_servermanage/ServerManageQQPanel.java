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
import serverframe_listener.ServerMQQ_Listener;
import serverframe_listener.ServerM_Listener;
import util.LogFile;

/**
 * ѡ�����_����������
 * 
 * @author lisu
 * 
 */
public class ServerManageQQPanel extends JPanel {
	/**
	 * ͨѶ��Ϣ��ʾ
	 */
	public JTextArea taPromptInfo;
	
	/**
	 * ���������
	 */
	public JTextArea taMessageInfo;
	

	/**
	 * ���͹���
	 */
	private JTextArea taSendBulletin;

	/**
	 * ���� ��ť
	 */
	private JButton btnSend;

	/**
	 * ͼƬ Label
	 */
	private JLabel labImg;

	/**
	 * ��ʼ����ť
	 */
	private JButton btnStarServer;

	/**
	 * ���ް�ť
	 */
	private JButton btnStopServer;

	/**
	 * ��ҳ���ް�ť
	 */
	private JButton btnOffLine;
	
	/**
	 * ����
	 */
	private JButton btnMessage;
	
	
	/**
	 * ��Ӻ���
	 */
	private JButton btnAddFir;


	/**
	 * ������������
	 */
	private ServerFrame serverFrame;

	/**
	 * ���������QQ���߼���
	 */
	private ServerMQQ_Listener serverMQQListener;

	public ServerManageQQPanel(ServerFrame serverFrame) {
		this.setLayout(new BorderLayout());
		//inittable_up();
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
	 * @return  ���ط����������²��ֵ��󲿷�(ͨѶ��Ϣ��ʾ�����淢��)������
	 */
	private JPanel inittable_down_Left() {
		JPanel jPanel = new JPanel(new BorderLayout());
		JPanel jPanel_up = new JPanel(new BorderLayout());
		JPanel jPanel_down = new JPanel(new BorderLayout());
		JPanel jPanel_up_1 = new JPanel(new BorderLayout());
		JPanel jPanel_up_2 = new JPanel(new BorderLayout());
		JPanel jPanel_down_1 = new JPanel(new BorderLayout());

		Border outBorde_upr = BorderFactory.createEmptyBorder(1, 1, 1, 1);
		Border inBorder_up = BorderFactory.createTitledBorder("��������޵�QQ�Ŷ���Զ��Ÿ���(,)");
		Border border_up = BorderFactory.createCompoundBorder(outBorde_upr,
				inBorder_up);
		
		Border outBorde_upr_2 = BorderFactory.createEmptyBorder(1, 1, 1, 1);
		Border inBorder_up_2 = BorderFactory.createTitledBorder("��������������");
		Border border_up_2 = BorderFactory.createCompoundBorder(outBorde_upr_2,
				inBorder_up_2);
		
		jPanel_up_2.setBorder(border_up_2);
		
		jPanel_up.setBorder(border_up);
		taPromptInfo = new JTextArea(3, 13);
		taMessageInfo = new JTextArea(3, 13);
		JScrollPane jspProInfo_up = new JScrollPane(taPromptInfo);
		JScrollPane jspProInfo_up_2 = new JScrollPane(taMessageInfo);
		Border outBorder_down = BorderFactory.createEmptyBorder(1, 1, 1, 1);
		Border inBorder_down = BorderFactory.createTitledBorder("��־���");
		Border border_down = BorderFactory.createCompoundBorder(outBorder_down,
				inBorder_down);
		jPanel_down.setBorder(border_down);

		taSendBulletin = new JTextArea(12, 13);
		JScrollPane jspProInfo_down = new JScrollPane(taSendBulletin);
		taSendBulletin.setEditable(false);
		jPanel_down.add(jspProInfo_down);
		jPanel_up.add(jspProInfo_up);
		jPanel_up_1.add(jPanel_up);
		jPanel_up_2.add(jspProInfo_up_2);
		jPanel_down_1.add(jPanel_down);
		jPanel.add(jPanel_up_1, BorderLayout.NORTH);
		jPanel.add(jPanel_up_2, BorderLayout.CENTER);
		jPanel.add(jPanel_down_1, BorderLayout.SOUTH);

		jPanel_up_1.setBorder(BorderFactory.createLoweredBevelBorder());
		jPanel_down_1.setBorder(BorderFactory.createLoweredBevelBorder());
		jPanel.setBorder(BorderFactory.createLoweredBevelBorder());

		return jPanel;
	}

	/**
	 * @return ���ط����������²��ֵ��Ҳ���(����������)������
	 */
	private JPanel inittable_down_Rigth() {
		JPanel jPanel = new JPanel(new BorderLayout());
		JPanel jPanel_1 = new JPanel(new BorderLayout());

		Border outBorder_down = BorderFactory.createEmptyBorder(1, 1, 1, 1);
		Border inBorder_down = BorderFactory.createTitledBorder("�¼�����");
		Border border_down = BorderFactory.createCompoundBorder(outBorder_down,
				inBorder_down);
		jPanel.setBorder(border_down);// ���

		btnStarServer = new JButton("qq��ʼ��");
		btnStopServer = new JButton("qq����");
		btnStopServer.setEnabled(false);
		btnOffLine = new JButton("qq��ҳ����");
		btnOffLine.setEnabled(false);
		btnMessage = new JButton("qq����");
		btnMessage.setEnabled(false);
		btnAddFir = new JButton("qq��Ӻ���");
		btnAddFir.setEnabled(false);
		Box boxChild = Box.createVerticalBox();
		boxChild.add(Box.createVerticalGlue());
		boxChild.add(btnStarServer);
		boxChild.add(Box.createVerticalGlue());
		boxChild.add(btnStopServer);
		boxChild.add(Box.createVerticalGlue());
		boxChild.add(btnOffLine);
		boxChild.add(Box.createVerticalGlue());
		boxChild.add(btnMessage);
		boxChild.add(Box.createVerticalGlue());
		boxChild.add(btnAddFir);
		boxChild.add(Box.createVerticalGlue());

		Box boxAll = Box.createHorizontalBox();
		boxAll.add(Box.createHorizontalStrut(20));
		boxAll.add(boxChild);
		boxAll.add(Box.createHorizontalStrut(10));
		
		
		jPanel.add(boxAll);// ���
		jPanel_1.add(jPanel);
		jPanel_1.setBorder(BorderFactory.createLoweredBevelBorder());
		return jPanel_1;
	}

	/**
	 * ע��ѡ�����_�����������еİ�ť����
	 */
	public void registerListener() {
		serverMQQListener = new ServerMQQ_Listener(this);
		btnStarServer.addActionListener(serverMQQListener);
		btnStopServer.addActionListener(serverMQQListener);
		btnOffLine.addActionListener(serverMQQListener);
		btnMessage.addActionListener(serverMQQListener);
		btnAddFir.addActionListener(serverMQQListener);
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

	public ServerFrame getServerFrame() {
		return serverFrame;
	}

	public void setServerFrame(ServerFrame serverFrame) {
		this.serverFrame = serverFrame;
	}

	public ServerMQQ_Listener getServerMQQListener() {
		return serverMQQListener;
	}

	public void setServerMQQListener(ServerMQQ_Listener serverMQQListener) {
		this.serverMQQListener = serverMQQListener;
	}

	public JButton getBtnMessage() {
		return btnMessage;
	}

	public void setBtnMessage(JButton btnMessage) {
		this.btnMessage = btnMessage;
	}

	public JButton getBtnAddFir() {
		return btnAddFir;
	}

	public void setBtnAddFir(JButton btnAddFir) {
		this.btnAddFir = btnAddFir;
	}


}

package serverframe_logmanage;

import java.awt.BorderLayout;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

import server_time.DateField;
import serverframe_listener.LogM_SearchListener;

/**ѡ�����_��־�������
 * @author lisu
 *
 */
public class LogManagePanel extends JPanel {
	
	/**
	 *���ڰ� 
	 */
	private DateField dateField = null;
	/**
	 * �ؼ���  (JTextField)
	 */
	private JTextField jthinge;

	public DateField getDateField() {
		return dateField;
	}
	public void setDateField(DateField dateField) {
		this.dateField = dateField;
	}
	/**
	 * ��ѯ��ť
	 */
	private JButton btnSearch;

	/**
	 * ��ʷ��־   (JTextArea) 
	 */
	private JTextArea taHistoryLog;

	public LogManagePanel() {
		this.setLayout(new BorderLayout());
		init_up();
		init_down();
	}
	/**
	 *ѡ�����_��־��������й�������ϲ��� ����
	 */
	public void init_up() {
		/* ����ѡ����� */
		JPanel panelDateChoose = new JPanel();
		btnSearch=new JButton("��ѯ");
		jthinge=new JTextField(5);
		this.dateField = new DateField(new Date());
		this.dateField.setDateTextWidth(300);
		JLabel jlhinge = new JLabel("�ؼ���:");
		
		Box boxdate=Box.createHorizontalBox();
		boxdate.add(Box.createHorizontalStrut(30));
		boxdate.add(dateField);
		boxdate.add(Box.createHorizontalStrut(10));
		boxdate.add(jlhinge);
		boxdate.add(Box.createHorizontalStrut(5));
		boxdate.add(jthinge);
		boxdate.add(Box.createHorizontalStrut(10));
		boxdate.add(btnSearch);
		boxdate.add(Box.createHorizontalStrut(20));
		
		Border outBorder_up = BorderFactory.createEmptyBorder(1, 1, 1, 1);
		Border inBorder_up = BorderFactory.createTitledBorder("��־����ѡ��");
		Border border_up = BorderFactory.createCompoundBorder(outBorder_up,
				inBorder_up);
		Box box=Box.createVerticalBox();
		box.add(Box.createVerticalStrut(5));
		box.add(boxdate);
		box.add(Box.createVerticalStrut(10));
		box.setBorder(border_up);
		LogM_SearchListener logMSearchListener =new LogM_SearchListener(this);
		btnSearch.addActionListener(logMSearchListener);
		this.add(box,BorderLayout.NORTH);
	
	}
	public JTextField getJthinge() {
		return jthinge;
	}
	public void setJthinge(JTextField jthinge) {
		this.jthinge = jthinge;
	}
	public JButton getBtnSearch() {
		return btnSearch;
	}
	public void setBtnSearch(JButton btnSearch) {
		this.btnSearch = btnSearch;
	}
	public JTextArea getTaHistoryLog() {
		return taHistoryLog;
	}
	public void setTaHistoryLog(JTextArea taHistoryLog) {
		this.taHistoryLog = taHistoryLog;
	}
	/**
	 * ѡ�����_��־��������й�������²���(��ʷ��־���)����
	 */
	private void init_down() {
		/* ��ʷ��־��� */
		JPanel panelHistorLog = new JPanel(new BorderLayout());
		JPanel panelHistorLog_1 = new JPanel(new BorderLayout());
		taHistoryLog=new JTextArea();
		taHistoryLog.setEditable(false);
		Border outBorder_down = BorderFactory.createEmptyBorder(1, 1, 1, 1);
		Border inBorder_down = BorderFactory.createTitledBorder("��ʷ��־");
		Border border_down = BorderFactory.createCompoundBorder(outBorder_down,
				inBorder_down);
		JScrollPane jspProInfo_down = new JScrollPane(taHistoryLog);
		panelHistorLog_1.add(jspProInfo_down);
		panelHistorLog_1.setBorder(border_down);
		panelHistorLog.add(panelHistorLog_1 );
		this.add(panelHistorLog,BorderLayout.CENTER);
		
	}

}

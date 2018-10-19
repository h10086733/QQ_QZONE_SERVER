package serverframe_listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.swing.JOptionPane;

import serverframe_logmanage.LogManagePanel;

/**日志管理查询监听
 * @author Administrator
 *
 */
public class LogM_SearchListener implements ActionListener {
	
	/**
	 *日志管理面板 
	 */
	private LogManagePanel logManagePanel;

	public LogM_SearchListener(LogManagePanel logManagePanel) {

		this.logManagePanel = logManagePanel;
	}

	public void actionPerformed(ActionEvent e) {
		logManagePanel.getTaHistoryLog().setText("");
		boolean found=false;
		CharSequence c ;
		c = logManagePanel.getJthinge().getText().subSequence(0, logManagePanel.getJthinge()
				.getText().length());
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String strDate = dateFormat.format(logManagePanel.getDateField().getDate());
		try {
			BufferedReader bufferedReader = new BufferedReader (new FileReader(".\\log\\"+strDate+".log"));
			
			String line=null;
			while((line=bufferedReader.readLine())!=null){
				if(line.contains(c)){
					found=true;
				logManagePanel.getTaHistoryLog().append(line);
				logManagePanel.getTaHistoryLog().append("\n");
				}	
			}
			if(found==false){
				JOptionPane.showMessageDialog(null, "在"+strDate+"日志里没有找到关键字"+c);
			}
		} catch (FileNotFoundException e1) {
			logManagePanel.getTaHistoryLog().setText("没有当前日志");
			//e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}

package serverframe_listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JTextArea;

import org.apache.commons.lang3.StringUtils;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;

import ly.entity.QQ;
import server_util.CommDateandTime;
import serverframe_servermanage.ServerManageQQPanel;
import serverframe_thread.Server_WaitClient_Thread;

/**
 * 
 * @author lisu
 * 
 */
public class ServerMQQ_Listener implements ActionListener {
	
	private static List<QQ> qqInit = new ArrayList<QQ>();
	

	/**
	 */
	private ServerManageQQPanel serverManageQQPanel;
	/**
	 */
	private ServerSocket serverSocket = null;
	/**
	 */
	private Server_WaitClient_Thread serverWaitClientThread;
	/**
	 * 
	 */
	private boolean isStart = false;

	public ServerMQQ_Listener(ServerManageQQPanel serverManageQQPanel) {
		this.serverManageQQPanel = serverManageQQPanel;
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(serverManageQQPanel.getBtnStarServer())) {
			String taPromptInfo = serverManageQQPanel.taPromptInfo.getText();
			Map<String, String> hm=new HashMap<String, String>();
			
			Reader reader;
			try {
				reader = new FileReader(".\\UserQQ.txt");
				BufferedReader bufferedReader = new BufferedReader(reader);
				String str = null;
				try {
					while ((str = bufferedReader.readLine()) != null) {
						String[] string = str.split(",");
						hm.put(string[0], string[1]);
					}

				} catch (IOException bb) {
					bb.printStackTrace();
				}

			} catch (FileNotFoundException cc) {
				cc.printStackTrace();
			}
			JTextArea textArea = serverManageQQPanel.getTaSendBulletin();
			try {
				for (Entry<String,String> entry:hm.entrySet()) {
					QQ qq=new QQ(entry.getKey(),entry.getValue());
					int login = qq.login();
					if(login!=0){
						textArea.append(CommDateandTime.getDateAndTime() + " "+entry.getKey()+"登录失败" + "\n");
						continue;
					}
					qqInit.add(qq);
					textArea.append(CommDateandTime.getDateAndTime() + " "+entry.getKey()+"登录成功" + "\n");
					// 光标定位在最后
					textArea.setCaretPosition(textArea.getDocument().getLength());
					
					
					
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
			
			serverManageQQPanel.getBtnStarServer().setEnabled(true);
			serverManageQQPanel.getBtnStopServer().setEnabled(true);
			serverManageQQPanel.getBtnOffLine().setEnabled(true);
			serverManageQQPanel.getBtnMessage().setEnabled(true);
			serverManageQQPanel.getBtnAddFir().setEnabled(true);
			//
			System.out.println(taPromptInfo);
		}
		else if (e.getSource().equals(serverManageQQPanel.getBtnStopServer())) {
			
			String taPromptInfo = serverManageQQPanel.taPromptInfo.getText();
			
			JTextArea textArea = serverManageQQPanel.getTaSendBulletin();
			
			for (QQ qq : qqInit) {
				try {
					String a = qq.parseQQ(taPromptInfo);
					textArea.append(CommDateandTime.getDateAndTime() + " "+qq.getUin()+"用户为"+taPromptInfo+","+a+ "\n");
				} catch (Exception e1) {
					textArea.append(CommDateandTime.getDateAndTime() + " "+qq.getUin()+"用户为"+taPromptInfo+"点赞失败" + "\n");
				}
			}
		}
		else if (e.getSource().equals(serverManageQQPanel.getBtnOffLine())) {
			
			String taPromptInfo = serverManageQQPanel.taPromptInfo.getText();
			
			JTextArea textArea = serverManageQQPanel.getTaSendBulletin();
			
			for (QQ qq : qqInit) {
				try {
					String a = qq.indexParseQQ(taPromptInfo);
					textArea.append(CommDateandTime.getDateAndTime() + " "+qq.getUin()+"用户为"+taPromptInfo+","+a+ "\n");
				} catch (Exception e1) {
					textArea.append(CommDateandTime.getDateAndTime() + " "+qq.getUin()+"用户为"+taPromptInfo+"首页点赞失败" + "\n");
				}
			}
		}
		else if (e.getSource().equals(serverManageQQPanel.getBtnMessage())) {
			
			String taPromptInfo = serverManageQQPanel.taPromptInfo.getText();
			String taMessageInfo = serverManageQQPanel.taMessageInfo.getText();
			JTextArea textArea = serverManageQQPanel.getTaSendBulletin();
			
			for (QQ qq : qqInit) {
				try {
					String a = qq.writeQQ(taPromptInfo,taMessageInfo);
					textArea.append(CommDateandTime.getDateAndTime() + " "+qq.getUin()+"用户为"+taPromptInfo+","+a+ "\n");
				} catch (Exception e1) {
					textArea.append(CommDateandTime.getDateAndTime() + " "+qq.getUin()+"用户为"+taPromptInfo+"留言失败" + "\n");
				}
			}
		}
		else if (e.getSource().equals(serverManageQQPanel.getBtnAddFir())) {
			
			String taPromptInfo = serverManageQQPanel.taPromptInfo.getText();
			JTextArea textArea = serverManageQQPanel.getTaSendBulletin();
			if(StringUtils.isBlank(taPromptInfo)){
				textArea.append("请输入QQ号!!!\n");
				return ;
			}
			for (QQ qq : qqInit) {
				try {
					String[] q = taPromptInfo.split(",|;");
					for (String tpi : q) {
						String a = qq.addFread(tpi);
						textArea.append(CommDateandTime.getDateAndTime() + " "+qq.getUin()+"用户为"+taPromptInfo+","+a+ "\n");
					}
				} catch (Exception e1) {
					textArea.append(CommDateandTime.getDateAndTime() + " "+qq.getUin()+"用户为"+taPromptInfo+"自动添加好友失败" + "\n");
				}
			}
		}
	}

}


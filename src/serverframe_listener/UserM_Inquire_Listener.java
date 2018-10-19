package serverframe_listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import serverframe_usermanage.UserManagePanel;

/**�û���������ѯ��ť����
 * @author Administrator
 *
 */
public class UserM_Inquire_Listener implements ActionListener {
	
	/**
	 *�û�������� 
	 */
	private UserManagePanel managePanel;

	public UserM_Inquire_Listener(UserManagePanel managePanel) {
		this.managePanel = managePanel;
	}

	public void actionPerformed(ActionEvent e) {
		JTable table = managePanel.getTable();
		Vector columnVector = managePanel.getColumnVector();
		columnVector = new Vector();
		columnVector.add("�˺�");
		columnVector.add("�û�����");
		columnVector.add("�û�����");
		columnVector.add("�Ա�");
		columnVector.add("����");
		columnVector.add("��ַ");
		columnVector.add("�Ƿ�����");
		columnVector.add("ע��ʱ��");

		Vector rowVector = new Vector();
		Reader reader;
		try {
			reader = new FileReader(".\\Userinfo.txt");
			BufferedReader bufferedReader = new BufferedReader(reader);
			String str = null;
			CharSequence c ;
			try {
				boolean isno=false;
				while ((str = bufferedReader.readLine()) != null) {
					Vector tempVector_2 = new Vector();
					String[] string = str.split(",");
					String id = string[0];
					String name = string[1];
					c = managePanel.getField_Name().getText().subSequence(0, managePanel.getField_Name()
							.getText().length());
					if(id.equals(managePanel.getField_Id().getText())&&managePanel.getField_Name().getText().equals("")){
						tempVector_2.add(string[0]);
						tempVector_2.add(string[1]);
						tempVector_2.add(string[2]);
						tempVector_2.add(string[3]);
						tempVector_2.add(string[4]);
						tempVector_2.add(string[5]);
						tempVector_2.add(string[6]);
						tempVector_2.add(string[7]);
						rowVector.add(tempVector_2);
						isno=false;
						break;
					}else 
						if(managePanel.getField_Name().getText().equals(name)&&managePanel.getField_Id().getText().equals("")){
						tempVector_2.add(string[0]);
						tempVector_2.add(string[1]);
						tempVector_2.add(string[2]);
						tempVector_2.add(string[3]);
						tempVector_2.add(string[4]);
						tempVector_2.add(string[5]);
						tempVector_2.add(string[6]);
						tempVector_2.add(string[7]);
						rowVector.add(tempVector_2);
						isno=false;
						break;
					
					}else if(name.equals(managePanel.getField_Name().getText())&&id.equals(managePanel.getField_Id().getText())){
						tempVector_2.add(string[0]);
						tempVector_2.add(string[1]);
						tempVector_2.add(string[2]);
						tempVector_2.add(string[3]);
						tempVector_2.add(string[4]);
						tempVector_2.add(string[5]);
						tempVector_2.add(string[6]);
						tempVector_2.add(string[7]);
						rowVector.add(tempVector_2);
						isno=false;
						break;
					}
					else if(managePanel.getField_Id().getText().equals("")&&managePanel.getField_Name().getText().equals("")){
						tempVector_2.add(string[0]);
						tempVector_2.add(string[1]);
						tempVector_2.add(string[2]);
						tempVector_2.add(string[3]);
						tempVector_2.add(string[4]);
						tempVector_2.add(string[5]);
						tempVector_2.add(string[6]);
						tempVector_2.add(string[7]);
						rowVector.add(tempVector_2);
						isno=false;
					}else{
						isno=true;
					}
				}if(isno){
				JOptionPane.showMessageDialog(null, "û���ҵ�Ҫ�ҵ��û�");
				}

			} catch (IOException e2) {
				e2.printStackTrace();
			}

		} catch (FileNotFoundException e2) {
			e2.printStackTrace();
		}

		TableModel dataModel = new DefaultTableModel(rowVector, columnVector);
		table.setModel(dataModel);
	}

}

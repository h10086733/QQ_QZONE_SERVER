package ly.iframe;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ly.entity.QQ;

public class CharFrm extends JFrame{
	static JFrame charFrm;
	public CharFrm() {
		createAndShowGUI(new QQ());
	}
	public CharFrm(QQ qq) {
		createAndShowGUI(qq);
	}
    /**{
     * 创建并显示GUI。出于线程安全的考虑，
     * 这个方法在事件调用线程中调用。
     */
    private static void createAndShowGUI(QQ qq) {
        // 确保一个漂亮的外观风格
        JFrame.setDefaultLookAndFeelDecorated(true);

        // 创建及设置窗口
        charFrm =new JFrame("qq:"+qq.getUin());
        charFrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        charFrm.setSize(350, 200);
        /* 创建面板，这个类似于 HTML 的 div 标签
         * 我们可以创建多个面板并在 JFrame 中指定位置
         * 面板中我们可以添加文本字段，按钮及其他组件。
         */
        JPanel panel = new JPanel();    
        // 添加面板
        charFrm.add(panel);
        /* 
         * 调用用户定义的方法并添加组件到面板
         */
        placeComponents(panel);
        // 设置界面可见
        charFrm.setVisible(true);
    }
    private static void placeComponents(JPanel panel) {

    	 System.out.println("commiing char ...");
        /* 布局部分我们这边不多做介绍
         * 这边设置布局为 null
         */
        panel.setLayout(null);
        
        
        
        
        
        
    }
    
}

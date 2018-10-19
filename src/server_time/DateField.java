package server_time;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public final class DateField extends JPanel {

	private static final long serialVersionUID = 1L;

	public final JTextField dateText = new JTextField(20);

	private final JButton dropdownButton = new JButton();

	private DatePicker dp;

	private JDialog dlg;

	Point origin = new Point();

	final class Listener extends ComponentAdapter {
		// 日期选择面板消失时调用的方法
		public void componentHidden(final ComponentEvent evt) {
			final Date dt = ((DatePicker) evt.getSource()).getDate();
			if (null != dt)
				dateText.setText(dateToString(dt));
			dlg.dispose();
		}

	}

	public DateField() {
		super();
		init();
	}

	public DateField(final Date initialDate) {
		super();
		init();
		dateText.setText(dateToString(initialDate));
	}

	public Date getDate() {
		return stringToDate(dateText.getText());
	}

	public void setDate(Date date) {
		String v = dateToString(date);
		if (v == null) {
			v = "";
		}
		dateText.setText(v);
	}

	public void setDateTextWidth(int width) {
		this.dateText.setPreferredSize(new Dimension(width, this.dateText
				.getHeight()));
		SwingUtilities.updateComponentTreeUI(this);
	}

	private void init() {
		setLayout(new BorderLayout());

		dateText.setText("");

		dateText.setEditable(false);
		dateText.setBackground(new Color(255, 255, 255));
		add(dateText, BorderLayout.CENTER);

		URL url = DateField.class.getResource("ns-expand.gif");// 修改路径,取你本地的图片路径.
		ImageIcon icon = new ImageIcon(url);
		dropdownButton.setIcon(icon);
		dropdownButton.setMargin(new Insets(2, 2, 2, 2));
		dropdownButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent evt) {
				onButtonClick(evt);
			}
		});
		add(dropdownButton, BorderLayout.EAST);
	}

	private void onButtonClick(final java.awt.event.ActionEvent evt) {
		if ("".equals(dateText.getText()))
			dp = new DatePicker();
		else
			dp = new DatePicker(stringToDate(dateText.getText()));
		dp.addComponentListener(new Listener());

		final Point p = dateText.getLocationOnScreen();
		p.setLocation(p.getX(), p.getY() - 1 + dateText.getSize().getHeight());

		dlg = new JDialog(new JFrame(), true);

		dlg.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				origin.x = e.getX();
				origin.y = e.getY();
			}
		});
		dlg.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				Point p = dlg.getLocation();
				dlg.setLocation(p.x + e.getX() - origin.x, p.y + e.getY()
						- origin.y);
			}
		});

		dlg.setLocation(p);
		dlg.setResizable(false);
		dlg.setUndecorated(true);
		dlg.getContentPane().add(dp);
		dlg.pack();
		dlg.setVisible(true);
	}

	private static String dateToString(final Date dt) {
		if (null != dt) {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			return dateFormat.format(dt);
		}
		// return DateFormat.getDateInstance(DateFormat.LONG).format(dt);
		return null;
	}

	private static Date stringToDate(final String s) {
		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			return dateFormat.parse(s);
			// return DateFormat.getDateInstance(DateFormat.LONG).parse(s);
		} catch (ParseException e) {
			return null;
		}
	}

	public static void main(String[] args) {
		JDialog dlg = new JDialog(new JFrame(), true);
		dlg.setLayout(new FlowLayout());
		final DateField df = new DateField();
		JButton button = new JButton("获取");

		dlg.getContentPane().add(df);
		dlg.getContentPane().add(button);
		button.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				System.out.println(df.dateText.getText());

			}
		});
		dlg.pack();
		dlg.setVisible(true);
		System.out.println(df.getDate());
		System.exit(0);
	}
}
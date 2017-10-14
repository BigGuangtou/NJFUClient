/**
 * 
 */
/**
 * 主窗口
 * @author WangBing
 *
 */
package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import controller.DBConnecter;
import controller.DBController;
import controller.FileSelectControl;
import controller.ImportControl;
import controller.PayCardControl;
import controller.UpdateCardControl;

/**
 * 界面布局的类
 */
public class MainFrame extends JFrame implements ActionListener {

	private static final long serialVersionUID = 721529053561467600L;
	/**
	 * 宽高
	 */
	public static int width = 500;
	public static int height = 300;
	/**
	 * 选择文件按钮
	 */
	JButton btSelectFile = new JButton("选择文件");
	/**
	 * 导入按钮
	 */
	JButton btImport = new JButton("导入");
	/**
	 * 文件路径文本框
	 */
	JTextField tfFileSource = new JTextField(10);
	/**
	 * 文件选择器
	 */
	JFileChooser jfc = new JFileChooser();
	/**
	 * 刷卡用的文本框
	 */
	JTextField payCard = new JTextField(10);
	/**
	 * 页面提示文本
	 */
	JLabel suggession = new JLabel("欢迎使用");
	/**
	 * 学号文本框
	 */
	JTextField StudentNumber = new JTextField(10);
	/**
	 * 卡号文本框
	 */
	JTextField CardNumber = new JTextField(10);
	/**
	 * 卡号更新按钮
	 */
	JButton update = new JButton("更新");
	/**
	 * 连接或者打开d:\test.db文件
	 */
	DBController dbc = new DBController(new DBConnecter().getConnection("test"));

	/**
	 * 初始化组件 添加监听事件
	 */
	public void initComponents() {

		JPanel panel = (JPanel) getContentPane();// 初始化内容界面，获得内容版面
		jfc.setCurrentDirectory(new File("d://"));
		JTabbedPane jtp = new JTabbedPane(JTabbedPane.TOP);// 初始化选项卡界面

		panel.add(jtp);// 在主内容界面添加选项卡界面

		JPanel importPanel = new JPanel(); // 初始化导入面板

		importPanel.add(btSelectFile);// 添加button按钮
		importPanel.add(tfFileSource);
		importPanel.add(btImport);
		jtp.add("导入", importPanel);// 在选项卡界面添加导入面板
		btSelectFile.addActionListener(this);
		tfFileSource.addActionListener(this);
		btImport.addActionListener(this);

		JPanel useCardPanel = new JPanel();// 初始化刷卡面板
		useCardPanel.add(payCard);
		useCardPanel.add(suggession);
		jtp.add("刷卡", useCardPanel);// 在选项卡界面添加刷卡面板
		payCard.addActionListener(this);

		JPanel changeCardPanel = new JPanel();// 初始化刷卡面板
		changeCardPanel.add(new JLabel("学号："));
		changeCardPanel.add(StudentNumber);
		changeCardPanel.add(new JLabel("转换     "));
		changeCardPanel.add(new JLabel("卡号："));
		changeCardPanel.add(CardNumber);
		changeCardPanel.add(update);
		jtp.add("换卡", changeCardPanel);// 在选项卡界面添加换卡面板
		update.addActionListener(this);
	}

	/**
	 * 监听事件
	 */
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		/*
		 * 选择文件的按钮
		 * 
		 */
		if (e.getSource().equals(btSelectFile)) {
			new FileSelectControl();
			FileSelectControl.conduct(jfc, tfFileSource);
		}
		/*
		 * 
		 * 导入按钮的监听事件
		 * 
		 */
		else if (e.getSource().equals(btImport)) {
			new ImportControl();
			ImportControl.conduct(tfFileSource, dbc);
		}
		/*
		 * 刷卡回车的监听事件
		 * 
		 */
		else if (e.getSource().equals(payCard)) {
			new PayCardControl();
			PayCardControl.conduct(payCard, suggession, dbc);
		}
		/*
		 * 更新按钮的监听事件
		 * 
		 */
		else if (e.getSource().equals(update)) {
			new UpdateCardControl();
			UpdateCardControl.conduct(StudentNumber, CardNumber, dbc);
		}
	}

	/**
	 * 初始化界面的函数
	 */
	public MainFrame() {
		setTitle("刷卡系统");// 设软件标题
		setSize(width, height);// 设软件宽和高
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);// 设软件宽和高
		setLocationRelativeTo(null);// 设置窗口显示位置
		initComponents();// 执行选项卡面板函数
	}

}

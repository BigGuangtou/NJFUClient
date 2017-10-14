package controller;

import javax.swing.JTextField;
/**
 * 更新卡号控制器
 * 
 * @author WangBing
 *
 */
public class UpdateCardControl {
	/**
	 * 
	 * @param jtx_Student 学号文本框
	 * @param jtx_Card 卡号文本框
	 * @param dbc 数据库连接
	 */
	public static void conduct(JTextField jtx_Student, JTextField jtx_Card, DBController dbc) {
		String Studentnumber = jtx_Student.getText();// 获取学生的学号
		String Cardnumber = jtx_Card.getText();// 获取新的卡号
		dbc.updateCardID(Studentnumber, Cardnumber);// 更新卡号
	}
}

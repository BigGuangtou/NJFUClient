/**
 * 
 */
package controller;

import java.util.ArrayList;
import java.util.Date;

import javax.swing.JTextField;

import model.Student;
import model.StudentRecord;

/**
 * 文件导入控制器
 * @author WangBing
 *
 */
public class ImportControl {
	/**
	 * 导入按钮的监听事件
	 * @param jtx 文件路径文本框
	 * @param dbc 数据库连接
	 */
	public static void conduct(JTextField jtx, DBController dbc) {
		String filePath = jtx.getText();// 拿到文件路径
		ArrayList<Student> studentList = ExcelImporter.importExcel(filePath);// 获取Excel文件里的数据
		for (Student student : studentList) {// 循环读取数据
			StudentRecord studentRecord = new StudentRecord(student.getID(), 0, new Date());// 用每一条数据初始化学生跑操数据
			dbc.insertStudentRecord(studentRecord);// 往StudentRecord表里面插入学生跑操数据
			dbc.insertStudent(student);// 往Student表里插入学生信息数据
		}
	}
}

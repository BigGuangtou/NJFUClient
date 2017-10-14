/**
 * 
 */
package controller;

import java.io.FileInputStream;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import model.Student;

/**
 * 表格读取器
 * 
 * @author WangJiacheng
 *
 */
public class ExcelImporter {

	/**
	 * 
	 */
	public ExcelImporter() {
		// TODO 自动生成的构造函数存根
	}

	/**
	 * 参数是带读取的xls文件完整路径
	 * 
	 * @param filename
	 *            文件名
	 * @return Student的ArrayList
	 */
	public static ArrayList<Student> importExcel(String filename) {
		ArrayList<Student> studentList = new ArrayList<Student>();
		Student newStudent = null;
		String ID = null;
		String name = null;
		String cardID = null;
		int i = 0;
		try {
			// 创建对Excel工作簿文件的引用
			XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(filename));
			// 创建对工作表的引用。在Excel文档中，第一张工作表的缺省索引是0
			XSSFSheet sheet = workbook.getSheetAt(0);
			// 输出单元内容
			for (Row row : sheet) {
				for (Cell cell : row) {
					// 将每个单元格数据格式设置为String
					cell.setCellType(CellType.STRING);
					// 每行三个单元格为一个学生的信息
					if (i == 0) {
						ID = cell.getStringCellValue();
					}
					if (i == 1) {
						name = cell.getStringCellValue();
					}
					if (i == 2) {
						cardID = cell.getStringCellValue();
					}
					i++;
				}
				newStudent = new Student(ID, name, cardID);
				studentList.add(newStudent);
				i = 0;
			}
			workbook.close();
			if (i != 0) {
				// 非正常结束
				return null;
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return studentList;
	}

}

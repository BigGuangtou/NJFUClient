/**
 * 
 */
package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import model.Record;
import model.Student;
import model.StudentRecord;

/**
 * 数据库控制器
 * 
 * @author WangZhiheng
 *
 */
public class DBController {
	/**
	 * 处理失败
	 */
	static final int UPDATE_FAIL = -1;
	/**
	 * 查无此人
	 */
	static final String STUDENT_NOT_EXIST = "查无此人";
	/**
	 * 数据库连接
	 */
	private Connection connection;

	/**
	 * 构造函数
	 * 
	 * @param _connection
	 *            数据库连接
	 */
	public DBController(Connection _connection) {
		setConnection(_connection);
		initDB();
	}

	/**
	 * 建表
	 */
	public void initDB() {
		createTableStudent();
		createTableRecord();
		createTableStudentRecord();
	}

	/**
	 * 建立Student表
	 */
	public void createTableStudent() {
		try {
			Statement stmt = connection.createStatement();
			String sql = "CREATE TABLE IF NOT EXISTS Student (" + "  ID varchar(20) PRIMARY KEY  NOT NULL,"
					+ "Name varchar(20) NOT NULL," + "  CardID varchar(20) NOT NULL);";
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 建立Record表
	 */
	public void createTableRecord() {
		try {
			Statement stmt = connection.createStatement();
			String sql = "CREATE TABLE IF NOT EXISTS Record (" + "  Record_ID INTEGER PRIMARY KEY NOT NULL,"
					+ "  ID varchar(20) NOT NULL," + "  Time datetime NOT NULL ," + "  Synced tinyint(1) NOT NULL) ;";
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 建立StudentRecord表
	 */
	public void createTableStudentRecord() {
		try {
			Statement stmt = connection.createStatement();
			String sql = "CREATE TABLE IF NOT EXISTS StudentRecord (" + "  ID varchar(20)  PRIMARY KEY NOT NULL,"
					+ "  Score int(11) NOT NULL," + "  LastRecord datetime NOT NULL);";
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 添加学生
	 * 
	 * @param student
	 *            学生
	 * @return 是否成功
	 */
	public int insertStudent(Student student) {
		try {
			String sql = "INSERT INTO Student (ID, Name, CardID) VALUES (?,?,?);";
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, student.getID());
			stmt.setString(2, student.getName());
			stmt.setString(3, student.getCardID());
			int res = stmt.executeUpdate();
			stmt.close();
			return res;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return UPDATE_FAIL;
	}

	/**
	 * 插入记录
	 * 
	 * @param record
	 *            记录
	 * @return 是否成功
	 */
	public int insertRecord(Record record) {
		try {
			String sql = "INSERT INTO Record ( ID, Time, Synced) VALUES (?,?,?);";
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, record.getID());
			stmt.setDate(2, new java.sql.Date(record.getTime().getTime()));
			stmt.setBoolean(3, record.isSynced());
			int res = stmt.executeUpdate();
			stmt.close();
			return res;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return UPDATE_FAIL;
	}

	/**
	 * 插入学生记录
	 * 
	 * @param studentRecord
	 *            学生记录
	 * @return 是否成功
	 */
	public int insertStudentRecord(StudentRecord studentRecord) {
		try {
			String sql = "INSERT INTO StudentRecord (ID, Score, LastRecord) VALUES (?,?,?);";
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, studentRecord.getID());
			stmt.setInt(2, studentRecord.getScore());
			stmt.setDate(3, new java.sql.Date(studentRecord.getLastRecord().getTime()));
			int res = stmt.executeUpdate();
			stmt.close();
			return res;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return UPDATE_FAIL;
	}

	/**
	 * 更新学生成绩
	 * 
	 * @param ID
	 *            学号
	 * @return 是否成功
	 */
	public int updateStudentRecord(String ID) {
		try {
			String sql = "UPDATE StudentRecord SET Score=Score+1,LastRecord=? WHERE ID=?;";
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setDate(1, new java.sql.Date(new java.util.Date().getTime()));
			stmt.setString(2, ID);
			int res = stmt.executeUpdate();
			stmt.close();
			return res;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return UPDATE_FAIL;
	}

	/**
	 * 更换卡号
	 * 
	 * @param ID
	 *            学号
	 * @param newCardID
	 *            新卡号
	 * @return 是否成功
	 */
	public int updateCardID(String ID, String newCardID) {
		try {
			String sql = "UPDATE Student SET CardID=? WHERE ID=?;";
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, newCardID);
			stmt.setString(2, ID);
			int res = stmt.executeUpdate();
			stmt.close();
			return res;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return UPDATE_FAIL;
	}

	/**
	 * 根据卡号查询学号
	 * 
	 * @param CardID
	 *            卡号
	 * @return 学号，若不存在返回“查无此人”
	 */
	public String getIDbyCardID(String CardID) {
		try {
			String sql = "SELECT ID FROM Student WHERE CardID=?;";
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, CardID);
			ResultSet res = stmt.executeQuery();
			String ID = STUDENT_NOT_EXIST;
			while (res.next()) {
				ID = res.getString("ID");
			}
			res.close();
			stmt.close();
			return ID;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return STUDENT_NOT_EXIST;
	}

	/**
	 * 是否有记录
	 * 
	 * @param CardID
	 *            卡号
	 * @return 是否有记录
	 */
	public boolean hasRecord(String CardID) {
		try {
			String sql = "SELECT * FROM Student WHERE CardID=?;";
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, CardID);
			ResultSet res = stmt.executeQuery();
			int count = 0;
			while (res.next()) {
				count++;
			}
			return count != 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 获取所有未同步记录
	 * 
	 * @return 未同步记录的HashMap,RecordID是key
	 */
	public HashMap<Integer, Record> getAllUnsyncedRecords() {
		try {
			HashMap<Integer, Record> map = new HashMap<Integer, Record>();
			String sql = "SELECT ID, Time FROM Record WHERE Synced=0;";
			Statement stmt = connection.createStatement();
			ResultSet res = stmt.executeQuery(sql);
			while (res.next()) {
				map.put(Integer.parseInt(res.getString("Record_ID")),
						new Record(res.getString("ID"), res.getDate("Time"), res.getBoolean("Synced")));
			}
			return map;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 标记已同步的记录
	 * 
	 * @param Record_ID
	 *            记录编号
	 * @return 是否成功
	 */
	public int updateRecord(int Record_ID) {
		try {
			String sql = "UPDATE Record SET Synced=1 WHERE Record_ID=？;";
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, Record_ID);
			int res = stmt.executeUpdate();
			stmt.close();
			return res;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return UPDATE_FAIL;
	}

	/**
	 * @return the connection
	 */
	public Connection getConnection() {
		return connection;
	}

	/**
	 * @param connection
	 *            the connection to set
	 */
	public void setConnection(Connection connection) {
		this.connection = connection;
	}
}

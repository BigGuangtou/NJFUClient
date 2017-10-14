/**
 * 
 */
package main;

import java.util.Date;

import controller.DBConnecter;
import controller.DBController;
import controller.RecordSyncer;
import model.Record;
import view.MainFrame;

/**
 * 主类
 * 
 * @author WangZhiheng
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DBController dbct = new DBController((new DBConnecter()).getConnection("test"));
		RecordSyncer rs = new RecordSyncer(dbct, "120.27.122.238", "/NJFUTest/basic/web/index.php");
		String response = rs.Sync(new Record("123", new Date(), false));
		System.out.println(response);
		// MainFrame newMainframe = new MainFrame();
		// newMainframe.setVisible(true);

	}

}

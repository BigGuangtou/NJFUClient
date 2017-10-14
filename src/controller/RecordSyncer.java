/**
 * 
 */
package controller;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import model.Record;

/**
 * 记录同步器
 * 
 * @author WangZhiheng
 *
 */
public class RecordSyncer {
	/**
	 * 同步失败
	 */
	static final String FAIL_TO_SYNC = "FAIL TO SYNC";
	/**
	 * 数据库控制器
	 */
	private DBController dbc;
	/**
	 * 服务器地址
	 */
	private String host;
	/**
	 * 路径
	 */
	private String path;

	/**
	 * 构造函数
	 * 
	 * @param _dbc
	 *            数据库控制器
	 * @param _host
	 *            服务器地址
	 * @param _path
	 *            路径
	 */
	public RecordSyncer(DBController _dbc, String _host, String _path) {
		setDbc(_dbc);
		setHost(_host);
		setPath(_path);
	}

	public void SyncAll() {
		HashMap<Integer, Record> map = dbc.getAllUnsyncedRecords();
		for (Integer key : map.keySet()) {
			if (!Sync(map.get(key)).equals(FAIL_TO_SYNC)) {
				dbc.updateRecord(key);
			} else {
				// TODO
			}
		}
	}

	/**
	 * 同步记录
	 * 
	 * @param record
	 *            要同步的记录
	 * @return 服务器回执
	 */
	public String Sync(Record record) {
		try {
			CloseableHttpClient httpclient = HttpClients.createDefault();
			/*
			 * URI targetURI = new
			 * URIBuilder().setScheme("http").setHost(getHost()).setPath(getPath())
			 * .setParameter("r", "record/record").setParameter("id", record.getID())
			 * .setParameter("time", ((Long)
			 * (record.getTime().getTime())).toString()).build();
			 */
			HttpGet httpget = new HttpGet("http://" + host + path + "?r=record/record" + "&id=" + record.getID()
					+ "&time=" + ((Long) (record.getTime().getTime())).toString());
			HttpResponse response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();
			return EntityUtils.toString(entity); // test
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return FAIL_TO_SYNC;

	}

	/**
	 * @return the host
	 */
	public String getHost() {
		return host;
	}

	/**
	 * @param host
	 *            the host to set
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @param path
	 *            the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * @return the dbc
	 */
	public DBController getDbc() {
		return dbc;
	}

	/**
	 * @param dbc
	 *            the dbc to set
	 */
	public void setDbc(DBController dbc) {
		this.dbc = dbc;
	}

}

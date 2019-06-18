package jp.myouth.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Events {
	private Connection conn = null;
	private Statement stmt = null;

	RDSVariables var = new RDSVariables();
	
	private final String hostname = var.hostname();
	private final String dbname = var.dbname();
	private final String username = var.username();
	private final String password = var.password();
	private final String port = var.port();

	public void open() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://" + hostname + ":" + port + "/" + dbname + "?user="
					+ username + "&password=" + password);
			stmt = conn.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void close() {
		if (conn != null || stmt != null)
			try {
				conn.close();
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}

	public ArrayList<String> managingEvents(String userId) {
		ArrayList<String> data = new ArrayList<String>();
		try {
			String query = "SELECT event.e_name, event.english_e_name FROM event, user_event WHERE user_event.user_id = \""
					+ userId + "\" AND event.event_id = user_event.event_id";
			ResultSet rset = stmt.executeQuery(query);
			while (rset.next()) {
				data.add(rset.getString("english_e_name"));
				data.add(rset.getString("e_name"));
			}
			return data;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * イベントの名前を取得
	 */
	public String eventName(String event) {
		try {
			String data = new String();
			String query = "SELECT e_name FROM event WHERE english_e_name=\"" + event + "\"";
			ResultSet rset = stmt.executeQuery(query);
			while (rset.next())
				data = rset.getString("e_name");
			rset.close();
			return data;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String eventPlace(String event) {
		try {
			String data = new String();
			String query = "SELECT place FROM event WHERE english_e_name=\"" + event + "\"";
			ResultSet rset = stmt.executeQuery(query);
			while (rset.next())
				data = rset.getString("place");
			rset.close();
			return data;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<String> eventTime(String event) {
		try {
			ArrayList<String> data = new ArrayList<String>();
			String query = "SELECT EXTRACT(HOUR FROM time) AS hour, EXTRACT(MINUTE FROM time) as minute FROM event WHERE english_e_name=\""
					+ event + "\"";
			ResultSet rset = stmt.executeQuery(query);
			while (rset.next()) {
				data.add(rset.getString("hour"));
				data.add(rset.getString("minute"));
			}
			rset.close();
			return data;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<String> eventDate(String eventname) {
		ArrayList<String> data = new ArrayList<String>();
		try {
			String query = "SELECT EXTRACT(YEAR FROM date) AS year, EXTRACT(MONTH FROM date) AS month, EXTRACT(DAY FROM date) AS day FROM event WHERE english_e_name=\""
					+ eventname + "\"";
			ResultSet rset = stmt.executeQuery(query);
			if (rset.next()) {
				data.add(rset.getString("year"));
				data.add(rset.getString("month"));
				data.add(rset.getString("day"));
			}
			rset.close();
			return data;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Integer totalEvents() {
		Integer data = 0;
		try {
			String query = "SELECT COUNT(*) AS totalEvents FROM event";
			ResultSet rset = stmt.executeQuery(query);
			
			if(rset.next())
				data = rset.getInt("totalEvents");
			
			return data;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}

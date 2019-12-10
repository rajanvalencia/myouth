package jp.myouth.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Notifications {
	private Connection conn = null;
	private Statement stmt = null;
	
	RDSVariables var = new RDSVariables();
	
	private final String hostname = var.dbVariables("HOSTNAME");
	private final String dbname = var.dbVariables("DATABASE.NAME");
	private final String username = var.dbVariables("USERNAME");
	private final String password = var.dbVariables("PASSWORD");
	private final String port = var.dbVariables("PORT");

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
	
	public Boolean insertEventNotification(String event, String userId, String notification) {
		try {
			String update = "INSERT INTO event_notifications (event_id, user_id, notification) VALUES((SELECT event_id FROM event WHERE english_e_name = ?),?,?)";
			PreparedStatement stmt = conn.prepareStatement(update);
			stmt.setString(1, event);
			stmt.setString(2, userId);
			stmt.setString(3, notification);
			int res = stmt.executeUpdate();
			
			if(res > 0)
				return true;
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}

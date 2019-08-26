package jp.myouth.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Messages {
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
	
	public void sentMessage(String messageId, String subject, String body) {
		try {
			String update = "INSERT INTO sent_messages (message_id, subject, body) VALUES(?,?,?)";
			PreparedStatement stmt = conn.prepareStatement(update);
			stmt.setString(1, messageId);
			stmt.setString(2, subject);
			stmt.setString(3, body);
			stmt.executeUpdate();
			
			return ;
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void sentEmailAddress(String messageId, String emailAddress) {
		try {
			String update = "INSERT INTO sent_email_addresses (message_id, email_address) VALUES(?,?)";
			PreparedStatement stmt = conn.prepareStatement(update);
			stmt.setString(1, messageId);
			stmt.setString(2, emailAddress);
			stmt.executeUpdate();
			
			return ;
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}

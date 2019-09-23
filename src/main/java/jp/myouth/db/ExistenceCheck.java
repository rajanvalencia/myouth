package jp.myouth.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ExistenceCheck {
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
	
	public Boolean eventImageUrl(String url) {
		try {
			String query = "SELECT * FROM event_images WHERE url = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, url);
			ResultSet rset = stmt.executeQuery();
			
			if(rset.next())
				return false;
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public Boolean eventAccess(String userId, String event) {
		try {
			String query = "SELECT * FROM user_event WHERE user_id = ? AND event_id = (SELECT event_id FROM event WHERE english_e_name = ?)";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, userId);
			stmt.setString(2, event);
			ResultSet rset = stmt.executeQuery();
			
			if(rset.next())
				return true;
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static void main(String[] args) {
		ExistenceCheck ec = new ExistenceCheck();
		ec.open();
		if(!ec.eventAccess("fWSwV9AqUN6", "altervoice"))
			System.out.println("fail");
		else
			System.out.println("success");
		ec.close();
	}
}

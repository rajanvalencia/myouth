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
	
	public Boolean event(String event) {
		try {
			String query = "SELECT * FROM event WHERE english_e_name = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, event);
			ResultSet rset = stmt.executeQuery();
			
			if(rset.next())
				return true;
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return false;
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
			String query = "SELECT * FROM event_administrators WHERE user_id = ? AND event_id = (SELECT event_id FROM event WHERE english_e_name = ?)";
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
	
	public Boolean eventIdDoesntExist(String eventId) {
		try {
			String query = "SELECT * FROM event WHERE english_e_name = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, eventId);
			ResultSet rset = stmt.executeQuery();
			
			if(!rset.next()) {
				rset.close();
				return true;
			}
			rset.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Boolean emailAddressAvailable(String emailAddress) {
		try {
			String query = "SELECT * FROM users WHERE email_address = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, emailAddress);
			ResultSet rset = stmt.executeQuery();
			
			if(rset.next()) {
				return false;
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} 
		return true;
	}
	
}

package jp.myouth.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.ResultSet;

public class User {
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

	public String userId(String email) {
		String data = new String();
		try {
			String query = "SELECT users.user_id FROM users WHERE email = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, email);
			ResultSet rset = stmt.executeQuery();
			if (rset.next())
				data = rset.getString("user_id");
			return data;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	

	public String name(String userId) {
		String data = new String();
		try {
			String query = "SELECT users.name FROM users WHERE user_id = \"" + userId + "\"";
			ResultSet rset = stmt.executeQuery(query);
			if (rset.next())
				data = rset.getString("name");
			return data;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String fname(String userId) {
		String data = new String();
		try {
			String query = "SELECT users.fname FROM users WHERE user_id = \"" + userId + "\"";
			ResultSet rset = stmt.executeQuery(query);
			if (rset.next())
				data = rset.getString("fname");
			return data;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String word(String userId) {
		String data = new String();
		try {
			String query = "SELECT users.word FROM users WHERE user_id = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, userId);
			ResultSet rset = stmt.executeQuery();
			if (rset.next())
				data = rset.getString("word");
			return data;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Boolean introduction(String userId, String name, String word) {
		try {
			String update = "UPDATE users SET name = ?, word = ? WHERE user_id ?";
			PreparedStatement stmt = conn.prepareStatement(update);
			stmt.setString(1, userId);
			stmt.setString(2, name);
			stmt.setString(3, word);
			stmt.executeUpdate();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Boolean register(String userId, String name, String fname, String email, String birthdate) {
		try {
			String update = "INSERT INTO users (user_id, name, fname, email, birthdate) VALUES(?,?,?,?,?)";
			PreparedStatement stmt = conn.prepareStatement(update);
			stmt.setString(1, userId);
			stmt.setString(2, name);
			stmt.setString(3, fname);
			stmt.setString(4, email);
			stmt.setString(5, birthdate);
			stmt.executeUpdate();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	public Boolean verifyEmail(String userId, Boolean verification) {
		try {
			if(!verification) {
				String insert = "INSERT INTO verify_email (user_id, verification) VALUES(?, ?)";
				PreparedStatement stmt = conn.prepareStatement(insert);
				stmt.setString(1, userId);
				stmt.setBoolean(2, verification);
				stmt.executeUpdate();
			}
			else {
				String update = "UPDATE verify_email SET verification = ? WHERE user_id = ?";
				PreparedStatement stmt = conn.prepareStatement(update);
				stmt.setBoolean(1, verification);
				stmt.setString(2, userId);
				stmt.executeUpdate();
			}
			return true;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Boolean checkVerification(String userId) {
		Boolean data = false;
		try {
			String query = "SELECT verification FROM verify_email WHERE user_id = \""+userId+"\"";
			ResultSet rset = stmt.executeQuery(query);
			if(rset.next())
				data = rset.getBoolean("verification");
			return data;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Integer totalUsers() {
		Integer data = 0;
		try {
			String query = "SELECT COUNT(*) AS totalUsers FROM users";
			ResultSet rset = stmt.executeQuery(query);
			
			if(rset.next())
				data = rset.getInt("totalUsers");
			
			return data;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public String userEmailAddress(String userId) {
		String data = new String();
		try {
			String query = "SELECT email FROM users WHERE user_id = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, userId);
			ResultSet rset = stmt.executeQuery();
			
			if(rset.next())
				data = rset.getString("email");
			
			return data;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<String> managingEvents(String userId) {
		ArrayList<String> data = new ArrayList<String>();
		try {
			String query = "SELECT event.e_name, event.english_e_name FROM event, user_event WHERE user_event.user_id = ? AND event.event_id = user_event.event_id";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, userId);
			ResultSet rset = stmt.executeQuery();
			while (rset.next()) {
				data.add(rset.getString("e_name"));
				data.add(rset.getString("english_e_name"));
			}
			return data;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public ArrayList<String> eventMember(String event){
		ArrayList<String> data = new ArrayList<String>();
		try {
			String query = "SELECT users.name, users.user_id FROM users, user_event, event WHERE users.user_id = user_event.user_id AND event.event_id = user_event.event_id AND event.english_e_name = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, event);
			ResultSet rset = stmt.executeQuery();

			while(rset.next()) {
				data.add(rset.getString("name"));
				data.add(rset.getString("user_id"));
			}
			
			return data;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public ArrayList<String> memberSearch(String search){
		ArrayList<String> data = new ArrayList<String>();
		try {
			String query = "SELECT name, fname FROM users WHERE name LIKE ? OR fname LIKE ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, search+"%");
			stmt.setString(2, search+"%");
			ResultSet rset = stmt.executeQuery();

			while(rset.next()) {
				data.add(rset.getString("name"));
				data.add(rset.getString("fname"));
			}
			
			return data;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}

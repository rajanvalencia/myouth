package jp.myouth.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
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
			String query = "SELECT users.user_id FROM users WHERE email = \"" + email + "\"";
			ResultSet rset = stmt.executeQuery(query);
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

	public String word(String userId) {
		String data = new String();
		try {
			String query = "SELECT users.word FROM users WHERE user_id = \"" + userId + "\"";
			ResultSet rset = stmt.executeQuery(query);
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
			String update = "UPDATE users SET name = \"" + name + "\", word = \"" + word + "\" WHERE user_id = \""
					+ userId + "\"";
			stmt.executeUpdate(update);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Boolean register(String userId, String name, String fname, String email, String birthdate) {
		try {
			String update = "INSERT INTO users (user_id, name, fname, email, birthdate) VALUES(\""
					+userId+"\", \""+name+"\", \""+fname+"\", \""+email+"\", \""+birthdate+"\")";
			stmt.executeUpdate(update);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	public Boolean verifyEmail(String userId, Boolean verification) {
		try {
			if(!verification) {
				String update = "INSERT INTO verify_email (user_id, verification) VALUES(\""+userId+"\", "+verification+")";
				stmt.executeUpdate(update);
			}
			String update = "UPDATE verify_email SET verification = "+verification+" WHERE user_id = \""+userId+"\"";
			stmt.executeUpdate(update);
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

}

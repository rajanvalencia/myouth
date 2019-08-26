package jp.myouth.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Credentials {
	
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

	public String salt(String userId) {
		String data = new String();
		try {
			String query = "SELECT salt FROM users_password WHERE user_id = \"" + userId + "\"";
			ResultSet rset = stmt.executeQuery(query);
			while (rset.next()) {
				data = rset.getString("salt");
			}
			rset.close();
			return data;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Boolean password(String userId, String password) {
		try {
			String query = "SELECT users_password.user_id FROM users_password WHERE users_password.password = \""
					+ password + "\"";
			ResultSet rset = stmt.executeQuery(query);
			if (rset.next())
				return true;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public String userId(String email) {
		String data = new String();
		try {
			String query = "SELECT users.user_id FROM users WHERE email = \"" + email + "\"";
			ResultSet rset = stmt.executeQuery(query);
			while (rset.next()) {
				data = rset.getString("user_id");
			}
			rset.close();
			return data;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Boolean insertUserCredentials(String userId, String hashedPassword, String salt) {
		try {
			String update = "INSERT INTO users_password (user_id, salt, password) VALUES(\"" + userId + "\", \"" + salt
					+ "\", \"" + hashedPassword + "\")";
			stmt.executeUpdate(update);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Boolean insertAccountVerificationToken(String userId, String token) {
		try {
			String update = "INSERT INTO account_verification_token (user_id, token) VALUES(?,?)";
			PreparedStatement stmt = conn.prepareStatement(update);
			stmt.setString(1, userId);
			stmt.setString(2, token);
			int res = stmt.executeUpdate();
			
			if(res > 0)
				return true;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Boolean changeUserCredentials(String userId, String hashedPassword, String salt) {
		try {
			String update = "UPDATE users_password SET password = ?, salt = ? WHERE user_id = ?";
			PreparedStatement stmt = conn.prepareStatement(update);
			stmt.setString(1, hashedPassword);
			stmt.setString(2, salt);
			stmt.setString(3, userId);
			int res = stmt.executeUpdate();
			
			if(res > 0)
				return true;
			else
				return false;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Boolean checkEmail(String email) {
		try {
			String query = "SELECT email FROM users WHERE email = ?";
			PreparedStatement stmt = conn.prepareStatement(query); 
			stmt.setString(1, email);
			ResultSet rset = stmt.executeQuery();
			if(rset.next())
				return true;
			else
				return false;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Boolean insertPasswordReissueToken(String token, String userId) {
		try {
			String update = "INSERT INTO reissue_password_token (user_id, token) VALUES(?,?)";
			PreparedStatement stmt = conn.prepareStatement(update);
			stmt.setString(1, userId);
			stmt.setString(2, token);
			int res = stmt.executeUpdate();
			
			if(res > 0)
				return true;
			else
				return false;
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public String authTokenUserId(String authToken) {
		try {
			String query = "SELECT user_id FROM reissue_password_token WHERE token = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, authToken);
			ResultSet rset = stmt.executeQuery();
			
			String update = "DELETE FROM reissue_password_token WHERE token = ?";
			PreparedStatement stmt1 = conn.prepareStatement(update);
			stmt1.setString(1, authToken);
			stmt1.executeUpdate();
			
			if(rset.next())
				return rset.getString("user_id");
			return null;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}

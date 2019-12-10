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
			String query = "SELECT salt FROM users_password WHERE user_id = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, userId);
			ResultSet rset = stmt.executeQuery();
			while (rset.next()) {
				data = rset.getString("salt");
			}
			rset.close();
			return data;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Boolean checkPasswordExistence(String password) {
		try {
			String query = "SELECT users_password.user_id FROM users_password WHERE users_password.password = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, password);
			ResultSet rset = stmt.executeQuery();
			if (rset.next())
				return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public String userId(String email) {
		try {
			String query = "SELECT user_id FROM users WHERE email_address = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, email);
			ResultSet rset = stmt.executeQuery();
			
			if (rset.next()) {
				return rset.getString("user_id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Boolean insertUserCredentials(String userId, String hashedPassword, String salt) {
		try {
			String update = "INSERT INTO users_password (user_id, salt, password) VALUES(?,?,?)";
			PreparedStatement stmt = conn.prepareStatement(update);
			stmt.setString(1, userId);
			stmt.setString(2, salt);
			stmt.setString(3, hashedPassword);
			stmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Boolean insertAccountVerificationToken(String userId, String token) {
		try {
			String update = "INSERT INTO users_account_verification_token (user_id, token) VALUES(?,?)";
			PreparedStatement stmt = conn.prepareStatement(update);
			stmt.setString(1, userId);
			stmt.setString(2, token);
			int res = stmt.executeUpdate();
			
			if(res > 0)
				return true;
		} catch(SQLException e) {
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
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Boolean checkEmail(String email) {
		try {
			String query = "SELECT email_address FROM users WHERE email_address = ?";
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
			String update = "INSERT INTO users_reissue_password_token (user_id, token) VALUES(?,?)";
			PreparedStatement stmt = conn.prepareStatement(update);
			stmt.setString(1, userId);
			stmt.setString(2, token);
			int res = stmt.executeUpdate();
			
			if(res > 0)
				return true;
			else
				return false;
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public String authTokenUserId(String authToken) {
		try {
			String query = "SELECT user_id FROM users_reissue_password_token WHERE token = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, authToken);
			ResultSet rset = stmt.executeQuery();
			
			String update = "DELETE FROM users_reissue_password_token WHERE token = ?";
			PreparedStatement stmt1 = conn.prepareStatement(update);
			stmt1.setString(1, authToken);
			stmt1.executeUpdate();
			
			if(rset.next())
				return rset.getString("user_id");
			return null;
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Boolean insertAjaxApiKey(String userId, String apiKey) {
		try {
			String update = "INSERT INTO users_ajax_api_keys (user_id, api_key) VALUES(?,?)";
			PreparedStatement stmt = conn.prepareStatement(update);
			stmt.setString(1, userId);
			stmt.setString(2, apiKey);
			int res = stmt.executeUpdate();
			if(res > 0)
				return true;
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public String getUserIdByAjaxApiKey(String apiKey) {
		try {
			String query = "SELECT user_id FROM users_ajax_api_keys WHERE api_key = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, apiKey);
			ResultSet rset = stmt.executeQuery();
			if(rset.next()) {
				return rset.getString("user_id");
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getUserIdFromToken(String token) {
		try {
			String userId = new String();
			
			String query = "SELECT user_id FROM users_account_verification_token WHERE token = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, token);
			ResultSet rset = stmt.executeQuery();
			if(rset.next())
				userId = rset.getString("user_id");
			
			String update = "DELETE FROM users_account_verification_token WHERE token = ?";
			PreparedStatement stmt1 = conn.prepareStatement(update);
			stmt1.setString(1, token);
			int res = stmt1.executeUpdate();
			
			if(res > 0)
				return userId;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Boolean updateEmailAddressVerificationStatus(String userId, Boolean verification) {
		try {
			int res = 0;
			
			if(!verification) {
				String insert = "INSERT INTO users_email_verification_status (user_id, verification) VALUES(?, ?)";
				PreparedStatement stmt = conn.prepareStatement(insert);
				stmt.setString(1, userId);
				stmt.setBoolean(2, verification);
				res = stmt.executeUpdate();
			}
			else {
				String update = "UPDATE users_email_verification_status SET verification = ? WHERE user_id = ?";
				PreparedStatement stmt = conn.prepareStatement(update);
				stmt.setBoolean(1, verification);
				stmt.setString(2, userId);
				res = stmt.executeUpdate();
			}
			if(res > 0)
				return true;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Boolean checkIfEmailAddressVerified(String userId) {
		Boolean data = false;
		try {
			String query = "SELECT verification FROM users_email_verification_status WHERE user_id = ?";
			
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, userId);
			ResultSet rset = stmt.executeQuery();
			if(rset.next())
				data = rset.getBoolean("verification");
			return data;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}

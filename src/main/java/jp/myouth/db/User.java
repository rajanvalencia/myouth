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
	private static Statement stmt = null;

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
			String query = "SELECT users.name FROM users WHERE user_id = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, userId);
			ResultSet rset = stmt.executeQuery();
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
			String query = "SELECT users.fname FROM users WHERE user_id = ?";
			
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, userId);
			ResultSet rset = stmt.executeQuery();
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
			String update = "UPDATE users SET name = ?, word = ? WHERE user_id = ?";
			PreparedStatement stmt = conn.prepareStatement(update);
			stmt.setString(1, name);
			stmt.setString(2, word);
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

	public Boolean register(String userId, String name, String fname, String email, String phone, String birthdate) {
		try {
			String update = "INSERT INTO users (user_id, name, fname, email, phone, birthdate) VALUES(?,?,?,?,?,?)";
			PreparedStatement stmt = conn.prepareStatement(update);
			stmt.setString(1, userId);
			stmt.setString(2, name);
			stmt.setString(3, fname);
			stmt.setString(4, email);
			stmt.setString(5, phone);
			stmt.setString(6, birthdate);
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
	
	public String getUserIdFromToken(String token) {
		try {
			String userId = new String();
			
			String query = "SELECT user_id FROM account_verification_token WHERE token = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, token);
			ResultSet rset = stmt.executeQuery();
			if(rset.next())
				userId = rset.getString("user_id");
			
			String update = "DELETE FROM account_verification_token WHERE token = ?";
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
	
	public Boolean verifyEmail(String userId, Boolean verification) {
		try {
			int res = 0;
			
			if(!verification) {
				String insert = "INSERT INTO verify_email (user_id, verification) VALUES(?, ?)";
				PreparedStatement stmt = conn.prepareStatement(insert);
				stmt.setString(1, userId);
				stmt.setBoolean(2, verification);
				res = stmt.executeUpdate();
			}
			else {
				String update = "UPDATE verify_email SET verification = ? WHERE user_id = ?";
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
	
	public Boolean checkVerification(String userId) {
		Boolean data = false;
		try {
			String query = "SELECT verification FROM verify_email WHERE user_id = ?";
			
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
			String query = "SELECT event.english_e_name, event_logo.logo_url, event.e_name AS permanent_e_name, event.place, event.date, TIME_FORMAT(event.time, \"%H:%i\") AS time, CONCAT(recruitment_start, \"~\", recruitment_end) AS recruitment_period, (SELECT COUNT(*) FROM event, event_participants WHERE event_participants.event_id = event.event_id AND recruitment_start <= join_date AND recruitment_end >= join_date AND event.e_name = permanent_e_name) AS current_total_participants FROM event, user_event, event_logo WHERE user_event.user_id = ? AND event.event_id = user_event.event_id AND event.event_id = event_logo.event_id";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, userId);
			ResultSet rset = stmt.executeQuery();
			while (rset.next()) {
				data.add(rset.getString("english_e_name"));
				data.add(rset.getString("logo_url"));
				data.add(rset.getString("permanent_e_name"));
				data.add(rset.getString("place"));
				data.add(rset.getString("date"));
				data.add(rset.getString("time"));
				data.add(rset.getString("recruitment_period"));
				data.add(rset.getString("current_total_participants"));
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
			String query = "SELECT user_profile_picture.path, users.name, users.user_id FROM users, user_event, event, user_profile_picture WHERE users.user_id = user_profile_picture.user_id AND users.user_id = user_event.user_id AND event.event_id = user_event.event_id AND event.english_e_name = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, event);
			ResultSet rset = stmt.executeQuery();

			while(rset.next()) {
				if(rset.getString("path").equals("") || rset.getString("path").equals(null))
					data.add("users/default/profile_pic.PNG");
				else
					data.add(rset.getString("path"));
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
			String query = "SELECT user_profile_picture.path, name, users.user_id FROM users, user_profile_picture WHERE users.user_id = user_profile_picture.user_id AND (name LIKE ? OR fname LIKE ?)";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, "%"+search+"%");
			stmt.setString(2, "%"+search+"%");
			ResultSet rset = stmt.executeQuery();

			while(rset.next()) {
				if(rset.getString("path").equals("") || rset.getString("path").equals(null))
					data.add("users/default/profile_pic.PNG");
				else
					data.add(rset.getString("path"));
				data.add(rset.getString("name"));
				data.add(rset.getString("user_id"));
			}
			
			return data;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Boolean memberAdd(String event, String userId) {
		String event_id = new String();
		try {
			String query = "SELECT event_id FROM event WHERE english_e_name = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, event);
			ResultSet rset = stmt.executeQuery();
			while(rset.next())
				event_id = rset.getString("event_id");
			rset.close();
			
			String update = "INSERT INTO user_event (user_id, event_id) VALUES(?, ?)";
			PreparedStatement stmt1 = conn.prepareStatement(update);
			stmt1.setString(1, userId);
			stmt1.setString(2, event_id);
			int res = stmt1.executeUpdate();
			
			if(res > 0)
				return true;
			else
				return false;
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Boolean memberRemove(String event, String userId) {
		String event_id = new String();
		try {
			String query = "SELECT event_id FROM event WHERE english_e_name = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, event);
			ResultSet rset = stmt.executeQuery();
			while(rset.next())
				event_id = rset.getString("event_id");
			rset.close();
			
			String update = "DELETE FROM user_event WHERE user_id = ? AND event_id = ?";
			PreparedStatement stmt1 = conn.prepareStatement(update);
			stmt1.setString(1, userId);
			stmt1.setString(2, event_id);
			int res = stmt1.executeUpdate();
			
			if(res > 0)
				return true;
			else
				return false;
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Boolean checkIfEmailDoesNotExist(String email) {
		try {
			String query = "SELECT email FROM users WHERE email = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, email);
			ResultSet rset = stmt.executeQuery();
			
			if(rset.next())
				return false;
			else 
				return true;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Boolean insertOrUpdateUserProfilePicture(String userId, String path) {
		try {
			String query = "SELECT * FROM user_profile_picture WHERE user_id = ?";
			PreparedStatement checkExistence = conn.prepareStatement(query);
			checkExistence.setString(1, userId);
			ResultSet rset = checkExistence.executeQuery();
			
			if(!rset.next()) {
				String insert = "INSERT INTO user_profile_picture (user_id, path) VALUES(?,?)";
				PreparedStatement stmt = conn.prepareStatement(insert);
				stmt.setString(1, userId);
				stmt.setString(2, path);
				int res = stmt.executeUpdate();
				if(res > 0)
					return true;
			} else {
			
			String update = "UPDATE user_profile_picture SET path = ? WHERE user_id = ?";
			PreparedStatement stmt = conn.prepareStatement(update);
			stmt.setString(1, path);
			stmt.setString(2, userId);
			int res = stmt.executeUpdate();
			
			if(res > 0)
				return true;
			}
			
				return false;
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String userProfilePicture(String userId) {
		try {
			String query = "SELECT path FROM user_profile_picture WHERE user_id = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, userId);
			ResultSet rset = stmt.executeQuery();
			
			if(rset.next())
				return rset.getString("path");
			else 
				return "users/default/profile_pic.PNG";
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Boolean insertProfilePictureModerationLabel(String userId, String photoUrl, String moderationName, String moderationParentName, float confidence) {
		try {
			String query = "INSERT INTO profile_picture_moderation_label (user_id, photo_url, name, parent_name, confidence) VALUES(?,?,?,?,?)";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, userId);
			stmt.setString(2, photoUrl);
			stmt.setString(3, moderationName);
			stmt.setString(4, moderationParentName);
			stmt.setFloat(5, confidence);
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
	
	public Boolean checkIfPhotoIsSuggestive(String photoUrl) {
		try {
			String query = "SELECT * FROM profile_picture_moderation_label WHERE photo_url = ? AND name = \"Suggestive\" AND confidence >= 90";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, photoUrl);
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
	
}

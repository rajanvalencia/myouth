package jp.myouth.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Images {
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
	
	public String imageUrl(String imageId) {
		try {
			String query = "SELECT url FROM event_images WHERE image_id = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, imageId);
			ResultSet rset = stmt.executeQuery();
			
			if(rset.next())
				return rset.getString("url");
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Boolean updateEventLogo(int eventId, String path) {
		try {
			String update = "UPDATE event_logo SET logo_url = ? WHERE event_id = ?";
			PreparedStatement stmt = conn.prepareStatement(update);
			stmt.setString(1, path);
			stmt.setInt(2, eventId);
			int res = stmt.executeUpdate();
			
			if(res > 0)
				return true;
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
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
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Boolean insertProfilePictureModerationLabel(String userId, String moderationName, String moderationParentName, float confidence) {
		try {
			String query = "INSERT INTO profile_picture_moderation_label (user_id, name, parent_name, confidence) VALUES(?,?,?,?)";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, userId);
			stmt.setString(2, moderationName);
			stmt.setString(3, moderationParentName);
			stmt.setFloat(4, confidence);
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
	
	public Boolean insertEventImageModerationLabel(String userId, int eventId, String moderationName, String moderationParentName, float confidence) {
		try {
			String query = "INSERT INTO event_image_moderation_label (user_id, event_id, name, parent_name, confidence) VALUES(?,?,?,?,?)";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, userId);
			stmt.setInt(2, eventId);
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
	
	public Boolean insertEventLogoModerationLabel(String userId, int eventId, String moderationName, String moderationParentName, float confidence) {
		try {
			String query = "INSERT INTO event_logo_moderation_label (user_id, event_id, name, parent_name, confidence) VALUES(?,?,?,?,?)";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, userId);
			stmt.setInt(2, eventId);
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
	
	public Boolean checkIfEventImageIsSuggestive(String photoUrl) {
		try {
			String query = "SELECT * FROM event_image_moderation_label WHERE photo_url = ? AND name = \"Suggestive\" AND confidence >= 90";
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
	
	public Boolean checkIfEventLogoIsSuggestive(String photoUrl) {
		try {
			String query = "SELECT * FROM event_logo_moderation_label WHERE photo_url = ? AND name = \"Suggestive\" AND confidence >= 90";
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
	
	public ArrayList<String> getImageList(String event) {
		ArrayList<String> data = new ArrayList<String>();
		try {
			String query = "SELECT url, description FROM event, event_images WHERE event.event_id = event_images.event_id AND english_e_name = ? ORDER BY image_id DESC";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, event);
			ResultSet rset = stmt.executeQuery();

			while (rset.next()) {
				data.add(rset.getString("url"));
				data.add(rset.getString("description"));
			}
			return data;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public ArrayList<String> getImageListViaUser(String event) {
		ArrayList<String> data = new ArrayList<String>();
		try {
			String query = "SELECT url, image_id, description FROM event, event_images WHERE event.event_id = event_images.event_id AND english_e_name = ? ORDER BY event_images.time DESC";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, event);
			ResultSet rset = stmt.executeQuery();

			while (rset.next()) {
				data.add(rset.getString("url"));
				data.add(rset.getString("image_id"));
				data.add(rset.getString("description"));
			}
			return data;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Boolean deleteEventImage(String imageId) {
		try {
			String update = "DELETE FROM event_images WHERE image_id = ?";
			PreparedStatement stmt = conn.prepareStatement(update);
			stmt.setString(1, imageId);
			int res = stmt.executeUpdate();
			
			if(res > 0)
				return true;
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Boolean insertEventImage(String imageId, String event, String photoUrl) {
		try {
			String insert = "INSERT INTO event_images (image_id, event_id, url) VALUES(?, (SELECT event_id FROM event WHERE english_e_name = ?), ?)";
			PreparedStatement stmt = conn.prepareStatement(insert);
			stmt.setString(1, imageId);
			stmt.setString(2, event);
			stmt.setString(3, photoUrl);
			int res = stmt.executeUpdate();
			
			if(res > 0)
				return true;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Boolean updateEventImage(String imageId, String photoUrl) {
		try {
			String update = "UPDATE event_images SET url = ? WHERE image_id = ?";
			PreparedStatement stmt = conn.prepareStatement(update);
			stmt.setString(1, photoUrl);
			stmt.setString(2, imageId);
			int res = stmt.executeUpdate();
			
			if(res > 0)
				return true;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Boolean updateEventImageDescription(String imageId, String description) {
		try {
			String update = "UPDATE event_images SET description = ? WHERE image_id = ?";
			PreparedStatement stmt = conn.prepareStatement(update);
			stmt.setString(1, description);
			stmt.setString(2, imageId);
			int res = stmt.executeUpdate();
			
			if(res > 0)
				return true;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}

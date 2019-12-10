package jp.myouth.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Events {
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
	
	public ArrayList<String> allEvents() {
		ArrayList<String> data = new ArrayList<String>();
		try {
			String query = "SELECT english_e_name, logo_url, e_name, description FROM event, event_logo, event_description WHERE event.event_id = event_logo.event_id AND event.event_id = event_description.event_id";
			ResultSet rset = stmt.executeQuery(query);
			while (rset.next()) {
				data.add(rset.getString("english_e_name"));
				data.add(rset.getString("logo_url"));
				data.add(rset.getString("e_name"));
				data.add(rset.getString("description"));
			}
			rset.close();
			return data;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Boolean registerEvent(String userId, String id, String name, String place, String date, String recruitmentStartDate, String recruitmentEndDate, String startTime, String endTime, String recruitLimit, String description, String templateId) {
		try {
			String insert = "INSERT INTO event (english_e_name, e_name, place, recruitment_start, recruitment_end, date, start_time, end_time, recruitno) VALUES(?,?,?,?,?,?,?,?,?)";
			PreparedStatement stmt = conn.prepareStatement(insert);
			stmt.setString(1, id);
			stmt.setString(2, name);
			stmt.setString(3, place);
			stmt.setString(4, recruitmentStartDate);
			stmt.setString(5, recruitmentEndDate);
			stmt.setString(6, date);
			stmt.setString(7, startTime);
			stmt.setString(8, endTime);
			stmt.setString(9, recruitLimit);
			int res = stmt.executeUpdate();
			
			String insert1 = "INSERT INTO event_logo (event_id) VALUES(LAST_INSERT_ID())";
			int res1 = stmt.executeUpdate(insert1);
			
			String insert2 = "INSERT INTO event_description (event_id, description) VALUES(LAST_INSERT_ID(), ?)";
			PreparedStatement stmt2 = conn.prepareStatement(insert2);
			stmt2.setString(1, description);
			int res2 = stmt2.executeUpdate();
			
			String insert3 = "INSERT INTO event_questions (event_id, template_id) VALUES(LAST_INSERT_ID(), ?)";
			PreparedStatement stmt3 = conn.prepareStatement(insert3);
			stmt3.setString(1, templateId);
			int res3 = stmt3.executeUpdate();
			
			String insert4 = "INSERT INTO questions_template_title (template_id, title) VALUES(?, ?)";
			PreparedStatement stmt4 = conn.prepareStatement(insert4);
			stmt4.setString(1, templateId);
			stmt4.setString(2, "参加申し込み");
			int res4 = stmt4.executeUpdate();
			
			String insert5 = "INSERT INTO questions_template_description (template_id, description) VALUES(?, ?)";
			PreparedStatement stmt5 = conn.prepareStatement(insert5);
			stmt5.setString(1, templateId);
			stmt5.setString(2, "以下の質問に答えてください");
			int res5 = stmt5.executeUpdate();
			
			String insert6 = "INSERT INTO event_administrators (user_id, event_id) VALUES(?, LAST_INSERT_ID())";
			PreparedStatement stmt6 = conn.prepareStatement(insert6);
			stmt6.setString(1, userId);
			int res6 = stmt6.executeUpdate();
			
			if(res > 0 && res1 > 0 && res2 > 0 && res3 > 0 && res4 > 0 && res5 > 0 && res6 > 0)
				return true;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Boolean deleteEvent(String event) {
		try {
			String delete = "DELETE FROM event_logo WHERE event_id = (SELECT event_id FROM event WHERE english_e_name = ?)";
			PreparedStatement stmt = conn.prepareStatement(delete);
			stmt.setString(1, event);
			stmt.executeUpdate();
			stmt.close();
			
			String delete1 = "DELETE FROM event_description WHERE event_id = (SELECT event_id FROM event WHERE english_e_name = ?)";
			PreparedStatement stmt1 = conn.prepareStatement(delete1);
			stmt1.setString(1, event);
			stmt1.executeUpdate();
			stmt1.close();
			
			String delete2 = "DELETE FROM event_images WHERE event_id = (SELECT event_id FROM event WHERE english_e_name = ?)";
			PreparedStatement stmt2 = conn.prepareStatement(delete2);
			stmt2.setString(1, event);
			stmt2.executeUpdate();
			stmt2.close();
			
			String delete3 = "DELETE FROM event_logo_moderation_label WHERE event_id = (SELECT event_id FROM event WHERE english_e_name = ?)";
			PreparedStatement stmt3 = conn.prepareStatement(delete3);
			stmt3.setString(1, event);
			stmt3.executeUpdate();
			stmt3.close();
			
			String delete4 = "DELETE FROM event_url WHERE event_id = (SELECT event_id FROM event WHERE english_e_name = ?)";
			PreparedStatement stmt4 = conn.prepareStatement(delete4);
			stmt4.setString(1, event);
			stmt4.executeUpdate();
			stmt4.close();
			
			String delete5 = "DELETE FROM event_administrators WHERE event_id = (SELECT event_id FROM event WHERE english_e_name = ?)";
			PreparedStatement stmt5 = conn.prepareStatement(delete5);
			stmt5.setString(1, event);
			stmt5.executeUpdate();
			stmt5.close();
			
			String delete6 = "DELETE FROM event_questions WHERE event_id = (SELECT event_id FROM event WHERE english_e_name = ?)";
			PreparedStatement stmt6 = conn.prepareStatement(delete6);
			stmt6.setString(1, event);
			stmt6.executeUpdate();
			stmt6.close();
			
			String delete7 = "DELETE FROM event WHERE english_e_name = ?";
			PreparedStatement stmt7 = conn.prepareStatement(delete7);
			stmt7.setString(1, event);
			stmt7.executeUpdate();
			stmt7.close();
			
			return true;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public String event(String eventId) {
		try {
			String query = "SELECT english_e_name FROM event WHERE event_id = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, eventId);
			ResultSet rset = stmt.executeQuery();
			
			if(rset.next())
				return rset.getString("english_e_name");
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Integer eventId(String event) {
		try {
			String query = "SELECT event_id FROM event WHERE english_e_name = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, event);
			ResultSet rset = stmt.executeQuery();
			
			if(rset.next())
				return rset.getInt("event_id");
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public String instagramUrl(String event) {
		String data = new String();
		try {
			String query = "SELECT instagram_url FROM event_url, event WHERE event.event_id = event_url.event_id AND english_e_name = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, event);
			ResultSet rset = stmt.executeQuery();
			while (rset.next())
				data = rset.getString("instagram_url");
			return data;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String facebookUrl(String event) {
		String data = new String();
		try {
			String query = "SELECT facebook_url FROM event_url, event WHERE event.event_id = event_url.event_id AND english_e_name = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, event);
			ResultSet rset = stmt.executeQuery();
			while (rset.next())
				data = rset.getString("facebook_url");
			return data;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String twitterUrl(String event) {
		String data = new String();
		try {
			String query = "SELECT twitter_url FROM event_url, event WHERE event.event_id = event_url.event_id AND english_e_name = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, event);
			ResultSet rset = stmt.executeQuery();
			while (rset.next())
				data = rset.getString("twitter_url");
			return data;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String eventLocation(String event) {
		String data = new String();
		try {
			String query = "SELECT place FROM event WHERE english_e_name = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, event);
			ResultSet rset = stmt.executeQuery();
			while (rset.next())
				data = rset.getString("place");
			return data;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String eventEmail(String event) {
		String data = new String();
		try {
			String query = "SELECT email FROM event, event_admin_email WHERE event.event_id = event_admin_email.event_id AND english_e_name = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, event);
			ResultSet rset = stmt.executeQuery();
			while (rset.next())
				data = rset.getString("email");
			return data;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String eventDescription(String event) {
		String data = new String();
		try {
			String query = "SELECT description FROM event, event_description WHERE event.event_id = event_description.event_id AND english_e_name = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, event);
			ResultSet rset = stmt.executeQuery();
			while (rset.next())
				data = rset.getString("description");
			return data;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String eventLogo(String event) {
		String data = new String();
		try {
			String query = "SELECT logo_url FROM event, event_logo WHERE event.event_id = event_logo.event_id AND english_e_name = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, event);
			ResultSet rset = stmt.executeQuery();
			while (rset.next())
				data = rset.getString("logo_url");
			return data;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String eventName(String event) {
		String data = new String();
		try {
			String query = "SELECT e_name FROM event WHERE english_e_name= ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, event);
			ResultSet rset = stmt.executeQuery();

			while (rset.next())
				data = rset.getString("e_name");
			rset.close();
			return data;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String eventPlace(String event) {
		try {
			String data = new String();
			String query = "SELECT place FROM event WHERE english_e_name= ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, event);
			ResultSet rset = stmt.executeQuery();
			while (rset.next())
				data = rset.getString("place");
			rset.close();
			return data;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String eventStartTime(String event) {
		try {
			String time = new String();
			String query = "SELECT start_time FROM event WHERE english_e_name = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, event);
			ResultSet rset = stmt.executeQuery();
			
			while (rset.next()) 
				time = rset.getString("start_time");
			rset.close();
			
			return time;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String eventEndTime(String event) {
		try {
			String time = new String();
			String query = "SELECT end_time FROM event WHERE english_e_name = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, event);
			ResultSet rset = stmt.executeQuery();
			
			while (rset.next()) 
				time = rset.getString("end_time");
			rset.close();
			
			return time;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String eventDate(String event) {
		String date = new String();
		try {
			String query = "SELECT date FROM event WHERE english_e_name = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, event);
			ResultSet rset = stmt.executeQuery();
			
			if (rset.next()) 
				date = rset.getString("date");
			rset.close();
			
			return date;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String formattedEventStartTime(String event) {
		try {
			String time = new String();
			String query = "SELECT TIME_FORMAT(start_time, \"%H時%i分\") AS time FROM event WHERE english_e_name = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, event);
			ResultSet rset = stmt.executeQuery();
			
			while (rset.next()) 
				time = rset.getString("time");
			rset.close();
			
			return time;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String formattedEventEndTime(String event) {
		try {
			String time = new String();
			String query = "SELECT TIME_FORMAT(end_time, \"%H時%i分\") AS time FROM event WHERE english_e_name = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, event);
			ResultSet rset = stmt.executeQuery();
			
			while (rset.next()) 
				time = rset.getString("time");
			rset.close();
			
			return time;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String formattedEventDate(String event) {
		String date = new String();
		try {
			String query = "SELECT (DATE_FORMAT(date, \"%Y年%m月%d日\")) AS date FROM event WHERE english_e_name = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, event);
			ResultSet rset = stmt.executeQuery();
			
			if (rset.next()) 
				date = rset.getString("date");
			rset.close();
			
			return date;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String recruitmentStartDate(String event) {
		String date = new String();
		try {
			String query = "SELECT DATE_FORMAT(recruitment_start, \"%Y-%m-%d\") AS date FROM event WHERE english_e_name = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, event);
			ResultSet rset = stmt.executeQuery();

			if (rset.next()) 
				date = rset.getString("date");
			rset.close();
			
			return date;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String recruitmentEndDate(String event) {
		String date = new String();
		try {
			String query = "SELECT DATE_FORMAT(recruitment_end, \"%Y-%m-%d\") AS date FROM event WHERE english_e_name = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, event);
			ResultSet rset = stmt.executeQuery();

			if (rset.next()) 
				date = rset.getString("date");
			rset.close();
			
			return date;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Integer recruitmentLimit(String event) {
		int data = 0;
		try {
			String query = "SELECT recruitno FROM event WHERE english_e_name = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, event);
			ResultSet rset = stmt.executeQuery();

			if (rset.next())
				data = rset.getInt("recruitno");
			rset.close();

			return data;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public Integer remainingRecruitmentLimit(String event) {
		int data = 0;
		try {
			String query = "SELECT (SELECT recruitno FROM event WHERE english_e_name = ?) - (SELECT COUNT(*) FROM event_questions, event, answers_transactions WHERE event_questions.event_id = event.event_id AND event_questions.template_id = answers_transactions.template_id AND answers_transactions.time <= event.recruitment_start AND answers_transactions.time >= recruitment_end AND event.english_e_name = ?) AS remainigRecruitLimit";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, event);
			stmt.setString(2, event);
			ResultSet rset = stmt.executeQuery();

			if (rset.next())
				data = rset.getInt("remainigRecruitLimit");
			rset.close();

			return data;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public Integer totalEvents() {
		Integer data = 0;
		try {
			String query = "SELECT COUNT(*) AS totalEvents FROM event";
			PreparedStatement stmt = conn.prepareStatement(query);
			ResultSet rset = stmt.executeQuery();

			if (rset.next())
				data = rset.getInt("totalEvents");

			return data;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<String> getImage(String event) {
		ArrayList<String> data = new ArrayList<String>();
		try {
			String query = "SELECT url, description FROM event, event_images WHERE event.event_id = event_images.event_id AND english_e_name = ? ORDER BY event_images.time DESC";
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

	public int totalParticipants(String event) {
		try {
			Integer total = 0;
			String query = "SELECT COUNT(*) FROM participants, event_participants, event WHERE participants.participant_id = event_participants.participant_id AND event.event_id = event_participants.event_id AND english_e_name= ? AND DATE(event_participants.time) >= recruitment_start AND DATE(event_participants.time) <= recruitment_end";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, event);
			ResultSet rset = stmt.executeQuery();

			if (rset.next())
				total = rset.getInt("COUNT(*)");
			rset.close();
			return total;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public Boolean updateEventDetails(String event, String eventName, String eventPlace, String eventDate, String recruitmentStartDate, String recruitmentEndDate, String startTime, String endTime, String recruitLimit) {
		try {
			String update = "UPDATE event SET e_name = ?, place = ?, recruitment_start = ?, recruitment_end = ?, date = ?, start_time = ?, end_time = ?, recruitno = ? WHERE english_e_name = ?";
			PreparedStatement stmt = conn.prepareStatement(update);
			stmt.setString(1, eventName);
			stmt.setString(2, eventPlace);
			stmt.setString(3, recruitmentStartDate);
			stmt.setString(4, recruitmentEndDate);
			stmt.setString(5, eventDate);
			stmt.setString(6, startTime);
			stmt.setString(7, endTime);
			stmt.setString(8, recruitLimit);
			stmt.setString(9, event);
			int res = stmt.executeUpdate();
			stmt.close();

			if (res > 0)
				return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Boolean updateEventDescription(String event, String description) {
		try {
		String update = "UPDATE event, event_description SET event_description.description = ? WHERE event.event_id = event_description.event_id AND event.english_e_name = ?";
		PreparedStatement stmt = conn.prepareStatement(update);
		stmt.setString(1, description);
		stmt.setString(2, event);
		int res = stmt.executeUpdate();
		
		if(res > 0)
			return true;
		
		} catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	
	public ArrayList<String> eventPageMember(String event){
		ArrayList<String> data = new ArrayList<String>();
		try {
			String query = "SELECT users_profile_picture.path, users.name FROM users, event_administrators, event, users_profile_picture WHERE users.user_id = users_profile_picture.user_id AND users.user_id = event_administrators.user_id AND event.event_id = event_administrators.event_id AND event.english_e_name = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, event);
			ResultSet rset = stmt.executeQuery();

			while(rset.next()) {
				data.add(rset.getString("path"));
				data.add(rset.getString("name"));
			}
			
			return data;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public ArrayList<String> searchEvents(String search) {
		ArrayList<String> data = new ArrayList<String>();
		try {
			String query = "SELECT english_e_name, logo_url, e_name, SUBSTRING(description, 1, 38) AS description FROM event, event_logo, event_description WHERE event.event_id = event_logo.event_id AND event.event_id = event_description.event_id AND (e_name LIKE ? OR english_e_name LIKE ?)";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, "%"+search+"%");
			stmt.setString(2, "%"+search+"%");
			ResultSet rset = stmt.executeQuery();
			
			while(rset.next()) {
				data.add(rset.getString("english_e_name"));
				data.add(rset.getString("logo_url"));
				data.add(rset.getString("e_name"));
				data.add(rset.getString("description"));
			}
			
			return data;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
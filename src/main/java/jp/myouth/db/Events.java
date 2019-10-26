package jp.myouth.db;

import java.sql.Date;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import jp.myouth.security.GenerateSecureString;

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
	
	public Boolean registerEvent(String userId, String event, String eventName, String eventPlace, String eventDate,
			String recruitmentStartDate, String recruitmentEndDate, String eventTime, String recruitNo) {
		try {
			String insert = "INSERT INTO event (english_e_name, e_name, place, recruitment_start, recruitment_end, date, time, recruitno) VALUES(?,?,?,?,?,?,?,?)";
			PreparedStatement stmt = conn.prepareStatement(insert);
			stmt.setString(1, event);
			stmt.setString(2, eventName);
			stmt.setString(3, eventPlace);
			stmt.setString(4, recruitmentStartDate);
			stmt.setString(5, recruitmentEndDate);
			stmt.setString(6, eventDate);
			stmt.setString(7, eventTime);
			stmt.setString(8, recruitNo);
			int res = stmt.executeUpdate();
			
			String insert1 = "INSERT INTO event_logo (event_id) VALUES(LAST_INSERT_ID())";
			int res1 = stmt.executeUpdate(insert1);
			
			String insert2 = "INSERT INTO event_description (event_id) VALUES(LAST_INSERT_ID())";
			int res2 = stmt.executeUpdate(insert2);
			
			String insert3 = "INSERT INTO form_questions (event_id, name, fname, email) VALUES(LAST_INSERT_ID(), TRUE, TRUE, TRUE)";
			int res3 = stmt.executeUpdate(insert3);
			
			String insert4 = "INSERT INTO survey_questions (event_id, satisfaction, comments) VALUES(LAST_INSERT_ID(), TRUE, TRUE)";
			int res4 = stmt.executeUpdate(insert4);
			
			String insert5 = "INSERT INTO user_event (user_id, event_id) VALUES(?, LAST_INSERT_ID())";
			PreparedStatement stmt5 = conn.prepareStatement(insert5);
			stmt5.setString(1, userId);
			int res5 = stmt5.executeUpdate();
			
			if(res > 0 && res1 > 0 && res2 > 0 && res3 > 0 && res4 > 0 && res5 > 0)
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
			
			String delete4 = "DELETE FROM event_participants WHERE event_id = (SELECT event_id FROM event WHERE english_e_name = ?)";
			PreparedStatement stmt4 = conn.prepareStatement(delete4);
			stmt4.setString(1, event);
			stmt4.executeUpdate();
			stmt4.close();
			
			String delete5 = "DELETE FROM event_url WHERE event_id = (SELECT event_id FROM event WHERE english_e_name = ?)";
			PreparedStatement stmt5 = conn.prepareStatement(delete5);
			stmt5.setString(1, event);
			stmt5.executeUpdate();
			stmt5.close();
			
			String delete6 = "DELETE FROM form_questions WHERE event_id = (SELECT event_id FROM event WHERE english_e_name = ?)";
			PreparedStatement stmt6 = conn.prepareStatement(delete6);
			stmt6.setString(1, event);
			stmt6.executeUpdate();
			stmt6.close();
			
			String delete7 = "DELETE FROM survey_questions WHERE event_id = (SELECT event_id FROM event WHERE english_e_name = ?)";
			PreparedStatement stmt7 = conn.prepareStatement(delete7);
			stmt7.setString(1, event);
			stmt7.executeUpdate();
			stmt7.close();
			
			String delete8 = "DELETE FROM event WHERE event_id = (SELECT event_id FROM event WHERE english_e_name = ?)";
			PreparedStatement stmt8 = conn.prepareStatement(delete8);
			stmt8.setString(1, event);
			stmt8.executeUpdate();
			stmt8.close();
			
			String delete9 = "DELETE FROM user_event WHERE event_id = (SELECT event_id FROM event WHERE english_e_name = ?)";
			PreparedStatement stmt9 = conn.prepareStatement(delete9);
			stmt9.setString(1, event);
			stmt9.executeUpdate();
			stmt9.close();
			
			return true;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public ArrayList<String> eventInfo(String event) {
		ArrayList<String> data = new ArrayList<String>();
		try {
			String query = "SELECT event_logo.logo_url, event.e_name AS current_e_name, event.place, event.date, TIME_FORMAT(event.time, \"%H:%i\") AS time, CONCAT(recruitment_start, \" ~ \", recruitment_end) AS recruitment_period, (SELECT COUNT(*) FROM event, event_participants WHERE event_participants.event_id = event.event_id AND recruitment_start <= event_participants.time AND recruitment_end >= event_participants.time AND event.e_name = current_e_name) AS current_total_participants, (SELECT event.recruitno - current_total_participants FROM event WHERE english_e_name = ?) AS current_recruitno, event_description.description FROM event, event_logo, event_description WHERE event.event_id = event_description.event_id AND event.english_e_name = ? AND event.event_id = event_logo.event_id";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, event);
			stmt.setString(2, event);
			ResultSet rset = stmt.executeQuery();

			while (rset.next()) {
				data.add(rset.getString("logo_url"));
				data.add(rset.getString("current_e_name"));
				data.add(rset.getString("description"));
				data.add(rset.getString("place"));
				data.add(rset.getString("date"));
				data.add(rset.getString("time"));
				data.add(rset.getString("current_total_participants"));
				data.add(rset.getString("recruitment_period"));
				data.add(rset.getString("current_recruitno"));
			}
			rset.close();

			return data;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
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

	public String eventTime(String event) {
		try {
			String time = new String();
			String query = "SELECT TIME_FORMAT(time, \"%H��:%i��\") AS time FROM event WHERE english_e_name = ?";
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
	
	public Integer eventTimeHour(String event) {
		try {
			int time = 0;
			String query = "SELECT EXTRACT(HOUR FROM time) AS hour FROM event WHERE english_e_name = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, event);
			ResultSet rset = stmt.executeQuery();
			
			while (rset.next()) 
				time = rset.getInt("hour");
			rset.close();
			
			return time;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public Integer eventTimeMinute(String event) {
		try {
			int time = 0;
			String query = "SELECT EXTRACT(MINUTE FROM time) AS minute FROM event WHERE english_e_name = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, event);
			ResultSet rset = stmt.executeQuery();
			
			while (rset.next()) 
				time = rset.getInt("time");
			rset.close();
			
			return time;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public String eventDate(String event) {
		String date = new String();
		try {
			String query = "SELECT (DATE_FORMAT(date, \"%Y�N%m��%d��\")) AS date FROM event WHERE english_e_name = ?";
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
	
	public String eventDateYear(String event) {
		String date = new String();
		try {
			String query = "SELECT EXTRACT(YEAR FROM date) AS year FROM event WHERE english_e_name = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, event);
			ResultSet rset = stmt.executeQuery();
			
			if (rset.next()) 
				date = rset.getString("year");
			rset.close();
			
			return date;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String eventDateMonth(String event) {
		String date = new String();
		try {
			String query = "SELECT EXTRACT(MONTH FROM date) AS month FROM event WHERE english_e_name = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, event);
			ResultSet rset = stmt.executeQuery();
			
			if (rset.next()) 
				date = rset.getString("month");
			rset.close();
			
			return date;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String eventDateDay(String event) {
		String date = new String();
		try {
			String query = "SELECT EXTRACT(DAY FROM date) AS day FROM event WHERE english_e_name = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, event);
			ResultSet rset = stmt.executeQuery();
			
			if (rset.next()) 
				date = rset.getString("day");
			rset.close();
			
			return date;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String eventDayName(String event) {
		ArrayList<String> week = new ArrayList<String>();
		int data = 0;
		
		week.add("��");
		week.add("��");
		week.add("��");
		week.add("��");
		week.add("��");
		week.add("��");
		week.add("�y");
		
		try {
			String query = "SELECT DAYOFWEEK(date) FROM event WHERE english_e_name = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, event);
			ResultSet rset = stmt.executeQuery();
			if (rset.next()) 
				data = rset.getInt("DAYOFWEEK(date)");
			
			rset.close();
			
			return week.get(data-1);
			
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
	
	public String recruitmentStartDateYear(String event) {
		String date = new String();
		try {
			String query = "SELECT EXTRACT(YEAR FROM recruitment_start) AS year FROM event WHERE english_e_name = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, event);
			ResultSet rset = stmt.executeQuery();

			if (rset.next()) 
				date = rset.getString("year");
			rset.close();
			
			return date;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String recruitmentStartDateMonth(String event) {
		String date = new String();
		try {
			String query = "SELECT EXTRACT(MONTH FROM recruitment_start) AS month FROM event WHERE english_e_name = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, event);
			ResultSet rset = stmt.executeQuery();

			if (rset.next()) 
				date = rset.getString("month");
			rset.close();
			
			return date;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String recruitmentStartDateDay(String event) {
		String date = new String();
		try {
			String query = "SELECT EXTRACT(DAY FROM recruitment_start) AS day FROM event WHERE english_e_name = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, event);
			ResultSet rset = stmt.executeQuery();

			if (rset.next()) 
				date = rset.getString("day");
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
	
	public String recruitmentEndDateYear(String event) {
		String date = new String();
		try {
			String query = "SELECT EXTRACT(YEAR FROM recruitment_end) AS year FROM event WHERE english_e_name = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, event);
			ResultSet rset = stmt.executeQuery();

			if (rset.next()) 
				date = rset.getString("year");
			rset.close();
			
			return date;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String recruitmentEndDateMonth(String event) {
		String date = new String();
		try {
			String query = "SELECT EXTRACT(MONTH FROM recruitment_end) AS month FROM event WHERE english_e_name = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, event);
			ResultSet rset = stmt.executeQuery();

			if (rset.next()) 
				date = rset.getString("month");
			rset.close();
			
			return date;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String recruitmentEndDateDay(String event) {
		String date = new String();
		try {
			String query = "SELECT EXTRACT(DAY FROM recruitment_end) AS day FROM event WHERE english_e_name = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, event);
			ResultSet rset = stmt.executeQuery();

			if (rset.next()) 
				date = rset.getString("day");
			rset.close();
			
			return date;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public int recruitmentLimit(String event) {
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

	public ArrayList<ArrayList<String>> geoMap(String event) {
		ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
		ArrayList<String> data = new ArrayList<String>();
		try {
			String query = "SELECT countries.country AS country, COUNT(*) AS total_participants_per_country FROM(SELECT country FROM participants, event_participants, event WHERE country IS NOT NULL AND participants.participant_id =  event_participants.participant_id AND english_e_name = ? AND event.event_id = event_participants.event_id UNION ALL SELECT country2 FROM participants, event_participants, event WHERE country2 IS NOT NULL AND participants.participant_id =  event_participants.participant_id AND english_e_name = ? AND event.event_id = event_participants.event_id UNION ALL SELECT country3 FROM participants, event_participants, event WHERE country3 IS NOT NULL AND participants.participant_id =  event_participants.participant_id AND english_e_name = ? AND event.event_id = event_participants.event_id)  AS countries GROUP BY country";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, event);
			stmt.setString(2, event);
			stmt.setString(3, event);
			ResultSet rset = stmt.executeQuery();

			while (rset.next()) {
				data.add(rset.getString("country"));
				data.add(rset.getString("total_participants_per_country"));
			}

			rset.close();
			list.add(data);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Boolean insertParticipantData(String event, String name, String fname, String gender, String email,
			String phone, Date birthdate, String career, String company, String country, String country2,
			String country3, String zip, String pref, String address, String allergy, String way) throws IOException {
		try {
			String query = "SELECT event_id FROM event WHERE english_e_name = \"" + event + "\"";
			ResultSet rset = stmt.executeQuery(query);
			String event_id = new String();
			while (rset.next()) {
				event_id = rset.getString("event_id");
			}
			rset.close();
			stmt.close();
			
			int res = 1;
			String participantId = new String();
			
			while (res != 0) {
				GenerateSecureString gen = new GenerateSecureString();
				participantId = gen.string(11);
				
				String query1 = "SELECT * FROM participants WHERE participant_id = ?";
				PreparedStatement stmt2 = conn.prepareStatement(query1);
				stmt2.setString(1, participantId);
				ResultSet rset1 = stmt2.executeQuery();
				res = rset1.getRow();
			}
			

			String insert = "INSERT INTO participants (participant_id, name, fname, gender, email, phone, birthdate, country, country2, country3, career, company, zip, address, allergy) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement stmt1 = conn.prepareStatement(insert);
			stmt1.setString(1, participantId);
			stmt1.setString(2, name);
			stmt1.setString(3, fname);
			stmt1.setString(4, gender);
			stmt1.setString(5, email);
			stmt1.setString(6, phone);
			stmt1.setDate(7, birthdate);
			stmt1.setString(8, country);
			stmt1.setString(9, country2);
			stmt1.setString(10, country3);
			stmt1.setString(11, career);
			stmt1.setString(12, company);
			stmt1.setString(13, zip);
			if (pref == null || address == null)
				stmt1.setString(14, null);
			else
				stmt1.setString(14, pref + address);
			stmt1.setString(15, allergy);
			stmt1.executeUpdate();
			stmt1.close();

			String insert1 = "INSERT INTO event_participants (event_id, participant_id, way) VALUES(?, ?, ?)";
			PreparedStatement stmt2 = conn.prepareStatement(insert1);
			stmt2.setString(1, event_id);
			stmt2.setString(2, participantId);
			stmt2.setString(3, way);
			stmt2.executeUpdate();
			stmt2.close();

			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public ArrayList<ArrayList<String>> careerPieChart(String event) {
		ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
		ArrayList<String> data = new ArrayList<String>();
		try {
			String total = "SET @total = (SELECT COUNT(*) FROM event, event_participants, participants WHERE event.event_id = event_participants.event_id AND participants.participant_id = event_participants.participant_id AND english_e_name = ?)";
			PreparedStatement stmt = conn.prepareStatement(total);
			stmt.setString(1, event);
			stmt.executeUpdate();

			String query = "SELECT career, ((COUNT(*)/@total)*100) AS percentage_per_career FROM participants, event_participants, event WHERE  english_e_name = ? AND participants.participant_id = event_participants.participant_id AND event.event_id = event_participants.event_id GROUP BY career";
			PreparedStatement stmt1 = conn.prepareStatement(query);
			stmt1.setString(1, event);
			ResultSet rset = stmt1.executeQuery();

			while (rset.next()) {
				data.add(rset.getString("career"));
				data.add(rset.getString("percentage_per_career"));
			}
			rset.close();
			list.add(data);
			return list;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<ArrayList<String>> wayPieChart(String event) {
		ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
		ArrayList<String> data = new ArrayList<String>();
		try {
			String total = "SET @total = (SELECT COUNT(*) FROM event, event_participants, participants WHERE event.event_id = event_participants.event_id AND participants.participant_id = event_participants.participant_id AND english_e_name = ?)";
			PreparedStatement stmt = conn.prepareStatement(total);
			stmt.setString(1, event);
			stmt.executeUpdate();

			String query = "SELECT way, ((COUNT(*)/@total)*100) AS percentage_per_way FROM participants, event_participants, event WHERE  english_e_name = ? AND participants.participant_id = event_participants.participant_id AND event.event_id = event_participants.event_id GROUP BY way";
			PreparedStatement stmt1 = conn.prepareStatement(query);
			stmt1.setString(1, event);
			ResultSet rset = stmt1.executeQuery();

			while (rset.next()) {
				data.add(rset.getString("way"));
				data.add(rset.getString("percentage_per_way"));
			}
			rset.close();
			list.add(data);
			return list;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<String> companyTable(String event) {
		ArrayList<String> data = new ArrayList<String>();
		try {
			String query = "SELECT company, COUNT(*) AS participant_per_company FROM participants, event_participants, event WHERE english_e_name = ? AND event.event_id = event_participants.event_id AND participants.participant_id = event_participants.participant_id GROUP BY company";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, event);
			ResultSet rset = stmt.executeQuery();

			while (rset.next()) {
				data.add(rset.getString("company"));
				data.add(rset.getString("participant_per_company"));
			}
			return data;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<String> commentsTable(String event) {
		ArrayList<String> data = new ArrayList<String>();
		try {
			String query = "SELECT event_survey.satisfaction, event_survey.date, event_survey.time, event_survey.comments FROM event_survey, event WHERE english_e_name = ? AND event.event_id = event_survey.event_id ORDER BY survey_id DESC";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, event);
			ResultSet rset = stmt.executeQuery();

			while (rset.next()) {
				data.add(rset.getString("satisfaction"));
				data.add(rset.getString("date") + " " + rset.getString("time"));
				data.add(rset.getString("comments"));
			}
			return data;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<ArrayList<String>> improvementPlansTable(String event) {
		ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
		ArrayList<String> data = new ArrayList<String>();
		try {
			String query = "SELECT event_survey.date, event_survey.time, event_survey.improvements FROM event_survey, event WHERE english_e_name = ? AND event.event_id = event_survey.event_id AND event_survey.date <= event.recruitment_end ORDER BY survey_id DESC";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, event);
			ResultSet rset = stmt.executeQuery();

			while (rset.next()) {
				if (rset.getString("improvements").length() > 0) {
					data.add(rset.getString("date") + " " + rset.getString("time"));
					data.add(rset.getString("improvements"));
				}
			}
			list.add(data);
			return list;
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
	
	public ArrayList<Boolean> formQuestion(String event) {
		ArrayList<Boolean> data = new ArrayList<Boolean>();
		try {
			String query = "SELECT name, fname, gender, email, phone, birthdate, career, company, country, country2, country3, address, allergy, way, parent_confirmation, confirmation FROM event, form_questions WHERE event.event_id = form_questions.event_id AND english_e_name = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, event);
			ResultSet rset = stmt.executeQuery();
			while (rset.next()) {
				data.add(rset.getBoolean("name"));
				data.add(rset.getBoolean("fname"));
				data.add(rset.getBoolean("gender"));
				data.add(rset.getBoolean("email"));
				data.add(rset.getBoolean("phone"));
				data.add(rset.getBoolean("birthdate"));
				data.add(rset.getBoolean("career"));
				data.add(rset.getBoolean("company"));
				data.add(rset.getBoolean("country"));
				data.add(rset.getBoolean("country2"));
				data.add(rset.getBoolean("country3"));
				data.add(rset.getBoolean("address"));
				data.add(rset.getBoolean("allergy"));
				data.add(rset.getBoolean("way"));
				data.add(rset.getBoolean("confirmation"));
				data.add(rset.getBoolean("parent_confirmation"));
			}
			rset.close();
			return data;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<Boolean> surveyQuestion(String event) {
		ArrayList<Boolean> data = new ArrayList<Boolean>();
		try {
			String query = "SELECT survey_questions.name, survey_questions.fname, survey_questions.satisfaction, survey_questions.comments, survey_questions.transportation, survey_questions.improvements, survey_questions.repetition FROM survey_questions, event WHERE event.event_id = survey_questions.event_id AND english_e_name = ?";

			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, event);
			ResultSet rset = stmt.executeQuery();

			while (rset.next()) {
				data.add(rset.getBoolean("name"));
				data.add(rset.getBoolean("fname"));
				data.add(rset.getBoolean("satisfaction"));
				data.add(rset.getBoolean("comments"));
				data.add(rset.getBoolean("transportation"));
				data.add(rset.getBoolean("improvements"));
				data.add(rset.getBoolean("repetition"));
			}
			rset.close();
			return data;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Boolean insertSurveyData(String event, String name, String fname, String satisfaction, String comments,
			String improvement, ArrayList<Boolean> transportation, String repetition) {
		String event_id = new String();
		try {
			String query = "SELECT event_id FROM event WHERE english_e_name = \"" + event + "\"";
			ResultSet rset = stmt.executeQuery(query);
			while (rset.next())
				event_id = rset.getString("event_id");
			rset.close();

			String insert = "INSERT INTO event_survey (event_id, date, time, name, fname, satisfaction, comments, improvements, repetition) VALUES(?, now(), now(), ?, ?, ?, ?, ?, ?)";
			PreparedStatement stmt = conn.prepareStatement(insert);
			stmt.setString(1, event_id);
			stmt.setString(2, name);
			stmt.setString(3, fname);
			stmt.setString(4, satisfaction);
			stmt.setString(5, comments);
			stmt.setString(6, improvement);
			stmt.setString(7, repetition);
			stmt.executeUpdate();
			stmt.close();

			String insert1 = "INSERT INTO event_transportation (survey_id, train, bus, four_wheel, two_wheel, others) VALUES(LAST_INSERT_ID(), ?, ?, ?, ?, ?)";
			PreparedStatement stmt1 = conn.prepareStatement(insert1);
			stmt1.setBoolean(1, transportation.get(0));
			stmt1.setBoolean(2, transportation.get(1));
			stmt1.setBoolean(3, transportation.get(2));
			stmt1.setBoolean(4, transportation.get(3));
			stmt1.setBoolean(5, transportation.get(4));
			stmt1.executeUpdate();
			stmt1.close();

			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public ArrayList<Double> ratingData(String event) {
		ArrayList<Double> list = new ArrayList<Double>();
		try {
			String query = "SELECT COUNT(*) AS totalRatings, ROUND(SUM(satisfaction)/COUNT(*), 1) AS averageRatings FROM event_survey, event WHERE english_e_name = ? AND event.event_id = event_survey.event_id";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, event);
			ResultSet rset = stmt.executeQuery();

			while (rset.next()) {
				list.add(rset.getDouble("totalRatings"));
				list.add(rset.getDouble("averageRatings"));
			}

			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<Double> totalPerSatisfaction(String event) {
		ArrayList<Double> list = new ArrayList<Double>();
		try {

			String query = "SELECT satisfaction, COUNT(*) AS total FROM event_survey, event WHERE english_e_name = ? AND event.event_id = event_survey.event_id GROUP BY satisfaction ORDER BY satisfaction DESC";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, event);
			ResultSet rset = stmt.executeQuery();

			while (rset.next()) {
				list.add(rset.getDouble("satisfaction"));
				list.add(rset.getDouble("total"));
			}
			return list;
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

	public Boolean changeEventDetails(String event, String eventName, String eventPlace, String eventDate,
			String recruitmentStartDate, String recruitmentEndDate, String eventTime, String recruitNo) {
		try {
			String update = "UPDATE event SET e_name = ?, place = ?, recruitment_start = ?, recruitment_end = ?, date = ?, time = ?, recruitno = ? WHERE english_e_name = ?";
			PreparedStatement stmt = conn.prepareStatement(update);
			stmt.setString(1, eventName);
			stmt.setString(2, eventPlace);
			stmt.setString(3, recruitmentStartDate);
			stmt.setString(4, recruitmentEndDate);
			stmt.setString(5, eventDate);
			stmt.setString(6, eventTime);
			stmt.setString(7, recruitNo);
			stmt.setString(8, event);
			int res = stmt.executeUpdate();
			stmt.close();

			if (res > 0)
				return true;
			else
				return false;
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

	public Boolean changeFormQuestion(String event, Boolean name, Boolean fname, Boolean gender, Boolean email,
			Boolean phone, Boolean birthdate, Boolean company, Boolean career, Boolean country, Boolean country2,
			Boolean country3, Boolean address, Boolean allergy, Boolean way, Boolean confirmation,
			Boolean parent_confirmation) {
		String event_id = new String();
		try {
			String query = "SELECT event_id FROM event WHERE english_e_name = \"" + event + "\"";
			ResultSet rset = stmt.executeQuery(query);
			if (rset.next())
				event_id = rset.getString("event_id");
			rset.close();

			String update = "UPDATE form_questions SET name = ?, fname = ?, gender = ?, email = ?, phone = ?, birthdate = ?, company = ?, career = ?, country = ?, country2 = ?, country3 = ?, zip = ?, address = ?, allergy = ?, way = ?, confirmation = ?, parent_confirmation = ? WHERE event_id = ?";
			PreparedStatement stmt = conn.prepareStatement(update);
			stmt.setBoolean(1, name);
			stmt.setBoolean(2, fname);
			stmt.setBoolean(3, gender);
			stmt.setBoolean(4, email);
			stmt.setBoolean(5, phone);
			stmt.setBoolean(6, birthdate);
			stmt.setBoolean(7, company);
			stmt.setBoolean(8, career);
			stmt.setBoolean(9, country);
			stmt.setBoolean(10, country2);
			stmt.setBoolean(11, country3);
			stmt.setBoolean(12, address);
			stmt.setBoolean(13, address);
			stmt.setBoolean(14, allergy);
			stmt.setBoolean(15, way);
			stmt.setBoolean(16, confirmation);
			stmt.setBoolean(17, parent_confirmation);
			stmt.setString(18, event_id);
			int res = stmt.executeUpdate();
			if (res > 0)
				return true;
			else
				return false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public Boolean changeSurveyQuestion(String event, Boolean name, Boolean fname, Boolean satisfaction,
			Boolean comments, Boolean transportation, Boolean improvements, Boolean repetition) {
		String event_id = new String();
		try {
			String query = "SELECT event_id FROM event WHERE english_e_name = \"" + event + "\"";
			ResultSet rset = stmt.executeQuery(query);
			if (rset.next())
				event_id = rset.getString("event_id");
			rset.close();

			String update = "UPDATE survey_questions SET name = ?, fname = ?, satisfaction = ?, comments = ?,  transportation = ?, improvements = ?, repetition = ? WHERE event_id = ?";
			PreparedStatement stmt = conn.prepareStatement(update);
			stmt.setBoolean(1, name);
			stmt.setBoolean(2, fname);
			stmt.setBoolean(3, satisfaction);
			stmt.setBoolean(4, comments);
			stmt.setBoolean(5, transportation);
			stmt.setBoolean(6, improvements);
			stmt.setBoolean(7, repetition);
			stmt.setString(8, event_id);
			int res = stmt.executeUpdate();
			if (res > 0)
				return true;
			else
				return false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public ArrayList<String> formQuestionsColumn(String event){
		ArrayList<String> data = new ArrayList<String>();
		String eventId = new String();
		try {
			String query = "SELECT event_id FROM event WHERE english_e_name = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, event);
			ResultSet rset = stmt.executeQuery();
			
			while(rset.next())
				eventId = rset.getString("event_id");
			
			String query1 = "SELECT col FROM ( SELECT col, CASE s.col WHEN 'name' THEN name WHEN 'fname' THEN fname WHEN 'gender' THEN gender WHEN 'email' THEN email WHEN 'phone' THEN phone WHEN 'birthdate' THEN birthdate WHEN 'country' THEN country WHEN 'country2' THEN country2 WHEN 'country3' THEN country3 WHEN 'career' THEN career WHEN 'company' THEN company WHEN 'zip' THEN zip WHEN 'address' THEN address WHEN 'allergy' THEN allergy WHEN 'way' THEN way END AS val FROM form_questions CROSS JOIN ( SELECT 'name' AS col UNION ALL SELECT 'fname' UNION ALL SELECT 'gender' UNION ALL  SELECT 'email' UNION ALL SELECT 'phone' UNION ALL SELECT 'birthdate' UNION ALL SELECT 'country' UNION ALL SELECT 'country2' UNION ALL SELECT 'country3' UNION ALL SELECT 'career' UNION ALL SELECT 'company' UNION ALL SELECT 'zip' UNION ALL SELECT 'address' UNION ALL SELECT 'allergy' UNION ALL SELECT 'way') s  WHERE event_id = ? ) s WHERE val = true";
			PreparedStatement stmt1 = conn.prepareStatement(query1);
			stmt1.setString(1, eventId);
			ResultSet rset1 = stmt1.executeQuery();
			
			while(rset1.next())
				data.add(rset1.getString("col"));
			
			System.out.println(data);
			
			rset.close();
			rset1.close();
			stmt1.close();
			stmt.close();
			
			return data;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public ArrayList<String> surveyQuestionsColumn(String event){
		ArrayList<String> data = new ArrayList<String>();
		String eventId = new String();
		try {
			String query = "SELECT event_id FROM event WHERE english_e_name = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, event);
			ResultSet rset = stmt.executeQuery();
			
			while(rset.next())
				eventId = rset.getString("event_id");
			
			String query1 = "SELECT col FROM ( SELECT col, CASE s.col WHEN 'name' THEN name WHEN 'fname' THEN fname WHEN 'satisfaction' THEN satisfaction WHEN 'comments' THEN comments  WHEN 'improvements' THEN improvements END AS val FROM survey_questions CROSS JOIN ( SELECT 'name' AS col UNION ALL SELECT 'fname' UNION ALL SELECT 'satisfaction' UNION ALL  SELECT 'comments' UNION ALL SELECT 'improvements') s  WHERE event_id = ? ) s WHERE val = true";
			PreparedStatement stmt1 = conn.prepareStatement(query1);
			stmt1.setString(1, eventId);
			ResultSet rset1 = stmt1.executeQuery();
			
			while(rset1.next())
				data.add(rset1.getString("col"));
			
			System.out.println(data);
			
			rset.close();
			rset1.close();
			stmt1.close();
			stmt.close();
			
			return data;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public ArrayList<String> formQuestionColumnNames(ArrayList<String> columnList){
		ArrayList<String> data = new ArrayList<String>();
		
		data.add("�\����");
		
		for(String string : columnList) {
			switch(string) {
			case "name":
				data.add("���O");
				break;
			case "fname":
				data.add("�t���K�i");
				break;
			case "gender":
				data.add("����");
				break;
			case "email":
				data.add("���[���A�h���X");
				break;
			case "phone":
				data.add("�d�b�ԍ�");
				break;
			case "birthdate":
				data.add("���N����");
				break;
			case "country":
				data.add("���[�c������");
				break;
			case "country2":
				data.add("���[�c������");
				break;
			case "country3":
				data.add("���[�c������");
				break;
			case "career":
				data.add("�w�N�܂��͐E��");
				break;
			case "company":
				data.add("�w�Z���܂��͉�Ж�");
				break;
			case "allergy":
				data.add("�A�����M�[");
				break;
			case "zip":
				data.add("�X�֔ԍ�");
				break;
			case "address":
				data.add("�Z��");
				break;
			case "way":
				data.add("�Q���̂�������");
				break;
			}
		}
		return data;
	}
	
	public String formQuestionColumnName(String column) {

		String columnName = new String();
		
		switch (column) {
		case "name":
			columnName = "���O";
			break;
		case "fname":
			columnName = "�t���K�i";
			break;
		case "gender":
			columnName = "����";
			break;
		case "email":
			columnName = "���[���A�h���X";
			break;
		case "phone":
			columnName = "�d�b�ԍ�";
			break;
		case "birthdate":
			columnName = "���N����";
			break;
		case "country":
			columnName = "���[�c������";
			break;
		case "country2":
			columnName = "���[�c������";
			break;
		case "country3":
			columnName = "���[�c������";
			break;
		case "career":
			columnName = "�w�N�܂��͐E��";
			break;
		case "company":
			columnName = "�w�Z���܂��͉�Ж�";
			break;
		case "allergy":
			columnName = "�A�����M�[";
			break;
		case "zip":
			columnName = "�X�֔ԍ�";
			break;
		case "address":
			columnName = "�Z��";
			break;
		case "way":
			columnName = "�Q���̂�������";
			break;
		}
		
		return columnName;
	}

	public ArrayList<String> surveyQuestionColumnName(ArrayList<String> columnList) {
		ArrayList<String> data = new ArrayList<String>();

		data.add("�\����");

		for (String string : columnList) {
			switch (string) {
			case "name":
				data.add("���O");
				break;
			case "fname":
				data.add("�t���K�i");
				break;
			case "satisfaction":
				data.add("�����x");
				break;
			case "comments":
				data.add("�R�����g");
				break;
			case "improvements":
				data.add("���P��");
				break;
			}
		}
		return data;
	}
	
	public ArrayList<String> eventPageMember(String event){
		ArrayList<String> data = new ArrayList<String>();
		try {
			String query = "SELECT user_profile_picture.path, users.name FROM users, user_event, event, user_profile_picture WHERE users.user_id = user_profile_picture.user_id AND users.user_id = user_event.user_id AND event.event_id = user_event.event_id AND event.english_e_name = ?";
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
package jp.myouth.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import jp.myouth.security.GenerateSecureString;

public class Participants {
	private static Connection conn = null;
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
	
	public ArrayList<String> getAllParticipantIds(){
		ArrayList<String> data = new ArrayList<String>();
		try {
			String query = "SELECT participant_id FROM participants";
			PreparedStatement stmt = conn.prepareStatement(query);
			ResultSet rset = stmt.executeQuery();
			
			while(rset.next()) {
				data.add(rset.getString("participant_id"));
			}
			rset.close();
			return data;
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getName(String participantId) {
		try {
			String query = "SELECT * FROM participants WHERE participant_id = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, participantId);
			ResultSet rset = stmt.executeQuery();
			
			if(rset.next()) {
				return rset.getString("name");
			}
				
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getFname(String participantId) {
		try {
			String query = "SELECT * FROM participants WHERE participant_id = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, participantId);
			ResultSet rset = stmt.executeQuery();
			
			if(rset.next()) {
				return rset.getString("fname");
			}
				
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getGender(String participantId) {
		try {
			String query = "SELECT * FROM participants WHERE participant_id = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, participantId);
			ResultSet rset = stmt.executeQuery();
			
			if(rset.next()) {
				return rset.getString("gender");
			}
				
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getEmail(String participantId) {
		try {
			String query = "SELECT * FROM participants WHERE participant_id = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, participantId);
			ResultSet rset = stmt.executeQuery();
			
			if(rset.next()) {
				return rset.getString("email");
			}
				
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getBirthdate(String participantId) {
		try {
			String query = "SELECT * FROM participants WHERE participant_id = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, participantId);
			ResultSet rset = stmt.executeQuery();
			
			if(rset.next()) {
				return rset.getString("birthdate");
			}
				
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getCountry(String participantId) {
		try {
			String query = "SELECT * FROM participants WHERE participant_id = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, participantId);
			ResultSet rset = stmt.executeQuery();
			
			if(rset.next()) {
				return rset.getString("country");
			}
				
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getCountry2(String participantId) {
		try {
			String query = "SELECT * FROM participants WHERE participant_id = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, participantId);
			ResultSet rset = stmt.executeQuery();
			
			if(rset.next()) {
				return rset.getString("country2");
			}
				
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getCareer(String participantId) {
		try {
			String query = "SELECT * FROM participants WHERE participant_id = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, participantId);
			ResultSet rset = stmt.executeQuery();
			
			if(rset.next()) {
				return rset.getString("career");
			}
				
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getCompany(String participantId) {
		try {
			String query = "SELECT * FROM participants WHERE participant_id = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, participantId);
			ResultSet rset = stmt.executeQuery();
			
			if(rset.next()) {
				return rset.getString("company");
			}
				
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public int allEventsTotalParticipants() {
		try {
			int data = 0;
			
			String query = "SELECT COUNT(*) FROM participants;";
			PreparedStatement stmt = conn.prepareStatement(query);
			ResultSet rset = stmt.executeQuery();
			
			if(rset.next())
				data = rset.getInt("COUNT(*)");
			
			return data;
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public int totalParticipants(String event, String periodType, String startPeriod, String endPeriod) {
		String query = new String();
		try {
			Integer total = 0;
			
			query = "SELECT COUNT(*) FROM participants, event_participants, event WHERE participants.participant_id = event_participants.participant_id AND event.event_id = event_participants.event_id AND english_e_name = ? ";
			
			if(periodType.equals("freePeriod"))
				query += "AND event_participants.time >= ? AND event_participants.time <= ?";
			else if(periodType.equals("recruitmentPeriod"))
				query += "AND event_participants.time >= event.recruitment_start AND event_participants.time <= event.recruitment_end";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, event);
			
			if(periodType.equals("freePeriod")) {
				stmt.setString(2, startPeriod);
				stmt.setString(3, endPeriod);
			}
			
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

	public ArrayList<String> participantsInfoLess(String event, String periodType, String startPeriod, String endPeriod) {
		ArrayList<String> data = new ArrayList<String>();
		String query = new String();
		try {
			
			query = "SELECT event_participants.time, participants.name, participants.participant_id FROM participants, event_participants, event WHERE english_e_name = ? AND event_participants.participant_id = participants.participant_id AND event.event_id = event_participants.event_id ";
			
			if(periodType.equals("allPeriod")) 
				query += "ORDER BY event_participants.time DESC";
			else
				query += "AND DATE(event_participants.time) >= ? AND DATE(event_participants.time) <= ? ORDER BY event_participants.time DESC";
			
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, event);
			
			if(!periodType.equals("allPeriod")) {
				stmt.setString(2, startPeriod);
				stmt.setString(3, endPeriod);
			}
			
			ResultSet rset = stmt.executeQuery();

			while (rset.next()) {
				data.add(rset.getString("time"));
				data.add(rset.getString("name"));
				data.add(rset.getString("participant_id"));
			}

			return data;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public ArrayList<String> participantsEmailAddress(String event, String senderEmail, String periodType, String startPeriod, String endPeriod) {
		ArrayList<String> data = new ArrayList<String>();
		String query = new String();
		try {
			query = "SELECT DISTINCT email FROM participants, event, event_participants WHERE english_e_name = ? AND participants.participant_id = event_participants.participant_id AND event_participants.event_id = event.event_id AND participants.email != ? AND participants.email NOT IN(SELECT email FROM bounced_recipients) ";
			
			if(!periodType.equals("allPeriod")) 
				query += "AND event_participants.join_date >= ? AND event_participants.join_date <= ? ";
			
			
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, event);
			stmt.setString(2, senderEmail);
			
			if(!periodType.equals("allPeriod")) {
				stmt.setString(3, startPeriod);
				stmt.setString(4, endPeriod);
			}
			
			ResultSet rset = stmt.executeQuery();
			while (rset.next())
				data.add(rset.getString("email"));

			return data;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Integer totalParticipantsByEmailAddress(String event, String senderEmail) {
		int data = 0;
		try {
			String query = "SELECT COUNT(DISTINCT email) AS totalParticipants FROM participants, event, event_participants WHERE english_e_name = ? AND participants.participant_id = event_participants.participant_id AND event_participants.event_id = event.event_id AND participants.email != ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, event);
			stmt.setString(2, senderEmail);
			ResultSet rset = stmt.executeQuery();
			
			if(rset.next())
				data = rset.getInt("totalParticipants");
			
			return data;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public static void main(String[] args) throws SQLException {
		Questions db = new Questions();
		db.open();
		Participants db1 = new Participants();
		db1.open();
		Library lib = new Library();
		lib.open();
		ArrayList<String> participantIds = db1.getAllParticipantIds();
		String templateId = "Z6FEhuzvJLfOOdmYtUIO5QxvaoL3vyh85IhH6nKLX6D0Z8dbtXpHCyn9wcyHh8EzrHAn3LnDl2nKEX6H";
		for(String participantId : participantIds) {
			System.out.println(participantId);
			String transactionId = new GenerateSecureString().string(50);
			db.insertTransaction(transactionId, templateId, participantId);
			db.insertParticipantInfo(participantId, db1.getName(participantId), db1.getFname(participantId), db1.getGender(participantId), db1.getEmail(participantId), db1.getBirthdate(participantId));
			ArrayList<String> questionIds = db.getQuestionIds(templateId);
			db.insertAnswer(transactionId, questionIds.get(0), lib.getCountryCode(db1.getCountry(participantId), "en"));
			db.insertAnswer(transactionId, questionIds.get(1), lib.getCountryCode(db1.getCountry2(participantId), "en"));
			db.insertOptionTypeAnswer(transactionId, questionIds.get(2), db1.getCareer(participantId));
		}
		lib.close();
		db1.close();
		db.close();
	}
}

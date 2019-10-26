package jp.myouth.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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

	public int totalSurveyAnswers(String event, String periodType, String startPeriod, String endPeriod) {
		String query = new String();
		try {
			Integer total = 0;
			
			query = "SELECT COUNT(*) FROM event_survey, event WHERE event.event_id = event_survey.event_id AND english_e_name = ? ";
			
			if(periodType.equals("freePeriod") || periodType.equals("recruitmentPeriod"))
				query += "AND join_date >= ? AND join_date <= ?";
			
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, event);
			
			if(periodType.equals("freePeriod") || periodType.equals("recruitmentPeriod")) {
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
	
	public ArrayList<String> participants(String event, String periodType, String startPeriod, String endPeriod) {
		ArrayList<String> list = new ArrayList<String>();
		String query = new String();
		try {
			String update = "UPDATE participants SET gender = NULL, phone = NULL, country2 = NULL, country3 = NULL, zip = NULL, address = NULL, allergy = NULL WHERE gender = \"null\" OR phone = \"null\" OR country2 = \"null\" OR country3 = \"null\" OR zip = \"null\" OR address = \"nullnull\" OR allergy = \"null\"";
			stmt.executeUpdate(update);
			
			query = "SELECT event_participants.time,";
			
			Events db = new Events();
			db.open();
			ArrayList<String> formQuestionColumn = db.formQuestionsColumn(event);
			list = db.formQuestionColumnNames(formQuestionColumn);
			db.close();
			
			int i = 0;
			for(String string : formQuestionColumn) {
				if(i == formQuestionColumn.size()-1)
					query += string + " ";
				else
					query += string + ", ";
				i++;
			}
			
			query += "FROM participants, event, event_participants WHERE english_e_name = ? AND participants.participant_id = event_participants.participant_id AND event.event_id = event_participants.event_id ";
			
			if(periodType.equals("allPeriod"))
				query += "ORDER BY event_participants.time DESC";
			else if(periodType.equals("recruitmentPeriod"))
				query += "AND event_participants.time >= (SELECT recruitment_start FROM event WHERE english_e_name = ?) AND event_participants.time <= (SELECT recruitment_end FROM event WHERE english_e_name = ?) ORDER BY event_participants.time DESC";
			else
				query += "AND event_participants.time >= ? AND event_participants.time <= ? ORDER BY event_participants.time DESC";
			
			System.out.println(query);
			
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, event);
			
			if(periodType.equals("recruitmentPeriod")) {
				stmt.setString(2, event);
				stmt.setString(3, event);
			} else if(periodType.equals("freePeriod")) {
				stmt.setString(2, startPeriod);
				stmt.setString(3, endPeriod);
			}
				
			ResultSet rset = stmt.executeQuery();
			
			while (rset.next()) {
				list.add(rset.getString("time"));
				for(String string : formQuestionColumn) {
					list.add(rset.getString(string));
				}
			}
			
			rset.close();
			
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public ArrayList<String> survey(String event, String periodType, String startPeriod, String endPeriod) {
		ArrayList<String> list = new ArrayList<String>();
		String query = new String();
		try {
			String update = "UPDATE participants SET gender = NULL, phone = NULL, country2 = NULL, country3 = NULL, zip = NULL, address = NULL, allergy = NULL WHERE gender = \"null\" OR phone = \"null\" OR country2 = \"null\" OR country3 = \"null\" OR zip = \"null\" OR address = \"nullnull\" OR allergy = \"null\"";
			stmt.executeUpdate(update);
			
			query = "SELECT event_survey.date, event_survey.time,";
			
			Events db = new Events();
			db.open();
			ArrayList<String> surveyQuestionColumn = db.surveyQuestionsColumn(event);
			list = db.surveyQuestionColumnName(surveyQuestionColumn);
			db.close();
			
			int i = 0;
			for(String string : surveyQuestionColumn) {
				if(i == surveyQuestionColumn.size()-1)
					query += string + " ";
				else
					query += string + ", ";
				i++;
			}
			
			query += "FROM event_survey, event WHERE english_e_name = ? AND event.event_id = event_survey.event_id ";
			
			if(periodType.equals("allPeriod"))
				query += "ORDER BY event_survey.event_id DESC";
			else
				query += "AND date >= ? AND date <= ? ORDER BY event_survey.event_id DESC";
			
			System.out.println(query);
			
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, event);
			
			if(periodType.equals("freePeriod") || periodType.equals("recruitmentPeriod")) {
				stmt.setString(2, startPeriod);
				stmt.setString(3, endPeriod);
			}
				
			ResultSet rset = stmt.executeQuery();
			
			while (rset.next()) {
				list.add(rset.getString("date")+" "+rset.getString("time"));
				for(String string : surveyQuestionColumn) {
					list.add(rset.getString(string));
				}
			}
			
			rset.close();
			
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
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
	
	public ArrayList<String> participantInfoFull(String participantId, String event) {
		ArrayList<String> list = new ArrayList<String>();
		try {
			Events db = new Events();
			db.open();
			ArrayList<String> formQuestionColumn = db.formQuestionsColumn(event);
			
			String query = "SELECT ";
			
			int i = 0;
			for(String string : formQuestionColumn) {
				if(i == formQuestionColumn.size()-1)
					query += string + " ";
				else
					query += string + ", ";
				i++;
			}
			query +=  "FROM participants, event_participants WHERE participants.participant_id = event_participants.participant_id AND participants.participant_id = ?";
			
			//System.out.println(query);
			
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, participantId);
			ResultSet rset = stmt.executeQuery();
			
			while (rset.next()) {
				for(String string : formQuestionColumn) {
					list.add(db.formQuestionColumnName(string));
					list.add(rset.getString(string));
				}
			}
			db.close();
			
			return list;
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/*
	 * イベントの全参加者のメールアドレスを取得する
	 */

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
}

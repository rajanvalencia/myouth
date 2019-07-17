package jp.myouth.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Participants {
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

	public ArrayList<ArrayList<String>> participantsInfo(String eventname) {
		ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
		ArrayList<String> list = new ArrayList<String>();
		try {
			String query = "SELECT fname, email, phone, country, company FROM participants, event_participants, event WHERE english_e_name=\""
					+ eventname
					+ "\" AND event_participants.participant_id = participants.participant_id AND event.event_id = event_participants.event_id AND join_date >= recruitment_start AND join_date <= recruitment_end ORDER BY participants.participant_id DESC";
			ResultSet rset = stmt.executeQuery(query);
			while (rset.next()) {
				list.add(rset.getString("fname"));
				list.add(rset.getString("email"));
				list.add(rset.getString("phone"));
				list.add(rset.getString("country"));
				list.add(rset.getString("company"));
				data.add(list);
			}
			return data;
		} catch (Exception e) {
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
			
			query = "SELECT join_date, join_time,";
			
			Events db = new Events();
			db.open();
			ArrayList<String> formQuestionColumn = db.formQuestionsColumn(event);
			list = db.formQuestionColumnName(formQuestionColumn);
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
				query += "ORDER BY participants.participant_id DESC";
			else
				query += "AND join_date >= ? AND join_date <= ? ORDER BY participants.participant_id DESC";
			
			System.out.println(query);
			
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, event);
			
			if(periodType.equals("freePeriod") || periodType.equals("recruitmentPeriod")) {
				stmt.setString(2, startPeriod);
				stmt.setString(3, endPeriod);
			}
				
			ResultSet rset = stmt.executeQuery();
			
			while (rset.next()) {
				list.add(rset.getString("join_date")+" "+rset.getString("join_time"));
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
			
			query = "SELECT event_participants.join_date, event_participants.join_time, participants.name, participants.participant_id FROM participants, event_participants, event WHERE english_e_name = ? AND event_participants.participant_id = participants.participant_id AND event.event_id = event_participants.event_id ";
			
			if(periodType.equals("allPeriod")) 
				query += "ORDER BY participants.participant_id DESC";
			else
				query += "AND join_date >= ? AND join_date <= ? ORDER BY participants.participant_id DESC";
			
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, event);
			
			if(!periodType.equals("allPeriod")) {
				stmt.setString(2, startPeriod);
				stmt.setString(3, endPeriod);
			}
			
			ResultSet rset = stmt.executeQuery();

			while (rset.next()) {
				data.add(rset.getString("join_date"));
				data.add(rset.getString("join_time"));
				data.add(rset.getString("name"));
				data.add(rset.getString("participant_id"));
			}

			return data;
		} catch (Exception e) {
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
			query = "SELECT DISTINCT email FROM participants, event, event_participants WHERE english_e_name = ? AND participants.participant_id = event_participants.participant_id AND event_participants.event_id = event.event_id AND participants.email != ? ";
			
			if(!periodType.equals("allPeriod")) 
				query += "AND event_participants.join_date >= ? AND event_participants.join_date <= ?";
			
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
	
	public static void main(String[] args) {
		String event = "multiculturalyouth";
		String periodType = "allPeriod";
		String startPeriod = null;
		String endPeriod = null;
		
		Participants db = new Participants();
		db.open();
		ArrayList<String> data = db.survey(event, periodType, startPeriod, endPeriod);
		int total = db.totalParticipants(event, periodType, startPeriod, endPeriod);
		db.close();
		System.out.println(total);
		System.out.println(data);
	}
}

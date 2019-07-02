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

	public int totalParticipants(String event) {
		try {
			Integer total = 0;
			String query = "SELECT COUNT(*) FROM participants, event_participants, event WHERE participants.participant_id = event_participants.participant_id AND event.event_id = event_participants.event_id AND english_e_name=\""
					+ event + "\" AND join_date >= recruitment_start AND join_date <= recruitment_end";
			ResultSet rset = stmt.executeQuery(query);
			if (rset.next())
				total = rset.getInt("COUNT(*)");
			rset.close();
			return total;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public ArrayList<String> participants(String event) {
		ArrayList<String> list = new ArrayList<String>();
		try {
			String update = "UPDATE participants SET gender = NULL, phone = NULL, country2 = NULL, country3 = NULL, zip = NULL, address = NULL, allergy = NULL WHERE gender = \"null\" OR phone = \"null\" OR country2 = \"null\" OR country3 = \"null\" OR zip = \"null\" OR address = \"null\" OR allergy = \"null\"";
			stmt.executeUpdate(update);
			String query = "SELECT join_date, name, fname, gender, email, phone, birthdate, country, country2, country3, company, career, zip, address, allergy, way FROM participants, event, event_participants WHERE english_e_name = \""
					+ event
					+ "\" AND join_date >= recruitment_start AND join_date <= recruitment_end AND participants.participant_id = event_participants.participant_id AND event.event_id = event_participants.event_id";
			ResultSet rset = stmt.executeQuery(query);
			while (rset.next()) {
				list.add(rset.getString("join_date"));
				list.add(rset.getString("name"));
				list.add(rset.getString("fname"));
				list.add(rset.getString("gender"));
				list.add(rset.getString("email"));
				list.add(rset.getString("phone"));
				list.add(rset.getString("birthdate"));
				list.add(rset.getString("country"));
				list.add(rset.getString("country2"));
				list.add(rset.getString("country3"));
				list.add(rset.getString("company"));
				list.add(rset.getString("career"));
				list.add(rset.getString("allergy"));
				list.add(rset.getString("zip"));
				list.add(rset.getString("address"));
				list.add(rset.getString("way"));
			}
			rset.close();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<String> participantsInfoLess(String eventname) {
		ArrayList<String> data = new ArrayList<String>();
		try {
			String query = "SELECT event_participants.join_date, event_participants.join_time, participants.name, participants.participant_id FROM participants, event_participants, event WHERE english_e_name=\""
					+ eventname
					+ "\" AND event_participants.participant_id = participants.participant_id AND event.event_id = event_participants.event_id AND join_date >= recruitment_start AND join_date <= recruitment_end ORDER BY participants.participant_id DESC";
			ResultSet rset = stmt.executeQuery(query);

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

	public ArrayList<String> participantsEmailAddress(String event, int size, String senderEmail) {
		ArrayList<String> data = new ArrayList<String>();
		try {
			String query = "SELECT DISTINCT email FROM participants, event, event_participants WHERE english_e_name = ? AND participants.participant_id = event_participants.participant_id AND event_participants.event_id = event.event_id AND participants.email != ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, event);
			stmt.setString(2, senderEmail);
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

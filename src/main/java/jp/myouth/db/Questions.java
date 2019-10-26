package jp.myouth.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Questions {
	
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

	public Boolean insertQuestion(String questionId, String question, String type, Boolean required) {
		try {
			String update = "INSERT INTO questions (question_id, question, type, required) VALUES(?,?,?,?)";
			PreparedStatement stmt = conn.prepareStatement(update);
			stmt.setString(1, questionId);
			stmt.setString(2, question);
			stmt.setString(3, type);
			stmt.setBoolean(4, required);
			int res = stmt.executeUpdate();
			if(res > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Boolean insertOptions(String questionId, String option) {
		try {
			String update = "INSERT INTO questions_options (question_id, option) VALUES(?,?)";
			PreparedStatement stmt = conn.prepareStatement(update);
			stmt.setString(1, questionId);
			stmt.setString(2, option);
			int res = stmt.executeUpdate();
			if(res > 0)
				return true;
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Boolean insertDatePeriod(String questionId, String start, String end) {
		try {
			String update = "INSERT INTO questions_date (question_id, start, end) VALUES(?,?,?)";
			PreparedStatement stmt = conn.prepareStatement(update);
			stmt.setString(1, questionId);
			stmt.setString(2, start);
			stmt.setString(3, end);
			int res = stmt.executeUpdate();
			if(res > 0)
				return true;
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Boolean insertTimePeriod(String questionId, String start, String end) {
		try {
			String update = "INSERT INTO questions_time (question_id, start, end) VALUES(?,?,?)";
			PreparedStatement stmt = conn.prepareStatement(update);
			stmt.setString(1, questionId);
			stmt.setString(2, start);
			stmt.setString(3, end);
			int res = stmt.executeUpdate();
			if(res > 0)
				return true;
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Boolean deleteQuestion(String questionId) {
		try {
			String update = "DELETE FROM questions WHERE question_id = ?";
			PreparedStatement stmt = conn.prepareStatement(update);
			stmt.setString(1, questionId);
			stmt.executeUpdate();
			
			String update1 = "DELETE FROM questions_options WHERE question_id = ?";
			PreparedStatement stmt1 = conn.prepareStatement(update1);
			stmt1.setString(1, questionId);
			stmt1.executeUpdate();
			
			String update2 = "DELETE FROM questions_date WHERE question_id = ?";
			PreparedStatement stmt2 = conn.prepareStatement(update2);
			stmt2.setString(1, questionId);
			stmt2.executeUpdate();
			
			String update3 = "DELETE FROM questions_time WHERE question_id = ?";
			PreparedStatement stmt3 = conn.prepareStatement(update3);
			stmt3.setString(1, questionId);
			stmt3.executeUpdate();
			
			return true;
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}

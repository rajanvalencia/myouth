package jp.myouth.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Analytics {
	
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
				//stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
	
	public Boolean updateGraphPublicOption(String templateId, String questionId, Boolean option) {
		try {
			String update = "UPDATE questions_template SET graph_public = ? WHERE template_id = ? AND question_id = ?";
			PreparedStatement stmt = conn.prepareStatement(update);
			stmt.setBoolean(1, option);
			stmt.setString(2, templateId);
			stmt.setString(3, questionId);
			int res = stmt.executeUpdate();
			
			if(res > 0)
				return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Boolean getGraphPublicOption(String templateId, String questionId) {
		try {
			String query = "SELECT graph_public FROM questions_template WHERE template_id = ? AND question_id = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, templateId);
			stmt.setString(2, questionId);
			ResultSet rset = stmt.executeQuery();
			
			if(rset.next()) {
				return rset.getBoolean("graph_public");
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}

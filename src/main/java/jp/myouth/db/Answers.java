package jp.myouth.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import jp.myouth.security.GenerateSecureString;

public class Answers {
	
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
	
	public ArrayList<String> getGroupedAnswers(String questionId){
		ArrayList<String> data = new ArrayList<String>();
		try {
			String query = "SELECT answer FROM answers WHERE question_id = ? AND answer IS NOT NULL GROUP BY answer";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, questionId);
			ResultSet rset = stmt.executeQuery();
			
			while(rset.next()) {
				data.add(rset.getString("answer"));
			}
			
			return data;
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public ArrayList<String> getGroupedOptionsAnswers(String questionId){
		ArrayList<String> data = new ArrayList<String>();
		try {
			String query = "SELECT option FROM answers_options WHERE question_id = ? GROUP BY option ORDER BY COUNT(option) DESC";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, questionId);
			ResultSet rset = stmt.executeQuery();
			
			while(rset.next()) {
				data.add(rset.getString("option"));
			}
			
			return data;
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Integer getTotalAnswersPerQuestion(String questionId) {
		try {
			String query = "SELECT COUNT(*) FROM answers WHERE question_id = ? AND answer IS NOT NULL";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, questionId);
			ResultSet rset = stmt.executeQuery();
			
			if(rset.next()) {
				return rset.getInt("COUNT(*)");
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Integer getTotalAnswersPerAnswer(String questionId, String answer) {
		try {
			String query = "SELECT COUNT(*) FROM answers WHERE question_id = ? AND answer = ? AND answer IS NOT NULL GROUP BY answer";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, questionId);
			stmt.setString(2, answer);
			ResultSet rset = stmt.executeQuery();
			
			if(rset.next()) {
				return rset.getInt("COUNT(*)");
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Integer getTotalAnswersPerOption(String questionId, String option) {
		try {
			String query = "SELECT COUNT(*) FROM answers_options WHERE question_id = ? AND option = ? GROUP BY option";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, questionId);
			stmt.setString(2, option);
			ResultSet rset = stmt.executeQuery();
			
			if(rset.next()) {
				return rset.getInt("COUNT(*)");
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Integer getTotalReviews(String templateId) {
		try {
			String query = "SELECT COUNT(*) FROM event_reviews WHERE template_id = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, templateId);
			ResultSet rset = stmt.executeQuery();
			
			if(rset.next()) {
				return rset.getInt("COUNT(*)");
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public ArrayList<String> getReviewIds(String templateId){
		ArrayList<String> data = new ArrayList<String>();
		try {
			String query = "SELECT review_id FROM event_reviews WHERE template_id = ? ORDER BY time DESC";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, templateId);
			ResultSet rset = stmt.executeQuery();
			
			while(rset.next()) {
				data.add(rset.getString("review_id"));
			}
			
			return data;
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Integer getStar(String templateId, String reviewId) {
		try {
			String query = "SELECT star FROM event_reviews WHERE template_id = ? AND review_id = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, templateId);
			stmt.setString(2, reviewId);
			ResultSet rset = stmt.executeQuery();
			
			if(rset.next()) {
				return rset.getInt("star");
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getReview(String templateId, String reviewId) {
		try {
			String query = "SELECT review FROM event_reviews WHERE template_id = ? AND review_id = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, templateId);
			stmt.setString(2, reviewId);
			ResultSet rset = stmt.executeQuery();
			
			if(rset.next()) {
				return rset.getString("review");
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getReviewDate(String templateId, String reviewId) {
		try {
			String query = "SELECT DATE(time) AS date FROM event_reviews WHERE template_id = ? AND review_id = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, templateId);
			stmt.setString(2, reviewId);
			ResultSet rset = stmt.executeQuery();
			
			if(rset.next()) {
				return rset.getString("date");
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Double getReviewsAverageStars(String templateId) {
		try {
			String query = "SELECT ROUND(SUM(star)/COUNT(*), 1) AS averageRating FROM event_reviews  WHERE template_id = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, templateId);
			ResultSet rset = stmt.executeQuery();
			
			if(rset.next())
				return rset.getDouble("averageRating");
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Integer getTotalAnswersByStar(String templateId, Integer star) {
		try {
			String query = "SELECT COUNT(*) FROM event_reviews WHERE template_id = ? AND star = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, templateId);
			stmt.setInt(2, star);
			ResultSet rset = stmt.executeQuery();
			
			if(rset.next())
				return rset.getInt("COUNT(*)");
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Double getStarPercentageOfStarsTotalAnswer(String templateId, int star) {
		try {
			String query = "SELECT ROUND((COUNT(*)/(SELECT COUNT(*) FROM event_reviews WHERE template_id = ?)) * 100, 1)  AS percentageOfStarsTotalAnswer FROM event_reviews WHERE template_id = ? AND star = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, templateId);
			stmt.setString(2, templateId);
			stmt.setInt(3, star);
			ResultSet rset = stmt.executeQuery();
			
			if(rset.next()) {
				return rset.getDouble("percentageOfStarsTotalAnswer");
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	public String getCountryName(String code, String lang) {
		try {
			String query = "SELECT name FROM lists_countries WHERE code = ? AND lang = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, code);
			stmt.setString(2, lang);
			ResultSet rset = stmt.executeQuery();
			
			if(rset.next()) 
				return rset.getString("name");
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Boolean deleteAllAnswers() {
		try {
			String update = "DELETE FROM answers";
			PreparedStatement stmt = conn.prepareStatement(update);
			stmt.execute();
			
			String update1 = "DELETE FROM answers_options";
			stmt.execute(update1);
			
			String update2 = "DELETE FROM answers_privacy";
			stmt.execute(update2);
			
			String update3 = "DELETE FROM answers_transactions";
			stmt.execute(update3);
			
			String update4 = "DELETE FROM participants_info";
			stmt.execute(update4);
			
			String update5 = "DELETE FROM event_participants";
			stmt.execute(update5);
			
			return true;
		} catch(SQLException e) {
			return false;
		}
	}
	
	public ArrayList<String> getReviews(String templateId) {
		ArrayList<String> data = new ArrayList<String>();
		try {
			String query = "SELECT review FROM event_reviews WHERE template_id = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, templateId);
			ResultSet rset = stmt.executeQuery();
			
			while(rset.next()) {
				data.add(rset.getString("review"));
			}
			
			rset.close();
			return data;
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Boolean insertReviewId(String reviewId, String review) {
		try {
			String update = "UPDATE event_reviews SET review_id = ? WHERE review = ?";
			PreparedStatement stmt = conn.prepareStatement(update);
			stmt.setString(1, reviewId);
			stmt.setString(2, review);
			int res = stmt.executeUpdate();
			
			if(res > 0)
				return true;
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static void main(String[] args) throws SQLException {
		String templateId = "Z6FEhuzvJLfOOdmYtUIO5QxvaoL3vyh85IhH6nKLX6D0Z8dbtXpHCyn9wcyHh8EzrHAn3LnDl2nKEX6H";
		Answers db = new Answers();
		db.open();
		//db.deleteAllAnswers();
		ArrayList<String> reviews = db.getReviews(templateId);
		for(String review : reviews) {
			String reviewId = new GenerateSecureString().string(8);
			System.out.println(reviewId+": "+review);
			db.insertReviewId(reviewId, review);
		}
		db.close();
	}
}

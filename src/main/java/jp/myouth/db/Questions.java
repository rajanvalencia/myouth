package jp.myouth.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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
				//stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
	
	public String getTitle(String templateId) {
		try {
			String query = "SELECT title FROM questions_template_title WHERE template_id = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, templateId);
			ResultSet rset = stmt.executeQuery();
			
			if(rset.next()) {
				return rset.getString("title");
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getDescription(String templateId) {
		try {
			String query = "SELECT description FROM questions_template_description WHERE template_id = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, templateId);
			ResultSet rset = stmt.executeQuery();
			
			if(rset.next()) {
				return rset.getString("description");
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getQuestion(String questionId) {
		try {
			String query = "SELECT question FROM questions WHERE question_id = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, questionId);
			ResultSet rset = stmt.executeQuery();
			
			if(rset.next()) {
				return rset.getString("question");
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getOption(String questionId, String optionId) {
		try {
			String query = "SELECT option FROM questions_options WHERE question_id = ? AND option_id = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, questionId);
			stmt.setString(2, optionId);
			ResultSet rset = stmt.executeQuery();
			
			if(rset.next()) {
				return rset.getString("option");
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public ArrayList<String> getQuestionIds(String templateId) {
		ArrayList<String> data = new ArrayList<String>();
		try {
			String query = "SELECT question_id FROM questions_template WHERE template_id = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, templateId);
			ResultSet rset = stmt.executeQuery();
			
			while(rset.next()) {
				data.add(rset.getString("question_id"));
			}
			
			return data;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public ArrayList<String> getPublicGraphQuestionIds(String templateId) {
		ArrayList<String> data = new ArrayList<String>();
		try {
			String query = "SELECT question_id FROM questions_template WHERE template_id = ? AND graph_public = TRUE";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, templateId);
			ResultSet rset = stmt.executeQuery();
			
			while(rset.next()) {
				data.add(rset.getString("question_id"));
			}
			
			return data;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getQuestionType(String questionId) {
		try {
			String query = "SELECT type FROM questions WHERE question_id = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, questionId);
			ResultSet rset = stmt.executeQuery();
			
			if(rset.next())
				return rset.getString("type");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public ArrayList<String> getQuestionOptions(String questionId){
		ArrayList<String> data = new ArrayList<String>();
		try {
			String query = "SELECT option FROM questions_options WHERE question_id = ?";
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
	
	public ArrayList<String> getQuestionOptionIds(String questionId){
		ArrayList<String> data = new ArrayList<String>();
		try {
			String query = "SELECT option_id FROM questions_options WHERE question_id = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, questionId);
			ResultSet rset = stmt.executeQuery();
			
			while(rset.next()) {
				data.add(rset.getString("option_id"));
			}
			
			return data;
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getDateStartPeriod(String questionId) {
		try {
			String query = "SELECT start FROM questions_date WHERE question_id = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, questionId);
			ResultSet rset = stmt.executeQuery();
			
			if(rset.next()) {
				return rset.getString("start");
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getDateEndPeriod(String questionId) {
		try {
			String query = "SELECT end FROM questions_date WHERE question_id = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, questionId);
			ResultSet rset = stmt.executeQuery();
			
			if(rset.next()) {
				return rset.getString("end");
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getTimeStartPeriod(String questionId) {
		try {
			String query = "SELECT start FROM questions_time WHERE question_id = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, questionId);
			ResultSet rset = stmt.executeQuery();
			
			if(rset.next()) {
				return rset.getString("start");
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getTimeEndPeriod(String questionId) {
		try {
			String query = "SELECT end FROM questions_time WHERE question_id = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, questionId);
			ResultSet rset = stmt.executeQuery();
			
			if(rset.next()) {
				return rset.getString("end");
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Boolean checkRequiredOption(String questionId) {
		try {
			String query = "SELECT required FROM questions WHERE question_id = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, questionId);
			ResultSet rset = stmt.executeQuery();
			
			if(rset.next()) {
				return rset.getBoolean("required");
			}
				
				
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public ArrayList<String> getTemplateIds(String event){
		ArrayList<String> data = new ArrayList<String>();
		try {
			String query = "SELECT template_id FROM event_questions WHERE event_id = (SELECT event_id FROM event WHERE english_e_name = ?)"; 
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, event);
			ResultSet rset = stmt.executeQuery();
			
			while(rset.next()) {
				data.add(rset.getString("template_id"));
			}

			return data;
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public ArrayList<String> getPublicTemplateIds(String event){
		ArrayList<String> data = new ArrayList<String>();
		try {
			String query = "SELECT template_id FROM event_questions WHERE event_id = (SELECT event_id FROM event WHERE english_e_name = ?) AND public = true"; 
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, event);
			ResultSet rset = stmt.executeQuery();
			
			while(rset.next()) {
				data.add(rset.getString("template_id"));
			}

			return data;
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Boolean getPublicOption(String templateId) {
		try {
			String query = "SELECT public FROM event_questions WHERE template_id = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, templateId);
			ResultSet rset = stmt.executeQuery();
			
			if(rset.next()) {
				return rset.getBoolean("public");
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Boolean getReviewOption(String templateId) {
		try {
			String query = "SELECT review_option FROM event_questions WHERE template_id = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, templateId);
			ResultSet rset = stmt.executeQuery();
			
			if(rset.next()) {
				return rset.getBoolean("review_option");
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Boolean insertTemplate(String event, String templateId) {
		try {
			String update = "INSERT INTO event_questions (event_id, template_id) VALUES((SELECT event_id FROM event WHERE english_e_name = ?),?)";
			PreparedStatement stmt = conn.prepareStatement(update);
			stmt.setString(1, event);
			stmt.setString(2, templateId);
			int res = stmt.executeUpdate();
			
			if(res > 0)
				return true;
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Boolean insertTitle(String templateId, String title) {
		try {
			String update = "INSERT INTO questions_template_title (template_id, title) VALUES(?,?)";
			PreparedStatement stmt = conn.prepareStatement(update);
			stmt.setString(1, templateId);
			stmt.setString(2, title);
			int res = stmt.executeUpdate();
			
			if(res > 0)
				return true;
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Boolean insertDescription(String templateId, String description) {
		try {
			String update = "INSERT INTO questions_template_description (template_id, description) VALUES(?,?)";
			PreparedStatement stmt = conn.prepareStatement(update);
			stmt.setString(1, templateId);
			stmt.setString(2, description);
			int res = stmt.executeUpdate();
			
			if(res > 0)
				return true;
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Boolean insertQuestionIdIntoTemplate(String templateId, String questionId) {
		try {
			String update = "INSERT INTO questions_template (template_id, question_id) VALUES(?,?)";
			PreparedStatement stmt = conn.prepareStatement(update);
			stmt.setString(1, templateId); 
			stmt.setString(2, questionId);
			int res = stmt.executeUpdate();
			
			if(res > 0)
				return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
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
	
	public Boolean insertQuestion(String templateId, String questionId, String question, String type, Boolean required) {
		try {
			String update = "INSERT INTO questions (question_id, question, type, required) VALUES(?,?,?,?)";
			PreparedStatement stmt = conn.prepareStatement(update);
			stmt.setString(1, questionId);
			stmt.setString(2, question);
			stmt.setString(3, type);
			stmt.setBoolean(4, required);
			int res = stmt.executeUpdate();
			
			String update1 = "INSERT INTO questions_template (template_id, question_id) VALUES(?,?)";
			PreparedStatement stmt1 = conn.prepareStatement(update1);
			stmt1.setString(1, templateId);
			stmt1.setString(2, questionId);
			int res1 = stmt1.executeUpdate();
			
			if(res > 0 && res1 > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Boolean insertOptions(String questionId, String optionId, String option) {
		try {
			String update = "INSERT INTO questions_options (question_id, option_id, option) VALUES(?,?,?)";
			PreparedStatement stmt = conn.prepareStatement(update);
			stmt.setString(1, questionId);
			stmt.setString(2, optionId);
			stmt.setString(3, option);
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
	
	public Boolean insertTransaction(String transactionId, String templateId, String participantId) {
		try {
			String update = "INSERT INTO answers_transactions (transaction_id, template_id, participant_id) VALUES(?,?,?)";
			PreparedStatement stmt = conn.prepareStatement(update);
			stmt.setString(1, transactionId);
			stmt.setString(2, templateId);
			stmt.setString(3, participantId);
			int res = stmt.executeUpdate();
			
			if(res > 0)
				return true;
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Boolean insertParticipantInfo(String participantId, String name, String fname, String gender, String email, String birthdate) {
		try {
			String update = "INSERT INTO event_participants (participant_id, name, fname, gender, email_address, birthdate) VALUES(?,?,?,?,?,?)";
			PreparedStatement stmt = conn.prepareStatement(update);
			stmt.setString(1, participantId);
			stmt.setString(2, name);
			stmt.setString(3, fname);
			stmt.setString(4, gender);
			stmt.setString(5, email);
			stmt.setString(6, birthdate);
			int res = stmt.executeUpdate();
			
			if(res > 0)
				return true;
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Boolean insertAnswer(String transactionId, String questionId, String answer) {
		try {
			String update = "INSERT INTO answers (transaction_id, question_id, answer) VALUES(?,?,?)";
			PreparedStatement stmt = conn.prepareStatement(update);
			stmt.setString(1, transactionId);
			stmt.setString(2, questionId);
			stmt.setString(3, answer);
			int res = stmt.executeUpdate();
			
			if(res > 0)
				return true;
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Boolean insertOptionTypeAnswer(String transactionId, String questionId, String option){
		try {
			String update = "INSERT INTO answers_options (transaction_id, question_id, option) VALUES(?,?,?)";
			PreparedStatement stmt = conn.prepareStatement(update);
			stmt.setString(1, transactionId);
			stmt.setString(2, questionId);
			stmt.setString(3, option);
			int res = stmt.executeUpdate();
			
			if(res > 0)
				return true;
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Boolean insertPublicOption(String transactionId, Boolean privacy) {
		try {
			String update = "INSERT INTO answers_privacy (transaction_id, public) VALUES(?,?)";
			PreparedStatement stmt = conn.prepareStatement(update);
			stmt.setString(1, transactionId);
			stmt.setBoolean(2, privacy);
			int res = stmt.executeUpdate();
			
			if(res > 0)
				return true;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Boolean insertReview(String templateId, String stars, String review) {
		try {
			String update = "INSERT INTO event_reviews (template_id, stars, review) VALUES(?,?,?)";
			PreparedStatement stmt = conn.prepareStatement(update);
			stmt.setString(1, templateId);
			stmt.setString(2, stars);
			stmt.setString(3, review);
			int res = stmt.executeUpdate();
			
			if(res > 0)
				return true;
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Boolean updateTemplatePublicOption(String templateId, Boolean option) {
		try {
			String update = "UPDATE event_questions SET public = ? WHERE template_id = ?";
			PreparedStatement stmt = conn.prepareStatement(update);
			stmt.setBoolean(1, option);
			stmt.setString(2, templateId);
			int res = stmt.executeUpdate();
			
			if(res > 0)
				return true;
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Boolean updateTitle(String templateId, String title) {
		try {
			String update = "UPDATE questions_template_title SET title = ? WHERE template_id = ?";
			PreparedStatement stmt = conn.prepareStatement(update);
			stmt.setString(1, title);
			stmt.setString(2, templateId);
			int res = stmt.executeUpdate();
			
			if(res > 0)
				return true;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Boolean updateDescription(String templateId, String description) {
		try {
			String update = "UPDATE questions_template_description SET description = ? WHERE template_id = ?";
			PreparedStatement stmt = conn.prepareStatement(update);
			stmt.setString(1, description);
			stmt.setString(2, templateId);
			int res = stmt.executeUpdate();
			
			if(res > 0)
				return true;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Boolean updateQuestion(String questionId, String question) {
		try {
			String update = "UPDATE questions SET question = ? WHERE question_id = ?";
			PreparedStatement stmt = conn.prepareStatement(update);
			stmt.setString(1, question);
			stmt.setString(2, questionId);
			int res = stmt.executeUpdate();
			
			if(res > 0)
				return true;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Boolean updateQuestionType(String questionId, String type) {
		try {
			String update = "UPDATE questions SET type = ? WHERE question_id = ?";
			PreparedStatement stmt = conn.prepareStatement(update);
			stmt.setString(1, type);
			stmt.setString(2, questionId);
			int res = stmt.executeUpdate();
			
			if(res > 0)
				return true;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Boolean updateOption(String questionId, String optionId, String option) {
		try {
			String update = "UPDATE questions_options SET option = ? WHERE option_id = ? AND question_id = ?";
			PreparedStatement stmt = conn.prepareStatement(update);
			stmt.setString(1, option);
			stmt.setString(2, optionId);
			stmt.setString(3, questionId);
			int res = stmt.executeUpdate();
			
			if(res > 0)
				return true;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Boolean updateRequiredOption(String questionId, Boolean required) {
		try {
			String update = "UPDATE questions SET required = ? WHERE question_id = ?";
			PreparedStatement stmt = conn.prepareStatement(update);
			stmt.setBoolean(1, required);
			stmt.setString(2, questionId);
			int res = stmt.executeUpdate();
			
			if(res > 0)
				return true;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Boolean updateDatePeriod(String questionId, String start, String end) {
		try {
			String update = "UPDATE questions_date SET start = ?,  end = ? WHERE question_id = ?";
			PreparedStatement stmt = conn.prepareStatement(update);
			stmt.setString(1, start);
			stmt.setString(2, end);
			stmt.setString(3, questionId);
			int res = stmt.executeUpdate();
			if(res > 0)
				return true;
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Boolean updateTimePeriod(String questionId, String start, String end) {
		try {
			String update = "UPDATE questions_time SET start = ?,  end = ? WHERE question_id = ?";
			PreparedStatement stmt = conn.prepareStatement(update);
			stmt.setString(1, start);
			stmt.setString(2, end);
			stmt.setString(3, questionId);
			int res = stmt.executeUpdate();
			if(res > 0)
				return true;
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Boolean updateFormTemplateReviewOption(String event, String templateId, Boolean option) {
		try {
			String update = "UPDATE event_questions SET review_option = ? WHERE event_id = (SELECT event_id FROM event WHERE english_e_name = ?) AND template_id = ?";
			PreparedStatement stmt = conn.prepareStatement(update);
			stmt.setBoolean(1, option);
			stmt.setString(2, event);
			stmt.setString(3, templateId);
			int res = stmt.executeUpdate();
			
			if(res > 0)
				return true;
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Boolean deleteTemplateData(String templateId) {
		try {
			String update = "DELETE FROM questions_template WHERE template_id = ?";
			PreparedStatement stmt = conn.prepareStatement(update);
			stmt.setString(1, templateId);
			stmt.executeUpdate();
			
			String update1 = "DELETE FROM questions_template_title WHERE template_id = ?";
			PreparedStatement stmt1 = conn.prepareStatement(update1);
			stmt1.setString(1, templateId);
			stmt1.executeUpdate();
			
			String update2 = "DELETE FROM questions_template_description WHERE template_id = ?";
			PreparedStatement stmt2 = conn.prepareStatement(update2);
			stmt2.setString(1, templateId);
			stmt2.executeUpdate();
			
			return true;
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Boolean deleteOption(String questionId, String optionId) {
		try {
			 String update = "DELETE FROM questions_options WHERE question_id = ? AND option_id = ?";
			 PreparedStatement stmt = conn.prepareStatement(update);
			 stmt.setString(1, questionId);
			 stmt.setString(2, optionId);
			 int res = stmt.executeUpdate();
			 
			 if(res > 0)
				 return true;
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Boolean deleteOptions(String questionId) {
		try {
			 String update = "DELETE FROM questions_options WHERE question_id = ?";
			 PreparedStatement stmt = conn.prepareStatement(update);
			 stmt.setString(1, questionId);
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
			
			String update4 = "DELETE FROM questions_template WHERE question_id = ?";
			PreparedStatement stmt4 = conn.prepareStatement(update4);
			stmt4.setString(1, questionId);
			stmt4.executeUpdate();
			
			return true;
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Boolean sortQuestions(String templateId, String[] questionIds) {
		try {
			String update = "DELETE FROM questions_template WHERE template_id = ?";
			PreparedStatement stmt = conn.prepareStatement(update);
			stmt.setString(1, templateId);
			int res = stmt.executeUpdate();
			
			String update1 = "INSERT INTO questions_template (template_id, question_id) VALUES(?,?)";
			PreparedStatement stmt1 = conn.prepareStatement(update1);
			for(String questionId : questionIds) {
				stmt1.setString(1, templateId);
				stmt1.setString(2, questionId);
				int res1 = stmt1.executeUpdate();
			}
			
			if(res > 0)
				return true;
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Boolean deleteTemplate(String templateId) {
		try {
			String update = "DELETE FROM event_questions WHERE template_id = ?";
			PreparedStatement stmt = conn.prepareStatement(update);
			stmt.setString(1, templateId);
			stmt.execute();
			
			String update1 = "DELETE FROM questions_template_title WHERE template_id = ?";
			PreparedStatement stmt1 = conn.prepareStatement(update1);
			stmt1.setString(1, templateId);
			stmt1.execute();
			
			String update2 = "DELETE FROM questions_template_description WHERE template_id = ?";
			PreparedStatement stmt2 = conn.prepareStatement(update2);
			stmt2.setString(1, templateId);
			stmt2.execute();
			
			return true;
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}

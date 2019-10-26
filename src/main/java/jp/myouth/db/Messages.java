package jp.myouth.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import jp.myouth.utilities.StringDecoder;

public class Messages {
	private Connection conn = null;
	private Statement stmt = null;

	RDSVariables var = new RDSVariables();
	
	StringDecoder str = new StringDecoder(); 

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
	
	public String replyTo(String messageId) {
		try {
			String query = "SELECT * FROM message_received WHERE message_id = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, messageId);
			ResultSet rset = stmt.executeQuery();
			
			String sender = null;
			if(rset.next()) {
				sender = rset.getString("sender");
			}
			
			rset.close();
			stmt.close();
			
			return sender;
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<String> getMessageIdsByTransactionId(String transactionId) {
		ArrayList<String> data = new ArrayList<String>();
		try {
			String query = "SELECT message_id FROM message_transactions WHERE transaction_id = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, transactionId);
			ResultSet rset = stmt.executeQuery();

			while (rset.next())
				data.add(rset.getString("message_id"));

			return data;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<String> getMessageIdsByTransactionIds(ArrayList<String> transactionIds) {
		ArrayList<String> data = new ArrayList<String>();
		try {
			String query = "SELECT message_id FROM message_transactions WHERE transaction_id = ?";
			PreparedStatement stmt = conn.prepareStatement(query);

			for (String transactionId : transactionIds) {
				stmt.setString(1, transactionId);
				ResultSet rset = stmt.executeQuery();

				while (rset.next())
					data.add(rset.getString("message_id"));
			}

			return data;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public ArrayList<String> getTrashedReceivedMessageIdsByEvent(String event) {
		ArrayList<String> data = new ArrayList<String>();
		try {
			String query = "SELECT message_received_deleted.message_id FROM message_received_deleted, message_received_recipients WHERE message_received_deleted.message_id = message_received_recipients.message_id AND message_received_recipients.recipient LIKE ?";
			PreparedStatement stmt = conn.prepareStatement(query);

			stmt.setString(1, "%"+event+"@myouth.jp%");
			ResultSet rset = stmt.executeQuery();

			while (rset.next())
				data.add(rset.getString("message_id"));

			return data;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<String> getTransactionIdsByEvent(String event) {
		ArrayList<String> data = new ArrayList<String>();
		try {
			String query = "SELECT transaction_id FROM message_deleted WHERE sender = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, event + "@myouth.jp");
			ResultSet rset = stmt.executeQuery();

			while (rset.next())
				data.add(rset.getString("transaction_id"));

			return data;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Boolean registerSentMessage(String transactionId, String sender, String subject, String body) {
		try {
			String insert = "INSERT INTO message_sent (transaction_id, sender, subject, body) VALUES(?,?,?,?)";
			PreparedStatement stmt = conn.prepareStatement(insert);
			stmt.setString(1, transactionId);
			stmt.setString(2, sender);
			stmt.setString(3, subject);
			stmt.setString(4, str.encodeString(body));
			stmt.executeUpdate();

			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public Boolean registerMessageTransaction(String transactionId, ArrayList<String> messageIds) {
		try {
			String insert = "INSERT INTO message_transactions (transaction_id, message_id) VALUES(?,?)";
			PreparedStatement stmt = conn.prepareStatement(insert);
			stmt.setString(1, transactionId);

			int res = 0;
			for (String messageId : messageIds) {
				stmt.setString(2, messageId);
				res += stmt.executeUpdate();
			}

			if (res > 0)
				return true;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public ArrayList<String> eventReceivedMessages(String event) {
		try {
			ArrayList<String> data = new ArrayList<String>();

			String query = "SELECT message_received.message_id, subject, text_body, DATE_FORMAT(message_received.time, \"%Y-%m-%d %H:%i\") AS time FROM message_received, message_received_recipients WHERE message_received.message_id = message_received_recipients.message_id AND message_received_recipients.recipient LIKE ? ORDER BY time DESC, EXTRACT(HOUR FROM time) DESC, EXTRACT(MINUTE FROM time) DESC, EXTRACT(SECOND FROM time) DESC";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, "%"+ event + "@myouth.jp%");
			ResultSet rset = stmt.executeQuery();

			String messageBody = new String();
			int messageBodyLen = 0;
			while (rset.next()) {
				data.add(rset.getString("message_id"));
				data.add(rset.getString("subject"));
				
				messageBody = str.decodeString(rset.getString("text_body"));
				messageBodyLen = str.decodeString(rset.getString("text_body")).length();
				System.out.println(messageBody);
				System.out.println(messageBodyLen+"\n");
				
				if(messageBodyLen <= 28)
					data.add(messageBody.replaceAll("(\r\n|\n)", " "));
				else 
					data.add(messageBody.replaceAll("(\r\n|\n)", " ").substring(0, 27));
					
				data.add(rset.getString("time"));
			}

			return data;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public ArrayList<String> eventSentMessages(String event) {
		try {
			ArrayList<String> data = new ArrayList<String>();

			String query = "SELECT transaction_id, subject, body, DATE_FORMAT(message_sent.time, \"%Y-%m-%d %H:%i\") AS time FROM message_sent WHERE sender = ? ORDER BY time DESC, EXTRACT(HOUR FROM time) DESC, EXTRACT(MINUTE FROM time) DESC, EXTRACT(SECOND FROM time) DESC";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, event + "@myouth.jp");
			ResultSet rset = stmt.executeQuery();

			String messageBody = new String();
			int messageBodyLen = 0;
			while (rset.next()) {
				data.add(rset.getString("transaction_id"));
				data.add(rset.getString("subject"));
				
				messageBody = str.decodeString(rset.getString("body"));
				messageBodyLen = str.decodeString(rset.getString("body")).length();
				if(messageBodyLen <= 28)
					data.add(messageBody.replaceAll("(\r\n|\n)", " "));
				else 
					data.add(messageBody.replaceAll("(\r\n|\n)", " ").substring(0, 27));
				
				data.add(rset.getString("time"));
			}

			return data;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public ArrayList<String> eventTrashedReceivedMessages(String event) {
		try {
			ArrayList<String> data = new ArrayList<String>();

			String query = "SELECT message_received_deleted.message_id, subject, text_body, DATE_FORMAT(message_received_deleted.time, \"%Y-%m-%d %H:%i\") AS time FROM message_received_deleted, message_received_recipients WHERE message_received_deleted.message_id = message_received_recipients.message_id AND message_received_recipients.recipient LIKE ? ORDER BY time DESC, EXTRACT(HOUR FROM time) DESC, EXTRACT(MINUTE FROM time) DESC, EXTRACT(SECOND FROM time) DESC";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, "%"+ event + "@myouth.jp%");
			ResultSet rset = stmt.executeQuery();

			String messageBody = new String();
			int messageBodyLen = 0;
			while (rset.next()) {
				data.add(rset.getString("message_id"));
				data.add(rset.getString("subject"));
				
				messageBody = str.decodeString(rset.getString("text_body"));
				messageBodyLen = str.decodeString(rset.getString("text_body")).length();
				System.out.println(messageBody);
				System.out.println(messageBodyLen+"\n");
				
				if(messageBodyLen <= 28)
					data.add(messageBody.replaceAll("(\r\n|\n)", " "));
				else 
					data.add(messageBody.replaceAll("(\r\n|\n)", " ").substring(0, 27));
					
				data.add(rset.getString("time"));
			}

			return data;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public ArrayList<String> eventTrashedMessages(String event) {
		try {
			ArrayList<String> data = new ArrayList<String>();

			String query = "SELECT transaction_id, subject, body, DATE_FORMAT(message_deleted.time, \"%Y-%m-%d %H:%i\") AS time FROM message_deleted WHERE sender = ? ORDER BY time DESC";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, event + "@myouth.jp");
			ResultSet rset = stmt.executeQuery();

			String messageBody = new String();
			int messageBodyLen = 0;
			while (rset.next()) {
				data.add(rset.getString("transaction_id"));
				data.add(rset.getString("subject"));
				
				messageBody = str.decodeString(rset.getString("body"));
				messageBodyLen = str.decodeString(rset.getString("body")).length();
				if(messageBodyLen <= 28)
					data.add(messageBody.replaceAll("(\r\n|\n)", " "));
				else 
					data.add(messageBody.replaceAll("(\r\n|\n)", " ").substring(0, 27));
				data.add(rset.getString("time"));
			}

			return data;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public ArrayList<String> eventReceivedMessage(String messageId) {
		try {
			ArrayList<String> data = new ArrayList<String>();

			String query = "SELECT sender, subject, text_body, html_body, CONCAT(DATE_FORMAT(time, \"%Y年%m月%d日\"), TIME_FORMAT(time, \" %H:%i\")) AS time FROM message_received WHERE message_received.message_id = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, messageId);
			ResultSet rset = stmt.executeQuery();

			while (rset.next()) {
				data.add(rset.getString("sender"));
				data.add(rset.getString("subject"));
				data.add(rset.getString("time"));
				String htmlBody = rset.getString("html_body");
				if(htmlBody == null)
					data.add(str.decodeString(rset.getString("text_body")).replaceAll("(\r\n|\n)", "<br />"));
				else
					data.add(rset.getString("html_body"));
			}

			return data;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public ArrayList<String> eventReceivedMessageAttachments(String messageId) {
		try {
			ArrayList<String> data = new ArrayList<String>();

			String query = "SELECT url, name FROM message_received_attachments WHERE message_id = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, messageId);
			ResultSet rset = stmt.executeQuery();

			while (rset.next()) {
				data.add(rset.getString("url"));
				data.add(rset.getString("name"));
			}

			return data;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public ArrayList<String> eventSentMessage(String transactionId) {
		try {
			ArrayList<String> data = new ArrayList<String>();

			String query = "SELECT subject, body, CONCAT(DATE_FORMAT(time, \"%Y年%m月%d日\"), TIME_FORMAT(time, \" %H:%i\")) AS time FROM message_sent WHERE transaction_id = ? ORDER BY time DESC";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, transactionId);
			ResultSet rset = stmt.executeQuery();

			while (rset.next()) {
				data.add(rset.getString("subject"));
				data.add(rset.getString("time"));
				data.add(str.decodeString(rset.getString("body")).replaceAll("(\r\n|\n)", "<br />"));
			}

			return data;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<String> eventTrashedReceivedMessage(String messageId) {
		try {
			ArrayList<String> data = new ArrayList<String>();

			String query = "SELECT sender, subject, text_body, html_body, CONCAT(DATE_FORMAT(time, \"%Y年%m月%d日\"), TIME_FORMAT(time, \" %H:%i\")) AS time FROM message_received_deleted WHERE message_id = ? ORDER BY time DESC";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, messageId);
			ResultSet rset = stmt.executeQuery();

			while (rset.next()) {
				data.add(rset.getString("sender"));
				data.add(rset.getString("subject"));
				data.add(rset.getString("time"));
				String htmlBody = rset.getString("html_body");
				if(htmlBody == null)
					data.add(str.decodeString(rset.getString("text_body")).replaceAll("(\r\n|\n)", "<br />"));
				else
					data.add(rset.getString("html_body"));
			}

			return data;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public ArrayList<String> eventTrashedMessage(String transactionId) {
		try {
			ArrayList<String> data = new ArrayList<String>();

			String query = "SELECT subject, body, CONCAT(DATE_FORMAT(time, \"%Y年%m月%d日\"), TIME_FORMAT(time, \" %H:%i\")) AS time FROM message_deleted WHERE transaction_id = ? ORDER BY time DESC";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, transactionId);
			ResultSet rset = stmt.executeQuery();

			while (rset.next()) {
				data.add(rset.getString("subject"));
				data.add(rset.getString("time"));
				data.add(str.decodeString(rset.getString("body")).replaceAll("(\r\n|\n)", "<br />"));
			}

			return data;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<String> alreadyReadParticipants(String transactionId) {
		try {
			ArrayList<String> data = new ArrayList<String>();

			String query = "SELECT name, CONCAT(DATE_FORMAT(message_read.time, \"%Y-%m-%d\"), TIME_FORMAT(message_read.time, \" %H:%i\")) AS time FROM message_transactions, message_read, message_recipients, participants WHERE message_transactions.message_id = message_recipients.message_id AND message_transactions.message_id = message_read.message_id AND message_recipients.recipient = participants.email AND message_recipients.recipient NOT IN (SELECT email FROM users) AND message_transactions.transaction_id = ? ORDER BY message_read.time DESC";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, transactionId);
			ResultSet rset = stmt.executeQuery();

			while (rset.next()) {
				data.add(rset.getString("name"));
				data.add(rset.getString("time"));
			}

			return data;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<String> alreadyReadUsers(String transactionId) {
		try {
			ArrayList<String> data = new ArrayList<String>();

			String query = "SELECT message_read.message_id, path, name, CONCAT(DATE_FORMAT(message_read.time, \"%Y-%m-%d\"), TIME_FORMAT(message_read.time, \" %H:%i\")) AS time FROM message_transactions, message_read, message_recipients, users, user_profile_picture WHERE users.user_id = user_profile_picture.user_id AND message_read.message_id = message_transactions.message_id AND users.email = message_recipients.recipient AND message_recipients.message_id = message_read.message_id AND message_transactions.transaction_id = ? ORDER BY message_read.time DESC";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, transactionId);
			ResultSet rset = stmt.executeQuery();

			while (rset.next()) {
				data.add(rset.getString("path"));
				data.add(rset.getString("name"));
				data.add(rset.getString("time"));
			}

			return data;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<String> linkClickedUsers(String transactionId) {
		ArrayList<String> data = new ArrayList<String>();
		try {
			String query = "SELECT path, name, link FROM message_clicked_links, message_recipients, message_transactions, users, user_profile_picture WHERE users.user_id = user_profile_picture.user_id AND message_recipients.recipient = users.email AND message_recipients.message_id = message_clicked_links.message_id AND message_transactions.message_id = message_clicked_links.message_id AND message_transactions.transaction_id = ? ORDER BY message_clicked_links.time DESC";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, transactionId);
			ResultSet rset = stmt.executeQuery();

			while (rset.next()) {
				data.add(rset.getString("path"));
				data.add(rset.getString("name"));
				data.add(rset.getString("link"));
			}

			return data;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<String> linkClickedParticipants(String transactionId) {
		ArrayList<String> data = new ArrayList<String>();
		try {
			String query = "SELECT name, link FROM message_transactions, message_recipients, message_clicked_links, participants WHERE message_transactions.message_id = message_recipients.message_id AND message_recipients.message_id = message_clicked_links.message_id AND message_recipients.recipient = participants.email AND message_recipients.recipient NOT IN (SELECT email FROM users) AND message_transactions.transaction_id = ? ORDER BY message_clicked_links.time DESC";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, transactionId);
			ResultSet rset = stmt.executeQuery();

			while (rset.next()) {
				data.add(rset.getString("name"));
				data.add(rset.getString("link"));
			}

			return data;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public int totalMessageViews(String transactionId) {
		try {
			String query = "SELECT COUNT(*) AS totalMessageViews FROM message_transactions, message_read WHERE message_transactions.message_id = message_read.message_id AND message_transactions.transaction_id = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, transactionId);
			ResultSet rset = stmt.executeQuery();

			if (rset.next())
				return rset.getInt("totalMessageViews");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int totalLinkClicks(String transactionId) {
		try {
			String query = "SELECT COUNT(*) AS totalLinkClicks FROM message_transactions, message_clicked_links WHERE message_transactions.message_id = message_clicked_links.message_id AND message_transactions.transaction_id = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, transactionId);
			ResultSet rset = stmt.executeQuery();

			if (rset.next())
				return rset.getInt("totalLinkClicks");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public Boolean deleteMessage(String transactionId) {
		try {
			String update = "INSERT INTO message_deleted SELECT * FROM message_sent WHERE transaction_id = ?";
			PreparedStatement stmt = conn.prepareStatement(update);
			stmt.setString(1, transactionId);
			int res = stmt.executeUpdate();

			String update1 = "DELETE FROM message_sent WHERE transaction_id = ?";
			PreparedStatement stmt1 = conn.prepareStatement(update1);
			stmt1.setString(1, transactionId);
			int res1 = stmt1.executeUpdate();

			if (res > 0 && res1 > 0)
				return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Boolean deleteReceivedMessage(String messageId) {
		try {
			String update = "INSERT INTO message_received_deleted SELECT * FROM message_received WHERE message_id = ?";
			PreparedStatement stmt = conn.prepareStatement(update);
			stmt.setString(1, messageId);
			int res = stmt.executeUpdate();

			String update1 = "DELETE FROM message_received WHERE message_id = ?";
			PreparedStatement stmt1 = conn.prepareStatement(update1);
			stmt1.setString(1, messageId);
			int res1 = stmt1.executeUpdate();

			if (res > 0 && res1 > 0)
				return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Boolean undoTrashedReceivedMessage(String messageId) {
		try {
			String update = "INSERT INTO message_received SELECT * FROM message_received_deleted WHERE message_id = ?";
			PreparedStatement stmt = conn.prepareStatement(update);
			stmt.setString(1, messageId);
			int res = stmt.executeUpdate();

			String update1 = "DELETE FROM message_received_deleted WHERE message_id = ?";
			PreparedStatement stmt1 = conn.prepareStatement(update1);
			stmt1.setString(1, messageId);
			int res1 = stmt1.executeUpdate();

			if (res > 0 && res1 > 0)
				return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Boolean undoTrashedMessage(String transactionId) {
		try {
			String update = "INSERT INTO message_sent SELECT * FROM message_deleted WHERE transaction_id = ?";
			PreparedStatement stmt = conn.prepareStatement(update);
			stmt.setString(1, transactionId);
			int res = stmt.executeUpdate();

			String update1 = "DELETE FROM message_deleted WHERE transaction_id = ?";
			PreparedStatement stmt1 = conn.prepareStatement(update1);
			stmt1.setString(1, transactionId);
			int res1 = stmt1.executeUpdate();

			if (res > 0 && res1 > 0)
				return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Boolean deleteTrashedReceivedMessage(String messageId) {
		try {
			String update = "DELETE FROM message_received_deleted WHERE message_id = ?";
			PreparedStatement stmt = conn.prepareStatement(update);
			stmt.setString(1, messageId);
			int res = stmt.executeUpdate();

			String update1 = "DELETE FROM message_received_recipients WHERE message_id = ?";
			PreparedStatement stmt1 = conn.prepareStatement(update1);
			stmt1.setString(1, messageId);
			int res1 = stmt1.executeUpdate();

			String update2 = "DELETE FROM message_received_attachments WHERE message_id = ?";
			PreparedStatement stmt2 = conn.prepareStatement(update2);
			stmt2.setString(1, messageId);
			stmt2.executeUpdate();

			if (res > 0 && res1 > 0)
				return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public Boolean deleteTrashedMessage(String transactionId, ArrayList<String> messageIds) {
		try {
			String update = "DELETE FROM message_deleted WHERE transaction_id = ?";
			PreparedStatement stmt = conn.prepareStatement(update);
			stmt.setString(1, transactionId);
			int res = stmt.executeUpdate();

			String update1 = "DELETE FROM message_transactions WHERE transaction_id = ?";
			PreparedStatement stmt1 = conn.prepareStatement(update1);
			stmt1.setString(1, transactionId);
			int res1 = stmt1.executeUpdate();

			for (String messageId : messageIds) {
				String update2 = "DELETE FROM message_recipients WHERE message_id = ?";
				PreparedStatement stmt2 = conn.prepareStatement(update2);
				stmt2.setString(1, messageId);
				stmt2.executeUpdate();

				String update3 = "DELETE FROM message_read WHERE message_id = ?";
				PreparedStatement stmt3 = conn.prepareStatement(update3);
				stmt3.setString(1, messageId);
				stmt3.executeUpdate();

				String update4 = "DELETE FROM message_clicked_links WHERE message_id = ?";
				PreparedStatement stmt4 = conn.prepareStatement(update4);
				stmt4.setString(1, messageId);
				stmt4.executeUpdate();
			}

			if (res > 0 && res1 > 0)
				return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Boolean deleteAllTrashedReceivedMessages(ArrayList<String> messageIds) {
		try {

			int res = 0, res1 = 0;
			
			for (String messageId : messageIds) {
				String update = "DELETE FROM message_received_deleted WHERE message_id = ?";
				PreparedStatement stmt = conn.prepareStatement(update);
				stmt.setString(1, messageId);
				res = stmt.executeUpdate();

				String update1 = "DELETE FROM message_received_recipients WHERE message_id = ?";
				PreparedStatement stmt1 = conn.prepareStatement(update1);
				stmt1.setString(1, messageId);
				res1 = stmt1.executeUpdate();

				String update2 = "DELETE FROM message_received_attachments WHERE message_id = ?";
				PreparedStatement stmt2 = conn.prepareStatement(update2);
				stmt2.setString(1, messageId);
				stmt2.executeUpdate();
			}

			if (res > 0 && res1 > 0)
				return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public Boolean deleteAllTrashedMessages(ArrayList<String> transactionIds, ArrayList<String> messageIds) {
		try {

			int res = 0, res1 = 0;
			for (String transactionId : transactionIds) {
				String update = "DELETE FROM message_deleted WHERE transaction_id = ?";
				PreparedStatement stmt = conn.prepareStatement(update);
				stmt.setString(1, transactionId);
				res += stmt.executeUpdate();

				String update1 = "DELETE FROM message_transactions WHERE transaction_id = ?";
				PreparedStatement stmt1 = conn.prepareStatement(update1);
				stmt1.setString(1, transactionId);
				res1 += stmt1.executeUpdate();
			}

			for (String messageId : messageIds) {
				String update2 = "DELETE FROM message_recipients WHERE message_id = ?";
				PreparedStatement stmt2 = conn.prepareStatement(update2);
				stmt2.setString(1, messageId);
				stmt2.executeUpdate();

				String update3 = "DELETE FROM message_read WHERE message_id = ?";
				PreparedStatement stmt3 = conn.prepareStatement(update3);
				stmt3.setString(1, messageId);
				stmt3.executeUpdate();

				String update4 = "DELETE FROM message_clicked_links WHERE message_id = ?";
				PreparedStatement stmt4 = conn.prepareStatement(update4);
				stmt4.setString(1, messageId);
				stmt4.executeUpdate();
			}

			if (res > 0 && res1 > 0)
				return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
}

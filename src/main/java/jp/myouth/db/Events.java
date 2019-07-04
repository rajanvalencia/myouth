package jp.myouth.db;

import java.sql.Date;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Events {
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

	/**
	 * イベントの情報を取得
	 */
	public ArrayList<ArrayList<String>> eventInfo(String event) {
		ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
		ArrayList<String> info = new ArrayList<String>();
		try {
			String query = "SELECT * FROM event where english_e_name = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, event);
			ResultSet rset = stmt.executeQuery();
			
			while (rset.next()) {
				info.add(rset.getString("e_name"));
				info.add(rset.getString("place"));
				info.add(rset.getString("date"));
				info.add(rset.getString("time"));
				info.add(rset.getString("recruitno"));
			}
			rset.close();
			
			String query1 = "SELECT recruitno-COUNT(*) AS participants_per_event FROM event_participants, event WHERE english_e_name = ? AND event.event_id = event_participants.event_id AND join_date > recruitment_start AND join_date < recruitment_end";
			PreparedStatement stmt1 = conn.prepareStatement(query1);
			stmt1.setString(1, event);
			ResultSet rset1 = stmt1.executeQuery();
			
			while (rset1.next()) {
				info.add(rset1.getString("participants_per_event"));
			}
			rset1.close();
			list.add(info);
			return list;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Instagramのurlを取得
	 */
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

	/**
	 * Facebookのurlを取得
	 */
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

	/**
	 * Twitterのurlを取得
	 */
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

	/**
	 * イベントの開催場所を取得
	 */
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

	/**
	 * イベントの管理者のメールを取得
	 */
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

	/**
	 * イベントの目的など取得する
	 */
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

	/**
	 * イベントのロゴを取得する
	 */
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

	/**
	 * イベントの名前を取得
	 */
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

	public ArrayList<String> eventTime(String event) {
		try {
			ArrayList<String> data = new ArrayList<String>();
			String query = "SELECT EXTRACT(HOUR FROM time) AS hour, EXTRACT(MINUTE FROM time) as minute FROM event WHERE english_e_name= ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, event);
			ResultSet rset = stmt.executeQuery();
			while (rset.next()) {
				data.add(rset.getString("hour"));
				data.add(rset.getString("minute"));
			}
			rset.close();
			return data;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<String> eventDate(String event) {
		ArrayList<String> data = new ArrayList<String>();
		try {
			String query = "SELECT EXTRACT(YEAR FROM date) AS year, EXTRACT(MONTH FROM date) AS month, EXTRACT(DAY FROM date) AS day FROM event WHERE english_e_name= ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, event);
			ResultSet rset = stmt.executeQuery();
			if (rset.next()) {
				data.add(rset.getString("year"));
				data.add(rset.getString("month"));
				data.add(rset.getString("day"));
			}
			rset.close();
			return data;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * イベントの募集開始期間を取得
	 */

	public ArrayList<String> recruitmentStartDate(String event) {
		ArrayList<String> data = new ArrayList<String>();
		try {
			String query = "SELECT EXTRACT(YEAR FROM recruitment_start) AS year, EXTRACT(MONTH FROM recruitment_start) AS month, EXTRACT(DAY FROM recruitment_start) AS day FROM event WHERE english_e_name = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, event);
			ResultSet rset = stmt.executeQuery();

			while (rset.next()) {
				data.add(rset.getString("year"));
				data.add(rset.getString("month"));
				data.add(rset.getString("day"));
			}
			rset.close();
			return data;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * イベントの募集終了期間を取得
	 */

	public ArrayList<String> recruitmentEndDate(String event) {
		ArrayList<String> data = new ArrayList<String>();
		try {
			String query = "SELECT EXTRACT(YEAR FROM recruitment_end) AS year, EXTRACT(MONTH FROM recruitment_end) AS month, EXTRACT(DAY FROM recruitment_end) AS day FROM event WHERE english_e_name = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, event);
			ResultSet rset = stmt.executeQuery();

			while (rset.next()) {
				data.add(rset.getString("year"));
				data.add(rset.getString("month"));
				data.add(rset.getString("day"));
			}
			rset.close();
			return data;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * イベントの募集人数制限を取得する
	 */
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

	/**
	 * イベントの全情報の取得
	 */
	public ArrayList<String> allEvents() {
		ArrayList<String> data = new ArrayList<String>();
		try {
			String query = "SELECT e_name, english_e_name FROM event";
			ResultSet rset = stmt.executeQuery(query);
			while (rset.next()) {
				data.add(rset.getString("english_e_name"));
				data.add(rset.getString("e_name"));
			}
			rset.close();
			return data;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 出身国ごとの参加数を取得
	 */
	public ArrayList<ArrayList<String>> geoMap(String event) {
		ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
		ArrayList<String> data = new ArrayList<String>();
		try {
			String query = "SELECT countries.country AS country, COUNT(*) AS total_participants_per_country FROM(SELECT country FROM participants, event_participants, event WHERE country IS NOT NULL AND participants.participant_id =  event_participants.participant_id AND english_e_name = ? AND event.event_id = event_participants.event_id AND join_date >= recruitment_start AND join_date <= recruitment_end UNION ALL SELECT country2 FROM participants, event_participants, event WHERE country2 IS NOT NULL AND participants.participant_id =  event_participants.participant_id AND english_e_name = ? AND event.event_id = event_participants.event_id AND join_date >= recruitment_start AND join_date <= recruitment_end UNION ALL SELECT country3 FROM participants, event_participants, event WHERE country3 IS NOT NULL AND participants.participant_id =  event_participants.participant_id AND english_e_name = ? AND event.event_id = event_participants.event_id AND join_date >= recruitment_start AND join_date <= recruitment_end)  AS countries GROUP BY country";
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

	/**
	 * 参加申し込みのデータをデータベスに書き込む
	 */
	public Boolean insertParticipantData(String event, String name, String fname, String gender, String email,
			String phone, Date birthdate, String career, String company, String country, String country2,
			String country3, String zip, String pref, String address, String allergy, String way) {
		try {
			String query = "SELECT event_id FROM event WHERE english_e_name = \"" + event + "\"";
			ResultSet rset = stmt.executeQuery(query);
			String event_id = new String();
			while (rset.next()) {
				event_id = rset.getString("event_id");
			}
			rset.close();
			stmt.close();

			String insert = "INSERT INTO participants (name, fname, gender, email, phone, birthdate, country, country2, country3, career, company, zip, address, allergy) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement stmt1 = conn.prepareStatement(insert);
			stmt1.setString(1, name);
			stmt1.setString(2, fname);
			stmt1.setString(3, gender);
			stmt1.setString(4, email);
			stmt1.setString(5, phone);
			stmt1.setDate(6, birthdate);
			stmt1.setString(7, country);
			stmt1.setString(8, country2);
			stmt1.setString(9, country3);
			stmt1.setString(10, career);
			stmt1.setString(11, company);
			stmt1.setString(12, zip);
			stmt1.setString(13, pref + address);
			stmt1.setString(14, allergy);
			stmt1.executeUpdate();
			stmt1.close();

			String insert1 = "INSERT INTO event_participants (event_id, participant_id, join_date, join_time, way) VALUES(\""
					+ event_id + "\", LAST_INSERT_ID(), now(), now(), ?)";
			PreparedStatement stmt2 = conn.prepareStatement(insert1);
			stmt2.setString(1, way);
			stmt2.executeUpdate();
			stmt2.close();

			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * パイチャート用の職種または学年ごとの情報を取得
	 */
	public ArrayList<ArrayList<String>> careerPieChart(String event) {
		ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
		ArrayList<String> data = new ArrayList<String>();
		try {
			String total = "SET @total = (SELECT COUNT(*) FROM event, event_participants, participants WHERE event.event_id = event_participants.event_id AND participants.participant_id = event_participants.participant_id AND english_e_name = ? AND join_date <= recruitment_end AND join_date >= recruitment_start)";
			PreparedStatement stmt = conn.prepareStatement(total);
			stmt.setString(1, event);
			stmt.executeUpdate();
			
			String query = "SELECT career, ((COUNT(*)/@total)*100) AS percentage_per_career FROM participants, event_participants, event WHERE  english_e_name = ? AND participants.participant_id = event_participants.participant_id AND event.event_id = event_participants.event_id AND join_date >= recruitment_start AND join_date <= recruitment_end GROUP BY career";
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

	/**
	 * パイチャート用の参加のきっかけの情報を取得
	 */
	public ArrayList<ArrayList<String>> wayPieChart(String event) {
		ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
		ArrayList<String> data = new ArrayList<String>();
		try {
			String total = "SET @total = (SELECT COUNT(*) FROM event, event_participants, participants WHERE event.event_id = event_participants.event_id AND participants.participant_id = event_participants.participant_id AND english_e_name = ? AND join_date <= recruitment_end AND join_date >= recruitment_start)";
			PreparedStatement stmt = conn.prepareStatement(total);
			stmt.setString(1, event);
			stmt.executeUpdate();
			
			String query = "SELECT way, ((COUNT(*)/@total)*100) AS percentage_per_way FROM participants, event_participants, event WHERE  english_e_name = ? AND participants.participant_id = event_participants.participant_id AND event.event_id = event_participants.event_id AND join_date >= recruitment_start AND join_date <= recruitment_end GROUP BY way";
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

	/**
	 * 会社ごとの参加人数
	 */
	public ArrayList<ArrayList<String>> companyTable(String event) {
		ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
		ArrayList<String> data = new ArrayList<String>();
		try {
			String query = "SELECT company, COUNT(*) AS participant_per_company FROM participants, event_participants, event WHERE english_e_name = ? AND event.event_id = event_participants.event_id AND participants.participant_id = event_participants.participant_id AND recruitment_start <= join_date AND join_date <= recruitment_end GROUP BY company";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, event);
			ResultSet rset = stmt.executeQuery();
			
			while (rset.next()) {
				data.add(rset.getString("company"));
				data.add(rset.getString("participant_per_company"));
			}
			list.add(data);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 各イベントのコメントを取得
	 */
	public ArrayList<ArrayList<String>> commentsTable(String event) {
		ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
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
			list.add(data);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 各イベントの改善案を取得
	 */
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

	/**
	 * 画像のurlを取得
	 */
	public ArrayList<ArrayList<String>> getImage(String event) {
		ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
		ArrayList<String> data = new ArrayList<String>();
		try {
			String query = "SELECT url, description FROM event, event_images WHERE event.event_id = event_images.event_id AND english_e_name = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, event);
			ResultSet rset = stmt.executeQuery();

			while (rset.next()) {
				data.add(rset.getString("url"));
				data.add(rset.getString("description"));
			}
			list.add(data);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * イベントのフォームの質問を取得する
	 */
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

	/**
	 * イベントのアンケートの質問を取得する
	 */
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

	/**
	 * アンケートの結果を入力する
	 */
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
	
	/*
	 * イベントの合計参加者を取得する
	 */
	
	public int totalParticipants(String event) {
		try {
			Integer total = 0;
			String query = "SELECT COUNT(*) FROM participants, event_participants, event WHERE participants.participant_id = event_participants.participant_id AND event.event_id = event_participants.event_id AND english_e_name= ? AND join_date >= recruitment_start AND join_date <= recruitment_end";
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

	/*
	 * イベントの詳細を変更する
	 */

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

	/*
	 * イベントの参加申し込みの質問を変更する
	 */

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

			String update = "UPDATE form_questions SET name = ?, fname = ?, gender = ?, email = ?, phone = ?, birthdate = ?, company = ?, career = ?, country = ?, country2 = ?, country3 = ?, address = ?, allergy = ?, way = ?, confirmation = ?, parent_confirmation = ? WHERE event_id = ?";
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
			stmt.setBoolean(13, allergy);
			stmt.setBoolean(14, way);
			stmt.setBoolean(15, confirmation);
			stmt.setBoolean(16, parent_confirmation);
			stmt.setString(17, event_id);
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

	/*
	 * イベントの参加申し込みの質問を変更する
	 */

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
}

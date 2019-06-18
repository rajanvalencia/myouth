package jp.myouth.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MySQL {
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
	 * イベントの名前を取得
	 */
	public String eventName(String eventname) {
		try {
			String data = new String();
			String query = "SELECT e_name FROM event WHERE english_e_name=\"" + eventname + "\"";
			ResultSet rset = stmt.executeQuery(query);
			while (rset.next())
				data = rset.getString("e_name");
			return data;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * イベントの情報を取得
	 */
	public ArrayList<ArrayList<String>> eventInfo(String eventname) {
		ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
		ArrayList<String> info = new ArrayList<String>();
		try {
			String query = "SELECT * FROM event where english_e_name = \"" + eventname + "\"";
			ResultSet rset = stmt.executeQuery(query);
			while (rset.next()) {
				info.add(rset.getString("e_name"));
				info.add(rset.getString("place"));
				info.add(rset.getString("date"));
				info.add(rset.getString("time"));
				info.add(rset.getString("recruitno"));
			}
			rset.close();
			String query1 = "SELECT recruitno-COUNT(*) AS participants_per_event FROM event_participants, event WHERE english_e_name = \""
					+ eventname
					+ "\" AND event.event_id = event_participants.event_id AND join_date > recruitment_start AND join_date < recruitment_end";
			ResultSet rset1 = stmt.executeQuery(query1);
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
	 * 出身国ごとの参加数を取得
	 */
	public ArrayList<ArrayList<String>> geoMap(String eventname) {
		ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
		ArrayList<String> data = new ArrayList<String>();
		try {
			String query = "SELECT countries.country AS country, COUNT(*) AS total_participants_per_country FROM(SELECT country FROM participants, event_participants, event WHERE country IS NOT NULL AND participants.participant_id =  event_participants.participant_id AND english_e_name = \""
					+ eventname +"\" AND event.event_id = event_participants.event_id UNION ALL SELECT country2 FROM participants, event_participants, event WHERE country2 IS NOT NULL AND participants.participant_id =  event_participants.participant_id AND english_e_name = \""
					+eventname+"\" AND event.event_id = event_participants.event_id UNION ALL SELECT country3 FROM participants, event_participants, event WHERE country3 IS NOT NULL AND participants.participant_id =  event_participants.participant_id AND english_e_name = \""
					+eventname+"\" AND event.event_id = event_participants.event_id) AS countries GROUP BY country";
			ResultSet rset = stmt.executeQuery(query);

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
			String phone, String birthdate, String career, String company, String country, String country2,
			String country3, String zip, String pref, String address, String allergy, String way) {
		try {
			String insert = "INSERT INTO participants (name, fname, gender, email, phone, birthdate, country, country2, country3, career, company, zip, address, allergy) VALUES(\""
					+ name + "\",\"" + fname + "\",\"" + gender + "\",\"" + email + "\",\"" + phone + "\",\""
					+ birthdate + "\",\"" + country + "\",\"" + country2 + "\",\"" + country3 + "\",\"" + career
					+ "\", \"" + company + "\", \""+zip+"\", \""+address+"\", \""+allergy+"\")";
			stmt.executeUpdate(insert);

			String query = "SELECT event_id FROM event WHERE english_e_name = \"" + event + "\"";
			ResultSet rset = stmt.executeQuery(query);
			String event_id = new String();
			while (rset.next()) {
				event_id = rset.getString("event_id");
			}
			rset.close();

			String insert3 = "INSERT INTO event_participants (event_id, participant_id, join_date, way) VALUES(\""
					+ event_id + "\", LAST_INSERT_ID(), now(), \"" + way + "\")";
			stmt.executeUpdate(insert3);

			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * パイチャート用の職種または学年ごとの情報を取得
	 */
	public ArrayList<ArrayList<String>> careerPieChart(String eventname) {
		ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
		ArrayList<String> data = new ArrayList<String>();
		try {
			String total = "SET @total = (SELECT COUNT(*) FROM event, event_participants, participants WHERE event.event_id = event_participants.event_id AND participants.participant_id = event_participants.participant_id AND english_e_name = \""
					+ eventname + "\" AND join_date < recruitment_end AND join_date > recruitment_start)";
			stmt.execute(total);
			String query = "SELECT career, ((COUNT(*)/@total)*100) AS percentage_per_career FROM participants, event_participants, event WHERE  english_e_name = \""
					+ eventname
					+ "\" AND participants.participant_id = event_participants.participant_id AND event.event_id = event_participants.event_id AND join_date > recruitment_start AND join_date < recruitment_end GROUP BY career";
			ResultSet rset = stmt.executeQuery(query);

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
	public ArrayList<ArrayList<String>> wayPieChart(String eventname) {
		ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
		ArrayList<String> data = new ArrayList<String>();
		try {
			String total = "SET @total = (SELECT COUNT(*) FROM event, event_participants, participants WHERE event.event_id = event_participants.event_id AND participants.participant_id = event_participants.participant_id AND english_e_name = \""
					+ eventname + "\" AND join_date < recruitment_end AND join_date > recruitment_start)";
			stmt.execute(total);
			String query = "SELECT way, ((COUNT(*)/@total)*100) AS percentage_per_way FROM participants, event_participants, event WHERE  english_e_name = \""
					+ eventname
					+ "\" AND participants.participant_id = event_participants.participant_id AND event.event_id = event_participants.event_id AND join_date > recruitment_start AND join_date < recruitment_end GROUP BY way";
			ResultSet rset = stmt.executeQuery(query);

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
	public ArrayList<ArrayList<String>> companyTable(String eventname) {
		ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
		ArrayList<String> data = new ArrayList<String>();
		try {
			String query = "SELECT company, COUNT(*) AS participant_per_company FROM participants, event_participants, event WHERE english_e_name = \""
					+ eventname
					+ "\" AND event.event_id = event_participants.event_id AND participants.participant_id = event_participants.participant_id AND recruitment_start < join_date AND join_date < recruitment_end GROUP BY company";
			ResultSet rset = stmt.executeQuery(query);
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
	public ArrayList<ArrayList<String>> commentsTable(String eventname) {
		ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
		ArrayList<String> data = new ArrayList<String>();
		try {
			String query = "SELECT event_survey.satisfaction, event_survey.date, event_survey.time, event_survey.comments FROM event_survey, event WHERE english_e_name = \""
					+ eventname
					+ "\" AND event.event_id = event_survey.event_id AND event_survey.date <= event.recruitment_end ORDER BY survey_id DESC";
			ResultSet rset = stmt.executeQuery(query);
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
	public ArrayList<ArrayList<String>> improvementPlansTable(String eventname) {
		ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
		ArrayList<String> data = new ArrayList<String>();
		try {
			String query = "SELECT event_survey.date, event_survey.time, event_survey.improvements FROM event_survey, event WHERE english_e_name = \""
					+ eventname
					+ "\" AND event.event_id = event_survey.event_id AND event_survey.date <= event.recruitment_end ORDER BY survey_id DESC";
			ResultSet rset = stmt.executeQuery(query);
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
	 * イベントの全情報の取得
	 */
	public ArrayList<ArrayList<String>> allEventsInfo() {
		ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
		ArrayList<String> data = new ArrayList<String>();
		try {
			String query = "SELECT e_name, english_e_name, place, date, recruitno FROM event";
			ResultSet rset = stmt.executeQuery(query);
			while (rset.next()) {
				data.add(rset.getString("e_name"));
				data.add(rset.getString("date"));
				data.add(rset.getString("recruitno"));
				data.add(rset.getString("english_e_name"));
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
	 * 画像のurlを取得
	 */
	public ArrayList<ArrayList<String>> getImage(String eventname) {
		ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
		ArrayList<String> data = new ArrayList<String>();
		try {
			String query = "SELECT url, description FROM event, event_images WHERE event.event_id = event_images.event_id AND english_e_name = \""
					+ eventname + "\"";
			ResultSet rset = stmt.executeQuery(query);
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
	 * Instagramのurlを取得
	 */
	public String instagramUrl(String eventname) {
		String data = new String();
		try {
			String query = "SELECT instagram_url FROM event_url, event WHERE event.event_id = event_url.event_id AND english_e_name = \""
					+ eventname + "\"";
			ResultSet rset = stmt.executeQuery(query);
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
	public String facebookUrl(String eventname) {
		String data = new String();
		try {
			String query = "SELECT facebook_url FROM event_url, event WHERE event.event_id = event_url.event_id AND english_e_name = \""
					+ eventname + "\"";
			ResultSet rset = stmt.executeQuery(query);
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
	public String twitterUrl(String eventname) {
		String data = new String();
		try {
			String query = "SELECT twitter_url FROM event_url, event WHERE event.event_id = event_url.event_id AND english_e_name = \""
					+ eventname + "\"";
			ResultSet rset = stmt.executeQuery(query);
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
	public String eventLocation(String eventname) {
		String data = new String();
		try {
			String query = "SELECT place FROM event WHERE english_e_name = \"" + eventname + "\"";
			ResultSet rset = stmt.executeQuery(query);
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
	public String eventEmail(String eventname) {
		String data = new String();
		try {
			String query = "SELECT email FROM event, event_admin_email WHERE event.event_id = event_admin_email.event_id AND english_e_name = \""
					+ eventname + "\"";
			ResultSet rset = stmt.executeQuery(query);
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
	public String eventDescription(String eventname) {
		String data = new String();
		try {
			String query = "SELECT description FROM event, event_description WHERE event.event_id = event_description.event_id AND english_e_name = \""
					+ eventname + "\"";
			ResultSet rset = stmt.executeQuery(query);
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
	public String eventLogo(String eventname) {
		String data = new String();
		try {
			String query = "SELECT logo_url FROM event, event_logo WHERE event.event_id = event_logo.event_id AND english_e_name = \""
					+ eventname + "\"";
			ResultSet rset = stmt.executeQuery(query);
			while (rset.next())
				data = rset.getString("logo_url");
			return data;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * イベントのフォームの種類を取得する
	 */
	public String eventForm(String eventname) {
		String data = new String();
		try {
			String query = "SELECT type FROM event, form_type WHERE event.event_id = form_type.event_id AND english_e_name = \""
					+ eventname + "\"";
			ResultSet rset = stmt.executeQuery(query);
			while (rset.next())
				data = rset.getString("type");
			return data;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * イベントのフォームの質問を取得する
	 */
	public ArrayList<Boolean> formQuestion(String eventname) {
		ArrayList<Boolean> data = new ArrayList<Boolean>();
		try {
			String query = "SELECT name, fname, gender, email, phone, birthdate, career, company, country, country2, country3, address, allergy, way, parent_confirmation, confirmation FROM event, form_questions WHERE event.event_id = form_questions.event_id AND english_e_name = \""
					+ eventname + "\"";
			ResultSet rset = stmt.executeQuery(query);
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
				data.add(rset.getBoolean("parent_confirmation"));
				data.add(rset.getBoolean("confirmation"));
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
	public ArrayList<Boolean> surveyQuestion(String eventname) {
		ArrayList<Boolean> data = new ArrayList<Boolean>();
		try {
			String query = "SELECT survey_questions.name, survey_questions.fname, survey_questions.satisfaction, survey_questions.comments, survey_questions.transportation, survey_questions.improvements FROM survey_questions, event WHERE event.event_id = survey_questions.event_id AND english_e_name = \""
					+ eventname + "\"";
			ResultSet rset = stmt.executeQuery(query);
			while (rset.next()) {
				data.add(rset.getBoolean("name"));
				data.add(rset.getBoolean("fname"));
				data.add(rset.getBoolean("satisfaction"));
				data.add(rset.getBoolean("comments"));
				data.add(rset.getBoolean("transportation"));
				data.add(rset.getBoolean("improvements"));
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
	public Boolean insertSurveyData(String eventname, String name, String fname, String satisfaction, String details, String improvement,
			ArrayList<Boolean> transportation) {
		String event_id = new String();
		try {
			String query = "SELECT event_id FROM event WHERE english_e_name = \"" + eventname + "\"";
			ResultSet rset = stmt.executeQuery(query);
			while (rset.next())
				event_id = rset.getString("event_id");
			rset.close();

			String update = "INSERT INTO event_survey (event_id, date, time, name, fname, satisfaction, comments, improvements) VALUES("
					+ event_id + ", now(), now(), \""+name+"\", \""+fname+"\", \"" + satisfaction + "\",\"" + details + "\",\"" + improvement
					+ "\")";
			stmt.executeUpdate(update);

			String update1 = "INSERT INTO event_transportation (survey_id, train, bus, four_wheel, two_wheel, others) VALUES(LAST_INSERT_ID(), "
					+ transportation.get(0) + ", " + transportation.get(1) + ", " + transportation.get(2) + ", "
					+ transportation.get(3) + ", " + transportation.get(4) + ")";
			stmt.executeUpdate(update1);

			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public ArrayList<Double> ratingData(String eventname) {
		ArrayList<Double> list = new ArrayList<Double>();
		try {
			String query = "SELECT COUNT(*) AS totalRatings, ROUND(SUM(satisfaction)/COUNT(*), 1) AS averageRatings FROM event_survey, event WHERE english_e_name = \""
					+ eventname + "\" AND event.event_id = event_survey.event_id";
			ResultSet rset = stmt.executeQuery(query);
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

	public ArrayList<Double> totalPerSatisfaction(String eventname) {
		ArrayList<Double> list = new ArrayList<Double>();
		try {

			String query = "SELECT satisfaction, COUNT(*) AS total FROM event_survey, event WHERE english_e_name = \""
					+ eventname
					+ "\" AND event.event_id = event_survey.event_id GROUP BY satisfaction ORDER BY satisfaction DESC";
			ResultSet rset = stmt.executeQuery(query);
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
}

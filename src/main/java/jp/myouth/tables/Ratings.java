package jp.myouth.tables;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import jp.myouth.db.Events;

public class Ratings {

	public void append(HttpServletRequest request) {
		HttpSession session = request.getSession();

		String event = (String) session.getAttribute("event");

		String result = "<section id=\"reviews\" class=\"four\"><div class=\"container\"><header><h2>評価</h2></header>";

		Events db = new Events();
		db.open();
		ArrayList<Double> ratingData = db.ratingData(event);
		ArrayList<String> comments = db.commentsTable(event);
		ArrayList<Double> ratingDataPerSatisfaction = db.totalPerSatisfaction(event);
		db.close();

		int totalRatings;
		double averageRatings;

		totalRatings = ratingData.get(0).intValue();
		averageRatings = ratingData.get(1);
		int i = 0;
		for (i = 1; i <= 5; i++) {
			if (i <= averageRatings)
				result += "<span class=\"fa fa-star light\"></span>";
			else {
				if ((double) i - 0.5 <= averageRatings) {
					result += "<span class=\"fa fa-star-half light\"></span>";
				} else
					result += "<span class=\"fa fa-star dark\"></span>";
			}
		}
		result += "<p>" + totalRatings + " 件中　平均満足度は " + averageRatings + "<p>";
		result += "<div class=\"row\">";

		i = 0;
		double total = totalRatings;
		for (Double data : ratingDataPerSatisfaction) {
			if (i % 2 == 0) {
				result += "<div class=\"side\"><div>";
				result += "<span style=\"margin-right: 18px;\">" + data.intValue()
						+ "</span><span class=\"fa fa-star light\"></span>";
				result += "</div></div>";
				result += "<div class=\"middle\"><div class=\"bar-container\"><div class=\"bar-" + data.intValue() + "\"";
			} else {
				result += " style=\"width:" + (data / total) * 100 + "%;\"></div></div></div>";
				result += "<div class=\"side right\"><div>" + data.intValue() + "</div></div>";
			}
			i++;
		}
		result += "</div><br /> <br />";

		if (comments.size() == 0)
			result += "コメントがありません";

		result += "<table><tbody>";

		i = 0;
		int j = 0;
		for (String string : comments) {
			if (i % 3 == 0) {
				result += "<tr>";
				result += "<td><span>";
				int stars = Integer.parseInt(string);
				for (j = 0; j < stars; j++)
					result += "<span class=\"fa fa-star light\"></span>";

				for (; j < 5; j++)
					result += "<span class=\"fa fa-star dark\"></span>";

				result += "</span>";
			} else if (i % 3 == 1)
				result += string + "</td></tr>";
			else
				result += "<tr><td>" + string + "</td></tr>";
			i++;
		}

		result += "</tbody></table></div></section>";

		request.setAttribute("eventRatings", result);
	}
}

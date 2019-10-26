package jp.myouth.tables;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import jp.myouth.db.Events;

public class UserPageRatings {

	public void append(HttpServletRequest request) {
		HttpSession session = request.getSession();

		String event = (String) session.getAttribute("event");

		String result = "<section id=\"reviews\" class=\"four\">";
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
				result += "\n\t\t\t\t\t\t\t<span class=\"fa fa-star light\"></span>";
			else {
				if ((double) i - 0.5 <= averageRatings) {
					result += "\n\t\t\t\t\t\t\t<span class=\"fa fa-star-half light\"></span>";
				} else
					result += "\n\t\t\t\t\t\t\t<span class=\"fa fa-star dark\"></span>";
			}
		}
		result += "\n\t\t\t\t\t\t\t<p>" + totalRatings + " 件中　平均満足度は " + averageRatings + "<p>";
		result += "\n\t\t\t\t\t\t\t<div class=\"row\">";

		i = 0;
		double total = totalRatings;
		for (Double data : ratingDataPerSatisfaction) {
			if (i % 2 == 0) {
				result += "\n\t\t\t\t\t\t\t\t<div class=\"side\">"
						+ "\n\t\t\t\t\t\t\t\t\t<div>";
				result += "\n\t\t\t\t\t\t\t\t\t\t<span style=\"margin-right: 18px;\">" + data.intValue() + "</span>"
						+ "\n\t\t\t\t\t\t\t\t\t\t<span class=\"fa fa-star light\"></span>";
				result += "\n\t\t\t\t\t\t\t\t\t</div>"
						+ "\n\t\t\t\t\t\t\t\t</div>";
				result += "\n\t\t\t\t\t\t\t\t<div class=\"middle\">"
						+ "\n\t\t\t\t\t\t\t\t\t<div class=\"bar-container\">"
						+ "\n\t\t\t\t\t\t\t\t\t\t<div class=\"bar-" + data.intValue() + "\"";
			} else {
				result += " style=\"width:" + (data / total) * 100 + "%;\"></div>"
						+ "\n\t\t\t\t\t\t\t\t\t</div>"
						+ "\n\t\t\t\t\t\t\t\t</div>";
				result += "\n\t\t\t\t\t\t\t\t<div class=\"side right\">"
						+ "\n\t\t\t\t\t\t\t\t\t<div>" + data.intValue() + "</div>"
						+ "\n\t\t\t\t\t\t\t\t</div>";
			}
			i++;
		}
		result += "\n\t\t\t\t\t\t\t</div>"
				+ "\n\t\t\t\t\t\t\t<br />"
				+ "\n\t\t\t\t\t\t\t<br />";

		if (comments.size() == 0)
			result += "\n\t\t\t\t\t\t\tコメントがありません";

		result += "\n\t\t\t\t\t\t\t<table>"
				+ "\n\t\t\t\t\t\t\t\t<tbody>";

		i = 0;
		int j = 0;
		for (String string : comments) {
			if (i % 3 == 0) {
				result += "\n\t\t\t\t\t\t\t\t\t<tr>";
				result += "\n\t\t\t\t\t\t\t\t\t\t<td>"
						+ "\n\t\t\t\t\t\t\t\t\t\t\t<span>";
				int stars = Integer.parseInt(string);
				for (j = 0; j < stars; j++)
					result += "\n\t\t\t\t\t\t\t\t\t\t\t\t<span class=\"fa fa-star light\"></span>";

				for (; j < 5; j++)
					result += "\n\t\t\t\t\t\t\t\t\t\t\t\t<span class=\"fa fa-star dark\"></span>";

				result += "\n\t\t\t\t\t\t\t\t\t\t\t</span>";
			} else if (i % 3 == 1)
				result += string 
						+ "\n\t\t\t\t\t\t\t\t\t\t</td>"
						+ "\n\t\t\t\t\t\t\t\t\t</tr>";
			else
				result += "\n\t\t\t\t\t\t\t\t\t<tr>"
						+ "\n\t\t\t\t\t\t\t\t\t\t<td>" + string.replaceAll("\n", "\n\t\t\t\t\t\t\t\t\t\t<br />") + "</td>"
						+ "\n\t\t\t\t\t\t\t\t\t</tr>";
			i++;
		}

		result += "\n\t\t\t\t\t\t\t\t</tbody>"
				+ "\n\t\t\t\t\t\t\t</table>";
		
		request.setAttribute("eventRatings", result);
	}
}

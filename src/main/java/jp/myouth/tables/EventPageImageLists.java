package jp.myouth.tables;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import jp.myouth.db.Events;

public class EventPageImageLists {

	public void append(HttpServletRequest request) {
		String result = new String();
		
		ArrayList<String> imageLists = new ArrayList<String>();

		HttpSession session = request.getSession();
		String event = (String) session.getAttribute("event");

		Events db = new Events();
		db.open();
		imageLists = db.getImage(event);
		db.close();

		int i = 0;
		int row2 = 0, row3 = 0,  len = 0;
		len = imageLists.size();
		
		if((imageLists.size() / 2)  % 3 == 0) {
			row2 = imageLists.size() / 3;
			row3 = (imageLists.size() / 3) * 2;
		} else if((imageLists.size() / 2)  % 3 == 1) {
			row2 = (imageLists.size() / 3) + 2;
			row3 = (row2 * 2) - 2;
		} else {
			row2 = (imageLists.size() / 3) + 1;
			row3 = row2 * 2;
		}

		for (String string : imageLists) {
			if (i == 0 || i == row2 || i == row3)
				result += "<div class=\"col-4 col-12-mobile\">";

			if (i % 2 == 0) {
				result += "<article class=\"item\">";
				result += "<a href=\"#\" class=\"image fit\">";
				result += "<img src=\"https://s3-ap-northeast-1.amazonaws.com/jp.myouth.images/events/" + event + "/"
						+ string + "\" alt=\"\" />";
				result += "</a>";
			}

			else if (i % 2 == 1 && string != null) {
				result += "<header><h3>" + string + "</h3></header>";
				result += "</article>";
			}

			else {
				result += "</article>";
			}

			if (i == row2 - 1 || i == row3 - 1 || i == len - 1)
				result += "</div>";

			i++;
		}
		request.setAttribute("imageLists", result);
	}
}

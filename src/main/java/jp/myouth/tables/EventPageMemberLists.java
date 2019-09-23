package jp.myouth.tables;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import jp.myouth.db.Events;

public class EventPageMemberLists {

	public void append(HttpServletRequest request) {
		String result = new String();
		
		HttpSession session = request.getSession();
		String event = (String) session.getAttribute("event");

		Events db = new Events();
		db.open();
		ArrayList<String> memberData = db.eventPageMember(event);
		db.close();

		int i = 0;

		int row2 = 0, row3 = 0,  len = 0;
		len = memberData.size();
		
		if((memberData.size() / 2)  % 3 == 0) {
			row2 = memberData.size() / 3;
			row3 = (memberData.size() / 3) * 2;
		} else if((memberData.size() / 2)  % 3 == 1) {
			row2 = (memberData.size() / 3) + 2;
			row3 = (row2 * 2) - 2;
		} else {
			row2 = (memberData.size() / 3) + 1;
			row3 = row2 * 2;
		}
		
		for (String string : memberData) {
			if (i == 0 || i == row2 || i == row3)
				result += "\n\t\t\t\t\t<div class=\"col-4\">";

			if (i % 2 == 0) {
				result += "\n\t\t\t\t\t\t<article class=\"item\">";
				result += "\n\t\t\t\t\t\t\t<a href=\"#\" class=\"image fit\">";
				result += "<img style=\"border-radius: 50%;\" src=\""+ string + "\" alt=\"\" /></a>";
			}

			else if (i % 2 == 1 && string != null) {
				result += string;
				result += "\n\t\t\t\t\t\t</article>";
			}

			else {
				result += "\n\t\t\t\t\t\t</article>";
			}

			if (i == row2 - 1 || i == row3 - 1 || i == len - 1)
				result += "\n\t\t\t\t\t</div>";

			i++;
		}
		request.setAttribute("memberLists", result);
	}
}

package jp.myouth.tables;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.myouth.db.Events;

public class EventLists {

	public void append(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		String result = new String();
		ArrayList<String> data = new ArrayList<String>();
		String search = new String(); 
		
		
		if(session.getAttribute("searchEvents") != null) {
			search = session.getAttribute("searchEvents").toString();
			session.removeAttribute("searchEvents");
		}

		Events db = new Events();
		db.open();
		if(search == null)
			data = db.allEvents();
		else
			data = db.searchEvents(search);
		db.close();

		result += "<div class=\"table-wrapper\"><table><tbody>";
		
		int i = 0;
		for (String string : data) {
			if (i % 4 == 0)
				result += "<tr><td><a href=\"/events/" + string + "\">";
			else if(i % 4 == 1)
				result += "<span class=\"image left\"><img style=\"border-radius: 25px;\" src=\""+string+"\" alt=\"\" width=\"100\"/></span>";
			else if(i % 4 == 2)
				result += "<h3>" + string + "</h3>";
			else
				result += string+"</p></a></td></tr>";
			i++;
		}

		result += "</tbody></table></div>";
		
		request.setAttribute("result", result);
		
	}
}

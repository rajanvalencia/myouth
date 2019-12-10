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

		result += "\n\t\t\t\t\t\t\t<div class=\"table-wrapper\">"
				+ "\n\t\t\t\t\t\t\t\t<table>";
		
		int i = 0;
		for (String string : data) {
			if (i % 4 == 0)
				result += "\n\t\t\t\t\t\t\t\t\t<tr>"
						+ "\n\t\t\t\t\t\t\t\t\t\t<td>"
						+ "\n\t\t\t\t\t\t\t\t\t\t\t<div class=\"row gtr-uniform gtr-50\">"
						+ "\n\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"col-2 col-4-mobile\">"
						+ "\n\t\t\t\t\t\t\t\t\t\t\t\t\t<a href=\"/events/" + string + "\">";
			else if(i % 4 == 1)
				result += "\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t<img style=\"border-radius: 25px;\" src=\""+string+"\" alt=\"\" width=\"100\"/>"
						+ "\n\t\t\t\t\t\t\t\t\t\t\t\t\t</a>"
						+ "\n\t\t\t\t\t\t\t\t\t\t\t\t</div>";
			else if(i % 4 == 2)
				
				result += "\n\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"col-10 col-8-mobile\">"
						+ "\n\t\t\t\t\t\t\t\t\t\t\t\t\t<h3>" + string + "</h3>";
			else
				result += "\n\t\t\t\t\t\t\t\t\t\t\t\t\t"+string
						+ "\n\t\t\t\t\t\t\t\t\t\t\t\t</div>"
						+ "\n\t\t\t\t\t\t\t\t\t\t\t</div>"
						+ "\n\t\t\t\t\t\t\t\t\t\t</td>"
						+ "\n\t\t\t\t\t\t\t\t\t</tr>";
			i++;
		}

		result += "\n\t\t\t\t\t\t\t\t</table>"
				+ "\n\t\t\t\t\t\t\t</div>";
		
		request.setAttribute("result", result);
		
	}
}

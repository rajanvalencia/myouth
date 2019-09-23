package jp.myouth.tables;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import jp.myouth.db.Messages;

public class EventSentMessages {

	public void append(HttpServletRequest request) {
		HttpSession session = request.getSession();
		
		String event = (String) session.getAttribute("event");
		
		Messages db = new Messages();
		db.open();
		ArrayList<String> data = db.eventSentMessages(event);
		db.close();
		
		String result = "<div class=\"table-wrapper\">"
				+ "\n\t\t\t\t<table>"
				+ "\n\t\t\t\t\t<tbody>";
		
		int i = 0;
		for(String string : data) {
			if(i % 4 == 0)
				result += "<tr><td><a href=\"/home/"+event+"/mail/sent/"+string+"\" style=\"color: #777;\"><div class=\"row gtr-50 gtr-uniform\">";
			else if(i % 4 == 1)
				result += "<div class=\"col-10 col-8-mobile\"><span style=\"font-size: 1.25em;\"><strong>"+string+"</strong></span>";
			else if(i % 4 == 2)
				result += "<p>"+string+"</p></div>";
			else
				result += "<div class=\"col-2 col-4-mobile\">"+string+"</div></div></a></td></tr>";
			i++;
		}
		
		result += "</tbody></table></div>";
		request.setAttribute("eventSentMessages", result);
	}
}

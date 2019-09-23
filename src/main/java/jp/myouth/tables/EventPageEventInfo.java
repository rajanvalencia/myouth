package jp.myouth.tables;

import java.util.ArrayList;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import jp.myouth.db.Events;

public class EventPageEventInfo {
	
	public void append(HttpServletRequest request) {
		HttpSession session = request.getSession();
		
		String event = (String) session.getAttribute("event");
		
		Events db = new Events();
		db.open();
		ArrayList<String> data = db.eventInfo(event);
		db.close();
		
		String result = new String();
		int i = 0;
		for(String string :  data) {
			if(i % 9 == 0)
				result += "<img src=\""+string+"\" style=\"border-radius: 25px; margin-right: auto; margin-left:auto;\" width=\"150\">";
			else if(i % 9 == 1)
				result += "\n\t\t\t\t<h2>"+string+"</h2>";
			else if(i % 9 == 2)
				result += "\n\t\t\t\t<p>"+string+"</p>";
			else if(i % 9 == 3)
				result += "\n\t\t\t\t<p><span class=\"fa fa-map-marker-alt\"></span> <a target=\"blank\" href=\"https://www.google.com/maps/dir/?api=1&destination="+string+"\">"+string+"</a></p>";
			else if(i % 9 == 4)
				result += "\n\t\t\t\t<p><span class=\"fa fa-calendar-check\"></span> "+string+"</p>";
			else if(i % 9 == 5)
				result += "\n\t\t\t\t<p><span class=\"fa fa-clock\"></span> "+string+"</p>";
			else if(i % 9 == 6)
				result += "\n\t\t\t\t<p><span class=\"fa fa-users\"></span> "+string+"</p>";
			else if(i % 9 == 7)
				result += "\n\t\t\t\t<p><span class=\"fa fa-calendar\"></span> "+string+"</p>";
			else if(i % 9 == 8) {
				result += "\n\t\t\t\t<p><span class=\"fa fa-user-plus\"></span> "+string+"</p>"
						+ "\n\t\t\t\t<p><span class=\"fa fa-file-alt\"></span> <a href=\"/events/"+event+"/form\">参加申し込み</a></span></p>"
						+ "\n\t\t\t\t<p><span class=\"fa fa-comment\"></span> <a href=\"/events/"+event+"/survey\">アンケート</a></span></p>"
						+ "\n\t\t\t\t<p><span class=\"fa fa-envelope\"></span> <a href=\"mailto:"+event+"@myouth.jp\">"+event+"@myouth.jp</a></p>";
			}
			i++;
		}
		
		request.setAttribute("eventPageEventInfo", result);
	}
}

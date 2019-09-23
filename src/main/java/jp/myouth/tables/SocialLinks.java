package jp.myouth.tables;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import jp.myouth.db.Events;

public class SocialLinks {
	public void append(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String event = (String) session.getAttribute("event");
		
		Events db = new Events();
		db.open();
		String instagramUrl = db.instagramUrl(event);
		String facebookUrl = db.facebookUrl(event);
		String twitterUrl = db.twitterUrl(event);
		String eventEmail = db.eventEmail(event);
		db.close();
		
		String result =	"\n\t\t<div class=\"bottom\">"
					+ "\n\t\t\t<ul class=\"icons\">"
					+ "\n\t\t\t\t<li>"
					+ "\n\t\t\t\t\t<a href=\"/\" class=\"icon fa-home\"><span class=\"label\">Home</span></a>"
					+ "\n\t\t\t\t</li>";
		
		if (instagramUrl.length() > 0) {
			result += "\n\t\t\t\t<li>"
					+ "\n\t\t\t\t\t<a href=\"" + instagramUrl + "\" class=\"icon fa-instagram\"><span class=\"label\">Instagram</span></a>"
					+ "\n\t\t\t\t</li>";
		}
		if (facebookUrl.length() > 0) {
			result += "\n\t\t\t\t<li>"
					+ "\n\t\t\t\t\t<a href=\"" + facebookUrl + "\" class=\"icon fa-facebook\"><span class=\"label\">Facebook</span></a>"
					+ "\n\t\t\t\t</li>";
		}
		if (twitterUrl.length() > 0) {
			result += "\n\t\t\t\t<li>"
					+ "\n\t\t\t\t\t<a href=\"" + twitterUrl + "\" class=\"icon fa-twitter\"><span class=\"label\">Twitter</span></a>"
					+ "\n\t\t\t\t</li>";
		}
	
		result += "\n\t\t\t</ul>"
				+ "\n\t\t</div>";
		
		request.setAttribute("socialLinks", result);
	}
}

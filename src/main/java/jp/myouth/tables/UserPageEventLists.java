package jp.myouth.tables;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import jp.myouth.db.User;

public class UserPageEventLists {
	
	public void append(HttpServletRequest request) {
		HttpSession session = request.getSession();
		
		String userId = (String) session.getAttribute("userId");
		
		User db = new User();
		db.open();
		ArrayList<String> data = db.managingEvents(userId);
		db.close();
		
		String result = "<div class=\"table-wrapper\"><table><tbody>";
		
		int i = 0;
		for(String string : data){
			if(i % 9 == 0)
				result += "<tr><td><p><div class=\"row gtr-50 gtr-uniform\"><div class=\"col-2 col-4-mobile\"><a href=\"/events/"+string+"\">";
			else if(i % 9 == 1)
				result += "<img style=\"border-radius: 25px;\" src=\""+string+"\" alt=\"\" width=\"100\"></div>";
			else if(i % 9 == 2)
				result += "<div class=\"col-8 col-7-mobile\"><span style=\"font-size: 1.75em;\">"+string+"</span><br />";
			else if(i % 9 == 3)
				result += "<span class=\"fa fa-map-marker\"></span> "+string+"<br />";
			else if(i % 9 == 4)
				result += "<span class=\"fa fa-calendar-check\"></span> "+string+"<br />";
			else if(i % 9 == 5)
				result += "<span class=\"fa fa-clock\"></span> "+string+"<br />";
			else if(i % 9 == 6) 
				result += "<span class=\"fa fa-calendar\"></span> "+string+"<br />";
			else if(i % 9 == 7)
				result += "<span class=\"fa fa-users\"></span> "+string+"</a></div>";
			else
				result += "<div class=\"col-2 col-1-mobile\"><a href=\"home/"+string+"/admin\" style=\"color: #e89980; border-bottom-color: transparent;\"><span class=\"fa fa-cog faa-spin animated\"></span></a></div></div></td></tr>";
			i++;
		}
		result += "</tbody></table></div>";
		
		request.setAttribute("userPageEventLists", result);
	}
}

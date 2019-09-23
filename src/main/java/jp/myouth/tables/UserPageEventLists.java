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

		String result = "<div class=\"table-wrapper\">\n\t\t\t\t\t\t<table>\n\t\t\t\t\t\t\t<tbody>";

		int i = 0;
		for (String string : data) {
			if(i % 4 == 0) {
				result += "\n\t\t\t\t\t\t\t\t<tr>"
						+ "\n\t\t\t\t\t\t\t\t\t<td>"
						+ "\n\t\t\t\t\t\t\t\t\t\t<p>"
						+ "\n\t\t\t\t\t\t\t\t\t\t\t<div class=\"row gtr-50 gtr-uniform\">"
						+ "\n\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"col-2-5 col-1-mobile\"></div>"
						+ "\n\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"col-2 col-3-mobile\"><a href=\"home/" + string + "/settings\" style=\"color: #e89980; border-bottom-color: transparent;\"><span class=\"fa fa-cog fa-2x\"></span></a></div>";
				
				result += "\n\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"col-2 col-3-mobile\"><a href=\"home/" + string + "/participants\" style=\"color: #e89980; border-bottom-color: transparent;\"><span class=\"fa fa-users fa-2x\"></span></a></div>";
				
				result += "\n\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"col-2 col-3-mobile\"><a href=\"home/" + string + "/member\" style=\"color: #e89980; border-bottom-color: transparent;\"><span class=\"fa fa-user-circle fa-2x\"></span></a></div>";
				
				result += "\n\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"col-2\"><a href=\"home/" + string + "/photo-video\" style=\"color: #e89980; border-bottom-color: transparent;\"><span class=\"fa fa-image fa-2x\"></span></a></div>"
						+ "\n\t\t\t\t\t\t\t\t\t\t\t</div>";
				
				result += "\n\t\t\t\t\t\t\t\t\t\t\t<div class=\"row gtr-50 gtr-uniform\">"
						+ "\n\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"col-3 col-1-mobile\"></div>"
						+ "\n\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"col-3 col-4-mobile\">"
						+ "\n\t\t\t\t\t\t\t\t\t\t\t<a href=\"/events/" + string + "\">";
			}
			else if (i % 4 == 1)
				result += "\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t<img style=\"border-radius: 25px;\" src=\"" + string + "\" alt=\"\" width=\"100\"></a></div>";
			else if (i % 4 == 2)
				result += "\n\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"col-6 col-7-mobile\"><br /><h3>" + string + "</h3></div>"
						+ "\n\t\t\t\t\t\t\t\t\t\t\t</div>";
			else {
				result += "\n\t\t\t\t\t\t\t\t\t\t\t<div class=\"row gtr-50 gtr-uniform\">"
						+ "\n\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"col-2-5 col-1-mobile\"></div>";
				
				result += "\n\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"col-2 col-3-mobile\"><a href=\"home/" + string + "/mail\" style=\"color: #e89980; border-bottom-color: transparent;\"><span class=\"fa fa-envelope fa-2x\"></span></a></div>";
				
				result += "\n\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"col-2 col-3-mobile\"><a href=\"home/" + string + "/analytics\" style=\"color: #e89980; border-bottom-color: transparent;\"><span class=\"fas fa-chart-area fa-2x\"></span></a></div>";
				
				result += "\n\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"col-2 col-3-mobile\"><a href=\"home/" + string + "/notifications\" style=\"color: #e89980; border-bottom-color: transparent;\"><span class=\"fa fa-bell fa-2x\"></span></a></div>";
				
				result += "\n\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"col-2 col-2-mobile\"><a href=\"home/" + string + "/downloadSettings\" style=\"color: #e89980; border-bottom-color: transparent;\"><span class=\"fa fa-download fa-2x\"></span></a></div>";
				
				result += "\n\t\t\t\t\t\t\t\t\t\t\t</div>"
						+ "\n\t\t\t\t\t\t\t\t\t\t</p>"
						+ "\n\t\t\t\t\t\t\t\t\t</td>"
						+ "\n\t\t\t\t\t\t\t\t</tr>";
			}
			i++;
		}
		result += "\n\t\t\t\t\t\t\t</tbody>\n\t\t\t\t\t\t\t</table>\n\t\t\t\t\t\t</div>";
		
		request.setAttribute("userPageEventLists", result);
	}
}

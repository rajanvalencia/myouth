package jp.myouth.tables;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.myouth.db.User;

public class MemberPageMemberList {

	public void append(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Boolean user = (Boolean) session.getAttribute("user");
		if (!user)
			response.sendRedirect("/login");
		
		String event = (String) session.getAttribute("event");
		
		User db = new User();
		db.open();
		ArrayList<String> data = db.eventMember(event);
		db.close();
		
		String result = "<div class=\"table-wrapper\">"+
						"\n\t\t\t\t\t\t\t<table>"+
						"\n\t\t\t\t\t\t\t\t<tbody>";
		int i = 0;
		for (String string : data) {
			if (i % 3 == 0) {
				result += "\n\t\t\t\t\t\t\t\t\t<tr>"
						+ "\n\t\t\t\t\t\t\t\t\t\t<td>"
						+ "\n\t\t\t\t\t\t\t\t\t\t<div class=\"row gtr-50 gtr-uniform\">"
						+ "\n\t\t\t\t\t\t\t\t\t\t\t<div class=\"col-2 col-4-mobile\">"
						+ "\n\t\t\t\t\t\t\t\t\t\t\t\t<img style=\"border-radius: 50%;\" src=\""+string+"\" alt=\"\" width=\"70\"/>"
						+ "\n\t\t\t\t\t\t\t\t\t\t\t</div>";
			}
			else if (i % 3 == 1)
				result += "\n\t\t\t\t\t\t\t\t\t\t\t<div class=\"col-9 col-6-mobile\">"
						+ "\n\t\t\t\t\t\t\t\t\t\t\t\t<br /><h4>" + string + "</h4>"
						+ "\n\t\t\t\t\t\t\t\t\t\t\t</div>";
			else
				result += "\n\t\t\t\t\t\t\t\t\t\t\t<div class=\"col-1 col-2-mobile\">"		
						+ "\n\t\t\t\t\t\t\t\t\t\t\t\t<br /><a href=\"/home/" + event + "/member/remove/" + string + "\"><i class=\"fa fa-close\"></i></a>"
						+ "\n\t\t\t\t\t\t\t\t\t\t\t</div>"
						+ "\n\t\t\t\t\t\t\t\t\t\t</div>"
						+ "\n\t\t\t\t\t\t\t\t\t\t</td>"
						+ "\n\t\t\t\t\t\t\t\t\t</tr>";
			i++;
		}
		
		result += "\n\t\t\t\t\t\t\t\t</tbody>"
				+ "\n\t\t\t\t\t\t\t</table>"
				+ "\n\t\t\t\t\t\t</div>";
		
		request.setAttribute("memberList", result);
	}
}

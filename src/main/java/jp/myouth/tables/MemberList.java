package jp.myouth.tables;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.myouth.db.Events;
import jp.myouth.db.User;

public class MemberList {

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
		
		String result = new String();
		int i = 0;
		for (String string : data) {
			if (i % 3 == 0)
				result += "<tr><td><img style=\"border-radius: 50%;\" src=\"https://s3-ap-northeast-1.amazonaws.com/jp.myouth.images/"+string+"\" alt=\"\" width=\"70\"/></td>";
			else if (i % 3 == 1)
				result += "<td>" + string + "</td>";
			else
				result += "<td><a href=\"/home/" + event + "/settings/member/remove/" + string + "\"><i class=\"fa fa-close faa-pulse animated faa-hover\"></i></a></td></tr>";
			i++;
		}
		
		request.setAttribute("memberList", result);
	}
}

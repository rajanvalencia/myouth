package jp.myouth.tables;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.myouth.db.Events;

public class SearchMemberList {

	public void append(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Boolean user = (Boolean) session.getAttribute("user");
		if (!user)
			response.sendRedirect("/login");
		
		String result = new String();
		
		String event = (String) session.getAttribute("event");
		
		@SuppressWarnings("unchecked")
		ArrayList<String> searchResults = (ArrayList<String>) session.getAttribute("searchResults");
		session.setAttribute("searchResults", null);
		
		int i = 0;
		for (String string : searchResults) {
			if (i % 3 == 0)
				result += "<tr><td><span class=\"image left\"><img style=\"border-radius: 50%;\" src=\"https://s3-ap-northeast-1.amazonaws.com/jp.myouth.images/"
								+ string + "\" alt=\"\" width=\"70\"/></span></td>";
			else if (i % 3 == 1)
				result += "<td>" + string + "</td>";
			else
				result += "<td><a href=\"/home/" + event + "/settings/member/add/" + string
						+ "\"><i class=\"fa fa-user-plus faa-pulse animated faa-hover\"></i></a></td></tr>";
			i++;
			
			request.setAttribute("searchMemberList", result);
		}
	}
}

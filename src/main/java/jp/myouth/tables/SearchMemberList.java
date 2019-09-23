package jp.myouth.tables;

import java.io.IOException;

import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SearchMemberList {

	public void append(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Boolean user = (Boolean) session.getAttribute("user");
		if (!user)
			response.sendRedirect("/login");
		
		String event = (String) session.getAttribute("event");
		
		@SuppressWarnings("unchecked")
		ArrayList<String> searchResults = (ArrayList<String>) session.getAttribute("searchResults");
		session.setAttribute("searchResults", null);
		
		String result = "<div class=\"table-wrapper\">"+
				"\n\t\t\t\t\t\t\t\t\t<table>"+
				"\n\t\t\t\t\t\t\t\t\t\t<tbody>";
		int i = 0;
		for (String string : searchResults) {
			if (i % 3 == 0) {
				result += "\n\t\t\t\t\t\t\t\t\t\t\t<tr>"
						+ "\n\t\t\t\t\t\t\t\t\t\t\t\t<td>"
						+ "\n\t\t\t\t\t\t\t\t\t\t\t\t\t<img style=\"border-radius: 50%;\" src=\""+string+"\" alt=\"\" width=\"70\"/>"
						+ "\n\t\t\t\t\t\t\t\t\t\t\t\t</td>";
			}
			else if (i % 3 == 1)
				result += "\n\t\t\t\t\t\t\t\t\t\t\t\t<td>"
						+ "\n\t\t\t\t\t\t\t\t\t\t\t\t\t" + string 
						+ "\n\t\t\t\t\t\t\t\t\t\t\t\t</td>";
			else
				result += "\n\t\t\t\t\t\t\t\t\t\t\t\t<td>"
						+ "\n\t\t\t\t\t\t\t\t\t\t\t\t\t<a href=\"/home/" + event + "/member/add/" + string + "\"><i class=\"fa fa-plus\"></i></a>"
						+ "\n\t\t\t\t\t\t\t\t\t\t\t\t</td>"
						+ "\n\t\t\t\t\t\t\t\t\t\t\t</tr>";
			i++;
		}

		result += "\n\t\t\t\t\t\t\t\t\t\t</tbody>"
				+ "\n\t\t\t\t\t\t\t\t\t</table>"
				+ "\n\t\t\t\t\t\t\t\t</div>";
			
			request.setAttribute("searchMemberList", result);
	}
}

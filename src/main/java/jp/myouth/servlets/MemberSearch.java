package jp.myouth.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.myouth.db.User;

@WebServlet("/memberSearch")
public class MemberSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		Boolean user = (Boolean) session.getAttribute("user");
		if (!user)
			response.sendRedirect("/login");
		String event = (String) session.getAttribute("event");

		request.setCharacterEncoding("UTF-8");
		
		String search = request.getParameter("search");
		
		User db = new User();
		db.open();
		ArrayList<String> searchResults =  db.memberSearch(search, event); 
		db.close();
		
		session.setAttribute("searchResults", searchResults);
		
		response.sendRedirect("/home/"+event+"/member");
	}

}

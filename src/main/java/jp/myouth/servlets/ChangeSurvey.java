package jp.myouth.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.myouth.db.Events;

@WebServlet("/changeSurvey")
public class ChangeSurvey extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		Boolean user = (Boolean) session.getAttribute("user");
		if (!user)
			response.sendRedirect("/login");
		String event = (String) session.getAttribute("event");

		request.setCharacterEncoding("UTF-8");
		
		Boolean name = Boolean.valueOf(request.getParameter("name"));
		Boolean fname = Boolean.valueOf(request.getParameter("fname"));
		Boolean satisfaction = Boolean.valueOf(request.getParameter("satisfaction"));
		Boolean comments = Boolean.valueOf(request.getParameter("comments"));
		Boolean transportation = Boolean.valueOf(request.getParameter("transportation"));
		Boolean improvements = Boolean.valueOf(request.getParameter("improvements"));
		Boolean repetition = Boolean.valueOf(request.getParameter("repetition"));
		
		Events db = new Events();
		db.open();
		Boolean res = db.changeSurveyQuestion(event, name, fname, satisfaction, comments, transportation, improvements, repetition);
		db.close();
		
		if(res){
			session.setAttribute("success","");
			response.sendRedirect("/home/"+event+"/settings/survey");
		}
	}

}

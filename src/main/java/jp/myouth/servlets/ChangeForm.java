package jp.myouth.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.myouth.db.Events;

@WebServlet("/changeForm")
public class ChangeForm extends HttpServlet {
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
		Boolean gender = Boolean.valueOf(request.getParameter("gender"));
		Boolean email = Boolean.valueOf(request.getParameter("email"));
		Boolean phone = Boolean.valueOf(request.getParameter("phone"));
		Boolean birthdate = Boolean.valueOf(request.getParameter("birthdate"));
		Boolean company = Boolean.valueOf(request.getParameter("company"));
		Boolean career = Boolean.valueOf(request.getParameter("career"));
		Boolean country = Boolean.valueOf(request.getParameter("country"));
		Boolean country2 = Boolean.valueOf(request.getParameter("country2"));
		Boolean country3 = Boolean.valueOf(request.getParameter("country3"));
		Boolean address = Boolean.valueOf(request.getParameter("address"));
		Boolean allergy = Boolean.valueOf(request.getParameter("allergy"));
		Boolean way = Boolean.valueOf(request.getParameter("way"));
		Boolean confirmation = Boolean.valueOf(request.getParameter("confirmation"));
		Boolean parent_confirmation = Boolean.valueOf(request.getParameter("parent_confirmation"));
		
		Events db = new Events();
		db.open();
		Boolean res = db.changeFormQuestion(event, name, fname, gender, email, phone, birthdate, company, career, country, country2, country3, address, allergy, way, confirmation, parent_confirmation);
		db.close();
		
		if(res){
			session.setAttribute("success","");
			response.sendRedirect("/home/"+event+"/settings/form");
		}
	}

}

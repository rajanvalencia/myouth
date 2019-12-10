package jp.myouth.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.text.StringEscapeUtils;
import org.springframework.ui.ModelMap;

import jp.myouth.db.Events;

@WebServlet("/servlets/events/updateEventDetails")
public class UpdateEventDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		Boolean user = (Boolean) session.getAttribute("user");
		if (!user)
			response.sendRedirect("/login");
		String event = (String) session.getAttribute("event");

		request.setCharacterEncoding("UTF-8");
		
		String name = StringEscapeUtils.escapeHtml4(request.getParameter("name"));
		
		String place = StringEscapeUtils.escapeHtml4(request.getParameter("place"));

		String date = StringEscapeUtils.escapeHtml4(request.getParameter("date"));

		String startTime = StringEscapeUtils.escapeHtml4(request.getParameter("startTime"));
		
		String endTime = StringEscapeUtils.escapeHtml4(request.getParameter("endTime"));

		String recruitLimit = StringEscapeUtils.escapeHtml4(request.getParameter("recruitLimit"));

		String recruitmentStartDate = StringEscapeUtils.escapeHtml4(request.getParameter("recruitmentStartDate"));

		String recruitmentEndDate = StringEscapeUtils.escapeHtml4(request.getParameter("recruitmentEndDate"));
		
		String description = StringEscapeUtils.escapeHtml4(request.getParameter("description"));
		
		Events db = new Events();
		db.open();
		Boolean res = db.updateEventDetails(event, name, place, date, recruitmentStartDate, recruitmentEndDate, startTime, endTime, recruitLimit);
		Boolean res1 = db.updateEventDescription(event, description);
		db.close();
		
		if(res && res1){
			new ModelMap().addAttribute("success", "");
			response.sendRedirect("/home/"+event+"/settings/details");
		}
	}

}

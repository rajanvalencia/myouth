package jp.myouth.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.text.StringEscapeUtils;

import jp.myouth.db.Events;

@WebServlet("/changeEventDetails")
public class ChangeEventDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		Boolean user = (Boolean) session.getAttribute("user");
		if (!user)
			response.sendRedirect("/login");
		String event = (String) session.getAttribute("event");

		request.setCharacterEncoding("UTF-8");
		
		String eventName = request.getParameter("eventName");
		eventName = StringEscapeUtils.escapeHtml4(eventName);
		
		String eventPlace = request.getParameter("eventPlace");
		eventPlace = StringEscapeUtils.escapeHtml4(eventPlace);

		String eventDate = request.getParameter("year") + "-" + request.getParameter("month") + "-"
				+ request.getParameter("day");
		eventDate = StringEscapeUtils.escapeHtml4(eventDate);

		String eventTime = request.getParameter("hour") + ":" + request.getParameter("minute");
		eventTime = StringEscapeUtils.escapeHtml4(eventTime);

		String recruitNo = request.getParameter("recruitNo");
		recruitNo = StringEscapeUtils.escapeHtml4(recruitNo);

		String recruitmentStartDate = request.getParameter("startYear") + "-" + request.getParameter("startMonth")
				+ "-" + request.getParameter("startDay");
		recruitmentStartDate = StringEscapeUtils.escapeHtml4(recruitmentStartDate);

		String recruitmentEndDate = request.getParameter("endYear") + "-" + request.getParameter("endMonth") + "-"
				+ request.getParameter("endDay");
		recruitmentEndDate = StringEscapeUtils.escapeHtml4(recruitmentEndDate);
		
		String description = request.getParameter("description");
		description = StringEscapeUtils.escapeHtml4(description);
		
		Events db = new Events();
		db.open();
		Boolean res = db.changeEventDetails(event, eventName, eventPlace, eventDate, recruitmentStartDate, recruitmentEndDate, eventTime, recruitNo);
		Boolean res1 = db.updateEventDescription(event, description);
		db.close();
		
		if(res && res1){
			session.setAttribute("success","");
			response.sendRedirect("/home/"+event+"/settings/details");
		}
	}

}

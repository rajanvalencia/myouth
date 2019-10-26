package jp.myouth.servlets;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.text.StringEscapeUtils;

import jp.myouth.db.Events;

@WebServlet("/registerEvent")
public class RegisterEvent extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		
		request.setCharacterEncoding("UTF-8");
		
		String eventId = request.getParameter("eventId");
		eventId = StringEscapeUtils.escapeHtml4(eventId);
		
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
		
		Events db = new Events();
		db.open();
		Boolean res = db.registerEvent(userId, eventId, eventName, eventPlace, eventDate, recruitmentStartDate, recruitmentEndDate, eventTime, recruitNo);
		db.close();
		
		if(res)
			response.sendRedirect("/home");
		else {
			response.setContentType("text/html");
			Writer writer = response.getWriter();
			writer.write("userId: "+userId);
			writer.write("<br />eventId: "+eventId);
			writer.write("<br />eventName: "+eventName);
			writer.write("<br />eventPlace: "+eventPlace);
			writer.write("<br />eventDate: "+eventDate);
			writer.write("<br />eventTime: "+eventTime);
			writer.write("<br />recruitNo: "+recruitNo);
			writer.write("<br />recruitStartDate: "+recruitmentStartDate);
			writer.write("<br />recruitEndDate: "+recruitmentEndDate);
		}
	}
}

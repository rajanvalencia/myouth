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
import jp.myouth.security.GenerateSecureString;

@WebServlet("/servlets/events/registerEvent")
public class RegisterEvent extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		
		request.setCharacterEncoding("UTF-8");
		
		String id = StringEscapeUtils.escapeHtml4(request.getParameter("id"));
		
		String name = StringEscapeUtils.escapeHtml4(request.getParameter("name"));
		
		String place = StringEscapeUtils.escapeHtml4(request.getParameter("place"));

		String date = StringEscapeUtils.escapeHtml4(request.getParameter("date"));

		String startTime = StringEscapeUtils.escapeHtml4(request.getParameter("startTime"));
		
		String endTime = StringEscapeUtils.escapeHtml4(request.getParameter("endTime"));

		String recruitLimit =StringEscapeUtils.escapeHtml4(request.getParameter("recruitLimit"));

		String recruitmentStartDate = StringEscapeUtils.escapeHtml4(request.getParameter("recruitmentStartDate"));

		String recruitmentEndDate = StringEscapeUtils.escapeHtml4(request.getParameter("recruitmentEndDate"));
		
		String description = StringEscapeUtils.escapeHtml4(request.getParameter("description"));
		
		String templateId = new GenerateSecureString().string(80);
		
		Events db = new Events();
		db.open();
		Boolean res = db.registerEvent(userId, id, name, place, date, recruitmentStartDate, recruitmentEndDate, startTime, endTime, recruitLimit, description, templateId);
		db.close();
		
		if(res)
			response.sendRedirect("/home");
		else {
			response.setContentType("text/html");
			Writer writer = response.getWriter();
			writer.write("userId: "+userId);
			writer.write("<br />eventId: "+id);
			writer.write("<br />eventName: "+name);
			writer.write("<br />eventPlace: "+place);
			writer.write("<br />eventDate: "+date);
			writer.write("<br />startTime: "+startTime);
			writer.write("<br />endTime: "+endTime);
			writer.write("<br />recruitNo: "+recruitLimit);
			writer.write("<br />recruitStartDate: "+recruitmentStartDate);
			writer.write("<br />recruitEndDate: "+recruitmentEndDate);
		}
	}
}

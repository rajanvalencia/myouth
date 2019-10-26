package jp.myouth.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.text.StringEscapeUtils;

import jp.myouth.db.Events;

@WebServlet("/insertSurveyData")
public class InsertSurveyData extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		String event = (String) session.getAttribute("event");
		if (event == null)
			response.sendRedirect("/events");
		request.setCharacterEncoding("UTF-8");

		String name = request.getParameter("name-field");
		name = StringEscapeUtils.escapeHtml4(name);
		
		String fname = request.getParameter("fname-field");
		fname = StringEscapeUtils.escapeHtml4(fname);
		
		String satisfaction = request.getParameter("satisfaction");
		satisfaction = StringEscapeUtils.escapeHtml4(satisfaction);

		String details = request.getParameter("details");
		details = StringEscapeUtils.escapeHtml4(details);
		
		String improvement = request.getParameter("improvement");
		improvement = StringEscapeUtils.escapeHtml4(improvement);
		
		String repetition = request.getParameter("repetition");
		repetition = StringEscapeUtils.escapeHtml4(repetition);

		String train = request.getParameter("train");
		String bus = request.getParameter("bus");
		String four_wheel_vehicle = request.getParameter("four_wheel_vehicle");
		String two_wheel_vehicle = request.getParameter("two_wheel_vehicle");
		String other_transportation = request.getParameter("other_transportation");

		ArrayList<Boolean> transportation = new ArrayList<Boolean>();

		if (train != null)
			transportation.add(true);
		else
			transportation.add(false);

		if (bus != null)
			transportation.add(true);
		else
			transportation.add(false);

		if (four_wheel_vehicle != null)
			transportation.add(true);
		else
			transportation.add(false);

		if (two_wheel_vehicle != null)
			transportation.add(true);
		else
			transportation.add(false);

		if (other_transportation != null)
			transportation.add(true);
		else
			transportation.add(false);

		Events db = new Events();
		db.open();
		Boolean res = db.insertSurveyData(event, name, fname, satisfaction, details, improvement, transportation, repetition);
		db.close();

		if (res) {
			session.setAttribute("success", "");
			response.sendRedirect("/events/" + event + "/survey");
		}
	}

}

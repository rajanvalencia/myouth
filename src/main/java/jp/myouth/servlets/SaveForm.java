package jp.myouth.servlets;

import java.io.IOException;
import java.io.Writer;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.myouth.db.Questions;
import jp.myouth.utilities.StringDecoder;

@WebServlet("/saveForm")
public class SaveForm extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

		Cookie[] cookies = request.getCookies();

		String questionsOrder = getCookie(cookies, "questionsOrder");
		StringDecoder sd = new StringDecoder();
		List<String> questionIds = new ArrayList<String>(Arrays.asList(sd.decodeString(questionsOrder).split(",")));

		Questions db = new Questions();
		db.open();

		response.setCharacterEncoding("utf-8");
		Writer writer = response.getWriter();

		Boolean res = false;
		Boolean res1 = false;

		for (String questionId : questionIds) {

			res = db.deleteQuestion(questionId);

			String question = sd.decodeString(getCookie(cookies, questionId));
			String type = getCookie(cookies, questionId + "-type");
			String required = getCookie(cookies, questionId + "-required");

			res1 = db.insertQuestion(questionId, question, type, Boolean.valueOf(required));
			
			if(type.equals("pulldown") || type.equals("checkbox") || type.equals("radio")) {
				String questionOptions = getCookie(cookies, questionId + "-options-order");
				String[] optionIds = sd.decodeString(questionOptions).split(",");
				for (String optionId : optionIds) {
					db.insertOptions(questionId, sd.decodeString(getCookie(cookies, optionId)));
				}
			} else if(type.equals("date")) {
				String start = getCookie(cookies, questionId + "-date-start");
				String end = getCookie(cookies, questionId + "-date-end");
				db.insertDatePeriod(questionId, start, end);
			} else if(type.equals("time")) {
				String start = sd.decodeString(getCookie(cookies, questionId + "-time-start"));
				String end = sd.decodeString(getCookie(cookies, questionId + "-time-end"));
				db.insertTimePeriod(questionId, start, end);
			}
		}

		writer.write("<br>delete Question: " + res);
		writer.write("<br>insert Question: " + res1);
		
		db.close();

	}

	private static String getCookie(Cookie[] cookies, String cookieName) {

		String cookieValue = null;
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(cookieName)) {
				return cookie.getValue();
			}
		}

		return cookieValue;
	}
}
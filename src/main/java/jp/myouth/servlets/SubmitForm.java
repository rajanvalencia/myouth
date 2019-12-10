package jp.myouth.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import org.json.JSONObject;
import org.springframework.ui.ModelMap;

import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.myouth.db.Questions;
import jp.myouth.mailTemplates.EventJoinedConfirmationMail;
import jp.myouth.security.GenerateSecureString;

@WebServlet("/forms/submitForm")
public class SubmitForm extends HttpServlet {
	private static final long serialVersionUID = 1L;
     
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String event = request.getParameter("event");
		String templateId = request.getParameter("templateId");
		
		GenerateSecureString gen = new GenerateSecureString();
		String transactionId = gen.string(50);
		String participantId = gen.string(50);
		
		Questions db = new Questions();
		db.open();
		
		db.insertTransaction(transactionId, templateId, participantId);
		
		String name = request.getParameter("name");
		String fname = request.getParameter("fname");
		String gender = request.getParameter("gender");
		String email = request.getParameter("emailAddress");
		String birthdate = request.getParameter("birthdate");
		String privacy = request.getParameter("privacy");
		if(privacy.equals("yes")) {
			db.insertPublicOption(transactionId, true);
		} else {
			db.insertPublicOption(transactionId, false);
		}
		
		Boolean res = db.insertParticipantInfo(participantId, name, fname, gender, email, birthdate);
		
		if(db.getReviewOption(templateId)) {
			String stars = request.getParameter("stars");
			String review = request.getParameter("review");
			db.insertReview(templateId, stars, review);
		}
		
		ArrayList<String> questionIds = db.getQuestionIds(templateId);
		
		for(String questionId : questionIds) {
			String answer = request.getParameter(questionId);
			String questionType = db.getQuestionType(questionId);
			
			if(questionType.equals("checkbox")) {
				String[] options = request.getParameter(questionId).split(",");
				for(String option : options) {
					db.insertOptionTypeAnswer(transactionId, questionId, option);
				}
			} else if(questionType.equals("pulldown") || questionType.equals("radio")) {
				db.insertOptionTypeAnswer(transactionId, questionId, answer);
			} else if(questionType.equals("address")){
				db.insertAnswer(transactionId, questionId, request.getParameter(questionId+"-zip")+" "+answer);
			} else {
				db.insertAnswer(transactionId, questionId, answer);
			}
		}
		db.close();
		
		Boolean res1 = new EventJoinedConfirmationMail().template(event, fname, email);
		
		if(res&&res1) {
			request.getSession().setAttribute("success", "");
			response.sendRedirect("/events/"+event+"/forms/"+templateId);
		}
	}
}

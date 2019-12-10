package jp.myouth.ajax;

import java.io.IOException;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import jp.myouth.db.Credentials;
import jp.myouth.db.Questions;

@WebServlet("/apis/ajax/forms/getAllQuestions")
public class GetAllQuestions extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String templateId = request.getParameter("templateId");
        
        String apiKey = request.getParameter("apiKey");
        Credentials db = new Credentials();
        db.open();
        String userId = db.getUserIdByAjaxApiKey(apiKey);
        db.close();
        
        if(userId == null)
        	return ;
		
        JSONObject json = new JSONObject();
        
        Questions db1 = new Questions();
        db1.open();
        
        String title = db1.getTitle(templateId);
        json.put("title", title);
        
        String description = db1.getDescription(templateId);
        json.put("description", description);
        
        ArrayList<String> questionIds = db1.getQuestionIds(templateId);
        json.put("response", questionIds.toString());
        
        for(String questionId : questionIds) {
        
        	json.put(questionId, db1.getQuestion(questionId));
        	json.put(questionId+"-required", db1.checkRequiredOption(questionId).toString());
        	json.put("review_option", db1.getReviewOption(templateId));
        	
        	String questionType = db1.getQuestionType(questionId);
        	json.put(questionId+"-type", questionType);
        	
        	if(questionType.equals("pulldown") || questionType.equals("radio") || questionType.equals("checkbox")) {
        		ArrayList<String> options = db1.getQuestionOptions(questionId);
        		json.put(questionId+"-options", options.toString());
        		ArrayList<String> optionIds = db1.getQuestionOptionIds(questionId);
        		json.put(questionId+"-optionIds", optionIds.toString());
        	} else if(questionType.equals("date")) {
        		json.put(questionId+"-start", db1.getDateStartPeriod(questionId));
        		json.put(questionId+"-end", db1.getDateEndPeriod(questionId));
        	} else if(questionType.equals("time")) {
        		json.put(questionId+"-start", db1.getDateStartPeriod(questionId));
        		json.put(questionId+"-end", db1.getDateEndPeriod(questionId));
        	}
        }
        db1.close();
        
        response.setContentType("application/json;charset=UTF-8");   //JSON�`��, UTF-8

        PrintWriter pw = response.getWriter();
        pw.print(json.toString());
        pw.close();
	}
}

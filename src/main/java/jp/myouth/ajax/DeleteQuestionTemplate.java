package jp.myouth.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;

import jp.myouth.db.Credentials;
import jp.myouth.db.Notifications;
import jp.myouth.db.Questions;
import jp.myouth.db.User;

@WebServlet("/apis/ajax/forms/deleteTemplate")
public class DeleteQuestionTemplate extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String templateId = request.getParameter("templateId");
		String event = request.getParameter("event");
        
        String apiKey = request.getParameter("apiKey");
        Credentials db = new Credentials();
        db.open();
        String userId = db.getUserIdByAjaxApiKey(apiKey);
        db.close();
        
        if(userId == null)
        	return ;
        
        Questions db1 = new Questions();
        db1.open();
        String title = db1.getTitle(templateId);
        ArrayList<String> questionIds = db1.getQuestionIds(templateId);
        for(String questionId : questionIds) {
        	db1.deleteQuestion(questionId);
        }
        Boolean res = db1.deleteTemplate(templateId);
        db1.close();
        
        Notifications db2 = new Notifications();
        db2.open();
        db2.insertEventNotification(event, userId, "が「"+title+"」を削除しました。");
        db2.close();
        
        JSONObject json = new JSONObject();
       	json.put("res", res);

        PrintWriter pw = response.getWriter();
        pw.print(json.toString());
        pw.close();
	}
}

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

@WebServlet("/apis/ajax/forms/sortOptions")
public class SortOptions extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String event = request.getParameter("event");
		String templateId = request.getParameter("templateId");
        String questionId = request.getParameter("questionId");
        String[] optionIds = request.getParameter("optionIds").split(",");
        
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
        String question = db1.getQuestion(questionId);
        ArrayList<String> options = new ArrayList<String>();
        for(String optionId : optionIds) {
        	options.add(db1.getOption(questionId, optionId));
        }
        
        Boolean res = db1.deleteOptions(questionId);
        
        int i = 0;
        for(String optionId : optionIds) {
        	db1.insertOptions(questionId, optionId, options.get(i++));
        }
        
        db1.close();
        
        Notifications db2 = new Notifications();
        db2.open();
        db2.insertEventNotification(event, userId, "が「"+title+"」の質問「"+question+"」の選択幕の順番を入れ替えました。");
        db2.close();

        JSONObject json = new JSONObject();
        json.put("res", true);

        PrintWriter pw = response.getWriter();
        pw.print(json.toString());
        pw.close();
	}
}

package jp.myouth.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import jp.myouth.db.Credentials;
import jp.myouth.db.Notifications;
import jp.myouth.db.Questions;
import jp.myouth.db.User;

@WebServlet("/apis/ajax/forms/addQuestion")
public class AddQuestion extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String event = request.getParameter("event");
		String templateId = request.getParameter("templateId");
        String questionId = request.getParameter("questionId");
        String question = request.getParameter("question");
        String questionType = request.getParameter("questionType");
        Boolean required = Boolean.valueOf(request.getParameter("required"));
        
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
        Boolean res = db1.insertQuestion(templateId, questionId, question, questionType, required);
        db1.close();
        
        Notifications db2 = new Notifications();
        db2.open();
        db2.insertEventNotification(event, userId, "が「"+title+"」に新たな質問「"+question+"」を追加しました。");
        db2.close();
        
        Map<String, Boolean> mapMsg = new HashMap<String, Boolean>();
        mapMsg.put("res", res);

        ObjectMapper mapper = new ObjectMapper();
        String jsonStr = mapper.writeValueAsString(mapMsg);  //list, map

        response.setContentType("application/json;charset=UTF-8");   //JSON�`��, UTF-8

        PrintWriter pw = response.getWriter();
        pw.print(jsonStr);
        pw.close();
	}
}

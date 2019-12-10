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

import jp.myouth.db.Analytics;
import jp.myouth.db.Credentials;
import jp.myouth.db.Notifications;
import jp.myouth.db.Questions;
import jp.myouth.db.User;

@WebServlet("/apis/ajax/analytics/editGraphPublicOption")
public class EditGraphPublicOption extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String event = request.getParameter("event");
		String templateId = request.getParameter("templateId");
        String questionId = request.getParameter("questionId");
        Boolean option = Boolean.valueOf(request.getParameter("option"));
        
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
        db1.close();
        
        Analytics db2 = new Analytics();
        db2.open();
        Boolean res = db2.updateGraphPublicOption(templateId, questionId, option);
        db2.close();
        
        String result = new String();
        if(res) {
        	result = "公開";
        } else {
        	result = "非公開";
        }
        
        Notifications db3 = new Notifications();
        db3.open();
        db3.insertEventNotification(event, userId, "が「"+title+"」の質問「"+question+"」のグラフを"+result+"に設定しました。");
        db3.close();
        
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

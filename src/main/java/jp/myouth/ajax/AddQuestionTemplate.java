package jp.myouth.ajax;

import java.io.IOException;
import java.io.PrintWriter;
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

@WebServlet("/apis/ajax/forms/addTemplate")
public class AddQuestionTemplate extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String event = request.getParameter("event");
		String templateId = request.getParameter("templateId");
		String title = request.getParameter("title");
        
        String apiKey = request.getParameter("apiKey");
        Credentials db = new Credentials();
        db.open();
        String userId = db.getUserIdByAjaxApiKey(apiKey);
        db.close();
        
        if(userId == null)
        	return ;
        
        String templateName = "無題なてテンプレート";
        
        Questions db1 = new Questions();
        db1.open();
        Boolean res = db1.insertTemplate(event, templateId);
        Boolean res1 = db1.insertTitle(templateId, title);
        Boolean res2 = db1.insertDescription(templateId, templateName);
        db1.close();
        
        Notifications db2 = new Notifications();
        db2.open();
        db2.insertEventNotification(event, userId, "が新たな質問テンプレート「"+templateName+"」を追加しました。");
        db2.close();
        
        JSONObject json = new JSONObject();
        json.put("res", res);

        PrintWriter pw = response.getWriter();
        pw.print(json.toString());
        pw.close();
	}
}

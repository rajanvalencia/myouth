package jp.myouth.ajax;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import jp.myouth.db.Credentials;
import jp.myouth.db.Notifications;
import jp.myouth.db.Questions;

@WebServlet("/apis/ajax/formTemplates/editReviewOption")
public class EditReviewOption extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String event = request.getParameter("event");
		String templateId = request.getParameter("templateId");
		String apiKey = request.getParameter("apiKey");
		Boolean option= Boolean.valueOf(request.getParameter("option"));
		
		Credentials db = new Credentials();
		db.open();
		String userId = db.getUserIdByAjaxApiKey(apiKey);
		db.close();
		
		if(userId == null) 
			return ;
		
		Questions db1 = new Questions();
		db1.open();
		String title = db1.getTitle(templateId);
		Boolean res = db1.updateFormTemplateReviewOption(event, templateId, option);
		db1.close();
	
		String notification = new String();
		if(option)
			notification = "追加しました";
		else
			notification = "解除しました";
		
		Notifications db2 = new Notifications();
		db2.open();
		Boolean res1 = db2.insertEventNotification(event, userId, "が「"+title+"」のレビュー機能を"+notification+"。");
		db2.close();
		
		JSONObject json = new JSONObject();
        json.put("res", res&&res1);

        PrintWriter pw = response.getWriter();
        pw.print(json.toString());
        pw.close();
	}
}

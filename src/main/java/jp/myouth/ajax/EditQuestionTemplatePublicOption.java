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

@WebServlet("/apis/ajax/forms/editTemplatePublicOption")
public class EditQuestionTemplatePublicOption extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String event = request.getParameter("event");
		String templateId = request.getParameter("templateId");
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
        Boolean res = db1.updateTemplatePublicOption(templateId, option);
        db1.close();
        
        String notification = new String();
		if(option)
			notification = "公開";
		else
			notification = "非公開";
        
        Notifications db2 = new Notifications();
        db2.open();
        db2.insertEventNotification(event, userId, "が「"+title+"」を"+notification+"に設定しました。");
        db2.close();
        
        JSONObject json = new JSONObject();
        json.put("res", res);

        PrintWriter pw = response.getWriter();
        pw.print(json.toString());
        pw.close();
	}
}

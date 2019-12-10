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
import jp.myouth.db.ExistenceCheck;
import jp.myouth.db.Notifications;
import jp.myouth.db.Questions;

@WebServlet("/apis/ajax/events/checkIfIdIsAvailable")
public class CheckIfIdIsAvailable extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String eventId = request.getParameter("id");
		
		Boolean res = false;
		
		ExistenceCheck db = new ExistenceCheck();
		db.open();
		res = db.eventIdDoesntExist(eventId);
		db.close();
		
		if(eventId.equals("admin") || eventId.equals("administrator"))
			res = false;
		
		JSONObject json = new JSONObject();
        json.put("res", res);

        PrintWriter pw = response.getWriter();
        pw.print(json.toString());
        pw.close();
	}
}

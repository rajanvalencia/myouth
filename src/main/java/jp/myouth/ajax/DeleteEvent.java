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
import jp.myouth.db.Events;

@WebServlet("/apis/ajax/events/deleteEvent")
public class DeleteEvent extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String event = request.getParameter("event");
		String apiKey = request.getParameter("apiKey");

		Credentials db = new Credentials();
		db.open();
		String userId = db.getUserIdByAjaxApiKey(apiKey);
		db.close();

		if (userId == null)
			return;

		Events db1 = new Events();
		db1.open();
		Boolean res = db1.deleteEvent(event);
		db1.open();

		JSONObject json = new JSONObject();

		PrintWriter pw = response.getWriter();
		pw.print(json.toString());
		pw.close();
	}

}

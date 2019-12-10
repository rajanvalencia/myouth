package jp.myouth.ajax;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import jp.myouth.db.ExistenceCheck;

@WebServlet("/apis/ajax/users/checkIfEmailAddressIsAvailable")
public class CheckIfEmailAddressIsAvailable extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest  request, HttpServletResponse response) throws IOException {
		String emailAddress = request.getParameter("emailAddress");
		
		ExistenceCheck check = new ExistenceCheck();
 		check.open();
		Boolean res = check.emailAddressAvailable(emailAddress);
		check.close();
		
		JSONObject json = new JSONObject();
		json.put("res", res);
		
		response.setContentType("text/json");
		PrintWriter pw = response.getWriter();
		pw.write(json.toString());
		pw.close();
	}
}

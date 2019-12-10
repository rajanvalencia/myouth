package jp.myouth.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.myouth.db.Credentials;

@WebServlet("/processPasswordToken")
public class ProcessPasswordToken extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		String authToken = (String) session.getAttribute("token");
		
		Credentials db = new Credentials();
		db.open();
		String userId = db.authTokenUserId(authToken);
		db.close();
		
		session.setAttribute("userId", userId);
		
		if(userId != null)
			response.sendRedirect("/setNewPassword");
		else
			response.sendRedirect("/error404");
	}

}

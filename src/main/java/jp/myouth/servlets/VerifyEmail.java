package jp.myouth.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.myouth.db.Credentials;
import jp.myouth.db.User;

@WebServlet("/verifyEmail")
public class VerifyEmail extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String token = (String) session.getAttribute("token");
		session.removeAttribute("token");
		Credentials db = new Credentials();
		db.open();
		String userId = db.getUserIdFromToken(token);
		Boolean res = db.updateEmailAddressVerificationStatus(userId, true);
		db.close();
		
		User db1 = new User();
		db1.open();
		String emailAddress = db1.getUserEmailAddress(userId);
		db1.close();
		
		if(res) {
			session.setAttribute("emailAddress", emailAddress);
			response.sendRedirect("/emailVerification");
		} else 
			response.sendRedirect("/error404");
	}
}

package jp.myouth.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.myouth.db.User;
import jp.myouth.security.Authentication;

@WebServlet("/loginRedirect")
public class LoginRedirect extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		Authentication auth = new Authentication();
		Boolean res = auth.authenticate(email, password);
		
		HttpSession session = request.getSession();
		if (res) {
			User db = new User();
			db.open();
			String userId = db.userId(email);
			db.close();
			session.setAttribute("userId", userId);
			session.setAttribute("user", true);
			response.sendRedirect("/home");
		} else {
			session.setAttribute("failure", true);
			response.sendRedirect("/login");
		}
	}

}

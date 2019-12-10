package jp.myouth.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.ui.ModelMap;

import jp.myouth.db.User;
import jp.myouth.security.Authentication;

@WebServlet("/loginRedirect")
public class LoginRedirect extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String token = request.getParameter("token");
		Boolean res = new Authentication().authenticate(email, password);
		
		HttpSession session = request.getSession();
		String loginToken = (String) session.getAttribute("loginToken");
		
		if(!token.equals(loginToken)) {
			session.setAttribute("loginFailure", true);
			response.sendRedirect("/login");
		}
		
		if (res) {
			User db = new User();
			db.open();
			String userId = db.getUserId(email);
			db.close();
			session.setMaxInactiveInterval(6*60*60);
			session.setAttribute("userId", userId);
			session.setMaxInactiveInterval(30*60);
			session.setAttribute("user", true);
			response.sendRedirect("/home");
		} else {
			session.setAttribute("loginFailure", true);
			response.setCharacterEncoding("utf-8");
			response.sendRedirect("/login");
		}
	}
}

package jp.myouth.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.myouth.security.Authentication;

@WebServlet("/changeToNewPassword")
public class ChangeToNewPassword extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		request.setCharacterEncoding("UTF-8");
		String userId = request.getParameter("userId");
		String password = request.getParameter("password");
		Authentication auth = new Authentication();
		Boolean res = auth.changePassword(userId, password);
		
		session.setAttribute("setNewPasswordSuccess", "");
		
		if (res)
			response.sendRedirect("/setNewPassword");	
		else
			response.sendRedirect("/error404");
	}
}

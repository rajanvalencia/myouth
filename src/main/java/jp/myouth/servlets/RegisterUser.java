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

@WebServlet("/register")
public class RegisterUser extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		request.setCharacterEncoding("UTF-8");
		
		String name = request.getParameter("name");
		
		String fname = request.getParameter("fname");
		
		String phone = request.getParameter("phone");
		
		String birthdate = request.getParameter("birth-date");
		
		String email = request.getParameter("email");
		
		String password = request.getParameter("password");
		
		new Authentication().registerUser(name, fname, email, phone, birthdate, password);
		
		session.setAttribute("success", "");
		
		response.sendRedirect("/registerUser");
	}

}

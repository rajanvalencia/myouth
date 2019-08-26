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
		session.setAttribute("registerUserName", name);
		
		String fname = request.getParameter("fname");
		session.setAttribute("registerUserFname", fname);
		
		String phone = request.getParameter("phone");
		session.setAttribute("registerUserPhone", phone);
		
		String year = request.getParameter("birth-year");
		String month = request.getParameter("birth-month");
		String day = request.getParameter("birth-day");
		
		session.setAttribute("registerUserYear", year);
		session.setAttribute("registerUserMonth", month);
		session.setAttribute("registerUserDay", day);
		
		String birthdate =  year + "-" +  month + "-" + day;
		
		String email = request.getParameter("email");
		User db = new User();
		db.open();
		Boolean res = db.checkIfEmailDoesNotExist(email);
		db.close();
		
		String password = request.getParameter("password");
		Authentication auth = new Authentication();
		Boolean res1;
		if(res)
			res1 = auth.registerUser(name, fname, email, phone, birthdate, password);
		else
			res1 = false;
		
		if (res1)
			session.setAttribute("registerUserSuccess", "");
		else 
			session.setAttribute("registerUserFailure", "");
		
		response.sendRedirect("/registerUser");
	}

}

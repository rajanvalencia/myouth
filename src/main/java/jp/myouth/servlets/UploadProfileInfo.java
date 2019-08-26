package jp.myouth.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.myouth.db.User;

@WebServlet("/uploadProfileInfo")
public class UploadProfileInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(); 
		String userId = (String) session.getAttribute("userId");
		request.setCharacterEncoding("UTF-8");
		String name = request.getParameter("name");
		String word = request.getParameter("word");
		User db = new User();
		db.open();
		Boolean res = db.introduction(userId, name, word);
		db.close();
		if(res)
			response.sendRedirect("/home/profile");
	}

}

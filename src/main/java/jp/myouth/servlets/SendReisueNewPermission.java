package jp.myouth.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.myouth.security.Authentication;

@WebServlet("/sendReissuePermission")
public class SendReisueNewPermission extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		request.setCharacterEncoding("UTF-8");
		String email = request.getParameter("email");
		Authentication auth = new Authentication();
		Boolean res = auth.identify(email);
		if (res){
			session.setAttribute("reissuePermissionSuccess", "");
			response.sendRedirect("/reissuePermission");	
		}
		else {
			session.setAttribute("reissuePermissionFailure", "");	
			response.sendRedirect("/reissuePermission");
		}
	}

}

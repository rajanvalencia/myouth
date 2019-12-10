package jp.myouth.servlets;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.myouth.db.Images;

@WebServlet("/updateEventImageDescription")
public class UpdateEventImageDescription extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	protected final String BUCKET_NAME = "myouth-images";
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		
		String event = (String) session.getAttribute("event");
		String imageId = (String) session.getAttribute("imageId");
		
		request.setCharacterEncoding("UTF-8");
		String description = request.getParameter("imageDescription");
		
		Images db = new Images();
		db.open();
		Boolean res = db.updateEventImageDescription(imageId, description);
		db.close();
		
		if(res)
			response.sendRedirect("/home/"+event+"/photo-video");
		else {
			Writer writer = response.getWriter();
			response.setContentType("text/html");
			writer.write("event: "+event+"<br />imageId: "+imageId);
		}
	}
}

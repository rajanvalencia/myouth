package jp.myouth.servlets;

import jp.myouth.ai.DetectModeration;

import jp.myouth.db.Events;
import jp.myouth.db.ExistenceCheck;
import jp.myouth.db.Images;
import jp.myouth.security.GenerateSecureString;
import jp.myouth.storage.UploadObject;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

@WebServlet("/uploadEventImage")
@MultipartConfig
public class UploadEventImage extends HttpServlet {

	private static final long serialVersionUID = 1L;

	static final String BUCKET_NAME = "jp.myouth.images";

	static final String EXPLICIT_CONTENT_PHOTO = "https://s3-ap-northeast-1.amazonaws.com/jp.myouth.images/events/ExplicitContentPhoto.jpg";

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		Part file = request.getPart("eventImage");

		String contentType = file.getContentType();

		InputStream input = file.getInputStream();

		GenerateSecureString generate = new GenerateSecureString();
		String imageId = generate.string(20);
		ExistenceCheck ec = new ExistenceCheck();
		ec.open();
		
		ec.close();

		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String event = (String)  session.getAttribute("event");
		
		String path = "events/" + event + "/" + imageId + ".jpg";
		String photoUrl = "https://s3-ap-northeast-1.amazonaws.com/jp.myouth.images/" + path;

		UploadObject s3 = new UploadObject();
		s3.upload(BUCKET_NAME, path, input, contentType);
		
		Events db = new Events();
		db.open();
		int eventId = db.eventId(event);
		db.close();
		
		Images db1 = new Images();
		db1.open();
		Boolean res = db1.insertEventImage(imageId, event, photoUrl);
		
		Boolean res2 = null;
		DetectModeration moderation = new DetectModeration();
		res2 = moderation.detect(userId, path, "event_image", eventId);

		Boolean res3 = null;
		if (!res2) {
			res3 = db1.checkIfPhotoIsSuggestive(photoUrl);
			if(!res3)
				db1.updateEventImage(imageId, EXPLICIT_CONTENT_PHOTO);
			db1.close();
		} else
			res3 = true;
		
		if (res3) {
			session.setAttribute("photoUrl", photoUrl);
			session.setAttribute("imageId", imageId);
		}
		else {
			session.setAttribute("photoUrl", EXPLICIT_CONTENT_PHOTO);
			session.setAttribute("imageId", imageId);
		}

		if(res)
			response.sendRedirect("/home/"+event+"/photo-video/description");
	}
}

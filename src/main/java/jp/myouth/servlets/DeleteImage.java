package jp.myouth.servlets;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.myouth.db.Images;
import jp.myouth.storage.DeleteObject;
import jp.myouth.utilities.UrlToS3KeyName;

public class DeleteImage {
	
	static final String BUCKET_NAME = "jp.myouth.images";
	
	public void event(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		String event = (String) session.getAttribute("event");
		
		String imageId = (String) request.getAttribute("imageId");
		
		Images db = new Images();
		db.open();
		String imageUrl = db.imageUrl(imageId);
		
		UrlToS3KeyName utskn = new UrlToS3KeyName();
		String keyName = utskn.convert(imageUrl);
		
		DeleteObject del = new DeleteObject();
		Boolean res = del.file(BUCKET_NAME, keyName);
		
		
		Boolean res1 = db.deleteEventImage(imageId);
		db.close();
		
		if(res && res1)
			response.sendRedirect("/home/"+event+"/photo-video");
	} 
}

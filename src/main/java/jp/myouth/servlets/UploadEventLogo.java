package jp.myouth.servlets;

import jp.myouth.ai.DetectModeration;

import jp.myouth.db.Events;
import jp.myouth.db.ExistenceCheck;
import jp.myouth.db.Images;
import jp.myouth.security.GenerateSecureString;
import jp.myouth.storage.UploadObject;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

@WebServlet("/uploadEventLogo")
@MultipartConfig
public class UploadEventLogo extends HttpServlet {

	private static final long serialVersionUID = 1L;

	static final String BUCKET_NAME = "jp.myouth.images";

	static final String EXPLICIT_CONTENT_PHOTO = "https://s3-ap-northeast-1.amazonaws.com/jp.myouth.images/events/ExplicitContentPhoto.jpg";

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Part file = request.getPart("eventLogo");

		String contentType = file.getContentType();

		InputStream input = file.getInputStream();

		/*Resize image to 500 x 500*/
		InputStream resizedInput = resizeImage(request, input);

		GenerateSecureString generate = new GenerateSecureString();
		String fileName = generate.string(20);
		ExistenceCheck ec = new ExistenceCheck();
		ec.open();
		
		ec.close();

		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String event = (String)  session.getAttribute("event");
		
		Events db = new Events();
		db.open();
		int eventId = db.eventId(event);
		db.close();
		
		String path = "events/" + event + "/logo/" + fileName + ".jpg";
		String photoUrl = "https://s3-ap-northeast-1.amazonaws.com/jp.myouth.images/" + path;

		UploadObject s3 = new UploadObject();
		s3.upload(BUCKET_NAME, path, resizedInput, contentType);

		Boolean res = null;
		DetectModeration moderation = new DetectModeration();
		res = moderation.detect(userId, path, "event_logo", eventId);

		Boolean res1 = null;
		if (!res) {
			Images db1 = new Images();
			db1.open();
			res1 = db1.checkIfPhotoIsSuggestive(photoUrl);
			db1.close();
		} else
			res1 = true;

		if (res1)
			session.setAttribute("photoUrl", photoUrl);
		else
			session.setAttribute("photoUrl", EXPLICIT_CONTENT_PHOTO);

		session.setAttribute("postUrl", "/cropAndSaveEventLogo");
		response.sendRedirect("/home/cropImage");
	}

	private static InputStream resizeImage(HttpServletRequest request, InputStream inputStream) throws IOException {
		BufferedImage sourceImage = ImageIO.read(inputStream);
		int sourceHeight = sourceImage.getHeight();
		int sourceWidth = sourceImage.getWidth();

		int width = 500;
		int height = 500;

		if (sourceHeight > sourceWidth) /* portrait */
			height = (int) (sourceHeight * (500.0 / sourceWidth));
		else if (sourceHeight < sourceWidth) /* landscape */
			width = (int) (sourceWidth * (500.0 / sourceHeight));

		HttpSession session = request.getSession();
		session.setAttribute("height", height);
		session.setAttribute("width", width);

		Image thumbnail = sourceImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		BufferedImage bufferedThumbnail = new BufferedImage(thumbnail.getWidth(null), thumbnail.getHeight(null),
				BufferedImage.TYPE_INT_RGB);
		bufferedThumbnail.getGraphics().drawImage(thumbnail, 0, 0, null);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(bufferedThumbnail, "jpg", baos);
		return new ByteArrayInputStream(baos.toByteArray());
	}
}

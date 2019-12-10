package jp.myouth.servlets;

import jp.myouth.ai.DetectModeration;

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

@WebServlet("/uploadProfilePicture")
@MultipartConfig
public class UploadProfilePicture extends HttpServlet {

	private static final long serialVersionUID = 1L;

	static final String BUCKET_NAME = "myouth-images";

	static final String URL_PREFIX = "https://s3-ap-northeast-1.amazonaws.com/myouth-images/";
	
	static final String EXPLICIT_CONTENT_PHOTO = URL_PREFIX+"events/ExplicitContentPhoto.jpg";

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Part file = request.getPart("profilePicture");

		String contentType = file.getContentType();

		InputStream input = file.getInputStream();

		/*Resize image to 500 x 500*/
		InputStream resizedInput = resizeImage(request, input);

		GenerateSecureString generate = new GenerateSecureString();
		String fileName = generate.string(20);

		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");

		String path = "users/" + userId + "/profilePicture/" + fileName + ".jpg";
		String photoUrl = URL_PREFIX + path;

		UploadObject s3 = new UploadObject();
		s3.upload(BUCKET_NAME, path, resizedInput, contentType);

		Boolean res = null;
		DetectModeration moderation = new DetectModeration();
		res = moderation.detect(userId, path, "profile_picture", 0);

		Boolean res1 = null;
		if (!res) {
			Images db = new Images();
			db.open();
			res1 = db.checkIfPhotoIsSuggestive(photoUrl);
			db.close();
		} else
			res1 = true;

		if (res1)
			session.setAttribute("photoUrl", photoUrl);
		else
			session.setAttribute("photoUrl", EXPLICIT_CONTENT_PHOTO);

		session.setAttribute("postUrl", "/cropAndSaveProfilePicture");
		response.sendRedirect("/home/cropImage");
	}

	private static InputStream resizeImage(HttpServletRequest request, InputStream inputStream) throws IOException {
		BufferedImage sourceImage = ImageIO.read(inputStream);
		int sourceHeight = sourceImage.getHeight();
		int sourceWidth = sourceImage.getWidth();

		int width = 800;
		int height = 800;

		if (sourceHeight > sourceWidth) /* portrait */
			height = (int) (sourceHeight * ((double) height / sourceWidth));
		else if (sourceHeight < sourceWidth) /* landscape */
			width = (int) (sourceWidth * ((double) width / sourceHeight));

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

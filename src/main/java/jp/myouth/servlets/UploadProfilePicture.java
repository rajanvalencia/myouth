package jp.myouth.servlets;

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
	
		static final String BUCKET_NAME = "jp.myouth.images";

		@Override
		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			Part file = request.getPart("profilePicture");
			
			String contentType = file.getContentType();
			
			InputStream input = file.getInputStream();
			
			InputStream resizedInput = resizeImage(input, 500, 500);
			
			GenerateSecureString generate = new GenerateSecureString();
			String fileName = generate.string(20);
			
			HttpSession session = request.getSession();
			String userId = (String) session.getAttribute("userId");
			
			String path = "users/"+userId+"/profilePicture/"+fileName;
			
			UploadObject s3 = new UploadObject();
			s3.upload(BUCKET_NAME, path, resizedInput, contentType);
			
			session.setAttribute("path", path);
			
			response.sendRedirect("/home/cropProfilePicture");
		}
	
		public static InputStream resizeImage(InputStream inputStream, int width, int height) throws IOException {
		    BufferedImage sourceImage = ImageIO.read(inputStream);
		    Image thumbnail = sourceImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		    BufferedImage bufferedThumbnail = new BufferedImage(thumbnail.getWidth(null),
		            thumbnail.getHeight(null),
		            BufferedImage.TYPE_INT_RGB);
		    bufferedThumbnail.getGraphics().drawImage(thumbnail, 0, 0, null);
		    ByteArrayOutputStream baos = new ByteArrayOutputStream();
		    ImageIO.write(bufferedThumbnail, "jpeg", baos);
		    return new ByteArrayInputStream(baos.toByteArray());
		}
}

package jp.myouth.servlets;

import java.awt.image.BufferedImage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.myouth.db.Images;
import jp.myouth.security.GenerateSecureString;
import jp.myouth.storage.DeleteObject;
import jp.myouth.storage.UploadObject;
import jp.myouth.utilities.UrlToS3KeyName;

@WebServlet("/cropAndSaveProfilePicture")
@MultipartConfig
public class CropAndSaveProfilePicture extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	static final String BUCKET_NAME = "jp.myouth.images";
	
	static final String CONTENT_TYPE = "image/jpeg";
	
	static final String URL_PREFIX = "https://s3-ap-northeast-1.amazonaws.com/jp.myouth.images/";
	
	static final String EXPLICIT_CONTENT_PHOTO = "https://s3-ap-northeast-1.amazonaws.com/jp.myouth.images/events/ExplicitContentPhoto.jpg";
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//Get all the parameters which were populated by JCrop
	    int x1 = Integer.parseInt(request.getParameter("x1"));
	    int y1 = Integer.parseInt(request.getParameter("y1"));
	    int maxheight = Integer.parseInt(request.getParameter("maxh"));
	    int maxwidth = Integer.parseInt(request.getParameter("maxw"));
	    
	    
	    HttpSession session = request.getSession();
	    String userId = (String) session.getAttribute("userId");
	    
	    double height = (int) session.getAttribute("height");
	    double width = (int) session.getAttribute("width");
	    
	    if(height > width) { //portrait
	    	y1 = (int) (y1 * (height / maxheight));
	    	x1 = 0;
	    } else if(height < width) { //landscape
	    	x1 = (int) (x1 * (width / maxwidth));;
	    	y1 = 0;
	    } else { /*else if(height == width)*/ //aspect ratio 1:1
	    	x1 = 0;
	    	y1 = 0;
	    }
	    String originalImageUrl = (String) session.getAttribute("photoUrl");
	    
	    URL url = new URL(originalImageUrl);
	    
	    BufferedImage originalImage = ImageIO.read(url);
	    
	    BufferedImage croppedImage = originalImage.getSubimage(x1, y1, 800, 800);
	    
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    
	    ImageIO.write(croppedImage, "jpeg", baos);
	    
	    InputStream croppedImg = new ByteArrayInputStream(baos.toByteArray());
	    
		GenerateSecureString generate = new GenerateSecureString();
		String fileName = generate.string(20)+".jpg";
		
		
		String path = "users/"+userId+"/profilePicture/"+fileName;
		
		Images db = new Images();
		db.open();
		db.insertOrUpdateUserProfilePicture(userId, URL_PREFIX+path);
		db.close();
		
		DeleteObject del = new DeleteObject();
    	del.folder(BUCKET_NAME, "users/"+userId+"/profilePicture");

	    UploadObject s3 = new UploadObject();
	    s3.upload(BUCKET_NAME, path, croppedImg, CONTENT_TYPE);
	    
	    response.sendRedirect("/home/profile");
	}
}

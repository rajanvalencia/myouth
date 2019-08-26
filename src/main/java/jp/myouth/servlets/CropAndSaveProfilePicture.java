package jp.myouth.servlets;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import jp.myouth.ai.DetectModeration;
import jp.myouth.db.User;
import jp.myouth.security.GenerateSecureString;
import jp.myouth.storage.DeleteObject;
import jp.myouth.storage.UploadObject;

@WebServlet("/cropAndSaveProfilePicture")
@MultipartConfig
public class CropAndSaveProfilePicture extends HttpServlet {
	
	static final String BUCKET_NAME = "jp.myouth.images";
	
	static final String CONTENT_TYPE = "image/jpeg";
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//Get all the parameters which were populated by JCrop
	    int x1 = Integer.parseInt(request.getParameter("x1"));
	    int y1 = Integer.parseInt(request.getParameter("y1"));
	    int x2 = Integer.parseInt(request.getParameter("x2"));
	    int y2 = Integer.parseInt(request.getParameter("y2"));
	    int w = Integer.parseInt(request.getParameter("w"));
	    int h  = Integer.parseInt(request.getParameter("h"));
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
	    
	    URL url = new URL((String) request.getParameter("file"));
	    
	    BufferedImage img = ImageIO.read(url);
	    
	    BufferedImage output = img.getSubimage(x1, y1, 500, 500);
	    
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    
	    ImageIO.write(output, "jpeg", baos);
	    
	    InputStream croppedImg = new ByteArrayInputStream(baos.toByteArray());
	    
		GenerateSecureString generate = new GenerateSecureString();
		String fileName = generate.string(20)+".jpg";
		
		String folderName = "users/"+userId+"/profilePicture";
		String newPath = folderName+"/"+fileName;
		
		User db = new User();
		db.open();
		db.insertOrUpdateUserProfilePicture(userId, newPath);
		db.close();

	    UploadObject s3 = new UploadObject();
	    s3.upload(BUCKET_NAME, newPath, croppedImg, CONTENT_TYPE);
	    
	    response.sendRedirect("/home/profile");
	}
}

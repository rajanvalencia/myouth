package jp.myouth.servlets;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import javax.imageio.ImageIO;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 * A servlet which crops the image based on the JCrop tools parameters
 * @author SANTHOSH REDDY MANDADI
 * @version 1.0
 * @since 04th September 2009
 */
public class CropProfilePicture extends HttpServlet {
  public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    //Get all the parameters which were populated by JCrop
    int x1=Integer.parseInt(request.getParameter("x1"));
    int y1=Integer.parseInt(request.getParameter("y1"));
    int x2=Integer.parseInt(request.getParameter("x2"));
    int y2=Integer.parseInt(request.getParameter("y2"));
    int w=Integer.parseInt(request.getParameter("w"));
    int h=Integer.parseInt(request.getParameter("h"));
    System.out.println(x1+" "+y1+" "+x2+" "+y2+" "+w+" "+" "+h);
    
    //Get the file name from the server
    String file=request.getParameter("file");
    
    String serverPath="/test/";
    String sourceFile=serverPath+file;
    //Forming the dest file path with t suffix. So, if the file is a.jpg, dest file will be at.jpg
    int x = sourceFile.lastIndexOf(".");
    String destFile = serverPath+(file.substring(0, x)+"t"+file.substring(x, file.length()));
    
    //Get the buffered image reference
    BufferedImage image=ImageIO.read(new File(sourceFile));

    //Get the sub image
    BufferedImage out=image.getSubimage(x1,y1,w,h);

    //Store the image to a new file
    ImageIO.write(out,"jpg",new File(destFile));
    
    //Sending the output to the client by showing the cropped image with dimensions
    PrintWriter printer=response.getWriter();
    response.setContentType("text/html");
    printer.println("Photo cropped from "+x1+","+y1+" to the width of "+w+" and height of "+h);
    printer.println("<img src=\""+destFile+"\" />");
  }
}

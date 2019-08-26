package jp.myouth.servlets;

import java.io.*;
import java.util.*;
import java.awt.image.*;

import javax.imageio.*;

public class ImageCompression {

   public InputStream compress(InputStream input) throws IOException {
   
      BufferedImage image = ImageIO.read(input);

      Iterator<ImageWriter>writers =  ImageIO.getImageWritersByFormatName("jpg");
      ImageWriter writer = (ImageWriter) writers.next();

      ByteArrayOutputStream os = new ByteArrayOutputStream();
      writer.setOutput(os);

      ImageWriteParam param = writer.getDefaultWriteParam();
      param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
      param.setCompressionQuality(0.05f);
      writer.write(null, new IIOImage(image, null, null), param);
      
      return new ByteArrayInputStream(os.toByteArray());
   }
}

package jp.myouth.mail; 
 
import java.io.IOException; 
import java.io.InputStream;
import java.util.ArrayList; 
import java.util.Properties;

import javax.mail.internet.InternetAddress;

import com.amazonaws.auth.AWSCredentials; 
import com.amazonaws.auth.AWSStaticCredentialsProvider; 
import com.amazonaws.auth.BasicAWSCredentials; 
import com.amazonaws.regions.Regions; 
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService; 
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder; 
import com.amazonaws.services.simpleemail.model.Body; 
import com.amazonaws.services.simpleemail.model.Content; 
import com.amazonaws.services.simpleemail.model.Destination; 
import com.amazonaws.services.simpleemail.model.Message; 
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
import com.amazonaws.services.simpleemail.model.SetIdentityNotificationTopicRequest;
import com.amazonaws.services.simpleemail.model.SetIdentityNotificationTopicResult; 
 
public class Mail { 
     
  static final String CONFIGSET = "ConfigSet"; 
 
  public Boolean send(String FROM, ArrayList<String> TO, String FROMNAME, String SUBJECT, String TEXTBODY, String HTMLBODY) throws IOException { 
    try { 
        AWSCredentials credentials = credentials(); 
         
        AmazonSimpleEmailService client = 
                AmazonSimpleEmailServiceClientBuilder.standard() 
                .withRegion(Regions.US_EAST_1) 
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build(); 
        
        SendEmailRequest request = new SendEmailRequest() 
          .withDestination( new Destination().withBccAddresses(TO)) 
          .withMessage(new Message() 
              .withBody(new Body() 
                  .withHtml(new Content() 
                      .withCharset("UTF-8").withData(HTMLBODY)) 
                  .withText(new Content() 
                      .withCharset("UTF-8").withData(TEXTBODY))) 
              .withSubject(new Content() 
                  .withCharset("UTF-8").withData(SUBJECT))) 
          .withSource(senderAddressBuilder(FROM, FROMNAME)) 
          // Comment or remove the next line if you are not using a 
          // configuration set 
          .withConfigurationSetName(CONFIGSET); 
         
      client.sendEmail(request);
      System.out.println("Email sent!"); 
       
      return true; 
       
    } catch (Exception e) { 
      System.out.println("The email was not sent. Error message: "  
          + e.getMessage()); 
    } 
    return false; 
  } 
   
  public static  BasicAWSCredentials credentials() { 
 
      try (InputStream input = Mail.class.getClassLoader().getResourceAsStream("application.properties")) { 
 
          Properties prop = new Properties(); 
 
          if (input == null) { 
              System.out.println("Sorry, unable to find application.properties"); 
              return null; 
          } 
 
          //load a properties file from class path, inside static method 
          prop.load(input); 
 
          return new BasicAWSCredentials(prop.getProperty("AccessKey"), prop.getProperty("SecretAccessKey")); 
 
      } catch (IOException ex) { 
          ex.printStackTrace(); 
      } 
      return null; 
  } 
  
  private String senderAddressBuilder(String FROM, String FROMNAME) {
	  try {
		  InternetAddress address = new InternetAddress(FROM, FROMNAME);
		  return address.toString();
	  }catch(Exception  e) {
		  e.printStackTrace();
	  }
	  return null;
  }
} 
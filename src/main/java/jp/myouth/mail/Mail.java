package jp.myouth.mail;

import java.io.IOException;
import java.util.ArrayList;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.model.Region;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClient;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest; 

public class Mail {

  static final String CONFIGSET = "ConfigSet";

  public Boolean send(String FROM, ArrayList<String> TO, String FROMNAME, String SUBJECT, String TEXTBODY, String HTMLBODY) throws IOException {
	  SESVariables get = new SESVariables();
    try {
    	AWSCredentials credentials = new BasicAWSCredentials(get.accessKey(), get.SecretAccessKey());
    	AmazonSimpleEmailService client =
    			AmazonSimpleEmailServiceClientBuilder.standard()
                .withRegion(Regions.US_EAST_1)
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();

    	SendEmailRequest request = new SendEmailRequest()
          .withDestination( new Destination().withToAddresses(TO))
          .withMessage(new Message()
              .withBody(new Body()
                  .withHtml(new Content()
                      .withCharset("UTF-8").withData(HTMLBODY))
                  .withText(new Content()
                      .withCharset("UTF-8").withData(TEXTBODY)))
              .withSubject(new Content()
                  .withCharset("UTF-8").withData(SUBJECT)))
          .withSource(FROM)
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
}

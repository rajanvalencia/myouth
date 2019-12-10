package jp.myouth.ai;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.comprehend.AmazonComprehend;
import com.amazonaws.services.comprehend.AmazonComprehendClientBuilder;
import com.amazonaws.services.comprehend.model.DetectEntitiesRequest;
import com.amazonaws.services.comprehend.model.DetectEntitiesResult;

public class DetectNameEntities {
    public static void main( String[] args ) {

    	Translate translate = new Translate();
    	
        String text = translate.text("", "ja", "en");

        AmazonComprehend comprehendClient = AmazonComprehendClientBuilder
        		.standard().withRegion(Regions.US_EAST_1)
				.withCredentials(new AWSStaticCredentialsProvider(credentials())).build();
                                         
        // Call detectEntities API
        System.out.println("Calling DetectEntities");
        DetectEntitiesRequest detectEntitiesRequest = new DetectEntitiesRequest().withText(text)
                                                                                 .withLanguageCode("en");
        DetectEntitiesResult detectEntitiesResult  = comprehendClient.detectEntities(detectEntitiesRequest);
        detectEntitiesResult.getEntities().forEach(System.out::println);
        System.out.println("End of DetectEntities\n");
    }
    
    public static BasicAWSCredentials credentials() {
		try (InputStream input = DetectNameEntities.class.getClassLoader()
				.getResourceAsStream("application.properties")) {
			Properties prop = new Properties();

			if (input == null) {
				System.out.println("Sorry, unable to find application.properties");
				return null;
			}

			// load a properties file from class path, inside static method
			prop.load(input);

			return new BasicAWSCredentials(prop.getProperty("AccessKey"), prop.getProperty("SecretAccessKey"));

		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return null;
	}
}

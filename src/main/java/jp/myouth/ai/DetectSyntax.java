package jp.myouth.ai;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.comprehend.AmazonComprehend;
import com.amazonaws.services.comprehend.AmazonComprehendClientBuilder;
import com.amazonaws.services.comprehend.model.DetectSyntaxRequest;
import com.amazonaws.services.comprehend.model.DetectSyntaxResult;
 
public class DetectSyntax {
	public String detect(String text){
 
		AmazonComprehend comprehendClient = AmazonComprehendClientBuilder.standard().withRegion(Regions.US_EAST_1)
				.withCredentials(new AWSStaticCredentialsProvider(credentials())).build();
 
		// Call detectSyntax API
		System.out.println("Calling DetectSyntax");
		DetectSyntaxRequest detectSyntaxRequest = new DetectSyntaxRequest()
				.withText(text)
				.withLanguageCode("en");
		DetectSyntaxResult detectSyntaxResult = comprehendClient.detectSyntax(detectSyntaxRequest);
		detectSyntaxResult.getSyntaxTokens().forEach(System.out::println);

		return detectSyntaxResult.getSyntaxTokens().toString();
	}
	
	 public static BasicAWSCredentials credentials() {
			try (InputStream input = DetectSyntax.class.getClassLoader()
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

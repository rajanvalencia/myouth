package jp.myouth.ai;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.comprehend.AmazonComprehend;
import com.amazonaws.services.comprehend.AmazonComprehendClientBuilder;
import com.amazonaws.services.comprehend.model.DetectDominantLanguageRequest;
import com.amazonaws.services.comprehend.model.DetectDominantLanguageResult;
import com.amazonaws.services.comprehend.model.DominantLanguage;

public class DetectDominantLanguage {
	
    public List<DominantLanguage> text(String text) {

        AmazonComprehend comprehendClient = AmazonComprehendClientBuilder
        		.standard().withRegion(Regions.US_EAST_1)
				.withCredentials(new AWSStaticCredentialsProvider(credentials())).build();
                                         
        // Call detectDominantLanguage API
        System.out.println("Calling DetectDominantLanguage");
        DetectDominantLanguageRequest detectDominantLanguageRequest = new DetectDominantLanguageRequest().withText(text);
        DetectDominantLanguageResult detectDominantLanguageResult = comprehendClient.detectDominantLanguage(detectDominantLanguageRequest);
        
        return detectDominantLanguageResult.getLanguages();
    }
    
    public static BasicAWSCredentials credentials() {
		try (InputStream input = DetectDominantLanguage.class.getClassLoader()
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

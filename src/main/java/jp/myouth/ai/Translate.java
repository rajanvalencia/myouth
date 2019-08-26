package jp.myouth.ai;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.translate.AmazonTranslate;
import com.amazonaws.services.translate.AmazonTranslateClient;
import com.amazonaws.services.translate.model.TranslateTextRequest;
import com.amazonaws.services.translate.model.TranslateTextResult;
 
public class Translate {

	public String text(String text, String SourceLanguageCode, String TargetLanguageCode) {
 
        AmazonTranslate translate = AmazonTranslateClient.builder().withRegion(Regions.AP_NORTHEAST_2)
				.withCredentials(new AWSStaticCredentialsProvider(credentials())).build();
 
        TranslateTextRequest request = new TranslateTextRequest()
                .withText(text)
                .withSourceLanguageCode(SourceLanguageCode)
                .withTargetLanguageCode(TargetLanguageCode);
        TranslateTextResult result  = translate.translateText(request);
        System.out.println(result.getTranslatedText());
		
        return result.getTranslatedText();
    }
    
    public static BasicAWSCredentials credentials() {
		try (InputStream input = DetectModeration.class.getClassLoader()
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

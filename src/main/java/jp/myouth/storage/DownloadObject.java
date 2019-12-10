package jp.myouth.storage;

import java.io.BufferedReader;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

public class DownloadObject {
	
	public String download(String clientRegion, String bucketName, String key) throws IOException {
		
		S3Object fullObject = null, objectPortion = null, headerOverrideObject = null;
		try {
			AWSCredentials credentials = credentials();
			
			AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
					.withRegion(clientRegion)
					.withCredentials(new AWSStaticCredentialsProvider(credentials))
					.build();
			
			// Get an object and print its contents.
			// System.out.println("Downloading an object");
			fullObject = s3Client.getObject(new GetObjectRequest(bucketName, key));
			// System.out.println("Content-Type: " +
			// fullObject.getObjectMetadata().getContentType());
			// System.out.println("Content: ");

			BufferedReader reader = new BufferedReader(new InputStreamReader(fullObject.getObjectContent()));
			String pepper = reader.readLine();

			return pepper;
		} catch (AmazonServiceException e) {
			// The call was transmitted successfully, but Amazon S3 couldn't process
			// it, so it returned an error response.
			e.printStackTrace();
		} catch (SdkClientException e) {
			// Amazon S3 couldn't be contacted for a response, or the client
			// couldn't parse the response from Amazon S3.
			e.printStackTrace();
		} finally {
			// To ensure that the network connection doesn't remain open, close any open
			// input streams.
			if (fullObject != null) {
				fullObject.close();
			}
			if (objectPortion != null) {
				objectPortion.close();
			}
			if (headerOverrideObject != null) {
				headerOverrideObject.close();
			}
		}
		return null;
	}
	
	public static  BasicAWSCredentials credentials() {
        try (InputStream input = DownloadObject.class.getClassLoader().getResourceAsStream("application.properties")) {
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
}

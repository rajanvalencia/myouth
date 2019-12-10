package jp.myouth.storage;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.BucketAccelerateConfiguration;
import com.amazonaws.services.s3.model.BucketAccelerateStatus;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.SetBucketAccelerateConfigurationRequest;

public class UploadObject {

    public Boolean upload(String bucketName, String path, InputStream file, String contentType) throws IOException {
        String clientRegion = "ap-northeast-1";

        try {
        	AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
					.withRegion(clientRegion)
					.withCredentials(new AWSStaticCredentialsProvider(credentials()))
					.enableAccelerateMode()
					.build();
        	
        	// Enable Transfer Acceleration for the specified bucket.
            s3Client.setBucketAccelerateConfiguration(
            		new SetBucketAccelerateConfigurationRequest(bucketName,
            				new BucketAccelerateConfiguration(BucketAccelerateStatus.Enabled)));
        	
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(contentType);
            metadata.addUserMetadata("x-amz-meta-title", "someTitle");
            // Upload a file as a new object with ContentType and title specified.
            PutObjectRequest request = new PutObjectRequest(bucketName, path, file, metadata);
            s3Client.putObject(request);
            return true;
        }
        catch(AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process 
            // it, so it returned an error response.
            e.printStackTrace();
        }
        catch(SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            e.printStackTrace();
        }
		return false;
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

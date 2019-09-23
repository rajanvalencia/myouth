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
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;

public class DeleteObject {
	
	static final String CLIENT_REGION = "ap-northeast-1";
	
	public Boolean file(String bucketName, String keyName) throws IOException {
        try {
        	AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
					.withRegion(CLIENT_REGION)
					.withCredentials(new AWSStaticCredentialsProvider(credentials()))
					.build();

            	 s3Client.deleteObject(new DeleteObjectRequest(bucketName, keyName));
             
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
	
    public Boolean folder(String bucketName, String prefix) throws IOException {
        try {
        	AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
					.withRegion(CLIENT_REGION)
					.withCredentials(new AWSStaticCredentialsProvider(credentials()))
					.build();

        	 ListObjectsRequest listObjectsRequest = new ListObjectsRequest()
                     .withBucketName(bucketName)
                     .withPrefix(prefix);

             ObjectListing objectListing = s3Client.listObjects(listObjectsRequest);

             for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries())
            	 s3Client.deleteObject(bucketName, objectSummary.getKey());
                 
             if (objectListing.isTruncated()) 
            	 objectListing = s3Client.listNextBatchOfObjects(objectListing);
             
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
        try (InputStream input = DeleteObject.class.getClassLoader().getResourceAsStream("application.properties")) {
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


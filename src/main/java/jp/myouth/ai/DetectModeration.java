package jp.myouth.ai;

//Copyright 2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.
//PDX-License-Identifier: MIT-0 (For details, see https://github.com/awsdocs/amazon-rekognition-developer-guide/blob/master/LICENSE-SAMPLECODE.)
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.AmazonRekognitionException;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.rekognition.model.ModerationLabel;
import com.amazonaws.services.rekognition.model.S3Object;

import jp.myouth.db.Images;

import com.amazonaws.services.rekognition.model.DetectModerationLabelsRequest;
import com.amazonaws.services.rekognition.model.DetectModerationLabelsResult;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public class DetectModeration {

	static final String BUCKET_NAME = "jp.myouth.images";

	static final String CLIENT_REGION = "ap-northeast-1";

	static final String EXPLICIT_CONTENT_PHOTO = "users/default/ExplicitContentPhoto.jpg";

	public Boolean detect(String userId, String path, String imageType, int eventId) throws AmazonRekognitionException {

		String photoUrl = "https://s3-ap-northeast-1.amazonaws.com/jp.myouth.images/" + path;

		AmazonRekognition rekognitionClient = AmazonRekognitionClientBuilder.standard().withRegion(CLIENT_REGION)
				.withCredentials(new AWSStaticCredentialsProvider(credentials())).build();

		DetectModerationLabelsRequest request = new DetectModerationLabelsRequest()
				.withImage(new Image().withS3Object(new S3Object().withName(path).withBucket(BUCKET_NAME)));
		// Replace Attribute.ALL with Attribute.DEFAULT to get default values.

			DetectModerationLabelsResult result = rekognitionClient.detectModerationLabels(request);
			List<ModerationLabel> moderationLabels = result.getModerationLabels();

			if(moderationLabels.size() > 0) {
				Images db = new Images();
				db.open();
				String moderationName = new String();
				String moderationParentName = new String();
				float confidence = 0;
				for (ModerationLabel moderation : moderationLabels) {
					moderationName = moderation.getName();
					moderationParentName = moderation.getParentName();
					confidence = moderation.getConfidence();
					if(imageType == "profile_picture") {
						db.insertProfilePictureModerationLabel(userId, moderationName, moderationParentName,confidence);
						db.insertOrUpdateUserProfilePicture(userId, EXPLICIT_CONTENT_PHOTO);
					}
					else if(imageType == "event_logo") {
						db.insertEventLogoModerationLabel(userId, eventId, moderationName, moderationParentName,confidence);
						db.updateEventLogo(eventId, EXPLICIT_CONTENT_PHOTO);
					}
					else if(imageType == "event_image") {
						db.insertEventImageModerationLabel(userId, eventId, moderationName, moderationParentName,confidence);
					}
				}
				db.close();
				return false;
			}
			return true;
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

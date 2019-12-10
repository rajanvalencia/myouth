package jp.myouth.security;

import jp.myouth.mailTemplates.EmailVerification;
import jp.myouth.mailTemplates.SetNewPasswordMail;
import jp.myouth.db.Credentials;
import jp.myouth.db.Images;
import jp.myouth.db.User;
import jp.myouth.storage.DownloadObject;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class Authentication {
	
	private static final String CLIENT_REGION  = "ap-northeast-1";
	
	private static final String BUCKETNAME = "jp.myouth.security";
	
	private static final String KEY = "pepper/pepper1.txt";

	private static final String ALGORITHM = "PBKDF2WithHmacSHA512";
	private static final int ITERATION_COUNT = 20000;
	private static final int KEY_LENGTH = 2048;
	
	public static String getSafetyPassword(String password, String salt) {

		char[] passCharAry = password.toCharArray();
		byte[] hashedSalt = getHashedSalt(salt);

		PBEKeySpec keySpec = new PBEKeySpec(passCharAry, hashedSalt, ITERATION_COUNT, KEY_LENGTH);

		SecretKeyFactory skf;
		try {
			skf = SecretKeyFactory.getInstance(ALGORITHM);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}

		SecretKey secretKey;
		try {
			secretKey = skf.generateSecret(keySpec);
		} catch (InvalidKeySpecException e) {
			throw new RuntimeException(e);
		}
		byte[] passByteAry = secretKey.getEncoded();

		StringBuilder sb = new StringBuilder(64);
		for (byte b : passByteAry) {
			sb.append(String.format("%02x", b & 0xff));
		}
		return sb.toString();
	}

	private static byte[] getHashedSalt(String salt) {
		MessageDigest messageDigest;
		try {
			messageDigest = MessageDigest.getInstance("SHA-512");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
		messageDigest.update(salt.getBytes());
		return messageDigest.digest();
	}

	public Boolean authenticate(String email, String password) throws IOException {
		DownloadObject s3 = new DownloadObject();
		String pepper = s3.download(CLIENT_REGION, BUCKETNAME, KEY);
		
		Credentials db = new Credentials();
		db.open();
		String userId = db.userId(email);
		
		System.out.println("userId: "+userId);
		
		String salt = db.salt(userId);
		
		System.out.println("salt: "+salt);
		
		String hashedPasswordWithSalt = getSafetyPassword(password, salt);
		String hashedPasswordWithSaltAndPepper = getSafetyPassword(hashedPasswordWithSalt, pepper);
		Boolean res = db.checkPasswordExistence(hashedPasswordWithSaltAndPepper);
		Boolean res1 = db.checkIfEmailAddressVerified(userId);
		db.close();
		
		if (res && res1)
			return true;
		else
			return false;
	}

	public Boolean registerUser(String name, String fname, String email, String phone, String birthdate, String password) throws IOException {
		GenerateSecureString gen = new GenerateSecureString();
		String userId = new String();
		Boolean res = false;
		
		while(res == false) {
			userId = gen.string(11);
			User db = new User();
			db.open();
			res = db.checkIfUserIdDoesNotExist(userId);
			db.close();
		}
		
		String salt = gen.string(50);
		String token = gen.string(100);

		DownloadObject s3 = new DownloadObject();
		String pepper = s3.download(CLIENT_REGION, BUCKETNAME, KEY);
		String hashedPasswordWithSalt = Authentication.getSafetyPassword(password, salt);
		String hashedPasswordWithSaltAndPepper = Authentication.getSafetyPassword(hashedPasswordWithSalt, pepper);

		Credentials db = new Credentials();
		db.open();
		Boolean res1 = db.insertUserCredentials(userId, hashedPasswordWithSaltAndPepper, salt);
		Boolean res2 = db.insertAccountVerificationToken(userId, token);
		Boolean res3 = db.updateEmailAddressVerificationStatus(userId, false);
		db.close();

		User db1 = new User();
		db1.open();
		Boolean res4 = db1.registerUser(userId, name, fname, email, phone, birthdate);
		db1.close();
		
		Images db3 = new Images();
		db3.open();
		Boolean res5 = db3.insertOrUpdateUserProfilePicture(userId, "https://s3-ap-northeast-1.amazonaws.com/myouth-images/users/default/default_image.jpg");
		db3.close();
		EmailVerification mail = new EmailVerification();
		Boolean res6 = mail.template(name, email, token);

		if (res && res1 && res2 && res3 && res4 && res5 && res6)
			return true;
		else
			return false;
	}
	
	public Boolean identify(String email) {
		
		GenerateSecureString gen = new GenerateSecureString();
		String token = gen.string(100);
		
		Credentials db = new Credentials();
		db.open();
		Boolean res = db.checkEmail(email);
		if(res) {
			String userId = db.userId(email);
			db.insertPasswordReissueToken(token, userId);
		}
		db.close();
		
		if(res) {
			SetNewPasswordMail mail = new SetNewPasswordMail();
			Boolean res1 = mail.template(email, token);
			if(res1)
				return true;
			else
				return false;
		}
		else
			return false;
	}
	
	public Boolean changePassword(String userId, String password) {
		try {
			GenerateSecureString gen = new GenerateSecureString();
			String salt = gen.string(50);

			DownloadObject s3 = new DownloadObject();
			String pepper = s3.download(CLIENT_REGION, BUCKETNAME, KEY);
			String hashedPasswordWithSalt = Authentication.getSafetyPassword(password, salt);
			String hashedPasswordWithSaltAndPepper = Authentication.getSafetyPassword(hashedPasswordWithSalt, pepper);

			Credentials db = new Credentials();
			db.open();
			Boolean res = db.changeUserCredentials(userId, hashedPasswordWithSaltAndPepper, salt);
			db.close();
			
			if(res)
				return true;
			else 
				return false;
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
package jp.myouth.security;

import jp.myouth.mail.Templates;


import jp.myouth.db.Credentials;
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

	/**
	 * �p�X���[�h�����S�ɂ��邽�߂̃A���S���Y��
	 */
	private static final String ALGORITHM = "PBKDF2WithHmacSHA512";
	/**
	 * �X�g���b�`���O��
	 */
	private static final int ITERATION_COUNT = 20000;
	/**
	 * ��������錮�̒���
	 */
	private static final int KEY_LENGTH = 2048;

	/**
	 * �����̃p�X���[�h�ƃ\���g������S�ȃp�X���[�h�𐶐����A�ԋp���܂�
	 *
	 * @param password �����̃p�X���[�h
	 * @param salt     �\���g
	 * @return ���S�ȃp�X���[�h
	 */
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

		/*
		 * �������ꂽ�o�C�g�z���16�i���̕�����ɕϊ�
		 */
		StringBuilder sb = new StringBuilder(64);
		for (byte b : passByteAry) {
			sb.append(String.format("%02x", b & 0xff));
		}
		return sb.toString();
	}

	/**
	 * �\���g���n�b�V�������ĕԋp���܂� ���n�b�V���A���S���Y����SHA-512���g�p
	 *
	 * @param salt �\���g
	 * @return �n�b�V�������ꂽ�o�C�g�z��̃\���g
	 */
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
		String salt = db.salt(userId);
		String hashedPasswordWithSalt = Authentication.getSafetyPassword(password, salt);
		String hashedPasswordWithSaltAndPepper = Authentication.getSafetyPassword(hashedPasswordWithSalt, pepper);
		Boolean res = db.password(userId, hashedPasswordWithSaltAndPepper);
		db.close();
		
		User db1 = new User();
		db1.open();
		Boolean res1 = db1.checkVerification(userId);
		db1.close();
		
		if (res && res1)
			return true;
		else
			return false;
	}

	public Boolean registerUser(String name, String fname, String email, String phone, String birthdate, String password) throws IOException {
		GenerateSecureString gen = new GenerateSecureString();
		String userId = gen.string(11);
		String salt = gen.string(50);
		String token = gen.string(100);

		DownloadObject s3 = new DownloadObject();
		String pepper = s3.download(CLIENT_REGION, BUCKETNAME, KEY);
		String hashedPasswordWithSalt = Authentication.getSafetyPassword(password, salt);
		String hashedPasswordWithSaltAndPepper = Authentication.getSafetyPassword(hashedPasswordWithSalt, pepper);

		Credentials db = new Credentials();
		db.open();
		Boolean res = db.insertUserCredentials(userId, hashedPasswordWithSaltAndPepper, salt);
		Boolean res1 = db.insertAccountVerificationToken(userId, token);
		db.close();

		User db1 = new User();
		db1.open();
		Boolean res2 = db1.register(userId, name, fname, email, phone, birthdate);
		Boolean res3 = db1.verifyEmail(userId, false);
		Boolean res4 = db1.insertOrUpdateUserProfilePicture(userId, "users/default/profile_pic.PNG");
		db1.close();

		Templates send = new Templates();
		Boolean res5 = send.accountVerificationMail(name, email, token);

		if (res && res1 && res2 && res3 && res4 && res5)
			return true;
		else
			return false;
	}
	
	/*
	 * ���[���A�h���X�ƐN��������v�����烁�[���A�h���X�Ƀp�X���[�h�Ĕ��s���邽�߂�url�����[���ɂđ���*/
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
			Templates send = new Templates();
			Boolean res1 = send.setNewPassword(email, token);
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
	
	public static void main(String... args) throws IOException{
		DownloadObject s3 = new DownloadObject();
		String pepper = s3.download(CLIENT_REGION, BUCKETNAME, KEY);
		System.out.println(pepper);
	}
}
package jp.myouth.security;

import jp.myouth.mail.Templates;
import jp.myouth.db.Credentials;
import jp.myouth.db.User;
import jp.myouth.storage.GetObject;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class Authorization {

	/**
	 * パスワードを安全にするためのアルゴリズム
	 */
	private static final String ALGORITHM = "PBKDF2WithHmacSHA512";
	/**
	 * ストレッチング回数
	 */
	private static final int ITERATION_COUNT = 20000;
	/**
	 * 生成される鍵の長さ
	 */
	private static final int KEY_LENGTH = 2048;

	/**
	 * 平文のパスワードとソルトから安全なパスワードを生成し、返却します
	 *
	 * @param password 平文のパスワード
	 * @param salt     ソルト
	 * @return 安全なパスワード
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
		 * 生成されたバイト配列を16進数の文字列に変換
		 */
		StringBuilder sb = new StringBuilder(64);
		for (byte b : passByteAry) {
			sb.append(String.format("%02x", b & 0xff));
		}
		return sb.toString();
	}

	/**
	 * ソルトをハッシュ化して返却します ※ハッシュアルゴリズムはSHA-512を使用
	 *
	 * @param salt ソルト
	 * @return ハッシュ化されたバイト配列のソルト
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
		GetObject get = new GetObject();
		String pepper = get.pepper();
		
		Credentials db = new Credentials();
		db.open();
		String userId = db.userId(email);
		String salt = db.salt(userId);
		String hashedPasswordWithSalt = Authorization.getSafetyPassword(password, salt);
		String hashedPasswordWithSaltAndPepper = Authorization.getSafetyPassword(hashedPasswordWithSalt, pepper);
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

	public Boolean registerUser(String name, String fname, String email, String birthdate, String password) throws Exception {
		GenerateSecureString gen = new GenerateSecureString();
		String userId = gen.string(11);
		String salt = gen.string(50);

		GetObject get = new GetObject();
		String pepper = get.pepper();
		String hashedPasswordWithSalt = Authorization.getSafetyPassword(password, salt);
		String hashedPasswordWithSaltAndPepper = Authorization.getSafetyPassword(hashedPasswordWithSalt, pepper);

		Credentials db = new Credentials();
		db.open();
		Boolean res = db.insertUserCredentials(userId, hashedPasswordWithSaltAndPepper, salt);
		db.close();

		User db1 = new User();
		db1.open();
		Boolean res1 = db1.register(userId, name, fname, email, birthdate);
		Boolean res2 = db1.verifyEmail(userId, false);
		db1.close();

		Templates send = new Templates();
		Boolean res3 = send.accountVerificationMail(name, email, userId);

		if (res && res1 && res2 && res3)
			return true;
		else
			return false;
	}
}
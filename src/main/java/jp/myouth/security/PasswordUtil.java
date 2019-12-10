package jp.myouth.security;

import jp.myouth.storage.DownloadObject;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
 
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
 
public class PasswordUtil {
	
private static final String CLIENT_REGION  = "ap-northeast-1";
	
	private static final String BUCKETNAME = "jp.myouth.security";
	
	private static final String KEY = "pepper/pepper1.txt";
	
    private static final String ALGORITHM = "PBKDF2WithHmacSHA512";
    private static final int ITERATION_COUNT = 20000;
    private static final int KEY_LENGTH = 2048;
 
    public static String getSafetyPassword(String password, String saltOrPepper) throws IOException {
 
        char[] passCharAry = password.toCharArray();
        byte[] hashedSalt = getHashedSalt(saltOrPepper);
        
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
            messageDigest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        messageDigest.update(salt.getBytes());
        return messageDigest.digest();
    }   
    
    public static void main(String[] args) throws IOException { 
        GenerateSecureString gen = new GenerateSecureString(); 
        DownloadObject s3 = new DownloadObject();
        String pepper = s3.download(CLIENT_REGION, BUCKETNAME, KEY);
        String hashedPasswordWithSalt = PasswordUtil.getSafetyPassword("1234", gen.string(500)); 
        String hashedPasswordWithSaltAndPepper = PasswordUtil.getSafetyPassword(hashedPasswordWithSalt, pepper);
        System.out.println("key: "+hashedPasswordWithSaltAndPepper); 
        System.out.println("key length: "+hashedPasswordWithSaltAndPepper.length()); 
    } 
}


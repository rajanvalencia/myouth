package jp.myouth.security;

import java.security.SecureRandom;
import java.util.Random;

public class GenerateSecureString {        

    public String string(int length) {

    //minimum length of 6
    if (length < 4) {
        length = 6;
    }

    final char[] allAllowed = "abcdefghijklmnopqrstuvwxyzABCDEFGJKLMNPRSTUVWXYZ0123456789".toCharArray();

    //Use cryptographically secure random number generator
    Random random = new SecureRandom();

    StringBuilder password = new StringBuilder(); 

    for (int i = 0; i < length; i++) {
        password.append(allAllowed[random.nextInt(allAllowed.length)]);
    }

    System.out.println(password.toString());
    return password.toString();

    }

}

package cm.abimmobiledev.passman.usefull;

import android.util.Log;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class Util {

    public static boolean stringNotFilled(String  stToControl){
        return stToControl == null || stToControl.isEmpty();
    }

    private static String convertToHex(byte[] data){
        StringBuilder buf = new StringBuilder();
        for (byte b : data) {
            int halfByte = (b >>> 4) & 0x0F;
            int twoHalf = 0;
            do {
                buf.append((0 <= halfByte) && (halfByte <=9) ? (char) ('0' + halfByte) : (char) ('a'+(halfByte - 10)));
                halfByte = b & 0x0F;
            } while (twoHalf++<1);
        }
        return  buf.toString();
    }

    public static  String computeHash(String valueToHash) throws NoSuchAlgorithmException, InvalidKeySpecException {

        MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
        byte[] valueToHashInBytes = valueToHash.getBytes(StandardCharsets.UTF_8);
        messageDigest.update(valueToHashInBytes, 0, valueToHashInBytes.length);
        byte [] hashedInSha1 = messageDigest.digest();

        String hashedString = convertToHex(hashedInSha1);

        Log.d("To_Hash", "computeHash: "+valueToHash);
        Log.d("hashed", "computeHash: "+hashedString);

        return hashedString;
    }

   public static  String computeHashRec(String valueToHash) throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        Log.d("To_Hash", "computeHash: "+valueToHash);

        KeySpec spec = new PBEKeySpec(valueToHash.toCharArray(), salt, 65536, 128);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

        byte[] hash = factory.generateSecret(spec).getEncoded();

        return convertToHex(hash);
    }

}

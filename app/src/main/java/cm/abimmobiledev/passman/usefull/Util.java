package cm.abimmobiledev.passman.usefull;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import cm.abimmobiledev.passman.R;
import cm.abimmobiledev.passman.ui.passwords.AddAppFormActivity;

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
        System.out.println(hashedString);

//        Log.d("To_Hash", "computeHash: "+valueToHash);
  //      Log.d("hashed", "computeHash: "+hashedString);

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

    /**
     *  <h1>Checking password and password confirmation</h1>
     * @param pwd String password
     * @param confPwd String confirm password
     * @return true if pwd and confPwd are equals
     */
    public static boolean passwordConfirmationOk(String pwd, String confPwd) {
        return pwd != null && !pwd.isEmpty() && pwd.equals(confPwd);
    }


    public static String encrypt(String seed, String cleartext) throws Exception {
        byte[] rawKey = getRawKey(seed.getBytes());
        byte[] result = encrypt(rawKey, cleartext.getBytes());
        return convertToHex(result);
    }
    public static String decrypt(String seed, String encrypted) throws Exception {
        byte[] rawKey = getRawKey(seed.getBytes());
        byte[] enc = toByte(encrypted);
        byte[] result = decrypt(rawKey, enc);
        return new String(result);
    }
    private static byte[] getRawKey(byte[] seed) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        sr.setSeed(seed);
        kgen.init(128, sr); // 192 and 256 bits may not be available
        SecretKey skey = kgen.generateKey();
        byte[] raw = skey.getEncoded();
        return raw;
    }
    private static byte[] encrypt(byte[] raw, byte[] clear) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(clear);
        return encrypted;
    }
    private static byte[] decrypt(byte[] raw, byte[] encrypted) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        byte[] decrypted = cipher.doFinal(encrypted);
        return decrypted;
    }
    public static String toHex(String txt) {
        return convertToHex(txt.getBytes());
    }
    public static String fromHex(String hex) {
        return new String(toByte(hex));
    }
    public static byte[] toByte(String hexString) {
        int len = hexString.length()/2;
        byte[] result = new byte[len];
        for (int i = 0; i < len; i++)
            result[i] = Integer.valueOf(hexString.substring(2*i, 2*i+2), 16).byteValue();
        return result;
    }
    /*public static String toHex(byte[] buf) {
        if (buf == null)
            return "";
        StringBuffer result = new StringBuffer(2*buf.length);
        for (int i = 0; i >4)&0x0f)).append(HEX.charAt(b&0x0f));
    }*/


    public static AlertDialog.Builder getDialog(Context context, String title, String message){
        AlertDialog.Builder appDialog = new AlertDialog.Builder(context);
        appDialog.setTitle(title);
        appDialog.setMessage(message);
        appDialog.setCancelable(true);
        return appDialog;
    }

    public static ProgressDialog progressDialogInit(Context context, String title) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setTitle(title);

        return progressDialog;
    }

}

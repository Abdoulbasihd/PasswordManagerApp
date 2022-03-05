package cm.abimmobiledev.passman.usefull;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class UtilTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }



    @Test
    public void encryptDecrypt() {
        try {
            String crypted = Util.encrypt(Util.computeHash("key"), "aztrdtrsre12323545fcvb0987@jhjh");
            String decrypted = Util.decrypt(Util.computeHash("key"), crypted);
            assertEquals("aztrdtrsre12323545fcvb0987@jhjh", decrypted);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
package cm.abimmobiledev.passman.ui.user;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LoginActivityTest {

    LoginActivity loginActivity;

    @Before
    public void setUp() throws Exception {
        loginActivity = new LoginActivity();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void usernameAndPassFilled() {
        assertTrue(loginActivity.usernameAndPassFilled("username", "pass"));
    }

    @Test
    public void usernameAndPassFilled_usernameNotFilled() {
        assertFalse(loginActivity.usernameAndPassFilled("", "pass"));
    }

    @Test
    public void usernameAndPassFilled_passNotFilled() {
        assertFalse(loginActivity.usernameAndPassFilled("username", ""));
    }

    @Test
    public void usernameAndPassFilled_usernamePassNotFilled() {
        assertFalse(loginActivity.usernameAndPassFilled("", ""));
    }

    @Test
    public void usernameAndPassFilled_usernameContainsIllegalChar1() {
        assertFalse(loginActivity.usernameAndPassFilled("Bonjour \"Houbbi\" am", "pass"));
    }

    @Test
    public void usernameAndPassFilled_usernameContainsIllegalChar2Filled() {
        assertFalse(loginActivity.usernameAndPassFilled("Houbi 'am !", "pass"));
    }

    @Test
    public void usernameAndPassFilled_passContainsIllegalChar1() {
        assertFalse(loginActivity.usernameAndPassFilled("Bonjour", "pass \"nord\""));
    }

    @Test
    public void usernameAndPassFilled_passContainsIllegalChar() {
        assertFalse(loginActivity.usernameAndPassFilled("Houbi", "pass 'wrong'"));
    }


    @Test
    public void accountVerified() {
    }

    @Test
    public void databaseAccountVerifier() {
    }
}
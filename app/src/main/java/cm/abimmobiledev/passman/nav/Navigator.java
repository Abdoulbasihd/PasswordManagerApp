package cm.abimmobiledev.passman.nav;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import cm.abimmobiledev.passman.MainActivity;
import cm.abimmobiledev.passman.ui.user.LoginActivity;
import cm.abimmobiledev.passman.ui.user.SignUpActivity;

public class Navigator {

    public static final String USER_NAME_INTENT = "USER_NAME_INTENT";
    public static final String ENCRYPT_KEY_INTENT = "ENCRYPT_KEY_INTENT";

    public static void openAccountCreationPage(Activity toSignUpAct){
        Intent intent = new Intent(toSignUpAct, SignUpActivity.class);
        toSignUpAct.startActivity(intent);
        toSignUpAct.finish();
    }

    public static void openSignInPage(Activity toLoginAct){
        Intent intent = new Intent(toLoginAct, LoginActivity.class);
        toLoginAct.startActivity(intent);
        toLoginAct.finish();
    }

    public static void openMainMenuPage(Activity toMenuAct, String username, String encryptKey){

        Bundle menuBundle = new Bundle();
        menuBundle.putString(USER_NAME_INTENT, username);
        menuBundle.putString(ENCRYPT_KEY_INTENT, encryptKey);

        Intent intent = new Intent(toMenuAct, MainActivity.class);

        toMenuAct.startActivity(intent);
        toMenuAct.finish();
    }
}

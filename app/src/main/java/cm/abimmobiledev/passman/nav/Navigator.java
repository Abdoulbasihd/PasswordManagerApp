package cm.abimmobiledev.passman.nav;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import cm.abimmobiledev.passman.ui.user.LoginActivity;
import cm.abimmobiledev.passman.ui.user.SignUpActivity;

public class Navigator {

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
}

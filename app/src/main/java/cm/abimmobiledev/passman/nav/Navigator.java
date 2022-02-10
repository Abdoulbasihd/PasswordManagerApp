package cm.abimmobiledev.passman.nav;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import cm.abimmobiledev.passman.MainActivity;
import cm.abimmobiledev.passman.database.entity.User;
import cm.abimmobiledev.passman.ui.passwords.AppsListActivity;
import cm.abimmobiledev.passman.ui.user.LoginActivity;
import cm.abimmobiledev.passman.ui.user.SignUpActivity;

public class Navigator {

    public static final String USER_INTENT = "USER_INTENT";
    public static final String ENCRYPT_KEY_INTENT = "ENCRYPT_KEY_INTENT";
    public static final String USER_PASS_INTENT = "USER_PASS_INTENT";

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

    public static void openMainMenuPage(Activity toMenuAct, User user){

        Intent intent = new Intent(toMenuAct, MainActivity.class);
        intent.putExtra(USER_INTENT, user);

        toMenuAct.startActivity(intent);
        toMenuAct.finish();
    }

    public static void openAppsPage(Activity toAppsAct, User connectedUser){

        Intent intent = new Intent(toAppsAct, AppsListActivity.class);
        intent.putExtra(USER_INTENT, connectedUser);

        toAppsAct.startActivity(intent);
        toAppsAct.finish();
    }
}

package cm.abimmobiledev.passman.nav;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import cm.abimmobiledev.passman.ui.user.SignUpActivity;

public class Navigator {

    public static void openAccountCreationPage(Activity context){
        Intent intent = new Intent(context, SignUpActivity.class);
        context.startActivity(intent);
        context.finish();
    }
}

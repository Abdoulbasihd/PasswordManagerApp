package cm.abimmobiledev.passman;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import cm.abimmobiledev.passman.database.entity.User;
import cm.abimmobiledev.passman.databinding.ActivityMainBinding;
import cm.abimmobiledev.passman.nav.Navigator;

public class MainActivity extends AppCompatActivity {

    //may be next private static final String TAG_MAIN = "PM_MAIN"
    ActivityMainBinding activityMainBinding;
    private User connectedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityMainBinding = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main);
        //no model for main
        activityMainBinding.executePendingBindings();

        //initializing data with bundle
        getMainBundleElements(getIntent());

        activityMainBinding.cardMyPassword.setOnClickListener(v -> Navigator.openAppsPage(MainActivity.this, connectedUser));

        activityMainBinding.cardMyAccount.setOnClickListener(v -> {
            //TODO
        });

        activityMainBinding.cardAboutApp.setOnClickListener(v -> {
            //TODO
        });

        activityMainBinding.cardOtherSettings.setOnClickListener(v -> {
            //TODO
        });

    }

    @Override
    public void onBackPressed(){
        Navigator.openSignInPage(MainActivity.this);
    }

    public void getMainBundleElements(Intent intentBundle){

        if (intentBundle==null)
            return; // todo : throw exception here

        connectedUser = (User) intentBundle.getSerializableExtra(Navigator.USER_INTENT);

    }
}